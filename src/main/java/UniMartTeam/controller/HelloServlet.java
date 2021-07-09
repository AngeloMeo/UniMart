package UniMartTeam.controller;

import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.*;
import UniMartTeam.model.EnumForBeans.TipoUtente;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "HelloServlet", value = "/HelloServlet")
public class HelloServlet extends HttpServlet
{
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      Utente utente = (Utente) SessionManager.getObjectFromSession(request, "utente");

      if (utente != null)
      {
         if (utente.getTipo().equals(TipoUtente.Amministratore))
         {
            try
            {
               request.setAttribute("CouponTotali", CouponDAO.countCoupon());
               request.setAttribute("CouponRiscattati", CouponDAO.countClaimedCoupon());
               request.setAttribute("UtentiTotali", UtenteDAO.countUsers());
               request.setAttribute("ClientiTotali", UtenteDAO.countClients());
               request.setAttribute("OrdiniEvasi", OrdineDAO.countOrdiniEvasi());
               request.setAttribute("NumeroInventari", InventarioDAO.countInventari());
               request.setAttribute("ProdottiStatistiche", OrdineDAO.countProdottiVenduti());
               request.setAttribute("OrdiniSalvati", OrdineDAO.countOrdiniSalvati());
               request.setAttribute("SpedizionePreferita", SpedizioneDAO.favouriteSpedizione());
               request.setAttribute("ProdottoPreferito", ProdottoDAO.getProdottoPreferito());
               request.setAttribute("OrdiniTotali", OrdineDAO.countOrdiniTotali());
            }
            catch (SQLException e)
            {
               request.setAttribute("message", "Errore nel salvataggio del coupon nel Database(Servlet:CouponMAnager Metodo:doPost)");
               request.setAttribute("exceptionStackTrace", e.getStackTrace());
               response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
               return;
            }

            request.getRequestDispatcher("/WEB-INF/results/dashboardAdmin.jsp").forward(request, response);
            return;
         }
         else if (utente.getTipo().equals(TipoUtente.Semplice))
         {//TODO
            request.getRequestDispatcher("/WEB-INF/results/dashboardUtente.jsp").forward(request, response);
            return;
         }
         else
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "L'utente corrente non è autorizzato a visualizzare questa pagina");
      }
      else
         response.sendRedirect(request.getServletContext().getContextPath() + "/LoginManager");
   }
}
