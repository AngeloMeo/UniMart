package UniMartTeam.controller;

import UniMartTeam.model.Beans.Inventario;
import UniMartTeam.model.Beans.Possiede;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.InventarioDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "GiacenzeManager", value = "/GiacenzeManager/*")
public class GiacenzeManager extends HttpServlet
{
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/GiacenzeManager", "");
      Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");
      Validator validator = new Validator(request);

      if (utente != null)
      {
         if (utente.getTipo().equals(TipoUtente.Amministratore))
         {
            switch (path)
            {

               case "/Modify":
               {
                  if (validator.assertInt("codiceInventario", "Formato Codice Inventario non valido"))
                  {
                     Enumeration<String> paramss = request.getParameterNames();
                     while (paramss.hasMoreElements())
                     {
                        String paramName = paramss.nextElement();
                     }

                     String codiceInventario = request.getParameter("codiceInventario");

                     Inventario i = fillInventario(request, codiceInventario);

                     Enumeration<String> params = request.getParameterNames();

                     int index = 0;
                     Inventario inventarioDummy = new Inventario();

                     String paramName = params.nextElement(); //codiceInventario
                     inventarioDummy.setCodiceInventario(Integer.parseInt(request.getParameter(paramName)));

                     while (params.hasMoreElements())
                     {
                        List<Possiede> list = i.getPossiedeList();
                        Possiede fromPage = new Possiede();

                        fromPage.setInventario(inventarioDummy);

                        Prodotto prodottoDummy = new Prodotto();

                        paramName = params.nextElement();//IAN
                        prodottoDummy.setCodiceIAN(Integer.parseInt(request.getParameter(paramName)));
                        fromPage.setProdotto(prodottoDummy);

                        paramName = params.nextElement();//Giacenza
                        fromPage.setGiacenza(Float.parseFloat(request.getParameter(paramName)));

                        if (!list.get(index).equals(fromPage))
                        {
                           Possiede fromDB = list.get(index);

                           if (fromPage.getGiacenza() == 0)
                           {
                              try
                              {
                                 InventarioDAO.deleteProdottoInventario(fromPage);
                              }
                              catch (SQLException e)
                              {
                                 request.setAttribute("exceptionStackTrace", e.getStackTrace());
                                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                                 return;
                              }
                           }
                           else if (fromPage.getGiacenza() > 0)
                           {
                              if (fromDB.getGiacenza() == 0)
                              {
                                 try
                                 {
                                    InventarioDAO.addProdottoInventario(fromPage);
                                 }
                                 catch (SQLException e)
                                 {
                                    request.setAttribute("exceptionStackTrace", e.getStackTrace());
                                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                                    return;
                                 }
                              }
                              else if (fromDB.getGiacenza() > 0)
                              {
                                 try
                                 {
                                    InventarioDAO.updateProdottoInventario(fromPage);
                                 } catch (SQLException e)
                                 {
                                    request.setAttribute("exceptionStackTrace", e.getStackTrace());
                                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                                    return;
                                 }
                              }
                           }
                        }
                        index++;
                     }
                  }
               }
               break;

               case "/addGiacenze":
               {
                  if (validator.assertInt("codiceInventario", "Formato Codice Inventario non valido") &&
                          validator.assertDouble("giacenza", "Errore nel formato della giacenza") && validator.assertInt("prodotto", "Formato IAN non valido"))
                  {
                     Inventario i = null;

                     try
                     {
                        i = InventarioDAO.doRetrieveByCond(InventarioDAO.CODICE_INVENTARIO, "=" + request.getParameter("codiceInventario")).get(0);
                     } catch (SQLException e)
                     {
                        request.setAttribute("exceptionStackTrace", e.getStackTrace());
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                        return;
                     }

                     Possiede p = new Possiede();
                     Inventario dummy = new Inventario();

                     dummy.setCodiceInventario(i.getCodiceInventario());
                     p.setInventario(i);

                     try
                     {
                        p.setProdotto(ProdottoDAO.doRetrieveByID(Integer.parseInt(request.getParameter("prodotto"))));
                     } catch (SQLException e)
                     {
                        request.setAttribute("exceptionStackTrace", e.getStackTrace());
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                        return;
                     }

                     p.setGiacenza(Float.parseFloat(request.getParameter("giacenza")));
                     i.addPossiedeList(p);

                     try
                     {
                        InventarioDAO.addProdottoInventario(p);
                     } catch (SQLException throwables)
                     {
                        throwables.printStackTrace();
                     }

                  }
               }
               break;

               case "/":
               {
                  if (validator.required(request.getParameter("codiceInventario&CF")))
                  {
                     String[] info = request.getParameter("codiceInventario&CF").split(",");
                     if (info.length == 2)
                     {
                        String codiceInventario = info[0], cfResponsabile = info[1];

                        if (cfResponsabile.equalsIgnoreCase(utente.getCF()))
                        {

                           Inventario i = fillInventario(request, codiceInventario);

                           if (i != null)
                           {
                              request.setAttribute("inventario", i);
                              request.getRequestDispatcher("/WEB-INF/results/giacenzeManager.jsp").forward(request, response);
                              return;
                           }
                        }
                     }
                  }
               }
               break;
            }
            response.sendRedirect(request.getContextPath() + "/InventarioManager");
            return;
         } else
         {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
         }
      } else
         response.sendRedirect(request.getServletContext().getContextPath() + "/LoginManager");

   }

   private Inventario fillInventario(HttpServletRequest request, String codiceInventario)
   {
      Inventario i = null;
      List<Prodotto> prodotti = null;

      try
      {
         i = InventarioDAO.doRetrieveByCond(InventarioDAO.CODICE_INVENTARIO, codiceInventario).get(0);
         prodotti = ProdottoDAO.doRetrieveAll();
      }
      catch (SQLException throwables)
      {
         throwables.printStackTrace();
      }

      ArrayList<Possiede> possiedeArrayList = new ArrayList<>();

      for (Prodotto p : prodotti)
      {
         Possiede possiede = new Possiede();
         possiede.setInventario(i);
         possiede.setProdotto(p);
         try
         {
            if (!InventarioDAO.getProdottoInventarioStock(possiede))
               possiede.setGiacenza(0);
         }
         catch (SQLException throwables)
         {
            throwables.printStackTrace();
         }
         possiedeArrayList.add(possiede);
      }
      i.setPossiedeList(possiedeArrayList);

      return i;
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