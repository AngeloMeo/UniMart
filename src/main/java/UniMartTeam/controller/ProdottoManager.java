package UniMartTeam.controller;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.CategoriaDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;

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
public class ProdottoManager extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");

        if(utente != null)
        {
            if(utente.getTipo().equals(TipoUtente.Amministratore))
            {
                switch (path)
                {
                    case "/":
                        List<Prodotto> prodottoList = null;
                        try
                        {
                             prodottoList = ProdottoDAO.doRetrieveAll();
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

                        request.setAttribute("prodottoList", prodottoList);
                        request.getRequestDispatcher("/WEB-INF/results/prodottoPage.jsp").forward(request, response);
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
            response.sendRedirect(request.getServletContext().getContextPath() + "/Login");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");

        if(utente != null && !utente.getCF().isEmpty()) {

            if (!utente.getTipo().equals(TipoUtente.Amministratore)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
                return;
            }
            Prodotto p = null;
            switch (path){

                case "/getProdotto":

                    try
                    {
                        p = ProdottoDAO.doRetrieveByID(Integer.parseInt(request.getParameter("codiceIAN")));
                    } catch (SQLException e)
                    {
                        request.setAttribute("message", "Errore nel eliminazione del coupon dal Database(Servlet:InventarioManager Metodo:doPost)");
                        request.setAttribute("exceptionStackTrace", e.getStackTrace());
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                        return;
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

                case "/creaProdotto":

                    if(convert(request.getParameter("prezzo")) == -0.1F || convert(request.getParameter("peso")) == -0.1F || convert(request.getParameter("volumeOccupato")) == -0.1F)
                        return;

                    p = new Prodotto();

                    p.setNome(request.getParameter("nome"));
                    p.setPrezzo(convert(request.getParameter("prezzo")));
                    p.setPeso(convert(request.getParameter("peso")));
                    p.setVolumeOccupato(convert(request.getParameter("volumeOccupato")));
                    p.setDescrizione(request.getParameter("descrizione"));
                    p.setFoto("");
                    Categoria c = new Categoria();
                    c.setNome(request.getParameter("categoria"));
                    p.setCategoria(c);

                    try {
                        ProdottoDAO.doSave(p);
                        p.uploadFoto(request.getPart("foto"), getServletContext().getInitParameter("uploadpath"));
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

                    if(convert(request.getParameter("prezzo")) == -0.1F || convert(request.getParameter("peso")) == -0.1F || convert(request.getParameter("volumeOccupato")) == -0.1F)
                        return;

                    p = new Prodotto();
                    p.setCodiceIAN(Integer.parseInt(request.getParameter("codiceIAN")));
                    p.setNome(request.getParameter("nome"));
                    p.setPrezzo(convert(request.getParameter("prezzo")));
                    p.setPeso(convert(request.getParameter("peso")));
                    p.setVolumeOccupato(convert(request.getParameter("volumeOccupato")));
                    p.setDescrizione(request.getParameter("descrizione"));
                    Categoria c1 = new Categoria();
                    c1.setNome(request.getParameter("categoria"));
                    p.setCategoria(c1);

                    if(!request.getPart("foto").getSubmittedFileName().isEmpty() && request.getPart("foto") != null){
                        try
                        {
                            p.uploadFoto(request.getPart("foto"), getServletContext().getInitParameter("uploadpath"));
                        }
                        catch (IOException e)
                        {
                            request.setAttribute("message", "Errore nel caricamento della foto(Servlet:CreaUtente Metodo:doPost)");
                            request.setAttribute("exceptionStackTrace", e.getStackTrace());
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                            return;
                        }
                    }
                    else{
                        int ian = Integer.parseInt(request.getParameter("codiceIAN"));
                        String name = null;
                        try {
                            name = ProdottoDAO.doRetrieveByID(ian).getFoto();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        p.setFoto(name);
                    }
                    try {
                        ProdottoDAO.doUpdate(p);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                break;
                case "/deleteProdotto":

                    try {
                        ProdottoDAO.doDelete(Integer.parseInt(request.getParameter("codiceIAN")));
                    } catch (SQLException e) {
                        request.setAttribute("message", "Errore nel caricamento della foto(Servlet:CreaUtente Metodo:doPost)");
                        request.setAttribute("exceptionStackTrace", e.getStackTrace());
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                        return;
                    }

                    break;
            }
            response.sendRedirect(request.getContextPath() + "/ProdottoManager");
            return;
        }

        response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
    }

    private float convert(String c){
        try {
            return Float.parseFloat(c);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return -0.1F;
        }
    }

    private List<Categoria> retrieveCategoria()
    {
        try {
            return CategoriaDAO.doRetrieveAll();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
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
