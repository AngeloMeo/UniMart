<header>
  <script src="${pageContext.request.contextPath}/js/navbar/navbar.js" defer></script>
  <form autocomplete="off">
    <input type="text" id="searchBar" placeholder="Cerca per codice IAN, nome prodotto o categoria" required>
  </form>
  <div id="resultsSearch">

  </div>

  <a href="https://wa.me/+393391904141">
    <img src="${pageContext.request.contextPath}/icons/help_center.svg">
  </a>

  <a href="LoginManager" id="login">
    <c:choose>
      <c:when test="${utente != null}">
        <img src="${pageContext.request.contextPath}/file/${utente.fotoProfilo}" height="48px" width="48px" style="border-radius: 30px">
      </c:when>
      <c:otherwise>
        <img src="${pageContext.request.contextPath}/icons/account_circle.svg">
      </c:otherwise>
    </c:choose>
  </a>
</header>