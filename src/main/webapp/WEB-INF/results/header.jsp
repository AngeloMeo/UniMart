<header class="transition">
  <script src="${pageContext.request.contextPath}/js/header/header.js" defer></script>

  <img class="logo" src="${pageContext.request.contextPath}/icons/logo.svg">

  <form autocomplete="off">
    <input type="text" id="searchBar" placeholder="Cerca per codice IAN, nome prodotto o categoria" required>
    <div id="resultsSearch" ></div>
  </form>

  <a href="${pageContext.request.contextPath}/CarrelloManager">
    <c:choose>
      <c:when test="${cart.compostoList == null || cart.compostoList.size() == 0}">
        <img src="${pageContext.request.contextPath}/icons/shopping_cart_empty.svg">
      </c:when>
      <c:otherwise>
        <img src="${pageContext.request.contextPath}/icons/shopping_cart.svg">
      </c:otherwise>
    </c:choose>

  </a>

  <a href="${pageContext.request.contextPath}/LoginManager" id="login">
    <c:choose>
      <c:when test="${utente != null}">
        <img src="${pageContext.request.contextPath}/file/${utente.fotoProfilo}">
      </c:when>
      <c:otherwise>
        <img src="${pageContext.request.contextPath}/icons/account_circle.svg">
      </c:otherwise>
    </c:choose>
  </a>

  <a class="assistenza" href="https://wa.me/+393391904141" target="_blank">
    <img src="${pageContext.request.contextPath}/icons/help_center.svg">
  </a>

</header>