package UniMartTeam.controller;

import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.Utils.ConPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet("/Search")
public class Search extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
