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
				<c:if test="${status eq 0}">
					<div class="alert alert-dismissible alert-success">
						<fmt:message key="status.new" />
					</div>
				</c:if>
				<c:if test="${status eq 1}">
					<div class="alert alert-dismissible alert-success">
						<fmt:message key="status.edit" />
					</div>
				</c:if>
				<c:if test="${status eq -1}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="status.error" />
					</div>
				</c:if>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<h1 class="display-4">
					<fmt:message key="mntnc.title" />
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<a class="btn btn-primary" href="?m=new" role="button"> <fmt:message key="button.new" />
				</a>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<table class="mt-3 table table-bordered table-striped table-condensed table-hover" id="car">
					<thead class="thead-light">
						<tr>
							<th class="text-center"><fmt:message key="mntnc.form.car" /></th>
							<th class="text-left"><fmt:message key="mntnc.form.date" /> / <fmt:message key="mntnc.form.desc" /></th>
							<th class="text-right"><fmt:message key="mntnc.form.amount">
									<fmt:param value="${user.currency}" />
								</fmt:message></th>
							<th class="text-center"><fmt:message key="mntnc.form.payment" /></th>
							<th class="text-right"><fmt:message key="mntnc.form.odometer">
									<fmt:param value="${user.distance eq '1' ? 'km' : 'mi'}" />
								</fmt:message></th>
							<th class="text-center"><fmt:message key="mntnc.form.expiration" /></th>
							<th class="text-center"><fmt:message key="table.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty maintenances}">
							<c:forEach items="${maintenances}" var="m">
								<tr>
									<td class="text-center align-middle"><c:out value="${m.car.friendlyName}" /> <br> <span class="badge badge-dark"><c:out
												value="${m.car.plateNumber}" /></span></td>
									<td class="align-middle"><fmt:formatDate type="both" value="${m.maintenanceDate}" pattern="${user.dateFormat}" /><br> <span
										class="badge badge-dark"><c:out value="${m.description}" /></span></td>
									<td class="text-right align-middle"><fmt:formatNumber value="${m.amount}" type="number"
											maxFractionDigits="${user.rounded eq 1 ? '0' : '2'}" pattern="#,##0.00" /></td>
									<td class="text-center align-middle"><c:out value="${m.payment.name}" /></td>
									<td class="text-right align-middle"><fmt:formatNumber value="${m.odometer}" pattern="#,##0.00" type="number" minFractionDigits="0"
											maxFractionDigits="${user.rounded eq 1 ? '0' : '2'}" /></td>
									<td class="text-center align-middle"><fmt:formatDate type="both" value="${m.expiration}" pattern="${user.dateFormat} ${user.timeFormat}" /><br></td>
									<td class="text-center align-middle"><a href="?m=update&amp;id=<c:out value="${m.id}" />" class="btn btn-primary" role="button"><fmt:message
												key="button.edit" /></a></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty maintenances}">
							<tr>
								<td colspan="6" class="align-middle text-center"><fmt:message key="nodata" /></td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="includes/archiveModal.jsp" />
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>