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
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/UtenteManager", "");
      Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");

      if(utente != null)
      {
         if(utente.getTipo().equals(TipoUtente.Amministratore))
         {
            switch (path)
            {
               case "/modificaTipo":
               {
                  String cfUtente = request.getParameter("cfUtente");

                  if(cfUtente != null)
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
                     }
                     catch (SQLException e)
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
