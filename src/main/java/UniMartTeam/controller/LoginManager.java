package UniMartTeam.controller;

import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.UtenteDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/LoginManager/*")
public class LoginManager extends HttpServlet
{
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
      HttpSession ssn = request.getSession();
      Utente u = (Utente) ssn.getAttribute("utente");

      if (u == null)
      {
         RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/login.jsp");
         dispatcher.forward(request, response);
         return;
      }

      switch(path)
      {
         case "/":
            response.sendRedirect(request.getServletContext().getContextPath() + "/HelloServlet");
         break;

         case "/Logout":
         SessionManager.invalidateSession(request);
            response.sendRedirect(request.getServletContext().getContextPath() + getServletContext().getInitParameter("homepage"));
         break;
      }
   }

   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      Validator validator = new Validator(request);

      if(validator.assertPassword("password", "Errore formato Password") &&
         (validator.assertEmail("usernameEmail", "Formato Email non valido") || validator.assertUsername("usernameEmail", "Formato Username non valido")))
      {
         String usernameEmail = request.getParameter("usernameEmail");
         String password = request.getParameter("password");
         Utente dummy = new Utente();
         dummy.setPasswordHash(password);

         Utente fromDB = null;
         try
         {
            if(usernameEmail.contains("@"))
               fromDB = UtenteDAO.doRetrieveByCond(UtenteDAO.EMAIL, "'" + usernameEmail + "'").get(0);
            else
               fromDB = UtenteDAO.doRetrieveByCond(UtenteDAO.USERNAME, "'" + usernameEmail + "'").get(0);
         }
         catch (SQLException e)
         {
            request.setAttribute("message", "Errore nella ricerca dell'utente nel Database(Servlet:LoginManager Metodo:doPost)");
            request.setAttribute("exceptionStackTrace", e.getStackTrace());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
            return;
         }

         if (fromDB != null && fromDB.getPasswordHash().equals(dummy.getPasswordHash()))
         {
            fromDB.setPassword("");

            SessionManager sessionManager = new SessionManager(request, true);
            sessionManager.setAttribute(fromDB, "utente");

            doGet(request, response);
            return;
         }
      }

      request.setAttribute("loginFail", "Username/Email o password errata");
      doGet(request, response);
   }

   @Override
   public void destroy()
   {
      try
      {
         ConPool.deleteConnection();
      } catch (SQLException throwables)
      {
         throwables.printStackTrace();
      } finally
      {
         super.destroy();
      }
   }
}
