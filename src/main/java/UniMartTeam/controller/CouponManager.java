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
      String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
      HttpSession session = request.getSession();
      Utente utente = (Utente) session.getAttribute("utente");

      if(session != null && utente != null)
      {
         if(utente.getTipo().equals(TipoUtente.Amministratore))
         {
            switch (path)
            {
               case "/":
                  if(session.getAttribute("ultimoCoupon") != null)
                  {
                     request.setAttribute("ultimoCoupon", session.getAttribute("ultimoCoupon"));
                     session.removeAttribute("ultimoCoupon");
                  }

                  listCoupon(request, response);
                  break;
               default:
                  response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
                  break;
            }
         }
         else
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
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
            case "/creaModal":
            {
               Coupon coupon = null;

               if (request.getParameter("sconto") != null)
               {
                  coupon = new Coupon();
                  coupon.setSconto(Float.parseFloat(request.getParameter("sconto")));
                  coupon.setCreatore(utente);

                  try
                  {
                     coupon.setNumeroCoupon(CouponDAO.doSave(coupon));
                  } catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel salvataggio del coupon nel Database(Servlet:CouponMAnager Metodo:doPost)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }

                  session.setAttribute("ultimoCoupon", coupon);
               }
            }
            break;

            case "/deleteCoupon":
            {
               if (checkParam(request) && request.getParameter("CF_Creatore").equalsIgnoreCase(utente.getCF()))
               {
                  int idCoupon = Integer.parseInt(request.getParameter("idCoupon"));

                  try
                  {
                     CouponDAO.doDelete(idCoupon);
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel eliminazione del coupon dal Database(Servlet:CouponManager Metodo:doPost)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }
               }
            }
            break;

            case "/updateCoupon":
            {
               if (checkParam(request) && request.getParameter("CF_Creatore").equalsIgnoreCase(utente.getCF()))
               {
                  Coupon coupon = new Coupon();

                  coupon.setNumeroCoupon(Integer.parseInt(request.getParameter("idCoupon")));
                  coupon.setSconto(Float.parseFloat(request.getParameter("sconto")));
                  coupon.setCreatore(utente);

                  try
                  {
                     CouponDAO.doUpdate(coupon);
                  }
                  catch (SQLException e)
                  {
                     request.setAttribute("exceptionStackTrace", e.getMessage());
                     request.setAttribute("message", "Errore nel update del coupon nel Database(Servlet:CouponManager Metodo:doPost)");
                     request.getRequestDispatcher("/WEB-INF/results/errorPage.jsp").forward(request, response);
                  }
               }
            }
            break;
         }
         response.sendRedirect(request.getContextPath() + "/CouponManager");
         return;
      }

      response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
   }



   private boolean checkParam(HttpServletRequest request)
   {
      return request.getParameter("CF_Creatore") != null && request.getParameter("sconto") != null &&
              request.getParameter("idCoupon") != null;
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

      if(couponList != null && couponList.get(0) != null)
         request.setAttribute("couponList", couponList);
      else
         request.setAttribute("couponList", null);

      request.getRequestDispatcher("/WEB-INF/results/couponPage.jsp").forward(request, response);
   }
}