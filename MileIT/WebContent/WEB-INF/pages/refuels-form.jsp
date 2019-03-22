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
						<fmt:message key="refuels.status.missing" />
					</div>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h1>
					<c:if test="${not empty refuel}">
						<fmt:message key="refuels.title.edit" />
					</c:if>
					<c:if test="${empty refuel}">
						<fmt:message key="refuels.title.new" />
					</c:if>
				</h1>
			</div>
		</div>
		<form method="post">
			<c:if test="${not empty refuel}">
				<input type="hidden" name="id" value="<c:out value="${refuel.id}" />">
			</c:if>
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
						<label for="location"><fmt:message key="refuels.form.place" /></label> <select id="location" class="form-control" name="location">
							<option value="" <c:if test="${empty refuel}">selected="selected"</c:if>>---</option>
							<c:forEach items="${locations}" var="l">
								<option <c:if test="${refuel.location.id eq l.id}">selected="selected"</c:if> value="<c:out value="${l.id}" />"><c:out
										value="${l.name}" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="odometer"><fmt:message key="refuels.form.odometer" /></label> <input name="odometer" type="text" class="form-control" id="odometer"
							placeholder="<fmt:message key="refuels.form.odometer" />"
							value="<c:out value="${not empty param.odometer ? param.odometer : not empty refuel ? refuel.odometer : ''}"/>">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="paymentMethod"><fmt:message key="refuels.form.pm" /></label> <select id="paymentMethod" class="form-control" name="paymentMethod">
							<c:forEach items="${paymentMethods}" var="pm">
								<option <c:if test="${refuel.payment.id eq pm.id}">selected="selected"</c:if> value="<c:out value="${pm.id}" />"><c:out
										value="${pm.name}" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="unitPrice"> <fmt:message key="refuels.form.unitprice">
								<fmt:param value="${user.currency}" />
							</fmt:message> <span class="ml-1 badge badge-pill badge-primary"> <fmt:message key="form.mandatory" /></span></label> <input name="unitPrice" type="text"
							class="form-control <c:if test="${validationMessages.contains('unitPrice') }">is-invalid</c:if>" id="unitPrice"
							placeholder="<fmt:message key="refuels.form.unitprice"><fmt:param value="${user.currency}" /></fmt:message>"
							value="<c:out value="${not empty param.unitPrice ? param.unitPrice : not empty refuel ? refuel.unitPrice : ''}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="amount"><fmt:message key="refuels.form.amount">
								<fmt:param value="${user.currency}" />
							</fmt:message><span class="ml-1 badge badge-pill badge-primary"> <fmt:message key="form.mandatory" /></span></label> <input name="amount" type="text"
							class="form-control <c:if test="${validationMessages.contains('amount') }">is-invalid</c:if>" id="amount"
							placeholder="<fmt:message key="refuels.form.amount"><fmt:param value="${user.currency}" /></fmt:message>"
							value="<c:out value="${not empty param.amount ? param.amount : not empty refuel ? refuel.amount : ''}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<c:if test="${not empty param.refuelDate}">
							<fmt:parseDate value="${param.refuelDate}" var="refuelDate" pattern="yyyy-MM-dd" />
						</c:if>
						<c:if test="${not empty refuel}">
							<c:set var="refuelDate" value="${refuel.refuelDate}" />
						</c:if>
						<label for="refuelTimestamp"><fmt:message key="refuels.form.date" /></label> <input type="text" id="refuelDate" name="refuelTimestamp"
							class="form-control" placeholder="<fmt:message key="refuels.form.date" />"
							value="<fmt:formatDate value="${refuelDate}" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6"></div>
				<div class="col-md-6 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="refuels"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>