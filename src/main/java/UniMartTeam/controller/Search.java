package UniMartTeam.controller;

import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.OrdineDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
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
import java.util.ArrayList;
import java.util.List;


@WebServlet(name= "Search", value = "/Search/*")
public class Search extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();
        switch (path)
        {
            case "/listProducts":
                listProducts(request, response);
                break;
            case "/listOrders":
                listOrders(request, response);//if not session then loginpage
                break;

            default:
                response.sendRedirect(request.getServletContext().getContextPath() + "/index.html");
        }

    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        HttpSession ssn = request.getSession();
        Utente ut = null;

        if(ssn == null)
            response.sendRedirect(request.getServletContext().getContextPath() + "/index.html");
        else
            ut = (Utente) ssn.getAttribute("utente");

        try {

            ArrayList<Ordine> list = (ArrayList<Ordine>) OrdineDAO.doRetrieveByCond(ut);

            request.setAttribute("list", list);

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/showOrders.jsp");
        dispatcher.forward(request, response);

    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            ArrayList<Prodotto> list = (ArrayList<Prodotto>) ProdottoDAO.doRetrieveByCondLimit("codiceIAN like '"+request.getParameter("searchBar")+"%' OR nome like '"+request.getParameter("searchBar")+"%'", 0, 50);

            if(list != null && list.get(0).getCodiceIAN()!=0)
                request.setAttribute("list", list);
            else
                request.setAttribute("list", null);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/searchResults.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public void destroy(){
        try {
            ConPool.deleteConnection();
        } catch (SQLException throwables) {

        }
        super.destroy();
    }

}
