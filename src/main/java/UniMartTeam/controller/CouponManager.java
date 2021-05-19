package UniMartTeam.controller;

import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.DAO.CouponDAO;
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
      switch (path)
      {
         case "/list":
            listCoupon(request, response);
         break;

         default:
            response.sendRedirect(request.getServletContext().getContextPath() + "/index.html");
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
         request.setAttribute("message", "Errore nel recupero info dal Database(Servlet:CouponManager Metodo:doGet)");
         request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
      }

      if(couponList != null && couponList.get(0).getNumeroCoupon() != 0)
         request.setAttribute("couponList", couponList);
      else
         request.setAttribute("couponList", null);

      request.getRequestDispatcher("/WEB-INF/results/couponPage.jsp").forward(request, response);
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {

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