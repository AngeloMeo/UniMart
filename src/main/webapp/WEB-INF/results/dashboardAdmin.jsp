<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard di ${utente.username}</title>

        <%@include file="general.jsp" %>
        <link href="${pageContext.request.contextPath}/css/dashboardPages.css" type="text/css" rel="stylesheet">
    </head>
    <body class="sidenavpresent">
        <%@include file="adminPanel.jsp" %>

        <c:if test="${CouponTotali != null}">
            <div>Coupon Totali: ${CouponTotali}</div>
        </c:if>

        <c:if test="${CouponRiscattati != null}">
            <div>Coupon Riscattati: ${CouponRiscattati}</div>
        </c:if>

        <c:if test="${UtentiTotali != null}">
            <div>Utenti Totali: ${UtentiTotali}</div>
        </c:if>

        <c:if test="${ClientiTotali != null}">
            <div>Clienti Totali: ${ClientiTotali}</div>
        </c:if>

        <c:if test="${OrdiniEvasi != null}">
            <div>Ordini Evasi: ${OrdiniEvasi}</div>
        </c:if>

        <c:if test="${NumeroInventari != null}">
            <div>Numero Inventari: ${NumeroInventari}</div>
        </c:if>

        <c:if test="${ProdottiStatistiche != null}">
            <div>Statistiche vendite: Incasso totale: ${ProdottiStatistiche.incasso} Quantita = ${ProdottiStatistiche.getQuantitaProdottiVenduti()} ProdottiVenduti: ${ProdottiStatistiche.getProdottiVenduti()}</div>
        </c:if>

        <c:if test="${OrdiniSalvati != null}">
            <div>Ordini Salvati: ${OrdiniSalvati}</div>
        </c:if>

        <c:if test="${SpedizionePreferita != null}">
            <div>Spedizione Preferita: Nome = ${SpedizionePreferita.nome} Utilizzi = ${SpedizionePreferita.utilizzi}</div>
        </c:if>

        <c:if test="${OrdiniTotali != null}">
            <div>Ordini Totali: ${OrdiniTotali}</div>
        </c:if>

        <c:if test="${ProdottoPreferito != null}">
            <div>Prodotto Più Venduto:
                <img src="file/${ProdottoPreferito.foto}" height="100" width="100">
                Nome = ${ProdottoPreferito.nome} Acquistato = ${ProdottoPreferito.nAcquisti}
            </div>
        </c:if>
    </body>
</html>