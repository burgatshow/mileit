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
						<fmt:message key="tyres.status.missing" />
					</div>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h1>
					<fmt:message key="tyres.title.map" />
				</h1>
			</div>
		</div>
		<form method="post">
			<c:if test="${not empty tyres}">
				<input type="hidden" name="id" value="<c:out value="${tyres.id}" />">
			</c:if>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label for="tyre"><fmt:message key="tyres.form.name" /></label> <input type="text" id="tyre" name="tyre" disabled="disabled"
							class="form-control disabled"
							value="<fmt:message key="tyres.name">
							<fmt:param value="${tyres.manufacturerName}" />
							<fmt:param value="${tyres.model}" />
						</fmt:message>">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="car"><fmt:message key="refuels.form.car" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <select id="car" class="form-control <c:if test="${validationMessages.contains('car') }">is-invalid</c:if>"
							name="car">
							<c:forEach items="${cars}" var="car">
								<option <c:if test="${refuel.car.id eq car.id}">selected="selected"</c:if> value="<c:out value="${car.id}" />"><c:out
										value="${car.friendlyName}" /> (
									<c:out value="${car.plateNumber}" />)
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<c:if test="${not empty param.changeDate}">
							<fmt:parseDate value="${param.changeDate}" var="changeDate" pattern="yyyy-MM-dd" />
						</c:if>
						<label for="changeDate"><fmt:message key="tyres.form.tyre_event.date" /></label> <input type="text" id="changeDate" name="changeDate"
							class="form-control" placeholder="<fmt:message key="tyres.form.tyre_event.date" />"
							value="<fmt:formatDate value="${changeDate}" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="odometer_start"><fmt:message key="tyres.form.odometer_start" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <input name="odometer_start" type="number"
							class="form-control <c:if test="${validationMessages.contains('odometer_start')}">is-invalid</c:if>" id="odometer_start"
							placeholder="<fmt:message key="tyres.form.odometer_start" />"
							value="<c:out value="${not empty param.odometer_start ? param.odometer_start : ''}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="odometer_end"><fmt:message key="tyres.form.odometer_end" /></label> <input name="odometer_end" type="number" class="form-control"
							id="odometer_end" placeholder="<fmt:message key="tyres.form.odometer_end" />"
							value="<c:out value="${not empty param.odometer_end ? param.odometer_end : ''}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="tyres"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>