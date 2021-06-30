package UniMartTeam.controller;

import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.UtenteDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;
import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "UtenteManager", value = "/UtenteManager/*")
@MultipartConfig
public class UtenteManager extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/UtenteManager", "");

      if (path.equalsIgnoreCase("/creaUtente") || path.equalsIgnoreCase("/modificaProfilo"))
      {
         request.getRequestDispatcher("/WEB-INF/results/creaModificaUtente.jsp").forward(request, response);
         return;
      }

      Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");

      if(utente != null)
      {
         if(utente.getTipo().equals(TipoUtente.Amministratore))
         {
            switch (path)
            {
               case "/":
               {
                  List<Utente> utenteList = null;

                  try
                  {
                     utenteList = UtenteDAO.doRetrieveAll();
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:UtenteManager Metodo:listUtente)");
                     request.setAttribute("exceptionStackTrace", e.getStackTrace());
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                     return;
                  }

                  if (utenteList != null && utenteList.get(0) != null)
                     request.setAttribute("utenteList", utenteList);
                  else
                     request.setAttribute("utenteList", null);

                  request.getRequestDispatcher("/WEB-INF/results/utentiPage.jsp").forward(request, response);
               }
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
   protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/UtenteManager", "");
      Utente utenteSession = (Utente) SessionManager.getObjectFromSession(request, "utente");

      if((path.equalsIgnoreCase("/CreaUtente") || path.equalsIgnoreCase("/modificaProfilo")) && request.getParameter("CF") != null && !request.getParameter("username").contains("@"))
      {
         Utente utente = new Utente();

         if(path.equalsIgnoreCase("/CreaUtente"))
            utente.setCF(request.getParameter("CF"));
         else
         {
            utente.setCF(((Utente)SessionManager.getObjectFromSession(request, "utente")).getCF());
            utente.setTipo(((Utente)SessionManager.getObjectFromSession(request, "utente")).getTipo());
         }

         utente.setCognome(request.getParameter("cognome"));
         utente.setNome(request.getParameter("nome"));
         utente.setViaCivico(request.getParameter("viaCivico"));
         utente.setCitta(request.getParameter("citta"));
         utente.setTelefono(request.getParameter("telefono"));
         utente.setRegione(request.getParameter("regione"));
         utente.setEmail(request.getParameter("email"));
         utente.setUsername(request.getParameter("username"));
         utente.setPasswordHash(request.getParameter("password"));
         utente.setFotoProfilo(utenteSession.getFotoProfilo()); //Caso in cui l'utente non aggiorna la foto profilo

         if (request.getParameter("dataDiNascita") != null)
            utente.setDataDiNascita(LocalDate.parse(request.getParameter("dataDiNascita")));

         try
         {
            utente.uploadFoto(request.getPart("fotoProfilo"), getServletContext().getInitParameter("uploadpath"));
         } catch (IOException e)
         {
            request.setAttribute("message", "Errore nel caricamento della foto(Servlet:CreaUtente Metodo:doPost)");
            request.setAttribute("exceptionStackTrace", e.getStackTrace());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
            return;
         }

         if (utente.validateObject())
         {
            try
            {
               if(path.equalsIgnoreCase("/modificaProfilo"))
                  UtenteDAO.doUpdate(utente);
               else
                  UtenteDAO.doSave(utente);
            }
            catch (SQLException e)
            {
               request.setAttribute("message", "Errore nel salvataggio dell'utente nel Database(Servlet:CreaUtente Metodo:doPost)");
               request.setAttribute("exceptionStackTrace", e.getStackTrace());
               response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
               return;
            }

            utente.setPasswordHash("");

            SessionManager.invalidateSession(request);
            SessionManager sessionManager = new SessionManager(request);
            sessionManager.setAttribute(utente, "utente");

            request.getRequestDispatcher("/WEB-INF/results/reportPage.jsp").forward(request, response);
            return;
         }
      }

      if (utenteSession != null)
      {
         switch (path)
         {
            case "/modificaTipo":
            {
               if (utenteSession.getTipo().equals(TipoUtente.Amministratore))
               {
                  String cfUtente = request.getParameter("cfUtente");

                  if (cfUtente != null)
                  {
                     Utente utenteRequest = new Utente();
                     utenteRequest.setCF(cfUtente);

                     try
                     {
                        utenteRequest = UtenteDAO.doRetrieveByCond(UtenteDAO.CF, "'" + utenteRequest.getCF() + "'").get(0);

                        if (utenteRequest.getTipo().equals(TipoUtente.Semplice))
                           utenteRequest.setTipo(TipoUtente.Amministratore);
                        else if (utenteRequest.getTipo().equals(TipoUtente.Amministratore))
                           utenteRequest.setTipo(TipoUtente.Semplice);

                        UtenteDAO.doUpdate(utenteRequest);
                     } catch (SQLException e)
                     {
                        request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:UtenteManager Metodo:modificaTipo)");
                        request.setAttribute("exceptionStackTrace", e.getStackTrace());
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                        return;
                     }

                     Gson json = new Gson();
                     response.setContentType("application/JSON");
                     response.setCharacterEncoding("UTF-8");
                     response.getWriter().println(json.toJson(utenteRequest));
                  }
               }
               else
                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
            }
            break;

            default:
               response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
               break;
         }
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
