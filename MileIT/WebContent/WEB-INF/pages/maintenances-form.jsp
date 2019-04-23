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
					<c:if test="${not empty maintenances}">
						<fmt:message key="mntnc.title.edit" />
					</c:if>
					<c:if test="${empty maintenances}">
						<fmt:message key="mntnc.title.new" />
					</c:if>
				</h1>
			</div>
		</div>
		<form method="post">
			<c:if test="${not empty maintenances}">
				<input type="hidden" name="id" value="<c:out value="${maintenances.id}" />">
			</c:if>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="car"><fmt:message key="refuels.form.car" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <select id="car" class="form-control <c:if test="${validationMessages.contains('car') }">is-invalid</c:if>"
							name="car">
							<c:forEach items="${cars}" var="car">
								<option <c:if test="${maintenance.car.id eq car.id}">selected="selected"</c:if> value="<c:out value="${car.id}" />"><c:out
										value="${car.friendlyName}" /> (
									<c:out value="${car.plateNumber}" />)
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="odometer"><fmt:message key="mntnc.form.odometer">
								<fmt:param value="${user.distance eq 1 ? 'km' : 'mi'}" />
							</fmt:message> <span class="ml-1 badge badge-pill badge-primary"> <fmt:message key="form.mandatory" /></span></label> <input name="odometer" type="text"
							class="form-control <c:if test="${validationMessages.contains('odometer') }">is-invalid</c:if>" id="odometer"
							placeholder="<fmt:message key="mntnc.form.odometer"><fmt:param value="${user.distance eq 1 ? 'km' : 'mi'}" /></fmt:message>"
							value="<fmt:formatNumber value="${not empty param.odometer ? param.odometer : not empty maintenances ? maintenances.odometer : ''}" type="number" pattern="#" minFractionDigits="0" maxFractionDigits="${user.rounded eq 1 ? '0' : '2'}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">

					<div class="form-group">
						<label for="paymentMethod"><fmt:message key="mntnc.form.payment" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <select id="paymentMethod"
							class="form-control <c:if test="${validationMessages.contains('paymentMethod') }">is-invalid</c:if>" name="paymentMethod">
							<c:forEach items="${pms}" var="pm">
								<option <c:if test="${maintenances.payment.id eq pm.id}">selected="selected"</c:if> value="<c:out value="${pm.id}" />"><c:out
										value="${pm.name}" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="amount"><fmt:message key="mntnc.form.amount">
								<fmt:param value="${user.currency}" />
							</fmt:message><span class="ml-1 badge badge-pill badge-primary"> <fmt:message key="form.mandatory" /></span></label> <input name="amount" type="text"
							class="form-control <c:if test="${validationMessages.contains('amount') }">is-invalid</c:if>" id="amount"
							placeholder="<fmt:message key="mntnc.form.amount"><fmt:param value="${user.currency}" /></fmt:message>"
							value="<fmt:formatNumber value="${not empty param.amount ? param.amount : not empty maintenances ? maintenances.amount : ''}" type="number" pattern="#" minFractionDigits="0" maxFractionDigits="${user.rounded eq 1 ? '0' : '2'}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<c:if test="${not empty param.maintenanceDate}">
							<fmt:parseDate value="${param.maintenanceDate}" var="maintenanceDate" pattern="yyyy-MM-dd" />
						</c:if>
						<c:if test="${not empty maintenances}">
							<c:set var="maintenanceDate" value="${maintenances.maintenanceDate }" />
						</c:if>
						<label for="maintenanceDate"><fmt:message key="mntnc.form.date" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <input type="text" id="maintenanceDate" name="maintenanceDate"
							class="form-control <c:if test="${validationMessages.contains('maintenanceDate') }">is-invalid</c:if>"
							placeholder="<fmt:message key="mntnc.form.date" />" value="<fmt:formatDate value="${maintenanceDate}" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<div class="form-group">
							<label for="description"><fmt:message key="mntnc.form.desc" /></label> <input name="description" type="text" class="form-control"
								id="description" placeholder="<fmt:message key="mntnc.form.desc" />"
								value="<c:out value="${not empty param.description ? param.description : not empty maintenances ? maintenances.description : ''}"/>">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="maintenance"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>