<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${forward != null}">
    <c:set var="path" scope="page" value="."/>
</c:if>

<nav>
    <div>
        <a href="index.jsp">Index</a>
        <a href="${path}./Login/Logout">Logout</a>
        <a href="${path}./CouponManager">Coupon</a>
        <a href="${path}./InventarioManager">Inventario</a>
        <a href="${path}./CategoriaManager">Categoria</a>
        <a href="${path}./ProdottoManager">Prodotti</a>
    </div>
</nav>