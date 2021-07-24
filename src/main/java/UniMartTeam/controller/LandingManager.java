package UniMartTeam.controller;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Spedizione;
import UniMartTeam.model.DAO.CategoriaDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.DAO.SpedizioneDAO;
import UniMartTeam.model.Utils.ConPool;
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
      loadProdottiRandom(20);
      loadProdottiPiuAcquistati(15);
      loadSpedizioni();
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      loadcategorie();
      loadProdottiRandom(20);
      loadProdottiPiuAcquistati(15);
      loadSpedizioni();

      response.sendRedirect(request.getServletContext().getContextPath() + getServletContext().getInitParameter("homepage"));
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

   private void loadSpedizioni()
   {
      try
      {
         List<Spedizione> spedizioni = SpedizioneDAO.doRetriveAll();

         getServletContext().setAttribute("spedizioni", spedizioni);
      }
      catch (SQLException e)
      {
         e.printStackTrace();
         return;
      }
   }

   private void loadProdottiPiuAcquistati(int size)
   {
      List <Prodotto> prodottiList = null;

      try
      {
         prodottiList = ProdottoDAO.prodottiPiuAcquistati(size);
      }
      catch (SQLException e)
      {
         e.printStackTrace();
         return;
      }

      getServletContext().removeAttribute("prodottiPiuAcquistati");
      getServletContext().setAttribute("prodottiPiuAcquistati", prodottiList);
   }

   private void loadProdottiRandom(int size)
   {
      List <Prodotto> prodotti = null;

      try
      {
         prodotti = ProdottoDAO.prodottiRandom(size);
      }
      catch (SQLException e)
      {
         e.printStackTrace();
         return;
      }

      getServletContext().removeAttribute("prodotti");
      getServletContext().setAttribute("prodotti", prodotti);
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

      getServletContext().removeAttribute("categorie");
      getServletContext().setAttribute("categorie", categorie);
   }
}
