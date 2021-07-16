<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="dashboard gradient2">
    <head>
        <title>Dashboard di ${utente.username}</title>

        <%@include file="general.jsp" %>
    </head>
    <body class="flex-container">
        <%@include file="header.jsp" %>
        <c:choose>
            <c:when test="${utente.tipo == 'Semplice'}">
                <%@include file="userPanel.jsp" %>
            </c:when>
            <c:otherwise>
                <%@include file="adminPanel.jsp" %>
            </c:otherwise>
        </c:choose>

        <main class="grid-container">
            <c:if test="${CouponTotali != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        ${CouponTotali}
                    </div>
                    <hr>
                    Coupon Totali
                </div>
            </c:if>

            <c:if test="${CouponRiscattati != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        ${CouponRiscattati}
                    </div>
                    <hr>
                    Coupon Riscattati
                </div>
            </c:if>

            <c:if test="${UtentiTotali != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                            ${UtentiTotali}
                    </div>
                    <hr>
                    Utenti Totali
                </div>
            </c:if>

            <c:if test="${ClientiTotali != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        ${ClientiTotali}
                    </div>
                    <hr>
                    Clienti Totali
                </div>
            </c:if>

            <c:if test="${OrdiniEvasi != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        ${OrdiniEvasi}
                    </div>
                    <hr>
                    Ordini Evasi
                </div>
            </c:if>

            <c:if test="${NumeroInventari != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        ${NumeroInventari}
                    </div>
                    <hr>
                    Numero Inventari
                </div>
            </c:if>

            <c:if test="${ProdottiStatistiche != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        Incasso totale: ${ProdottiStatistiche.incasso} &euro;
                        <br>
                        Quantità = ${ProdottiStatistiche.getQuantitaProdottiVenduti()}
                        <br>
                        Prodotti Venduti: ${ProdottiStatistiche.getProdottiVenduti()}
                    </div>
                    <hr>
                    Statistiche Vendite
                </div>
            </c:if>

            <c:if test="${OrdiniSalvati != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        ${OrdiniSalvati}
                    </div>
                    <hr>
                    Ordini Salvati
                </div>
            </c:if>

            <c:if test="${SpedizionePreferita != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        <c:choose>
                            <c:when test="${utente.tipo == 'Semplice'}">
                                ${SpedizionePreferita.nome}
                            </c:when>
                            <c:otherwise>
                                Nome = ${SpedizionePreferita.nome}
                                <br>
                                Utilizzi = ${SpedizionePreferita.utilizzi}
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <hr>
                    Spedizione Preferita
                </div>
            </c:if>

            <c:if test="${OrdiniTotali != null}">
                <div class="gradient3 box transition2">
                    <div class="data">
                        ${OrdiniTotali}
                    </div>
                    <hr>
                    Ordini Totali
                </div>
            </c:if>

            <c:if test="${ProdottoPreferito != null}">
                <div class="gradient3 box transition2">
                    <img src="file/${ProdottoPreferito.foto}" class="img-medium" alt="Foto Prodotto" onclick="redirect('SearchManager/prodotto?id=${ProdottoPreferito.codiceIAN}')">
                    <br>
                    <c:choose>
                        <c:when test="${utente.tipo == 'Semplice'}">
                            ${ProdottoPreferito.nome}
                        </c:when>
                        <c:otherwise>
                            Nome = ${ProdottoPreferito.nome}
                            <br>
                            Acquistato = ${ProdottoPreferito.nAcquisti}
                        </c:otherwise>
                    </c:choose>
                    <hr>
                    Prodotto Più Venduto
                </div>
            </c:if>
        </main>

        <script>
            function redirect(target)
            {
                window.location.href = target;
            }
        </script>

        <%@include file="footer.jsp"%>
    </body>
</html>