<!DOCTYPE html>
<html lang="it">
   <head>
      <title>Unimart</title>

      <%@include file="WEB-INF/results/general.jsp"%>
   </head>
   <body>
      <%@include file="WEB-INF/results/header.jsp"%>
      <main>
         <c:if test="${categorie != null}">
            <div>
               <c:forEach items="${categorie}" var="categoria">
                  <div>
                     <a href="${context}/SearchManager/categoria?id=${categoria.nome}">
                        ${categoria.nome}
                     </a>
                  </div>
               </c:forEach>
            </div>
         </c:if>

      </main>
      <%@include file="WEB-INF/results/footer.jsp"%>
   </body>
</html>