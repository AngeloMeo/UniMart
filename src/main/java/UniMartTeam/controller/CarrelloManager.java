package UniMartTeam.controller;

import UniMartTeam.model.Beans.Composto;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.DAO.ProdottoDAO;
import UniMartTeam.model.Utils.ConPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrelloManager extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String path = request.getPathInfo() == null ? "/" : request.getPathInfo();
        SessionManager sessionManager = new SessionManager(request);
        Utente utente = (Utente) sessionManager.getObjectFromSession("utente");
//TODO
//solo visual


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
                //TODO
                break;
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
