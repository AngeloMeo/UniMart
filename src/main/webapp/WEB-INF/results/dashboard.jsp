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
        <c:choose>
            <c:when test="${utente.tipo == 'Semplice'}">
                <%@include file="userPanel.jsp" %>
            </c:when>
            <c:otherwise>
                <%@include file="adminPanel.jsp" %>
            </c:otherwise>
        </c:choose>

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
            <c:choose>
                <c:when test="${utente.tipo == 'Semplice'}">
                    <div>Spedizione Preferita: ${SpedizionePreferita.nome}</div>
                </c:when>
                <c:otherwise>
                    <div>Spedizione Preferita: Nome = ${SpedizionePreferita.nome} Utilizzi = ${SpedizionePreferita.utilizzi}</div>
                </c:otherwise>
            </c:choose>
        </c:if>

        <c:if test="${OrdiniTotali != null}">
            <div>Ordini Totali: ${OrdiniTotali}</div>
        </c:if>

        <c:if test="${ProdottoPreferito != null}">
            <div>Prodotto Più Venduto:
                <img src="file/${ProdottoPreferito.foto}" height="100" width="100">
                <c:choose>
                    <c:when test="${utente.tipo == 'Semplice'}">
                        ${ProdottoPreferito.nome}
                    </c:when>
                    <c:otherwise>
                        Nome = ${ProdottoPreferito.nome} Acquistato = ${ProdottoPreferito.nAcquisti}
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </body>
</html>