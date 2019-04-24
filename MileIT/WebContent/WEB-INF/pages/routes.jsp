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
				<c:if test="${status eq '0'}">
					<div class="alert alert-dismissible alert-success">
						<fmt:message key="status.new" />
					</div>
				</c:if>
				<c:if test="${status eq '1'}">
					<div class="alert alert-dismissible alert-success">
						<fmt:message key="status.edit" />
					</div>
				</c:if>
				<c:if test="${status eq '2'}">
					<div class="alert alert-dismissible alert-success">
						<fmt:message key="status.delete" />
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
					<fmt:message key="routes.title" />
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
				<table class="mt-3 table table-bordered table-striped table-condensed table-hover" id="cars">
					<thead class="thead-light">
						<tr>
							<th class="align-middle text-center"><fmt:message key="routes.form.car" /></th>
							<th class="align-middle text-left"><fmt:message key="routes.form.route" /></th>
							<th class="align-middle text-center"><fmt:message key="routes.form.date" /></th>
							<th class="align-middle text-center"><fmt:message key="routes.form.type" /></th>
							<th class="align-middle text-right"><fmt:message key="routes.form.distance">
									<fmt:param value="${user.distance eq '1' ? 'km' : 'mi'}" />
								</fmt:message></th>
							<th class="align-middle text-center"><fmt:message key="table.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty routes}">
							<c:forEach items="${routes}" var="r">
								<c:set var="sum" scope="page" value="${sum + r.distance}" />
								<tr>
									<td class="align-middle text-center"><c:out value="${r.car.friendlyName}" /> <br> <span class="badge badge-dark"><c:out
												value="${r.car.plateNumber}" /></span></td>
									<td class="align-middle text-left"><span class="badge badge-success"><fmt:message key="routes.form.route.from" /></span>&nbsp;<c:out
											value="${r.startPlace.name}" /><span data-toggle="tooltip" title="<c:out value="${r.startPlace.address}" />"
										class="ml-1 badge badge-pill badge-info">?</span><br> <span class="badge badge-danger"><fmt:message key="routes.form.route.to" /></span>&nbsp;<c:out
											value="${r.endPlace.name}" /><span data-toggle="tooltip" title="<c:out value="${r.endPlace.address}" />"
										class="ml-1 badge badge-pill badge-info">?</span></td>
									<td class="align-middle text-center"><fmt:formatDate type="both" value="${r.routeDatetime}" pattern="yyyy. MM. dd. HH:mm" /></td>
									<td class="align-middle text-center"><fmt:message key="routes.form.type.${r.routeType eq 'BUSINESS' ? 'business' : 'private' }" /></td>
									<td class="align-middle text-right"><fmt:formatNumber value="${r.distance}" pattern="#,##0.00" type="number" minFractionDigits="0"
											maxFractionDigits="${user.rounded eq 1 ? 0 : 2}" /></td>
									<td class="align-middle text-center"><a href="?m=update&amp;id=<c:out value="${r.id}" />" class="btn btn-primary" role="button"><fmt:message
												key="button.edit" /></a><a href="#" data-href="?m=delete&amp;id=<c:out value="${r.id}" />" class="btn btn-danger ml-2" data-toggle="modal"
										data-target="#confirm-delete" role="button"><fmt:message key="button.delete" /></a></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty routes}">
							<tr>
								<td colspan="6" class="align-middle text-center"><fmt:message key="nodata" /></td>
							</tr>
						</c:if>
					</tbody>
					<tfoot>
						<tr class="table-info">
							<td colspan="4"></td>
							<td class="align-middle text-right"><fmt:formatNumber value="${sum}" pattern="#,##0.00" type="number" minFractionDigits="0"
									maxFractionDigits="${user.rounded eq 1 ? 0 : 2}" /></td>
							<td></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="includes/deleteModal.jsp" />
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>