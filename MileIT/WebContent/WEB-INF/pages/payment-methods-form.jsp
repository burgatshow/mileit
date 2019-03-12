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
	<div class="container mt-3">
		<div class="row">
			<div class="col-md-12">
				<c:if test="${status eq -2}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="pm.status.missing" />
					</div>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h1>
					<c:if test="${not empty pm}">
						<fmt:message key="pm.title.edit" />
					</c:if>
					<c:if test="${empty pm}">
						<fmt:message key="pm.title.new" />
					</c:if>
				</h1>
			</div>
		</div>
		<form method="post">
			<c:if test="${not empty pm}">
				<input type="hidden" name="id" value="<c:out value="${pm.id}" />">
			</c:if>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label for="name"><fmt:message key="pm.form.name" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <input name="name" type="text"
							class="form-control <c:if test="${validationMessages.contains('name') }">is-invalid</c:if>" id="name"
							placeholder="<fmt:message key="pm.form.name" />" value="<c:out value="${not empty param.name ? param.name : not empty pm ? pm.name : ''}" />">
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label for="description"><fmt:message key="pm.form.description" /></label> <input name="description" type="text" class="form-control"
							id="description" placeholder="<fmt:message key="pm.form.description" />"
							value="<c:out value="${not empty param.description ? param.description : not empty pm ? pm.description : ''}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6"></div>
				<div class="col-md-6 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="payment_methods"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>