<header>
  <script src="${pageContext.request.contextPath}/js/navbar/navbar.js" defer></script>

  <img class="logo" src="${pageContext.request.contextPath}/icons/logo.svg">

  <form autocomplete="off">
    <input type="text" id="searchBar" placeholder="Cerca per codice IAN, nome prodotto o categoria" required>
  </form>

  <div id="resultsSearch"></div>

  <a href="">
    <img src="${pageContext.request.contextPath}/icons/shopping_cart_empty.svg">
  </a>

  <a href="LoginManager" id="login">
    <c:choose>
      <c:when test="${utente != null}">
        <img src="${pageContext.request.contextPath}/file/${utente.fotoProfilo}">
      </c:when>
      <c:otherwise>
        <img src="${pageContext.request.contextPath}/icons/account_circle.svg">
      </c:otherwise>
    </c:choose>
  </a>
  <a href="https://wa.me/+393391904141">
    <img src="${pageContext.request.contextPath}/icons/help_center.svg">
  </a>

  <a href="https://wa.me/+393663096054">
    <img src="${pageContext.request.contextPath}/icons/whatsapp.svg">
  </a>

</header>