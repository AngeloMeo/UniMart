<!DOCTYPE html>
<html lang="it">
   <head>
      <title>Unimart</title>

      <%@include file="WEB-INF/results/general.jsp"%>
      <script src="${context}/js/setupSlick.js" defer></script>
   </head>
   <body>
      <%@include file="WEB-INF/results/header.jsp"%>
      <main class="footer-present-big">
         <hr>

         <div class="flex-container flex-dirRow col-gap cover-container">
            <img src="${context}/images/cover.jpg" class="flex-item-40 img-responsive">
            <div class="flex-item-50">
               Il nostro shop punta ad eliminare le inutili attese alla cassa con conseguente
               risparmio di tempo, inoltre ci sta a cuore tentare di aiutare l’ambiente
               consegnando noi la spesa ai nostri clienti per mezzo di hub sparsi in tutta la
               penisola.
               Offriamo ai nostri clienti vari tipi di consegna:
            </div>
            <div class="flex-item-50">
               <ul>
                  <li>
                     Express: entro un'ora la spesa verr&agrave; recapitata al cliente, disponibile tutta la
                     giornata e ad un sovrapprezzo.
                  </li>
                  <li>
                     Standard: sar&agrave; consegnata la spesa in giornata, ordinabile 8-13, senza
                     sovrapprezzo.
                  </li>
                  <li>
                     Eco: &egrave; una consegna nel rispetto dell'ambiente effettuata con Route planning e veicoli ecologici.
                  </li>
               </ul>
            </div>

            <img src="${context}/images/cover2.jpg" class="flex-item-40 img-responsive">
         </div>

         <hr>

         <c:if test="${categorie != null}">
            <div class="flex-container justify-content-center col-gap categorie-container">
               <c:forEach items="${categorie}" var="categoria">
                  <div class="flex-item-20 transition">
                     <a href="${context}/SearchManager/categoria?id=${categoria.nome}">
                        ${categoria.nome}
                     </a>
                  </div>
               </c:forEach>
            </div>
         </c:if>

         <hr>

         <c:if test="${prodotti != null}">
            <h2 class="text-center">Prodotti scelti per te</h2>
            <div class="container-slick">
               <c:forEach items="${prodotti}" var="prodotto">
                  <div class="card text-center">
                     <a href="${context}/SearchManager/prodotto?id=${prodotto.codiceIAN}">
                        <div>
                           <img src="${context}/file/${prodotto.foto}" class="img-large">
                        </div>
                        ${prodotto.nome}
                        <br>
                        <c:set var="price"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${prodotto.prezzo+(prodotto.prezzo*prodotto.categoria.aliquota)/100}"/></c:set>
                        <h3>Prezzo: <c:out value="${price}"/> &euro;</h3>
                     </a>
                  </div>
               </c:forEach>
            </div>
         </c:if>

         <hr>

         <c:if test="${prodottiPiuAcquistati != null}">
            <h2 class="text-center">Prodotti pi&ugrave; acquistati</h2>
            <div class="container-slick">
               <c:forEach items="${prodottiPiuAcquistati}" var="prodotto">
                  <div class="card text-center">
                     <a href="${context}/SearchManager/prodotto?id=${prodotto.codiceIAN}">
                        <div>
                           <img src="${context}/file/${prodotto.foto}" class="img-large">
                        </div>
                           ${prodotto.nome}
                        <br>
                        <c:set var="price"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${prodotto.prezzo+(prodotto.prezzo*prodotto.categoria.aliquota)/100}"/></c:set>
                        <h3>Prezzo: ${price} &euro;</h3>
                     </a>
                  </div>
               </c:forEach>
            </div>
         </c:if>

         <hr>
      </main>
      <%@include file="WEB-INF/results/footer.jsp"%>
   </body>
</html>