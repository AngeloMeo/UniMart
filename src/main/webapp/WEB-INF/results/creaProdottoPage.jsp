<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="gradient2">
    <head>
        <title>${title}</title>

        <%@include file="general.jsp"%>
        <script src="${context}/js/check/prodotto.js" defer></script>
        <script src="${context}/js/validator.js" defer></script>

    </head>
    <body>
        <%@include file="header.jsp" %>
        <%@include file="adminPanel.jsp" %>

        <main class="flex-container">
            <form method="post" enctype="multipart/form-data" class="flex-item-100" id="form">
                <table class="flex-item-100">
                    <c:if test="${prodotto.codiceIAN != null}">
                        <tr class="flex-container">
                            <td class="flex-item-50">
                                <label for="codiceIAN">Codice IAN</label>
                            </td>
                            <td class="flex-item-50">
                                <input type="text" id="codiceIAN" name="codiceIAN" value="${prodotto.codiceIAN}" readonly>
                            </td>
                        </tr>
                    </c:if>
                    <tr class="flex-container">
                        <td class="flex-item-50">
                            <label for="nome">Nome</label>
                        </td>
                        <td class="flex-item-50">
                            <input type="text" id="nome" name="nome" value="${prodotto.nome}" required>
                        </td>
                    </tr>

                    <tr class="flex-container">
                        <td class="flex-item-50">
                            <label for="prezzo">Prezzo</label>
                        </td>
                        <td class="flex-item-50">
                            <input type="text" id="prezzo" name="prezzo" value="${prodotto.prezzo}" required>
                        </td>
                    </tr>

                    <tr class="flex-container">
                        <td class="flex-item-50">
                            <label for="peso">Peso</label>
                        </td>
                        <td class="flex-item-50">
                            <input type="text" id="peso" name="peso" value="${prodotto.peso}" required>
                        </td>
                    </tr>

                    <tr class="flex-container">
                        <c:if test="${not empty prodotto.foto}">
                            <td class="flex-item-40">
                                <img src="${pageContext.request.contextPath}/file/${prodotto.foto}" class="img-medium">
                            </td>
                        </c:if>

                        <td class="flex-item-25">
                            <label for="foto">Foto</label>
                        </td>

                        <td class="flex-item-25">
                            <input type="file" id="foto" name="foto">
                        </td>
                    </tr>

                    <tr class="flex-container">
                        <td class="flex-item-50">
                            <label for="volumeOccupato">Volume Occupato</label>
                        </td>
                        <td class="flex-item-50">
                            <input type="text" id="volumeOccupato" name="volumeOccupato" value="${prodotto.volumeOccupato}">
                        </td>
                    </tr>

                    <tr class="flex-container">
                        <td class="flex-item-50">
                            <label for="descrizione">Descrizione</label>
                        </td>
                        <td class="flex-item-50">
                            <input type="textarea" id="descrizione" name="descrizione" value="${prodotto.descrizione}" required>
                        </td>
                    </tr>

                    <tr class="flex-container">
                        <td class="flex-item-50">
                            <label for="categoria" class="flex-item-50">Categoria</label>
                        </td>
                        <td class="flex-item-50">
                            <select id="categoria" name="categoria">
                                <c:forEach items="${categoria}" var="cat">
                                    <c:choose>
                                        <c:when test="${prodotto.categoria.nome == cat.nome}">
                                            <option selected>
                                        </c:when>
                                        <c:otherwise>
                                            <option>
                                        </c:otherwise>
                                    </c:choose>

                                    ${cat.nome}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="flex-item-100 flex-container flex-dirCol">
                    <c:choose>
                        <c:when test="${prodotto.codiceIAN == null}">
                            <button type="submit" formaction="./creaProdotto" class="flex-item-100">Crea Nuovo Prodotto</button>
                        </c:when>

                        <c:otherwise>
                            <button type="submit" formaction="./updateProdotto" class="flex-item-40 btn-verde">Modifica Prodotto</button>
                            <button type="submit" formaction="./deleteProdotto" class="flex-item-40 btn-primary">Elimina Prodotto</button>
                        </c:otherwise>
                    </c:choose>

                    <button type="button" onclick="javascript:history.go(-1)" class="flex-item-40 btn-secondary">Indietro</button>
                </div>
            </form>
        </main>
    </body>
</html>
