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

      switch(path){
         case "/":
            TipoUtente tipo = u.getTipo();

            switch (tipo)
            {
               case Semplice:
                  response.sendRedirect(request.getServletContext().getContextPath() + "/OrdiniManager");
                  break;

               case Amministratore:
                  response.sendRedirect(request.getServletContext().getContextPath() + "/InventarioManager");
                  break;

               default:
                  response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
                  break;
            }
         break;

         case "/Logout":
         SessionManager.invalidateSession(request);
         response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
         break;
      }
   }

   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String usernameEmail = request.getParameter("usernameEmail");
      String password = request.getParameter("password");

      Utente dummy = new Utente();
      dummy.setPasswordHash(password);

      Utente fromDB = null;
      try
      {
         if(usernameEmail.contains("@"))
         {
            fromDB = UtenteDAO.doRetrieveByCond(UtenteDAO.EMAIL, "'" + usernameEmail + "'").get(0);
         }
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

      if (fromDB == null || !fromDB.getPasswordHash().equals(dummy.getPasswordHash()))
      {
         request.setAttribute("loginFail", new Utente());
         doGet(request, response);
         return;
      }

      fromDB.setPassword("");

      SessionManager.invalidateSession(request);
      SessionManager sessionManager = new SessionManager(request, true);
      sessionManager.setAttribute(fromDB, "utente");

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
