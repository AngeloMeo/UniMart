package UniMartTeam.controller;

import UniMartTeam.model.Beans.Composto;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.OrdineDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "OrdiniManager", value = "/OrdiniManager/*")
public class OrdiniManager extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/OrdiniManager", "");
      Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");

      if (utente != null)
      {
         switch (path)
         {
            case "/":
               List<Ordine> ordiniList = null;

               try
               {
                  if (utente.getTipo().equals(TipoUtente.Amministratore))
                     ordiniList = OrdineDAO.doRetrieveAll();
                  else if (utente.getTipo().equals(TipoUtente.Semplice))
                     ordiniList = OrdineDAO.doRetrieveByCond(utente);
                  else
                  {
                     response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
                     return;
                  }
               } catch (SQLException e)
               {
                  request.setAttribute("message", "Errore nel recupero ordini dal Database(Servlet:OrdiniManager Metodo:doGet)");
                  request.setAttribute("exceptionStackTrace", e.getStackTrace());
                  response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                  return;
               }

               if (ordiniList != null && ordiniList.get(0) != null)
                  request.setAttribute("ordiniList", ordiniList);
               else
                  request.setAttribute("ordiniList", null);

               request.getRequestDispatcher("/WEB-INF/results/showOrders.jsp").forward(request, response);
               break;

            case "/getOrdine":
               if (request.getParameter("id") != null)
               {
                  int idOrdine = Integer.parseInt(request.getParameter("id"));
                  Ordine ordine = null;

                  try
                  {
                     ordine = OrdineDAO.doRetrieveByID(idOrdine);

                     if ((utente.getTipo().equals(TipoUtente.Amministratore)) || ordine.getCliente().getCF().equalsIgnoreCase(utente.getCF()))
                     {
                        OrdineDAO.doRetiveProducts(ordine);
                     }
                     else
                     {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "L'utente non è autorizzato.");
                        return;
                     }
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("message", "Errore nel recupero ordini dal Database(Servlet:OrdiniManager Metodo:doGet)");
                     request.setAttribute("exceptionStackTrace", e.getStackTrace());
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                     return;
                  }

                  float totale = 0;

                  for(Composto c : ordine.getCompostoList())
                     totale += (c.getPrezzo() * c.getQuantita());

                  request.setAttribute("totale", totale);

                  if(ordine.getCoupon() != null)
                  {
                     totale -= ((totale * ordine.getCoupon().getSconto()) / 100);
                     request.setAttribute("totaleCoupon", totale);
                  }

                  request.removeAttribute("ordine");
                  request.setAttribute("ordine", ordine);

                  request.getRequestDispatcher("/WEB-INF/results/showOrder.jsp").forward(request, response);
               }
               break;
         }
      }
      else
         response.sendRedirect(request.getServletContext().getContextPath() + "/LoginManager");
   }

   @Override
   public void destroy()
   {
      try
      {
         ConPool.deleteConnection();
      } catch (SQLException e)
      {
         e.printStackTrace();
      } finally
      {
         super.destroy();
      }
   }
}

