<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctxRoot" value="${pageContext.request.contextPath}" />

<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="hu.thom.mileit.i18n.mileit" />

<!DOCTYPE html>
<html lang="<c:out value="${lang}" />">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="application-name" content="<fmt:message key="appname" />" />

<title><fmt:message key="appname" /></title>
<jsp:include page="includes/appResources.jsp" />
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<a class="navbar-brand" href="."> <fmt:message key="appname" /></a>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<c:if test="${status eq -2}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="status.missing" />
					</div>
				</c:if>
				<c:if test="${status eq -1}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="status.login" />
					</div>
				</c:if>
			</div>
		</div>
		<form action="login" method="post" class="mt-3">
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div class="form-group">
						<label for="username"><fmt:message key="login.username" /></label> <input type="text" name="username"
							class="form-control <c:if test="${validationMessages.contains('username') }">is-invalid</c:if>" id="username"
							placeholder="<fmt:message key="login.username" />">
					</div>
					<div class="form-group">
						<label for="password"><fmt:message key="login.password" /></label> <input type="password" name="password"
							class="form-control <c:if test="${validationMessages.contains('password') }">is-invalid</c:if>" id="password"
							placeholder="<fmt:message key="login.password" />">
					</div>
				</div>
				<div class="col-md-4"></div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4 text-right">
					<button type="submit" class="btn btn-primary">
						<fmt:message key="login.button" />
					</button>
				</div>
				<div class="col-md-4"></div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>
