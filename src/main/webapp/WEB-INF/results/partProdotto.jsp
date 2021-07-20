<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${prodottoList}" var="prodotto">
   <tr>
      <td data-label="IAN">${prodotto.codiceIAN}</td>
      <td data-label="Nome">${prodotto.nome}</td>
      <td data-label="Categoria">${prodotto.categoria.nome}</td>
      <td data-label="Prezzo">${prodotto.prezzo} &euro;</td>
      <td data-label="Foto">
         <c:if test="${not empty prodotto.foto}">
            <img src="file/${prodotto.foto}" class="img-medium">
         </c:if>
      </td>
      <td data-label="Gestisci">
         <form method="post" action="${context}/ProdottoManager/getProdotto">
            <button type="submit" class="btn btn-small" name="codiceIAN" value="${prodotto.codiceIAN}">Modifica</button>
         </form>
      </td>
   </tr>
</c:forEach>