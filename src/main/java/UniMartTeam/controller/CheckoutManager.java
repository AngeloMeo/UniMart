package UniMartTeam.controller;

import UniMartTeam.model.Beans.Composto;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.CouponDAO;
import UniMartTeam.model.DAO.OrdineDAO;
import UniMartTeam.model.DAO.SpedizioneDAO;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
import UniMartTeam.model.Utils.ConPool;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "CheckoutManager", value = "/CheckoutManager/*")
public class CheckoutManager extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        SessionManager sessionManager = new SessionManager(request);
        Utente utente = (Utente) sessionManager.getObjectFromSession("utente");

        if(utente != null && !utente.getCF().isBlank()){

            Ordine o = (Ordine) sessionManager.getObjectFromSession("cart");

            if(o!=null){
                //setting parametri
                o.setCitta(request.getParameter("citta"));
                o.setViaCivico(request.getParameter("viaCivico"));
                o.setRegione(request.getParameter("regione"));
                try{
                    switch (request.getParameter("spedizione")){
                        case "eco":
                            o.setSpedizione(SpedizioneDAO.doRetrieveById(1));
                            break;
                        case "standard":
                            o.setSpedizione(SpedizioneDAO.doRetrieveById(2));
                            break;
                        case "express":
                            o.setSpedizione(SpedizioneDAO.doRetrieveById(3));
                            break;
                        default:
                            o.setSpedizione(SpedizioneDAO.doRetrieveById(2));
                            break;
                    }
                    o.setCoupon(CouponDAO.doRetrieveById(Integer.parseInt(request.getParameter("coupon"))));
                }catch(SQLException e){
                    request.setAttribute("message", "Errore Database(Servlet:CheckoutManager Metodo:doPost)");
                    request.setAttribute("exceptionStackTrace", e.getStackTrace());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                    e.printStackTrace();
                }
                o.setDataAcquisto(LocalDate.now());
                o.setFeedback(request.getParameter("feedback"));

                //calcolo del totale
                float totale = 0;

                for(Composto c : o.getCompostoList())
                    totale += (c.getPrezzo() * c.getQuantita());

                request.setAttribute("totale", totale);

                if(o.getCoupon() != null)
                {
                    totale -= ((totale * o.getCoupon().getSconto()) / 100);
                    request.setAttribute("totaleCoupon", totale);
                }

                o.setStatoOrdine(StatoOrdine.Accettato);

                try {
                    //se l'ordine non è stato salvato in precedenza, non avrà numero identificativo
                    //in quanto è generato dal DB. Così facendo la ricevuta sarà completa di ID in ogni caso
                    if(o.getNumeroOrdine() == 0 || o.getStatoOrdine() == null){
                        OrdineDAO.doSave(o);
                    }

                    OrdineDAO.doUpdate(o);
                } catch (SQLException e) {
                    request.setAttribute("message", "Errore Database(Servlet:CheckoutManager Metodo:doPost)");
                    request.setAttribute("exceptionStackTrace", e.getStackTrace());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                    e.printStackTrace();
                }

                o.setRicevutaPagamento("Ricevuta ordine "+o.getNumeroOrdine()+" €"+totale+" il "+o.getDataAcquisto());
                sessionManager.removeAttribute("cart");
                response.sendRedirect(request.getContextPath() + "/OrdiniManager");
            }

        }

        response.sendRedirect(request.getServletContext().getContextPath() + getServletContext().getInitParameter("homepage"));
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
