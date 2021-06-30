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

@WebServlet(name= "SearchManager", value = "/SearchManager/*")
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
               request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:SearchManager Metodo:list/)");
               request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
            }

            if(results.isEmpty())
               results.put("default", "Nessun Risultato...");

            Gson json = new Gson();
            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(json.toJson(results));

            break;

         default:
            response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
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