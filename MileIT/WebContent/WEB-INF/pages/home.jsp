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
	<div class="container-fluid mt-3">
		<div class="row">
			<div class="col-md-12">
				<h1 class="display-4">
					<c:if test="${empty lr.refuelDate}">
						<fmt:message key="index.stat.title.na" />
					</c:if>
					<c:if test="${not empty lr.refuelDate}">
						<fmt:message key="index.stat.title">
							<fmt:formatDate type="both" value="${lr.refuelDate}" pattern="yyyy. MM. dd." var="formatted_LR" />
							<fmt:param value="${formatted_LR}" />
						</fmt:message>
					</c:if>
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="alert alert-primary">
					<p class="h4">
						<fmt:message key="refuels.form.fuel_amount" />
					</p>
					<p class="text-right display-4">
						<strong><fmt:formatNumber type="number" pattern="###,##" minFractionDigits="0" maxFractionDigits="${user.rounded eq 1 ? '0' : '2'}"
								value="${not empty lr.fuelAmount ? lr.fuelAmount : 0}" /></strong>
					</p>
				</div>
			</div>
			<div class="col-md-4">
				<div class="alert alert-secondary">
					<p class="h4">
						<fmt:message key="refuels.form.unitprice">
							<fmt:param value="${user.currency}" />
						</fmt:message>
					</p>
					<p class="text-right display-4">
						<strong><fmt:formatNumber value="${not empty lr.unitPrice ? lr.unitPrice : 0}" type="number" pattern="###.###" minFractionDigits="0"
								maxFractionDigits="${user.rounded eq 1 ? '0' : '2'}" currencySymbol="" /></strong>
					</p>
				</div>
			</div>
			<div class="col-md-4">
				<div class="alert alert-info">
					<p class="h4">
						<fmt:message key="refuels.form.amount">
							<fmt:param value="${user.currency}" />
						</fmt:message>
					</p>
					<p class="text-right display-4">
						<strong><fmt:formatNumber value="${not empty lr.amount ? lr.amount : 0}" type="number" minFractionDigits="0"
								maxFractionDigits="${user.rounded eq 1 ? '0' : '2'}" /></strong>
					</p>
				</div>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col-md-12">
				<h1 class="display-4">
					<fmt:message key="index.fuel.price.stat.title" />
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<canvas id="priceChart" height="50"></canvas>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col-md-12">
				<h1 class="display-4">
					<fmt:message key="index.fuel.amount.stat.title" />
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<canvas id="amountChart" height="50"></canvas>
			</div>
		</div>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>