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
					<fmt:message key="tyres.title" />
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
				<table class="mt-3 table table-bordered table-condensed table-hover" id="tyres">
					<thead class="thead-light">
						<tr>
							<th><fmt:message key="tyres.form.name" /></th>
							<th class="text-center"><fmt:message key="tyres.form.type" /></th>
							<th class="text-center"><fmt:message key="tyres.form.axis" /></th>
							<th class="text-center"><fmt:message key="tyres.form.purchasedate" /></th>
							<th class="text-center"><fmt:message key="tyres.form.car" /></th>
							<th class="text-center"><fmt:message key="table.actions" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty tyres}">
							<c:forEach items="${tyres}" var="t">
								<tr>
									<td class="align-middle"><fmt:message key="tyres.name">
											<fmt:param value="${t.manufacturerName}" />
											<fmt:param value="${t.model}" />
										</fmt:message><br> <span class="badge badge-dark"> <fmt:message key="tyres.size">
												<fmt:param value="${t.sizeW}" />
												<fmt:param value="${t.sizeH}" />
												<fmt:param value="${t.sizeR}" />
											</fmt:message>
									</span></td>
									<td class="align-middle text-center"><fmt:message
											key="${t.type eq 'WINTER' ? 'tyres.form.type.winter' : t.type eq 'SUMMER' ? 'tyres.form.type.summer' : t.type eq 'ALLSEASONS' ? 'tyres.form.type.all' : 'tyres.form.type.other'}" />
									</td>
									<td class="align-middle text-center"><fmt:message
											key="${t.axis eq 'FRONT' ? 'tyres.form.axis.front' : t.axis eq 'REAR' ? 'tyres.form.axis.rear' : t.axis eq 'BOTH' ? 'tyres.form.axis.all' : 'tyres.form.axis.none'}" /></td>
									<td class="align-middle text-center"><fmt:formatDate type="both" value="${t.purchaseDate}" pattern="yyyy. MM. dd." /></td>
									<td class="text-center align-middle"><c:out value="${t.car.friendlyName}" /> <br> <span class="badge badge-dark"><c:out
												value="${t.car.plateNumber}" /></span></td>
									<td class="align-middle text-center"><c:if test="${t.car.id eq 0}">
											<a href="?m=map&amp;id=<c:out value="${t.id}" />" class="btn btn-warning" role="button"><fmt:message key="button.map" /></a>
										</c:if> <a href="?m=update&amp;id=<c:out value="${t.id}" />" class="btn btn-primary" role="button"><fmt:message key="button.edit" /></a><a
										href="#" data-href="?m=archive&amp;id=<c:out value="${t.id}" />" class="btn btn-danger ml-2" data-toggle="modal"
										data-target="#confirm-archive" role="button"><fmt:message key="button.archive" /></a></td>
								</tr>
								<tr class="table-primary">
									<th class="align-middle text-left">First installed</th>
									<td class="align-middle text-center"><fmt:formatDate type="both" value="${t.tyreEvent.dateStart}" pattern="yyyy. MM. dd." /> <br>
										<span class="badge badge-dark"><fmt:formatNumber value="${t.tyreEvent.odometerStart}" pattern="#,##0.00" type="number"
												minFractionDigits="0" maxFractionDigits="${user.rounded eq 1 ? 0 : 2}" /></span></td>
									<th class="align-middle text-left">Last removed</th>
									<td class="align-middle text-center"><fmt:formatDate type="both" value="${t.tyreEvent.dateEnd}" pattern="yyyy. MM. dd." /> <br>
										<span class="badge badge-dark"><fmt:formatNumber value="${t.tyreEvent.odometerEnd}" pattern="#,##0.00" type="number"
												minFractionDigits="0" maxFractionDigits="${user.rounded eq 1 ? 0 : 2}" /></span></td>
									<th class="align-middle text-left">Total distance</th>
									<td class="align-middle text-right"><fmt:formatNumber value="${t.tyreEvent.totalDistance}" pattern="#,##0.00" type="number"
											minFractionDigits="0" maxFractionDigits="${user.rounded eq 1 ? 0 : 2}" /></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty tyres}">
							<tr>
								<td colspan="5" class="align-middle text-center"><fmt:message key="nodata" /></td>
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