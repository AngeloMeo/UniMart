package UniMartTeam.controller;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.CategoriaDAO;
import UniMartTeam.model.DAO.InventarioDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProdottoManager", value = "/ProdottoManager/*")
public class ProdottoManager extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if(session != null && utente != null)
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
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                            request.setAttribute("exceptionStackTrace", e.getMessage());
                            request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:ProdottoManager Metodo:listProdotti)");
                            request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                        }

                        if (prodottoList.get(0) == null)
                            prodottoList = null;

                        request.setAttribute("prodottoList", prodottoList);
                        request.getRequestDispatcher("/WEB-INF/results/prodottoPage.jsp").forward(request, response);
                    break;

                    case "/CreaProdotto":
                        request.setAttribute("title", "Nuovo Prodotto");
                        request.setAttribute("forward", true);
                        request.setAttribute("categoria", retriveCategoria());

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

//doPost: attento alla foto, se null significa che non c'è bisogno di aggiornare
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if(session != null && utente != null && !utente.getCF().isEmpty()) {

            if (!utente.getTipo().equals(TipoUtente.Amministratore)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
                return;
            }

            switch (path){
                case "/getProdotto":
                    Prodotto p = null;
                    try
                    {
                        p = ProdottoDAO.doRetrieveByID(Integer.parseInt(request.getParameter("codiceIAN")));
                    } catch (SQLException e)
                    {
                        request.setAttribute("exceptionStackTrace", e.getMessage());
                        request.setAttribute("message", "Errore nel eliminazione del coupon dal Database(Servlet:InventarioManager Metodo:doPost)");
                        request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                    }

                    if (p != null)
                    {
                        request.setAttribute("title", "Modifica Prodotto");
                        request.setAttribute("forward", true);
                        request.setAttribute("prodotto", p);
                        request.setAttribute("categoria", retriveCategoria());

                        request.getRequestDispatcher("/WEB-INF/results/creaProdottoPage.jsp").forward(request, response);
                        return;
                    }
                break;
            }

        }

        response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
    }

    private List<Categoria> retriveCategoria()
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
