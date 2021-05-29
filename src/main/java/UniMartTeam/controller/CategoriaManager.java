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

@WebServlet(name = "CategoriaManager", value = "/CategoriaManager/*")
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String path = request.getPathInfo();
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if(session != null && utente != null && !utente.getCF().isEmpty())
        {
            if(!utente.getTipo().equals(TipoUtente.Amministratore))
            {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
                return;
            }
            switch (path)
            {
                case "/creaCategoria":
                {
                    Categoria categoria = null;

                    if (checkParam(request))
                    {
                        categoria = new Categoria();
                        categoria.setNome(request.getParameter("nomecat"));

                        categoria.setAliquota(Float.parseFloat(request.getParameter("ali")));

                        try
                        {
                            CategoriaDAO.doSave(categoria);
                        } catch (SQLException e)
                        {
                            request.setAttribute("exceptionStackTrace", e.getMessage());
                            request.setAttribute("message", "Errore nel salvataggio della Categoria nel Database(Servlet:CategoriaManager Metodo:doPost)");
                            request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                        }
                    }
                }
                break;

                case "/deleteCategoria":
                {
                    if (checkParam(request))
                    {
                        String catname = request.getParameter("nomecat");

                        try
                        {
                            CategoriaDAO.doDelete(catname);
                        }
                        catch (SQLException e)
                        {

                            request.setAttribute("exceptionStackTrace", e.getMessage());
                            request.setAttribute("message", "Errore nel eliminazione della categoria dal Database(Servlet:CategoriaManager Metodo:doPost)");
                            request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                        }
                    }
                }
                break;

                case "/updateCategoria":
                {
                    if (checkParam(request))
                    {
                        Categoria categoria = new Categoria();

                        categoria.setNome(request.getParameter("nomecat"));
                        categoria.setAliquota(Float.parseFloat(request.getParameter("ali")));

                        try
                        {
                            CategoriaDAO.doSaveOrUpdate(categoria);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                            request.setAttribute("exceptionStackTrace", e.getMessage());
                            request.setAttribute("message", "Errore nel update del coupon nel Database(Servlet:CategoriaManager Metodo:doPost)");
                            request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                        }
                    }
                }
                break;

            }
            response.sendRedirect(request.getContextPath() + "/CategoriaManager");
            return;
        }

        response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
    }



    private boolean checkParam(HttpServletRequest request)
    {
        return request.getParameter("nomecat") != null && request.getParameter("ali") != null;
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
            request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:CategoriaManager Metodo:listCategoria)");
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
