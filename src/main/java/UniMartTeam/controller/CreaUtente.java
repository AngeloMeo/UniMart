package UniMartTeam.controller;

import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.UtenteDAO;
import UniMartTeam.model.Utils.ConPool;

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
      request.getRequestDispatcher("/WEB-INF/results/creaUtente.jsp").forward(request, response);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      if(request.getParameter("CF") != null)
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
         utente.setToken(request.getParameter("token"));
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
            request.setAttribute("exceptionStackTrace", e.getMessage());
            request.setAttribute("message", "Errore nel caricamento della foto(Servlet:CreaUtente Metodo:doPost)");
            request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
         }

         if(utente.validateObject(utente))
         {

            try
            {
               UtenteDAO.doSave(utente);
            }
            catch (SQLException e)
            {
               request.setAttribute("exceptionStackTrace", e.getMessage());
               request.setAttribute("message", "Errore nel salvataggio dell'utente nel Database(Servlet:CreaUtente Metodo:doPost)");
               request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
            }

            utente.setPasswordHash("");
            utente.setToken("");

            request.getSession().invalidate();
         }

         request.getRequestDispatcher("/WEB-INF/results/reportPage.jsp").forward(request, response);
      }
      else
         response.sendRedirect(request.getServletContext().getContextPath() + "/index.html");
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
