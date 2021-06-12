package UniMartTeam.controller;

import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.UtenteDAO;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.Validator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "CreaUtente", value = "/CreaUtente")
@MultipartConfig
public class CreaUtente extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      request.getRequestDispatcher("/WEB-INF/results/creaModificaUtente.jsp").forward(request, response);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      if(request.getParameter("CF") != null && !request.getParameter("username").contains("@"))
      {
         Utente utente = new Utente();

         utente.setCF(request.getParameter("CF"));
         utente.setCognome(request.getParameter("cognome"));
         utente.setNome(request.getParameter("nome"));
         utente.setViaCivico(request.getParameter("viaCivico"));
         utente.setCitta(request.getParameter("citta"));
         utente.setTelefono(request.getParameter("telefono"));
         utente.setRegione(request.getParameter("regione"));
         utente.setEmail(request.getParameter("email"));
         utente.setUsername(request.getParameter("username"));
         utente.setPasswordHash(request.getParameter("password"));

         if(request.getParameter("dataDiNascita") != null)
            utente.setDataDiNascita(LocalDate.parse(request.getParameter("dataDiNascita")));

         try
         {
            utente.uploadFoto(request.getPart("fotoProfilo"), getServletContext().getInitParameter("uploadpath"));
         }
         catch (IOException e)
         {
            request.setAttribute("message", "Errore nel caricamento della foto(Servlet:CreaUtente Metodo:doPost)");
            request.setAttribute("exceptionStackTrace", e.getStackTrace());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
            return;
         }

         if(utente.validateObject())
         {
            try
            {
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
            request.setAttribute("utente", utente);
         }

         request.getRequestDispatcher("/WEB-INF/results/reportPage.jsp").forward(request, response);
      }
      else
         response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
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
