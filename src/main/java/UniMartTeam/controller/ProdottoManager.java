package UniMartTeam.controller;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.CategoriaDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.Validator;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProdottoManager", value = "/ProdottoManager/*")
@MultipartConfig
public class ProdottoManager extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");
        Validator validator = new Validator(request);

        if(utente != null)
        {
            if(utente.getTipo().equals(TipoUtente.Amministratore))
            {
                switch (path)
                {
                    case "/":
                        List<Prodotto> prodottoList = null;
                        validator.assertInt("size", "Error size");
                        validator.assertInt("offset", "Error offset");

                        try
                        {
                            int size = 5, offset = 0;

                            if(!validator.hasErrors())
                            {
                                size = Integer.parseInt(request.getParameter("size"));
                                offset = Integer.parseInt(request.getParameter("offset"));
                            }

                            prodottoList = ProdottoDAO.doRetrieveAll(offset, size);
                        }
                        catch (SQLException e)
                        {
                            request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:ProdottoManager Metodo:listProdotti)");
                            request.setAttribute("exceptionStackTrace", e.getStackTrace());
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                            return;
                        }

                        if (prodottoList.get(0) == null)
                            prodottoList = null;

                        request.removeAttribute("prodottoList");
                        request.setAttribute("prodottoList", prodottoList);

                        if(!validator.hasErrors())
                            request.getRequestDispatcher("/WEB-INF/results/partProdotto.jsp").forward(request, response);
                        else
                            request.getRequestDispatcher("/WEB-INF/results/prodottoPage.jsp").forward(request, response);
                    break;

                    case "/getProdotto":

                        Prodotto p = null;

                        if(validator.assertInt("codiceIAN", "Formato codice IAN non corretto"))
                        {
                            try
                            {
                                p = ProdottoDAO.doRetrieveByID(Integer.parseInt(request.getParameter("codiceIAN")));
                            }
                            catch (SQLException e)
                            {
                                request.setAttribute("message", "Errore nel get Prodotto dal Database(Servlet:ProdottoManager Metodo:doPost)");
                                request.setAttribute("exceptionStackTrace", e.getStackTrace());
                                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                                return;
                            }
                        }

                        if (p != null)
                        {
                            request.setAttribute("title", "Modifica Prodotto");
                            request.setAttribute("prodotto", p);
                            request.setAttribute("categoria", retrieveCategoria());

                            request.getRequestDispatcher("/WEB-INF/results/creaProdottoPage.jsp").forward(request, response);
                            return;
                        }
                        break;

                    case "/CreaProdotto":
                        request.setAttribute("title", "Nuovo Prodotto");
                        request.setAttribute("categoria", retrieveCategoria());

                        request.getRequestDispatcher("/WEB-INF/results/creaProdottoPage.jsp").forward(request, response);
                    break;

                }
            }
            else
            {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
            }
        }
        else
            response.sendRedirect(request.getServletContext().getContextPath() + "/LoginManager");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");
        Validator validator = new Validator(request);

        if(utente != null && !utente.getCF().isEmpty())
        {
            if (!utente.getTipo().equals(TipoUtente.Amministratore))
            {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
                return;
            }

            Prodotto p = null;
            switch (path)
            {
                case "/creaProdotto":

                    if(!validateProduct(request, validator))
                        return;

                    p = new Prodotto();

                    p.setNome(request.getParameter("nome"));
                    p.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
                    p.setPeso(Float.parseFloat(request.getParameter("peso")));
                    p.setVolumeOccupato(Float.parseFloat(request.getParameter("volumeOccupato")));
                    p.setDescrizione(request.getParameter("descrizione"));
                    p.setFoto("");
                    Categoria c = new Categoria();
                    c.setNome(request.getParameter("categoria"));
                    p.setCategoria(c);

                    try {
                        ProdottoDAO.doSave(p);
                        p.uploadFoto(request.getPart("foto"), FileServlet.basePath);
                        ProdottoDAO.doUpdate(p);
                    }
                    catch (SQLException | IOException e)
                    {
                        request.setAttribute("message", "Errore nel caricamento della foto(Servlet:CreaUtente Metodo:doPost)");
                        request.setAttribute("exceptionStackTrace", e.getStackTrace());
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                        return;
                    }

                    break;

                case "/updateProdotto":

                    if(!validateProduct(request, validator))
                        return;

                    p = new Prodotto();
                    p.setCodiceIAN(Integer.parseInt(request.getParameter("codiceIAN")));
                    p.setNome(request.getParameter("nome"));
                    p.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
                    p.setPeso(Float.parseFloat(request.getParameter("peso")));
                    p.setVolumeOccupato(Float.parseFloat(request.getParameter("volumeOccupato")));
                    p.setDescrizione(request.getParameter("descrizione"));
                    Categoria c1 = new Categoria();
                    c1.setNome(request.getParameter("categoria"));
                    p.setCategoria(c1);

                    if(!request.getPart("foto").getSubmittedFileName().isEmpty() && request.getPart("foto") != null){
                        try
                        {
                            p.uploadFoto(request.getPart("foto"), FileServlet.basePath);
                        }
                        catch (IOException e)
                        {
                            request.setAttribute("message", "Errore nel caricamento della foto(Servlet:CreaUtente Metodo:doPost)");
                            request.setAttribute("exceptionStackTrace", e.getStackTrace());
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                            return;
                        }
                    }
                    else
                    {
                        if(validator.assertInt("codiceIAN", "Formato codice IAN non corretto"))
                        {
                            int ian = Integer.parseInt(request.getParameter("codiceIAN"));
                            String name = null;
                            try
                            {
                                name = ProdottoDAO.doRetrieveByID(ian).getFoto();
                            }
                            catch (SQLException throwables)
                            {
                                throwables.printStackTrace();
                            }

                            p.setFoto(name);
                        }
                    }
                    try
                    {
                        ProdottoDAO.doUpdate(p);
                    }
                    catch (SQLException throwables)
                    {
                        throwables.printStackTrace();
                    }

                break;

                case "/deleteProdotto":
                    if(validator.assertInt("codiceIAN", "Formato codice IAN non corretto"))
                    {
                        try
                        {
                            ProdottoDAO.doDelete(Integer.parseInt(request.getParameter("codiceIAN")));
                        } catch (SQLException e)
                        {
                            request.setAttribute("message", "Errore nel caricamento della foto(Servlet:CreaUtente Metodo:doPost)");
                            request.setAttribute("exceptionStackTrace", e.getStackTrace());
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                            return;
                        }
                    }
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/ProdottoManager");
            return;
        }

        response.sendRedirect(request.getServletContext().getContextPath() + getServletContext().getInitParameter("homepage"));
    }

    private List<Categoria> retrieveCategoria()
    {
        try
        {
            return CategoriaDAO.doRetrieveAll();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return null;
    }

    private boolean validateProduct(HttpServletRequest request, Validator validator)
    {
        return validator.required(request.getParameter("nome")) && validator.required(request.getParameter("descrizione")) &&
                validator.assertDouble("prezzo", "Formato prezzo non valido") && validator.assertDouble("peso", "Formato Peso non valido") &&
                validator.assertDouble("volumeOccupato", "Formato volume non valido");
    }

    @Override
    public void destroy()
    {
        try
        {
            ConPool.deleteConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            super.destroy();
        }
    }

}
