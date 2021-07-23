package UniMartTeam.controller;

import UniMartTeam.model.Beans.*;
import UniMartTeam.model.DAO.OrdineDAO;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.DAO.SpedizioneDAO;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
import UniMartTeam.model.Utils.ConPool;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "CarrelloManager", value = "/CarrelloManager/*")
public class CarrelloManager extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/CarrelloManager", "");
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
        request.getRequestDispatcher("/WEB-INF/results/carrello.jsp").forward(request, response);
    }

    @Override
    public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo().replace("/CarrelloManager", "");
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
                        c.setQuantita(Math.abs(quantity));

                        Composto dummy = new Composto();
                        dummy.setQuantita(Math.abs(quantity));
                        dummy.setPrezzo(c.getProdotto().getPrezzo());


                        Gson json = new Gson();
                        response.setContentType("application/JSON");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().println(json.toJson(dummy));
                    }
                    sessionManager.setAttribute(cart, "cart");
                    return;
                }
            }
        }
    }

    private void add2cart(HttpServletResponse response, HttpServletRequest request, SessionManager sessionManager) throws IOException, ServletException
    {

        int productIan = Integer.parseInt(request.getParameter("IAN"));
        float quantity = Float.parseFloat(request.getParameter("quantity"));
        Ordine cart = (Ordine) sessionManager.getObjectFromSession("cart");

        if(productIan>0){

            if(cart == null) { //se inesistente lo creo
                cart = new Ordine();
                cart.setCompostoList(new ArrayList<>());
                cart.setCliente((Utente) sessionManager.getObjectFromSession("utente"));
            }

            Composto composto = new Composto();
            composto.setOrdine(cart);


            Prodotto p = null;
            try{
                p = ProdottoDAO.doRetrieveByID(productIan);
            } catch (SQLException e) {
                request.setAttribute("message", "Errore Database(Servlet:CarrelloManager Metodo:Add2Cart)");
                request.setAttribute("exceptionStackTrace", e.getStackTrace());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
                e.printStackTrace();
                return;
            }

            boolean contains = false;

            for(Composto c : cart.getCompostoList()){
                if(c.getProdotto().equals(p)){
                    c.setQuantita(c.getQuantita()+quantity);
                    contains = true;
                }
            }

            if(!contains) {
                composto.setProdotto(p);
                composto.setQuantita(quantity);
            }


            composto.setPrezzo(composto.getProdotto().getPrezzo());
//TODO da sistemare
            cart.addCompostoList(composto);

            sessionManager.setAttribute(cart, "cart");

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
