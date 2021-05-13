<%@ page import="UniMartTeam.model.DAO.SpedizioneDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="UniMartTeam.model.Beans.Spedizione" %>
<%@ page import="UniMartTeam.model.DAO.OrdineDAO" %>
<%@ page import="UniMartTeam.model.DAO.UtenteDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="UniMartTeam.model.Beans.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
   <head>
      <title>Title</title>
   </head>
   <body>
      <%
         OrdineDAO.doRetrieveAll();
         OrdineDAO.doRetrieveAll(0, 10);

         List<Utente> ut = UtenteDAO.doRetrieveAll();

         for(Utente u : ut)
         {

         %>
      <%= u.getCF()%>
      <%
            }
      %>


   </body>
</html>