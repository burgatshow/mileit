<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="lang"
	value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="hu.thom.mileit.i18n.mileit" />

<!DOCTYPE html>
<html lang="<c:out value="${lang}" />">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="application-name" content="<fmt:message key="appname" />" />

<title><fmt:message key="error.403.page.title" /></title>
<c:set var="ctxRoot" value="${pageContext.request.contextPath}" />
<link href="<c:out value="${ctxRoot}" />/css/error.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<section id="not-found">
		<div id="title">
			<fmt:message key="error.403.title" />
		</div>
		<div class="circles">
			<p>403</p>
			<span class="circle big"></span> <span class="circle med"></span> <span
				class="circle small"></span>
		</div>
	</section>
</body>
</html>