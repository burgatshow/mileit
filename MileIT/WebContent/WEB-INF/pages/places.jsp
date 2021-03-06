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
					<fmt:message key="places.title" />
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
							<th class="align-middle text-left"><fmt:message key="places.form.name" /></th>
							<th class="align-middle text-center"><fmt:message key="places.form.fuel_station" /></th>
							<th class="align-middle text-center"><fmt:message key="table.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty places}">
							<c:forEach items="${places}" var="l">
								<tr>
									<td class="align-middle"><c:out value="${l.name}" /><br> <span class="badge badge-dark"><c:out value="${l.address}" /></span></td>
									<td class="align-middle text-center"><img class="img-fluid icon"
										title="<fmt:message key="places.form.fuel_station.alt_${l.fuelStation ? 'yes' : 'no'}" />"
										alt="<fmt:message key="places.form.fuel_station.alt_${l.fuelStation ? 'yes' : 'no'}" />"
										src="img/<c:out value="${l.fuelStation ? 'petrol-station' : 'house'}"></c:out>.svg"></td>
									<td class="align-middle text-center"><a href="?m=update&amp;id=<c:out value="${l.id}" />" class="btn btn-primary" role="button"><fmt:message
												key="button.edit" /></a><a href="#" data-href="?m=archive&amp;id=<c:out value="${l.id}" />" class="btn btn-danger ml-2" data-toggle="modal"
										data-target="#confirm-archive" role="button"><fmt:message key="button.archive" /></a></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty places}">
							<tr>
								<td colspan="2" class="align-middle text-center"><fmt:message key="nodata" /></td>
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