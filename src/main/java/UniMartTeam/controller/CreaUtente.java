package UniMartTeam.controller;

import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.UtenteDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.GregorianCalendar;

@WebServlet(name = "CreaUtente", value = "/CreaUtente")
@MultipartConfig
public class CreaUtente extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {

   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      Utente u = new Utente();
      u.setCF(request.getParameter("CF"));
      u.setCognome(request.getParameter("cognome"));
      u.setNome(request.getParameter("nome"));
      u.setViaCivico(request.getParameter("viaCivico"));
      u.setCitta(request.getParameter("citta"));
      u.setTelefono(request.getParameter("telefono"));
      u.setRegione(request.getParameter("regione"));
      u.setEmail(request.getParameter("email"));
      u.setToken(request.getParameter("token"));
      u.setUsername(request.getParameter("username"));
      u.setPasswordHash(request.getParameter("password"));
      u.setDataDiNascita(LocalDate.parse(request.getParameter("dataDiNascita")));

      //ServletOutputStream os = response.getOutputStream();
      String path = getServletContext().getInitParameter("uploadpath");
      Part filePart = request.getPart("fotoProfilo");
      String fileName = u.getCF() + "_" + filePart.getSubmittedFileName();
      InputStream is = filePart.getInputStream();
      Files.copy(is, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);

      u.setFotoProfilo(fileName);

      try
      {
         UtenteDAO.doSave(u);
      } catch (SQLException throwables)
      {
         throwables.printStackTrace();
      }
   }
}
