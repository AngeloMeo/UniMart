<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
    <head>
        <title>${title}</title>
        <%@include file="general.jsp"%>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="adminPanel.jsp" %>
        <c:set var="index" scope="page" value="0"></c:set>
        <main class="flex-container">
            <c:choose>
                <c:when test="${inventario.possiedeList == null}">
                    <h1>Nessun prodotto creato...</h1>
                </c:when>
                <c:otherwise>
                    <form action="GiacenzeManager/Modify" method="post">
                        <input type="hidden" name="codiceInventario" id="codiceInventario" value="${inventario.codiceInventario}">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Codice IAN</th>
                                    <th>Nome</th>
                                    <th>Categoria</th>
                                    <th>Prezzo</th>
                                    <th>Foto</th>
                                    <th>Giacenza</th>
                                </tr>
                            </thead>
                            <c:forEach items="${inventario.possiedeList}" var="list">
                                <tr>
                                    <td data-label="IAN">
                                            ${list.prodotto.codiceIAN}
                                    </td>

                                    <input type="hidden" name="codiceIAN${index}" value="${list.prodotto.codiceIAN}">

                                    <td data-label="Nome">
                                            ${list.prodotto.nome}
                                    </td>

                                    <td data-label="Categoria">
                                            ${list.prodotto.categoria.nome}
                                    </td>

                                    <td data-label="Prezzo">
                                            ${list.prodotto.prezzo}
                                    </td>

                                    <td data-label="Foto">
                                        <c:if test="${not empty list.prodotto.foto}">
                                            <img src="${pageContext.request.contextPath}/file/${list.prodotto.foto}" class="img-medium" alt="Foto Prodotto">
                                        </c:if>
                                    </td>
                                    <td data-label="Giacenza">
                                        <input type="number" name="giacenza${index}" value="${list.giacenza}">
                                    </td>
                                </tr>
                                <c:set var="index" value="${index+1}"/>
                            </c:forEach>
                    </table>
                    <div class="flex-container flex-dirCol justify-content-center">
                        <button type="submit" class="flex-item-50 btn btn-verde">Salva modifiche</button>
                        <button type="button" onclick="javascript:history.go(-1)" class="flex-item-50 btn btn-secondary">Indietro</button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
        </main>
    </body>
</html>
