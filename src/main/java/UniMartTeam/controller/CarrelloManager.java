package UniMartTeam.controller;

import UniMartTeam.model.Beans.*;
import UniMartTeam.model.DAO.OrdineDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.DAO.SpedizioneDAO;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
import UniMartTeam.model.Utils.ConPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarrelloManager extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        SessionManager sessionManager = new SessionManager(request);
        Utente utente = (Utente) sessionManager.getObjectFromSession("utente");

        if(utente != null && !utente.getCF().isBlank()){
            try {
                request.setAttribute("orders", OrdineDAO.doRetrieveByCond(utente, StatoOrdine.Salvato));
            } catch (SQLException e) {
                request.setAttribute("message", "Errore Database(Servlet:CarrelloManager Metodo:doGet)");
                request.setAttribute("exceptionStackTrace", e.getStackTrace());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                e.printStackTrace();
            }
        }
//todo manda alla pagina carrello
    }


    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * ADD 2 CART
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        SessionManager sessionManager = new SessionManager(request);

        switch(path){
            case "/add2cart":
                    add2cart(response, request, sessionManager);
                break;

            case "/alterquantities":
                    alterQuantities(request, response, sessionManager);
                break;
            case "/saveOrder":
                saveOrder(request, response, sessionManager);
                break;
            case "/saved2cart":
                saved2cart(request, response, sessionManager);
                break;
        }

    }

    private void saved2cart(HttpServletRequest request, HttpServletResponse response, SessionManager sessionManager) throws IOException {

        Utente utente = (Utente) sessionManager.getObjectFromSession("utente");

        if(utente != null && !utente.getCF().isEmpty()){

            String id = request.getParameter("orderID");

            if(!id.isBlank()){
                try {
                    sessionManager.setAttribute(OrdineDAO.doRetrieveByID(Integer.parseInt(id)), "cart");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            /*TODO ajax*/
        }
    }

    private void saveOrder(HttpServletRequest request, HttpServletResponse response, SessionManager sessionManager) throws IOException {

        Utente utente = (Utente) sessionManager.getObjectFromSession("utente");

        if(utente != null && !utente.getCF().isEmpty()){

            Ordine o = (Ordine) sessionManager.getObjectFromSession("cart");
            o.setCliente(utente);
            o.setStatoOrdine(StatoOrdine.Salvato);
            o.setRegione(utente.getRegione());
            o.setCitta(utente.getCitta());
            o.setViaCivico(utente.getViaCivico());

            try {
                o.setSpedizione(SpedizioneDAO.doRetrieveById(1));
            } catch (SQLException e) {
                request.setAttribute("message", "Errore Database(Servlet:CarrelloManager Metodo:saveOrder)");
                request.setAttribute("exceptionStackTrace", e.getStackTrace());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                e.printStackTrace();
            }

            try {
                OrdineDAO.doSave(o);
            } catch (SQLException e) {
                request.setAttribute("message", "Errore Database(Servlet:CarrelloManager Metodo:saveOrder)");
                request.setAttribute("exceptionStackTrace", e.getStackTrace());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                e.printStackTrace();
            }

            sessionManager.removeAttribute("cart");
            /*TODO: ajax*/
        }

    }

    private void alterQuantities(HttpServletRequest request, HttpServletResponse response, SessionManager sessionManager) throws IOException {

        int productIan = Integer.parseInt(request.getParameter("IAN"));
        float quantity = Float.parseFloat(request.getParameter("quantity"));
        Ordine cart = (Ordine) sessionManager.getObjectFromSession("cart");

        if(productIan>0){

            for(Composto c : cart.getCompostoList()){

                if(c.getProdotto().getCodiceIAN() == productIan){

                    if(quantity == 0){
                        cart.getCompostoList().remove(c);
                    }
                    else {
                        c.setQuantita(quantity);
                        c.setPrezzo(c.getProdotto().getPrezzo() * quantity);
                    }
                }
            }


            sessionManager.setAttribute(cart, "cart");

            /*TODO: quando aggiungo un prodotto al carrello rimane sulla pagina; ajax cambierà il testo di "Aggiungi al carrello" in "Aggiunto"*/


        }
    }

    private void add2cart(HttpServletResponse response, HttpServletRequest request, SessionManager sessionManager) throws IOException {

        int productIan = Integer.parseInt(request.getParameter("IAN"));
        float quantity = Float.parseFloat(request.getParameter("quantity"));
        Ordine cart = (Ordine) sessionManager.getObjectFromSession("cart");

        if(productIan>0){

            if(cart == null) { //se inesistente lo creo
                cart = new Ordine();
                cart.setCompostoList(new ArrayList<>());
            }

            Composto prod = new Composto();
            prod.setOrdine(cart);

            try {
                prod.setProdotto(ProdottoDAO.doRetrieveByID(productIan));
                prod.setPrezzo(prod.getProdotto().getPrezzo() * quantity);
            } catch (SQLException e) {
                request.setAttribute("message", "Errore Database(Servlet:CarrelloManager Metodo:Add2Cart)");
                request.setAttribute("exceptionStackTrace", e.getStackTrace());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                e.printStackTrace();
                return;
            }


            prod.setQuantita(quantity);
            cart.addCompostoList(prod);

            sessionManager.setAttribute(cart, "cart");

            /*TODO: quando aggiungo un prodotto al carrello rimane sulla pagina; ajax cambierà il testo di "Aggiungi al carrello" in "Aggiunto"*/


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
