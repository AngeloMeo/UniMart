package UniMartTeam.controller;

import UniMartTeam.model.Beans.Composto;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.OrdineDAO;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
import UniMartTeam.model.EnumForBeans.TipoUtente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LandingManager", value = "/LandingManager/*")
public class LandingManager extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/LandingManager", "");

      switch (path)
      {
         case "/":
            /*catch (SQLException e)
            {
               request.setAttribute("message", "Errore nel recupero ordini dal Database(Servlet:OrdiniManager Metodo:doGet)");
               request.setAttribute("exceptionStackTrace", e.getStackTrace());
               response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
               return;
            }*/

            break;

         default:
            response.sendRedirect(request.getServletContext().getContextPath() + "/OrdiniManager");
      }
   }
}
