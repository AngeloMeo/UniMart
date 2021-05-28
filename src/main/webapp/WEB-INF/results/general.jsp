<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${forward != null}">
   <c:set var="path" scope="page" value="."/>
</c:if>

<meta charset="utf-8">
<link href="${path}./css/normalize.css" type="text/css" rel="stylesheet">
<link href="${path}./css/general.css" type="text/css" rel="stylesheet">
<meta name="viewport" content="width=device-width, viewport-fit=cover, initial-scale=1">
<meta name="description" content="Ecommerce alimentari UniMart">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone-no">
<meta name="apple-mobile-web-app-title" content="UniMart">
<meta name="apple-mobile-web-app-status-bar-style" content="default">
<link rel="apple-touch-icon" href="${path}./icons/logo.svg">
<link rel="apple-touch-startup-image" href="${path}./icons/logo.svg">
<meta name="theme-color" content="#1583bf">
<link rel="icon" href="${path}./icons/logo.svg">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>