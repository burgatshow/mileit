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
					<c:if test="${not empty tyres}">
						<fmt:message key="tyres.title.edit" />
					</c:if>
					<c:if test="${empty tyres}">
						<fmt:message key="tyres.title.new" />
					</c:if>
				</h1>
			</div>
		</div>
		<form method="post">
			<c:if test="${not empty tyres}">
				<input type="hidden" name="id" value="<c:out value="${tyres.id}" />">
			</c:if>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="manufacturer"><fmt:message key="tyres.form.manufacturer" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <select id="manufacturer" name="manufacturer"
							class="form-control <c:if test="${validationMessages.contains('manufacturer')}">is-invalid</c:if>">
							<c:forEach items="${tyreVendors}" var="tyreManufacturer">
								<option value="<c:out value="${tyreManufacturer.key}" />"
									<c:if test="${not empty tyres and tyres.manufacturerId eq tyreManufacturer.key}">selected="selected"</c:if>><c:out
										value="${tyreManufacturer.value}" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="model"><fmt:message key="tyres.form.model" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <input name="model" type="text"
							class="form-control <c:if test="${validationMessages.contains('model')}">is-invalid</c:if>" id="model"
							placeholder="<fmt:message key="tyres.form.model" />"
							value="<c:out value="${not empty param.model ? param.model : not empty tyres ? tyres.model : ''}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label for="width"><fmt:message key="tyres.form.size.width" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
											key="form.mandatory" /></span></label> <input name="width" type="number"
									class="form-control <c:if test="${validationMessages.contains('width')}">is-invalid</c:if>" id="width"
									placeholder="<fmt:message key="tyres.form.size.width" />"
									value="<c:out value="${not empty param.width ? param.width : not empty tyres ? tyres.sizeW : ''}" />">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="height"><fmt:message key="tyres.form.size.height" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
											key="form.mandatory" /></span></label> <input name="height" type="number"
									class="form-control <c:if test="${validationMessages.contains('height')}">is-invalid</c:if>" id="height"
									placeholder="<fmt:message key="tyres.form.size.height" />"
									value="<c:out value="${not empty param.height ? param.height : not empty tyres ? tyres.sizeH : ''}" />">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="radius"><fmt:message key="tyres.form.size.radius" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
											key="form.mandatory" /></span></label> <input name="radius" type="number"
									class="form-control <c:if test="${validationMessages.contains('radius')}">is-invalid</c:if>" id="radius"
									placeholder="<fmt:message key="tyres.form.size.radius" />"
									value="<c:out value="${not empty param.radius ? param.radius : not empty tyres ? tyres.sizeR : ''}" />">
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="type"><fmt:message key="tyres.form.type" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
											key="form.mandatory" /></span></label> <select id="type" name="type"
									class="form-control <c:if test="${validationMessages.contains('type')}">is-invalid</c:if>">
									<option value="1" <c:if test="${not empty tyres and tyres.type eq 'WINTER'}">selected="selected"</c:if>><fmt:message
											key="tyres.form.type.winter" /></option>
									<option value="2" <c:if test="${not empty tyres and tyres.type eq 'SUMMER'}">selected="selected"</c:if>><fmt:message
											key="tyres.form.type.summer" /></option>
									<option value="3" <c:if test="${not empty tyres and tyres.type eq 'ALLSEASONS'}">selected="selected"</c:if>><fmt:message
											key="tyres.form.type.all" /></option>
									<option value="4" <c:if test="${not empty tyres and tyres.type eq 'OTHER'}">selected="selected"</c:if>><fmt:message
											key="tyres.form.type.other" /></option>
								</select>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="axis"><fmt:message key="tyres.form.axis" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
											key="form.mandatory" /></span></label> <select id="axis" name="axis"
									class="form-control <c:if test="${validationMessages.contains('axis')}">is-invalid</c:if>">
									<option value="1" <c:if test="${not empty tyres and tyres.axis eq 'FRONT'}">selected="selected"</c:if>><fmt:message
											key="tyres.form.axis.front" /></option>
									<option value="2" <c:if test="${not empty tyres and tyres.axis eq 'REAR'}">selected="selected"</c:if>><fmt:message
											key="tyres.form.axis.rear" /></option>
									<option value="3" <c:if test="${not empty tyres and tyres.axis eq 'BOTH'}">selected="selected"</c:if>><fmt:message
											key="tyres.form.axis.all" /></option>
									<option value="4" <c:if test="${not empty tyres and tyres.axis eq 'NONE'}">selected="selected"</c:if>><fmt:message
											key="tyres.form.axis.none" /></option>
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<c:if test="${not empty param.purchaseDate}">
							<fmt:parseDate value="${param.purchaseDate}" var="purchaseDate" pattern="yyyy-MM-dd" />
						</c:if>
						<c:if test="${not empty tyres}">
							<c:set var="purchaseDate" value="${tyres.purchaseDate}" />
						</c:if>
						<label for="purchaseDate"><fmt:message key="refuels.form.date" /></label> <input type="text" id="purchaseDate" name="purchaseDate"
							class="form-control" placeholder="<fmt:message key="tyres.form.purchasedate" />"
							value="<fmt:formatDate value="${purchaseDate}" pattern="yyyy-MM-dd"/>">
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