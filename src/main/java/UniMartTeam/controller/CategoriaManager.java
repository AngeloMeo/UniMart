package UniMartTeam.controller;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.CategoriaDAO;
import UniMartTeam.model.DAO.CouponDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value = "/CategoriaManager/*")
public class CategoriaManager extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
                        listCategorie(request, response);
                        break;
                    default:
                        response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
                        break;
                }
            }
            else
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
        }
        else
            response.sendRedirect(request.getServletContext().getContextPath() + "/Login");
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{


    }

    private void listCategorie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Categoria> catList = null;

        try
        {
            catList = CategoriaDAO.doRetrieveAll();
        }
        catch (SQLException e)
        {
            request.setAttribute("exceptionStackTrace", e.getMessage());
            request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:CouponManager Metodo:listCoupon)");
            request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
        }

        if(catList != null && catList.get(0) != null)
            request.setAttribute("categoriaList", catList);
        else
            request.setAttribute("categoriaList", null);

        request.getRequestDispatcher("/WEB-INF/results/categoriaPage.jsp").forward(request, response);
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
