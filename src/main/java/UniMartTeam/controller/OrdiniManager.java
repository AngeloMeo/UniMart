package UniMartTeam.controller;

import UniMartTeam.model.Beans.*;
import UniMartTeam.model.DAO.InventarioDAO;
import UniMartTeam.model.DAO.OrdineDAO;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.Validator;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "OrdiniManager", value = "/OrdiniManager/*")
public class OrdiniManager extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/OrdiniManager", "");
      Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");
      Validator validator = new Validator(request);

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
                     response.sendRedirect(request.getServletContext().getContextPath() + getServletContext().getInitParameter("homepage"));
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

               if (ordiniList != null && ordiniList.get(0) != null)
                  request.setAttribute("ordiniList", ordiniList);
               else
                  request.setAttribute("ordiniList", null);

               request.getRequestDispatcher("/WEB-INF/results/showOrders.jsp").forward(request, response);
               break;

            case "/getOrdine":
               if (validator.assertInt("id", "Formato ID non valido"))
               {
                  int idOrdine = Integer.parseInt(request.getParameter("id"));
                  Ordine ordine = null;

                  try
                  {
                     ordine = OrdineDAO.doRetrieveByID(idOrdine);

                     if ((utente.getTipo().equals(TipoUtente.Amministratore)) || ordine.getCliente().getCF().equalsIgnoreCase(utente.getCF()))
                     {
                        OrdineDAO.doRetrieveProducts(ordine);
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

            case "/deleteOrdine":
               if (validator.assertInt("id", "Formato ID non valido"))
               {
                  int id = Integer.parseInt(request.getParameter("id"));

                  try
                  {
                     if (utente.getTipo().equals(TipoUtente.Semplice))
                     {
                        Ordine ordine = OrdineDAO.doRetrieveByID(id);

                        if(ordine.getStatoOrdine().equals(StatoOrdine.Accettato))
                        {
                           ordine.setStatoOrdine(StatoOrdine.Annullato);
                           OrdineDAO.doUpdate(ordine);
                           response.getWriter().println("Ordine Eliminato");
                        }
                     }
                     response.getWriter().println("Azione non permessa");
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("message", "Errore (Servlet:OrdiniManager Metodo:doPost)");
                     request.setAttribute("exceptionStackTrace", e.getStackTrace());
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                     return;
                  }
               }
               break;

            default:
               response.sendRedirect(request.getServletContext().getContextPath() + "/OrdiniManager");
         }
      }
      else
         response.sendRedirect(request.getServletContext().getContextPath() + "/LoginManager");
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/OrdiniManager", "");
      Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");
      Validator validator = new Validator(request);

      if (utente != null)
      {
         switch (path)
         {
            case "/deleteOrdine":
               if (validator.assertInt("id", "Formato ID non valido"))
               {
                  int id = Integer.parseInt(request.getParameter("id"));

                  try
                  {
                     if (utente.getTipo().equals(TipoUtente.Semplice))
                     {
                        Ordine ordine = OrdineDAO.doRetrieveByID(id);
                        OrdineDAO.doRetrieveProducts(ordine);

                        if(ordine.getStatoOrdine().equals(StatoOrdine.Accettato) ||
                                ordine.getStatoOrdine().equals(StatoOrdine.Preparazione) ||
                                ordine.getStatoOrdine().equals(StatoOrdine.Spedito))
                        {
                           ordine.setStatoOrdine(StatoOrdine.Annullato);

                           for(Composto c : ordine.getCompostoList())
                           {
                              Possiede possiede = new Possiede();
                              possiede.setProdotto(c.getProdotto());
                              possiede.setGiacenza(c.getQuantita());

                              InventarioDAO.updateQuantitaProdottoInventario(possiede);
                           }

                           OrdineDAO.doUpdate(ordine);
                           response.getWriter().println("Ordine Eliminato");
                        }
                     }
                     else
                        response.getWriter().println("Azione non permessa");
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("message", "Errore (Servlet:OrdiniManager Metodo:doPost)");
                     request.setAttribute("exceptionStackTrace", e.getStackTrace());
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                     return;
                  }
               }
               break;

            case "/feedbackOrdine":

               if (validator.assertInt("id", "Formato ID non valido") && validator.required(request.getParameter("feedback")))
               {
                  int id = Integer.parseInt(request.getParameter("id"));

                  try
                  {
                     if (utente.getTipo().equals(TipoUtente.Semplice))
                     {
                        Ordine ordine = OrdineDAO.doRetrieveByID(id);

                        if(ordine.getStatoOrdine().equals(StatoOrdine.Consegnato) && utente.getCF().equalsIgnoreCase(ordine.getCliente().getCF()))
                        {
                           ordine.setFeedback(request.getParameter("feedback"));

                           OrdineDAO.doUpdate(ordine);
                        }
                     }

                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("message", "Errore (Servlet:OrdiniManager Metodo:doPost)");
                     request.setAttribute("exceptionStackTrace", e.getStackTrace());
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                     return;
                  }

                  response.sendRedirect(request.getServletContext().getContextPath() + "/OrdiniManager");
               }
               break;

            default:
               response.sendRedirect(request.getServletContext().getContextPath() + "/OrdiniManager");
         }
      }
      else
         response.sendRedirect(request.getServletContext().getContextPath() + "/LoginManager");
   }

   private String processPayment (String data)
   {
      try
      {
         data += LocalDate.now();
         MessageDigest digest = MessageDigest.getInstance("SHA-1");
         digest.reset();
         digest.update(data.getBytes(StandardCharsets.UTF_8));
         return String.format("%040x", new BigInteger(1, digest.digest()));
      }
      catch (NoSuchAlgorithmException e)
      {
         throw new RuntimeException(e);
      }
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

