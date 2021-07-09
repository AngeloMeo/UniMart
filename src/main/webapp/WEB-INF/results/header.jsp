<header>
  <form autocomplete="off">
    <input type="text" id="searchBar" placeholder="Cerca per codice IAN, nome prodotto o categoria" required>
  </form>
  <div id="resultsSearch">

  </div>

  <a href="LoginManager" id="login">
    <c:choose>
      <c:when test="${utente != null}">
        <img src="${pageContext.request.contextPath}/file/${utente.fotoProfilo}" height="48px" width="48px" style="border-radius: 30px">
      </c:when>
      <c:otherwise>
        <img src="icons/account_circle_white.svg">
      </c:otherwise>
    </c:choose>
  </a>
</header>
