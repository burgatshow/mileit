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
						<fmt:message key="status.missing" />
					</div>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h1>
					<c:if test="${not empty routes}">
						<fmt:message key="routes.title.edit" />
					</c:if>
					<c:if test="${empty routes}">
						<fmt:message key="routes.title.new" />
					</c:if>
				</h1>
			</div>
		</div>
		<form method="post">
			<c:if test="${not empty routes.id}">
				<input type="hidden" name="id" value="<c:out value="${routes.id}" />">
			</c:if>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="car"><fmt:message key="routes.form.car" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <select id="car" class="form-control <c:if test="${validationMessages.contains('car') }">is-invalid</c:if>"
							name="car">
							<c:forEach items="${cars}" var="car">
								<option <c:if test="${routes.car.id eq car.id}">selected="selected"</c:if> value="<c:out value="${car.id}" />"><c:out
										value="${car.friendlyName}" /> (
									<c:out value="${car.plateNumber}" />)
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<label for="type"><fmt:message key="routes.form.type" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
								key="form.mandatory" /></span></label> <select id="type" class="form-control <c:if test="${validationMessages.contains('type') }">is-invalid</c:if>"
						name="type">
						<option value="1"
							<c:out value="${not empty param.type and param.type eq 1 ? 'selected=\"selected\"' : not empty routes and routes.routeType eq 'BUSINESS' ? 'selected=\"selected\"' : ''}" />><fmt:message
								key="routes.form.type.business" /></option>
						<option value="0"
							<c:out value="${not empty param.type and param.type eq 0 ? 'selected=\"selected\"' : not empty routes and routes.routeType eq 'PRIVATE' ? 'selected=\"selected\"' : ''}" />><fmt:message
								key="routes.form.type.private" /></option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="startPlace"><fmt:message key="routes.form.route.from" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <select id="startPlace"
							class="form-control <c:if test="${validationMessages.contains('startPlace') }">is-invalid</c:if>" name="startPlace">
							<option value="" <c:if test="${empty routes}">selected="selected"</c:if>>---</option>
							<c:forEach items="${places}" var="p">
								<option
									<c:out value="${not empty param.startPlace and param.startPlace eq p.id ? 'selected=\"selected\"' : not empty routes and routes.startPlace.id eq p.id ? 'selected=\"selected\"' : ''}" />
									value="<c:out value="${p.id}" />"><c:out value="${p.name}" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="endPlace"><fmt:message key="routes.form.route.to" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <select id="endPlace"
							class="form-control <c:if test="${validationMessages.contains('endPlace') }">is-invalid</c:if>" name="endPlace">
							<option value="" <c:if test="${empty routes}">selected="selected"</c:if>>---</option>
							<c:forEach items="${places}" var="p">
								<option
									<c:out value="${not empty param.endPlace and param.endPlace eq p.id ? 'selected=\"selected\"' : not empty routes and routes.endPlace.id eq p.id ? 'selected=\"selected\"' : ''}" />
									value="<c:out value="${p.id}" />"><c:out value="${p.name}" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="distance"> <fmt:message key="routes.form.distance">
								<fmt:param value="${user.distance eq 1 ? 'km' : 'mi'}" />
							</fmt:message></label> <input name="distance" type="number" class="form-control <c:if test="${validationMessages.contains('distance') }">is-invalid</c:if>"
							id="distance"
							value="<fmt:formatNumber value="${not empty param.distance ? param.distance : not empty routes ? routes.distance : ''}" type="number" pattern="#" minFractionDigits="0" maxFractionDigits="${user.rounded eq 1 ? '0' : '2'}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<c:if test="${not empty param.routeDatetime}">
							<fmt:parseDate value="${param.routeDatetime}" var="routeDatetime" pattern="yyyy-MM-dd HH:mm" />
						</c:if>
						<c:if test="${not empty routes}">
							<c:set var="routeDatetime" value="${routes.routeDatetime}" />
						</c:if>
						<label for="routeDatetime"><fmt:message key="routes.form.date" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <input type="text" id="routeDatetime" name="routeDatetime"
							class="form-control <c:if test="${validationMessages.contains('routeDatetime') }">is-invalid</c:if>"
							placeholder="<fmt:message key="routes.form.date" />" value="<fmt:formatDate value="${routeDatetime}" pattern="yyyy-MM-dd HH:mm"/>">
					</div>
				</div>
			</div>
			<c:if test="${empty routes.id}">
				<div class="row">
					<div class="col-md-12">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" value="1" name="roundTrip" id="roundTrip"> <label class="form-check-label"
								for="roundTrip"> <fmt:message key="routes.form.roundtrip" />
							</label> <small id="roundTripHelp" class="form-text text-muted"> <fmt:message key="routes.form.roundtrip.help" />
							</small>
						</div>
					</div>
				</div>
			</c:if>
			<div class="row">
				<div class="col-md-12 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="routes"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>