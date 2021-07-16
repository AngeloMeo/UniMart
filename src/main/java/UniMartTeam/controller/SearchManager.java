package UniMartTeam.controller;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.DAO.CategoriaDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.Utils.ConPool;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name= "SearchManager", value = "/SearchManager/*", loadOnStartup = 0)
public class SearchManager extends HttpServlet
{
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/SearchManager", "");
      
      switch (path)
      {
         case "/":
            String text = request.getParameter("text");
            HashMap<String, Object> results = new HashMap<>();

            if(text != null)
               text = text.trim();

            try
            {
               List<Categoria> resultsCategoria = CategoriaDAO.doRetrieveByKey(text+"%", true, true, 0, 2);

               if(resultsCategoria != null && resultsCategoria.get(0) != null)
               {
                  results.put("categorie", new ArrayList<>());

                  for(Categoria c : resultsCategoria)
                     ((ArrayList<String>) results.get("categorie")).add(c.getNome());
               }

               List<Prodotto> resultsProdotto = ProdottoDAO.doRetrieveByCondLimit("nome LIKE('" + text + "%')", 0, 2);

               if(resultsProdotto != null && resultsProdotto.get(0) != null)
               {
                  results.put("prodotti", new ArrayList<>());

                  for(Prodotto p : resultsProdotto)
                     ((ArrayList<String>) results.get("prodotti")).add(p.getNome() + " : " + p.getCodiceIAN());
               }

               resultsProdotto = null;
               resultsProdotto = ProdottoDAO.doRetrieveByCondLimit("codiceIAN LIKE('" + text + "%')", 0, 2);

               if(resultsProdotto != null && resultsProdotto.get(0) != null)
               {
                  if(results.get("prodotti") == null)
                     results.put("prodotti", new ArrayList<>());

                  for(Prodotto p : resultsProdotto)
                     ((ArrayList<String>) results.get("prodotti")).add(p.getNome() + " : " + p.getCodiceIAN());
               }
            }
            catch (SQLException e)
            {
               request.setAttribute("exceptionStackTrace", e.getStackTrace());
               request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:SearchManager Metodo:/)");
               request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
            }

            if(results.isEmpty())
               results.put("default", "Nessun Risultato...");

            Gson json = new Gson();
            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(json.toJson(results));

            break;

         case "/prodotto":

            int codiceIAN = 0;

            try
            {
               codiceIAN = Integer.parseInt(request.getParameter("id"));
            }
            catch (NumberFormatException ex)
            {
               response.sendRedirect(request.getServletContext().getContextPath() + getServletContext().getInitParameter("homepage"));
               return;
            }

            Prodotto prodotto = null;

            try
            {
               prodotto = ProdottoDAO.doRetrieveByID(codiceIAN);
            }
            catch (SQLException e)
            {
               request.setAttribute("exceptionStackTrace", e.getStackTrace());
               request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:SearchManager Metodo:/prodotto)");
               request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
            }

            if (prodotto != null)
            {
               request.setAttribute("prodotto", prodotto);
               request.getRequestDispatcher("/WEB-INF/results/schedaProdotto.jsp").forward(request, response);
               return;
            }

            response.sendRedirect(request.getServletContext().getContextPath() + getServletContext().getInitParameter("homepage"));
            break;

         case "/categoria":

            String categoria = request.getParameter("id");

            if(categoria != null)
            {
               try
               {
                  Categoria cat = new Categoria();
                  cat.setNome(categoria);

                  List<Prodotto> prodotti = CategoriaDAO.doRetrieveProducts(cat);

                  request.setAttribute("prodotti", prodotti);
                  request.setAttribute("categoria", cat.getNome());
                  request.getRequestDispatcher("/WEB-INF/results/risultatiRicerca.jsp").forward(request, response);
                  return;
               }
               catch (SQLException e)
               {
                  request.setAttribute("exceptionStackTrace", e.getStackTrace());
                  request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:SearchManager Metodo:/prodotto)");
                  request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
               }
            }

            break;

         default:
         {
            response.sendRedirect(request.getServletContext().getContextPath() + getServletContext().getInitParameter("homepage"));
            return;
         }
      }
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