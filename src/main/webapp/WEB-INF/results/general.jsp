<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="${pageContext.request.contextPath}/css/normalize.css" type="text/css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/general.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/slick/slick-theme.css"/>
<link rel="apple-touch-icon" href="${pageContext.request.contextPath}/icons/logo.svg">
<link rel="apple-touch-startup-image" href="${pageContext.request.contextPath}/icons/logo.svg">
<link rel="icon" href="${pageContext.request.contextPath}/icons/favicon.png">
<meta name="viewport" content="width=device-width, viewport-fit=cover, initial-scale=1">
<meta name="description" content="E-commerce alimentari UniMart">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone-no">
<meta name="apple-mobile-web-app-title" content="UniMart">
<meta name="apple-mobile-web-app-status-bar-style" content="default">
<meta name="theme-color" content="#1583bf">
<meta charset="utf-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" defer></script>
<script src="${pageContext.request.contextPath}/js/nav/nav.js" defer></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/slick/slick.min.js" defer></script>

<c:set scope="application" value="${pageContext.request.contextPath}" var="context"></c:set>
<script>
   var context = '';

   function getPageContext()
   {
      if(context === '')
         context = window.location.origin + window.location.pathname.substring(0, window.location.pathname.indexOf('/',1));

      return context;
   }
</script>