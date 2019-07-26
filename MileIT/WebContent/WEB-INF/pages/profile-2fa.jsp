<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
	<jsp:include page="includes/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<c:if test="${status eq -1}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="profile.status.nok" />
					</div>
				</c:if>
				<c:if test="${status eq -2}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="profile.status.2fa_verification_failed" />
					</div>
				</c:if>
				<c:if test="${status eq -3}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="profile.status.2fa_general_error" />
					</div>
				</c:if>
				<c:if test="${status eq -4}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="profile.status.2fa_verification_timedout" />
					</div>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h1 class="display-4">
					<fmt:message key="profile.2fa.title" />
				</h1>
			</div>
		</div>
		<ol>
			<c:forEach items="1,2,3,4,5" var="i">
				<div class="row">
					<div class="col-md-12">
						<li><fmt:message key="profile.2fa.setup.${i}" /></li>
					</div>
				</div>
			</c:forEach>
		</ol>
		<hr>
		<div class="row">
			<div class="col-md-12 text-center">
				<img alt="QR" src="qr_code?s=${two_fa_secret}">
			</div>
		</div>
		<hr>
		<form method="post">
			<input type="hidden" name="ct" value="<c:out value="${ct}" />">
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div class="form-group">
						<label for="totp_code"><fmt:message key="profile.2fa.setup.verification" /></label> <input name="totp_code" type="number" class="form-control"
							id="totp_code">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="col-md-4 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="profile"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
				<div class="col-md-4"></div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>