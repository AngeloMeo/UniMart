package UniMartTeam.controller;

import UniMartTeam.model.Beans.Inventario;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.InventarioDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;

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

                  request.getRequestDispatcher("/WEB-INF/results/inventarioPage.jsp").forward(request, response);
                  break;

               case "/creaInventario":
                  request.setAttribute("title", "Aggiunta Nuovo Inventario");
                  request.setAttribute("forward", true);
                  request.getRequestDispatcher("/WEB-INF/results/creaInventarioPage.jsp").forward(request, response);
                  break;

               default:
                  response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
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