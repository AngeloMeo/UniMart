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
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/InventarioManager", "");
      SessionManager sessionManager = new SessionManager(request);
      Utente utente = (Utente) sessionManager.getObjectFromSession("utente");

      if(utente != null)
      {
         if(utente.getTipo().equals(TipoUtente.Amministratore))
         {
            switch (path)
            {
               case "/":
                  List<Inventario> inventarioList = null;

                  if(sessionManager.getObjectFromSession("ultimoInventario") != null)
                  {
                     request.setAttribute("ultimoInventario", sessionManager.getObjectFromSession("ultimoInventario"));
                     sessionManager.removeAttribute("ultimoInventario");
                  }

                  try
                  {
                     inventarioList = InventarioDAO.doRetrieveAll();
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
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/InventarioManager", "");
      SessionManager sessionManager = new SessionManager(request);
      Utente utente = (Utente) sessionManager.getObjectFromSession("utente");

      if(utente != null && !utente.getCF().isEmpty())
      {
         if(!utente.getTipo().equals(TipoUtente.Amministratore))
         {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
            return;
         }

         switch (path)
         {
            case "/creaInventario":
            {
               Inventario inventario = new Inventario();

               inventario.setRegione(request.getParameter("regione"));
               inventario.setResponsabile(utente);
               inventario.setNote(request.getParameter("note"));
               inventario.setIndirizzo(request.getParameter("indirizzo"));
               inventario.setNome(request.getParameter("nome"));

               if (inventario.validateObject())
               {
                  try
                  {
                     inventario.setCodiceInventario(InventarioDAO.doSave(inventario));
                     sessionManager.setAttribute(inventario, "ultimoInventario");
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel salvataggio dell'inventario nel Database(Servlet:InventarioManager Metodo:doPost)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }
               }
            }
            break;

            case "/getInventario"://TODO spostiamo in get?
            {
               if (request.getParameter("codiceInventario&CF") != null)
               {
                  String[] info = request.getParameter("codiceInventario&CF").split(",");

                  if (info.length == 2)
                  {
                     String codiceInventario = info[0], cfResponsabile = info[1];

                     if (cfResponsabile.equalsIgnoreCase(utente.getCF()))
                     {
                        String cond = "='" + codiceInventario + "'";
                        Inventario inventario = null;

                        try
                        {
                           inventario = InventarioDAO.doRetrieveByCond(InventarioDAO.CODICE_INVENTARIO, cond).get(0);
                        } catch (SQLException e)
                        {
                           request.setAttribute("exceptionStackTrace", e.getMessage());
                           request.setAttribute("message", "Errore nel retrieve dell'inventario dal Database(Servlet:InventarioManager Metodo:doPost)");
                           request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                        }

                        if (inventario != null)
                        {
                           request.setAttribute("title", "Modifica Inventario");
                           request.setAttribute("forward", true);
                           request.setAttribute("inventario", inventario);
                           request.getRequestDispatcher("/WEB-INF/results/creaInventarioPage.jsp").forward(request, response);
                           return;
                        }
                     }
                  }
               }
            }
            break;

            case "/deleteInventario":
            {
               if (request.getParameter("cfResponsabile") != null && request.getParameter("cfResponsabile").equalsIgnoreCase(utente.getCF()))
               {
                  int codiceInventario = Integer.parseInt(request.getParameter("codiceInventario"));

                  try
                  {
                     InventarioDAO.doDelete(codiceInventario);
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel eliminazione del coupon dal Database(Servlet:InventarioManager Metodo:doPost)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }
               }
            }
            break;

            case "/updateInventario":
            {
               if (checkParam(request) && request.getParameter("cfResponsabile").equalsIgnoreCase(utente.getCF()))
               {
                  Inventario inventario = new Inventario();

                  inventario.setResponsabile(utente);
                  inventario.setCodiceInventario(Integer.parseInt(request.getParameter("codiceInventario")));
                  inventario.setIndirizzo(request.getParameter("indirizzo"));
                  inventario.setRegione(request.getParameter("regione"));
                  inventario.setNome(request.getParameter("nome"));
                  inventario.setNote(request.getParameter("note"));

                  try
                  {
                     InventarioDAO.doUpdate(inventario);
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel eliminazione del coupon dal Database(Servlet:InventarioManager Metodo:doPost)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }
               }
            }
            break;
         }
         response.sendRedirect(request.getContextPath() + "/InventarioManager");
         return;
      }

      response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
   }

   private boolean checkParam(HttpServletRequest request)
   {
      return request.getParameter("codiceInventario") != null && request.getParameter("cfResponsabile") != null &&
              request.getParameter("indirizzo") != null && request.getParameter("regione") != null &&
              request.getParameter("nome") != null && request.getParameter("note") != null;
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