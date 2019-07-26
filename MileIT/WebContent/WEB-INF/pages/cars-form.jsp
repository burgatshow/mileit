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
				<c:if test="${status eq -2}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="status.missing" />
					</div>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h1 class="display-4">
					<c:if test="${not empty cars}">
						<fmt:message key="cars.cars.title.edit" />
					</c:if>
					<c:if test="${empty cars}">
						<fmt:message key="cars.cars.title.new" />
					</c:if>
				</h1>
			</div>
		</div>
		<form method="post">
			<input type="hidden" name="ct" value="<c:out value="${ct}" />">
			<c:if test="${not empty cars}">
				<input type="hidden" name="id" value="<c:out value="${cars.id}" />">
			</c:if>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label for="friendlyName"><fmt:message key="cars.form.friendlyname" /></label> <input name="friendlyName" type="text" class="form-control"
							id="friendlyName" placeholder="<fmt:message key="cars.form.friendlyname" />"
							value="<c:out value="${not empty param.friendlyName ? param.friendlyName : not empty cars ? cars.friendlyName : ''}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="manufacturer"><fmt:message key="cars.form.manufacturer" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <select id="manufacturer" name="manufacturer"
							class="form-control <c:if test="${validationMessages.contains('manufacturer') }">is-invalid</c:if>">
							<c:forEach items="${carVendors}" var="carManufacturer">
								<option value="<c:out value="${carManufacturer.key}" />"
									<c:if test="${not empty cars and cars.manufacturer eq carManufacturer.key}">selected="selected"</c:if>><c:out
										value="${carManufacturer.value}" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="model"><fmt:message key="cars.form.model" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <input name="model" type="text"
							class="form-control <c:if test="${validationMessages.contains('model') }">is-invalid</c:if>" id="model"
							placeholder="<fmt:message key="cars.form.model" />"
							value="<c:out value="${not empty param.model ? param.model : not empty cars ? cars.model : ''}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="manufactureDate"><fmt:message key="cars.form.manufacturedate" /></label> <input name="manufactureDate" type="number"
							class="form-control" id="manufactureDate" placeholder="<fmt:message key="cars.form.manufacturedate" />"
							value="<fmt:formatDate value="${not empty param.manufacturerDate ? param.manufacturerDate : not empty cars ? cars.manufacturerDate : ''}" pattern="yyyy" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="color"><fmt:message key="cars.form.color" /></label>
						<div id="color" class="input-group">
							<input name="color" id="color" type="text" value="<c:out value="${not empty param.color ? param.color : not empty cars ? cars.color : ''}" />"
								class="form-control" /> <span class="input-group-append"> <span class="input-group-text colorpicker-input-addon"><i></i></span>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="VIN"><fmt:message key="cars.form.vin" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <input name="vin" type="text"
							class="form-control <c:if test="${validationMessages.contains('vin') }">is-invalid</c:if>" id="vin"
							placeholder="<fmt:message key="cars.form.vin" />" value="<c:out value="${not empty param.vin ? param.vin : not empty cars ? cars.vin : ''}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="plateNumber"><fmt:message key="cars.form.platenumber" /><span class="ml-1 badge badge-pill badge-primary"> <fmt:message
									key="form.mandatory" /></span></label> <input name="plateNumber" type="text"
							class="form-control <c:if test="${validationMessages.contains('plateNumber') }">is-invalid</c:if>" id="plateNumber"
							placeholder="<fmt:message key="cars.form.platenumber" />"
							value="<c:out value="${not empty param.plateNumber ? param.plateNumber : not empty cars ? cars.plateNumber : ''}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="fuelCapacity"><fmt:message key="cars.form.fuelcapacity" /></label> <input name="fuelCapacity" type="text" class="form-control"
							id="fuelCapacity" placeholder="<fmt:message key="cars.form.fuelcapacity" />"
							value="<c:out value="${not empty param.fuelCapacity ? param.fuelCapacity : not empty cars ? cars.fuelCapacity : ''}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="fuel"><fmt:message key="cars.form.fuel" /></label> <select id="fuel"
							class="form-control <c:if test="${validationMessages.contains('fuel') }">is-invalid</c:if>" name="fuel">
							<option value="1" <c:if test="${not empty cars and cars.fuel eq 'PETROL'}">selected="selected"</c:if>><fmt:message
									key="cars.form.fuel.petrol" /></option>
							<option value="2" <c:if test="${not empty cars and cars.fuel eq 'DIESEL'}">selected="selected"</c:if>><fmt:message
									key="cars.form.fuel.diesel" /></option>
							<option value="3" <c:if test="${not empty cars and cars.fuel eq 'ELECTRIC'}">selected="selected"</c:if>><fmt:message
									key="cars.form.fuel.electric" /></option>
							<option value="4" <c:if test="${not empty cars and cars.fuel eq 'BIOETHANOL'}">selected="selected"</c:if>><fmt:message
									key="cars.form.fuel.bioethanol" /></option>
							<option value="5" <c:if test="${not empty cars and cars.fuel eq 'OTHER'}">selected="selected"</c:if>><fmt:message
									key="cars.form.fuel.other" /></option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<c:if test="${not empty param.startDate }">
							<fmt:parseDate value="${param.startDate}" var="startDate" pattern="yyyy-MM-dd" />
						</c:if>
						<c:if test="${not empty cars}">
							<c:set var="startDate" value="${cars.startDate }" />
						</c:if>
						<label for="startDate"><fmt:message key="cars.form.startdate" /></label> <input type="text" id="startDate" name="startDate"
							class="form-control" placeholder="<fmt:message key="cars.form.startdate" />"
							value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<c:if test="${not empty param.endDate }">
							<fmt:parseDate value="${param.endDate}" var="endDate" pattern="yyyy-MM-dd" />
						</c:if>
						<c:if test="${not empty cars}">
							<c:set var="endDate" value="${cars.endDate }" />
						</c:if>
						<label for="endDate"><fmt:message key="cars.form.enddate" /></label> <input name="endDate" type="text" class="form-control" id="endDate"
							placeholder="<fmt:message key="cars.form.enddate" />" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="description"><fmt:message key="cars.form.description" /></label> <input name="description" type="text" class="form-control"
							id="description" placeholder="<fmt:message key="cars.form.description" />"
							value="<c:out value="${not empty param.description ? param.description : not empty cars ? cars.description : ''}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="status"><fmt:message key="cars.form.status" /></label> <select id="status"
							class="form-control <c:if test="${validationMessages.contains('status') }">is-invalid</c:if>" name="status">
							<option value="1" <c:if test="${not empty cars and cars.active}">selected="selected"</c:if>><fmt:message
									key="cars.form.status.primary" /></option>
							<option value="0" <c:if test="${not empty cars and not cars.active}">selected="selected"</c:if>><fmt:message
									key="cars.form.status.notprimary" /></option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="cars"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>