<script type="text/javascript" src="${pageContext.request.contextPath}/js/carrello/add2cart.js" defer></script>

<button class="add2cart" onclick="add(${prodotto.codiceIAN}, this)">Aggiungi al carrello</button>
<button id="remove2cart" class="hide" value="${prodotto.codiceIAN}">Rimuovi dal carrello</button>