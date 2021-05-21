package UniMartTeam.controller;

import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.CouponDAO;
import UniMartTeam.model.DAO.UtenteDAO;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import UniMartTeam.model.Utils.ConPool;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CouponManager", value = "/CouponManager/*")
public class CouponManager extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo();
      HttpSession session = request.getSession();
      Utente utente = (Utente) session.getAttribute("utente");

      if(session != null && utente != null)
      {
         if(utente.getTipo().equals(TipoUtente.Amministratore))
         {
            switch (path)
            {
               case "/list":
                  if(session.getAttribute("ultimoCoupon") != null)
                  {
                     request.setAttribute("ultimoCoupon", session.getAttribute("ultimoCoupon"));
                     session.removeAttribute("ultimoCoupon");
                  }

                  listCoupon(request, response);
                  return;
            }
         }

         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
         return;
      }
      else
         response.sendRedirect(request.getServletContext().getContextPath() + "/Login");
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String path = request.getPathInfo();
      HttpSession session = request.getSession();
      Utente utente = (Utente) session.getAttribute("utente");

      if(session != null && utente != null && !utente.getCF().isEmpty())
      {
         if(!utente.getTipo().equals(TipoUtente.Amministratore))
         {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
            return;
         }

         switch (path)
         {
            case "/creaCoupon":
               Coupon coupon = null;

               if (request.getParameter("sconto") != null)
               {
                  coupon = new Coupon();
                  coupon.setSconto(Float.parseFloat(request.getParameter("sconto")));
                  coupon.setCreatore(utente);

                  try
                  {
                     coupon.setNumeroCoupon(CouponDAO.doSave(coupon));
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel salvataggio del coupon nel Database(Servlet:CouponMAnager Metodo:doPost)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }

                  session.setAttribute("ultimoCoupon", coupon);
                  response.sendRedirect(request.getContextPath() + "/CouponManager/list");
               }
               return;
         }
      }

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

   private void listCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      List<Coupon> couponList = null;

      try
      {
         couponList = CouponDAO.doRetrieveAll();
      }
      catch (SQLException e)
      {
         request.setAttribute("exceptionStackTrace", e.getMessage());
         request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:CouponManager Metodo:listCoupon)");
         request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
      }

      if(couponList != null && couponList.get(0).getNumeroCoupon() != 0)
         request.setAttribute("couponList", couponList);
      else
         request.setAttribute("couponList", null);

      request.getRequestDispatcher("/WEB-INF/results/couponPage.jsp").forward(request, response);
   }
}