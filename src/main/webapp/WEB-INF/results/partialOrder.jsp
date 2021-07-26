<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fieldset class="flex-item-100">
   <caption>Fatturazione E Spedizione</caption>

   <h4>Spedizione Scelta: ${ordine.spedizione.nome}</h4>
   <c:if test="${ordine.statoOrdine != 'Salvato'}">
      <h4>Ricevuta Acquisto: ${ordine.ricevutaPagamento}</h4>
      <h4>Data Acquisto: ${ordine.dataAcquisto}</h4>
   </c:if>

   <fieldset class="flex-item-100">
      <caption>Indirizzo Spedizione</caption>

      <h4>Via e Civico: ${ordine.viaCivico}</h4>
      <h4>Citt&agrave;: ${ordine.citta}</h4>
      <h4>Regione: ${ordine.regione}</h4>
   </fieldset>

   <c:if test="${ordine.coupon != null}">
      <h4>Coupon utilizzato:</h4>
      <h4>Numero coupon: ${ordine.coupon.numeroCoupon}</h4>
      <h4>Sconto: ${ordine.coupon.sconto} &#37;</h4>
   </c:if>

</fieldset>