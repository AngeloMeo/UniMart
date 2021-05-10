<%@ page import="UniMartTeam.model.DAO.SpedizioneDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="UniMartTeam.model.Beans.Spedizione" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
   <head>
      <title>Title</title>
   </head>
   <body>
      <%
         Spedizione sp = new Spedizione();
         sp.setID(3);
         sp.setNome("express");
         sp.setCosto(15.99F);
         SpedizioneDAO.doUpdate(sp);
         ArrayList<Spedizione> arr = (ArrayList<Spedizione>) SpedizioneDAO.doRetriveAll();

         for(Spedizione s : arr)
         {
      %>

      <%= s.getNome() + " " + s.getCosto() + " " + s.getID()%>
      <br>

      <%}
      %>

   </body>
</html>