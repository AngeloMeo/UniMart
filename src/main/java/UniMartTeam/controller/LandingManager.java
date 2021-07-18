package UniMartTeam.controller;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.DAO.CategoriaDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LandingManager", value = "/Home", loadOnStartup = 0)
public class LandingManager extends HttpServlet
{
   @Override
   public void init() throws ServletException
   {
      super.init();

      loadcategorie();
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      loadcategorie();
   }

   private void loadcategorie()
   {
      List <Categoria> categorie = null;

      try
      {
         categorie = CategoriaDAO.doRetrieveAll();
      }
      catch (SQLException e)
      {
         e.printStackTrace();
         return;
      }

      getServletContext().setAttribute("categorie", categorie);
   }
}
