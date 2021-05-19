package UniMartTeam.controller;

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


@WebServlet("/Search")
public class Search extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String searchType;

        switch(request.getParameter("type")){
            case "nome":
                searchType = "nome";
            break;

            case "ian":
                searchType = "codiceIAN";
            break;

            default:
                searchType = "nome";
        }

        try {
            request.setAttribute("list", ProdottoDAO.doRetrieveByCondLimit(searchType+" like '"+request.getParameter("searchBar")+"%'", 0, 50));

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
