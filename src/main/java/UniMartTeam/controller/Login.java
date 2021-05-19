package UniMartTeam.controller;

import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.UtenteDAO;
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

@WebServlet("/Login")
public class Login extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession ssn = request.getSession();
        Utente u = (Utente) ssn.getAttribute("utente");
        if( u == null){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(request, response);
        }
        else{

            TipoUtente tipo = u.getTipo();

            switch (tipo){
                case Semplice:
                    //forward al pannello utente
                    break;
                case Amministratore:
                    //forward all'admin panel
                    break;
            }

        }

    }
    
    @Override//login
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Utente dummy = new Utente();
        dummy.setPasswordHash(password);

        Utente fromDB = UtenteDAO.doRetrieveByCond(UtenteDAO., username);

        //caso username non trovato; password errata;



    }


    @Override
    public void destroy(){
        try {
            ConPool.deleteConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            super.destroy();
        }

    }

}
