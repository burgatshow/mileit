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
					<c:if test="${not empty places}">
						<fmt:message key="places.title.edit" />
					</c:if>
					<c:if test="${empty places}">
						<fmt:message key="places.title.new" />
					</c:if>
				</h1>
			</div>
		</div>
		<form method="post">
			<c:if test="${not empty places}">
				<input type="hidden" name="id" value="<c:out value="${places.id}" />">
			</c:if>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="name"><fmt:message key="places.form.name" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span><span data-toggle="tooltip" title="<fmt:message key="places.form.name.tooltip" />"
							class="ml-1 badge badge-pill badge-info">?</span></label> <input name="name" type="text"
							class="form-control <c:if test="${validationMessages.contains('name') }">is-invalid</c:if>" id="name"
							placeholder="<fmt:message key="places.form.name" />"
							value="<c:out value="${not empty param.name ? param.name : not empty places ? places.name : ''}" />">
					</div>
				</div>
				<div class="col-md-6">
					<label for="fuelStation"><fmt:message key="places.form.fuel_station" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
								key="form.mandatory" /></span></label><select id="fuelStation" class="form-control" name="fuelStation">
						<option value="0" <c:if test="${not places.fuelStation}">selected="selected"</c:if>><fmt:message key="places.form.fuel_station.alt_no" /></option>
						<option value="1" <c:if test="${places.fuelStation}">selected="selected"</c:if>><fmt:message key="places.form.fuel_station.alt_yes" /></option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label for="address"><fmt:message key="places.form.address" /><span data-toggle="tooltip"
							title="<fmt:message key="places.form.address.tooltip" />" class="ml-1 badge badge-pill badge-info">?</span></label> <input name="address" type="text"
							class="form-control" id="address" placeholder="<fmt:message key="places.form.address" />"
							value="<c:out value="${not empty param.address ? param.address : not empty places ? places.address : ''}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="latitude"><fmt:message key="places.form.latitude" /><span data-toggle="tooltip"
							title="<fmt:message key="places.form.latitude.tooltip" />" class="ml-1 badge badge-pill badge-info">?</span></label> <input name="latitude" type="text"
							class="form-control" id="latitude" placeholder="<fmt:message key="places.form.latitude" />"
							value="<c:out value="${not empty param.latitude ? param.latitude : not empty places ? places.latitude : ''}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="longitude"><fmt:message key="places.form.longitude" /><span data-toggle="tooltip"
							title="<fmt:message key="places.form.longitude.tooltip" />" class="ml-1 badge badge-pill badge-info">?</span></label> <input name="longitude"
							type="text" class="form-control" id="longitude" placeholder="<fmt:message key="places.form.longitude" />"
							value="<c:out value="${not empty param.longitude ? param.longitude : not empty places ? places.longitude : ''}" />">
					</div>
				</div>
			</div>
			<c:if test="${not empty places and (not empty places.longitude and not empty places.latitude)}">
				<div class="row mb-3">
					<div class="col-md-12 mapCol">
						<div class="map" id="map" data-latitude="<c:out value="${places.latitude}" />" data-longitude="<c:out value="${places.longitude}" />"></div>
					</div>
				</div>
			</c:if>
			<div class="row">
				<div class="col-md-12 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="places"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>