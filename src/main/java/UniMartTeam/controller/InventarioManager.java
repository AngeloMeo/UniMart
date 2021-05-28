package UniMartTeam.controller;

import UniMartTeam.model.Beans.Inventario;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.InventarioDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "InventarioManager", value = "/InventarioManager/*")
public class InventarioManager extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
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
                  List<Inventario> inventarioList = null;

                  try
                  {
                     inventarioList = InventarioDAO.doRetriveAll();
                  } catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:InventarioManager Metodo:listInventario)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }


                  if (inventarioList != null && inventarioList.get(0) != null)
                     request.setAttribute("inventarioList", inventarioList);
                  else
                     request.setAttribute("inventarioList", null);

                  String ex = "/WEB-INF/results/" + "inventarioPage" + ".jsp";

                  request.getRequestDispatcher(ex).forward(request, response);
                  break;
               default:
                  System.out.println("ciao qui " + path);
                  break;
            }
         }
         else
         {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
         }
      }
      else
         response.sendRedirect(request.getServletContext().getContextPath() + "/Login");
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {

   }
}