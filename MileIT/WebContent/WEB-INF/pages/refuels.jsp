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
				<c:if test="${status eq '0'}">
					<div class="alert alert-dismissible alert-success">
						<fmt:message key="refuels.status.add.ok" />
					</div>
				</c:if>
				<c:if test="${status eq '1'}">
					<div class="alert alert-dismissible alert-success">
						<fmt:message key="refuels.status.edit.ok" />
					</div>
				</c:if>
				<c:if test="${status eq -1}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="refuels.status.nok" />
					</div>
				</c:if>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<h1>
					<fmt:message key="refuels.title" />
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<a class="btn btn-primary" href="?m=new" role="button"> <fmt:message key="button.new" />
				</a>
			</div>
		</div>

		<!-- Cars table -->
		<div class="row">
			<div class="col-md-12">
				<table class="mt-3 table table-bordered table-striped table-condensed table-hover" id="cars">
					<thead class="thead-light">
						<tr>
							<th class="text-center"><fmt:message key="refuels.form.car" /></th>
							<th class="text-center"><fmt:message key="refuels.form.date" /> / <fmt:message key="refuels.form.place" /></th>
							<th class="text-right"><fmt:message key="refuels.form.unitprice">
									<fmt:param value="${user.currency}" />
								</fmt:message></th>
							<th class="text-right"><fmt:message key="refuels.form.fuel_amount" /></th>
							<th class="text-right"><fmt:message key="refuels.form.amount">
									<fmt:param value="${user.currency}" />
								</fmt:message></th>
							<th class="text-center"><fmt:message key="refuels.form.odometer" /></th>
							<th class="text-center"><fmt:message key="table.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty refuels}">
							<c:forEach items="${refuels}" var="r">
								<tr>
									<td class="text-center align-middle"><c:out value="${r.car.friendlyName}" /> <br> <span class="badge badge-dark"><c:out
												value="${r.car.plateNumber}" /></span></td>
									<td class="align-middle"><c:out value="${r.location.name}" /><br> <span class="badge badge-dark"><fmt:formatDate
												type="both" value="${r.refuelTimestamp}" pattern="yyyy. MM. dd." /></span></td>
									<td class="text-right align-middle"><fmt:formatNumber value="${r.unitPrice}" type="currency" pattern="###.###" maxFractionDigits="2"
											currencySymbol="" /></td>
									<td class="text-right align-middle"><fmt:formatNumber type="number" pattern="###.##" maxFractionDigits="2" value="${r.fuelAmount}" /></td>
									<td class="text-right align-middle"><fmt:formatNumber value="${r.amount}" type="currency" maxFractionDigits="0" currencySymbol="" /></td>
									<td class="text-right align-middle"><fmt:formatNumber value="${r.odometer}" type="number" minFractionDigits="0" maxFractionDigits="0" /></td>
									<td class="align-middle text-center"><a href="?m=update&amp;id=<c:out value="${r.id}" />" class="btn btn-primary" role="button"><fmt:message
												key="button.edit" /></a></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		<!-- END Cars table -->
	</div>
	<div class="modal fade" id="confirm-archive" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<fmt:message key="archive.modal.title" />
				</div>
				<div class="modal-body">
					<fmt:message key="archive.modal.body" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<fmt:message key="button.cancel" />
					</button>
					<a class="btn btn-danger btn-ok"><fmt:message key="button.archive" /></a>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>