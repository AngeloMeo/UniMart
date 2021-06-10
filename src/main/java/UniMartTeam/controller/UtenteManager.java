package UniMartTeam.controller;

import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.UtenteDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UtenteManager", value = "/UtenteManager/*")
public class UtenteManager extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/UtenteManager", "");
      Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");

      if(utente != null)
      {
         if(utente.getTipo().equals(TipoUtente.Amministratore))
         {
            switch (path)
            {
               case "/":
                  List<Utente> utenteList = null;

                  try
                  {
                     utenteList = UtenteDAO.doRetrieveAll();
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:UtenteManager Metodo:listUtente)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }

                  if (utenteList != null && utenteList.get(0) != null)
                     request.setAttribute("utenteList", utenteList);
                  else
                     request.setAttribute("utenteList", null);

                  request.getRequestDispatcher("/WEB-INF/results/utentiPage.jsp").forward(request, response);
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
