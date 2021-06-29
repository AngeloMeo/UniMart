package UniMartTeam.controller;

import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.OrdineDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

@WebServlet(name= "SearchManager", value = "/SearchManager/*")
public class SearchManager extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/SearchManager", "");

        switch (path)
        {
            case "/":
                String text = request.getParameter("text");
                ArrayList<String> ar = new ArrayList<>();
                Random rx = new Random();

                for(int i = 0; i < rx.nextInt(6)+1; i++)
                    ar.add(text+i);

                Gson json = new Gson();
                response.setContentType("application/JSON");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().println(json.toJson(ar));
                break;

            default:
                response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
        }
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