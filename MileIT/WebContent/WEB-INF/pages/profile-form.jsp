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
				<c:if test="${status eq 0}">
					<div class="alert alert-dismissible alert-success mt-4">
						<fmt:message key="profile.status.ok" />
					</div>
				</c:if>
				<c:if test="${status eq 100}">
					<div class="alert alert-dismissible alert-success mt-4">
						<fmt:message key="profile.status.ok_2fa" />
						<hr>
						<fmt:message key="profile.status.ok_2fa_backup_codes">
							<fmt:param value="${backup_code_0}" />
							<fmt:param value="${backup_code_1}" />
							<fmt:param value="${backup_code_2}" />
							<fmt:param value="${backup_code_3}" />
							<fmt:param value="${backup_code_4}" />
							<fmt:param value="${backup_code_5}" />
						</fmt:message>
					</div>
				</c:if>
				<c:if test="${status eq -1}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="profile.status.nok" />
					</div>
				</c:if>
				<c:if test="${status eq -99}">
					<div class="alert alert-dismissible alert-danger mt-4">
						<fmt:message key="profile.status.2fa_already_enabled" />
					</div>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h1 class="display-4">
					<fmt:message key="profile.title" />
				</h1>
			</div>
		</div>
		<form method="post">
			<input type="hidden" name="ct" value="<c:out value="${ct}" />">
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="username"><fmt:message key="profile.form.username" /></label> <input name="username" disabled="disabled" type="text"
							class="form-control" id="name" placeholder="<fmt:message key="profile.form.username" />" value="<c:out value="${user.username}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="email"><fmt:message key="profile.form.email" /></label> <input name="email" type="email" class="form-control" id="email"
							placeholder="<fmt:message key="profile.form.email" />" value="<c:out value="${user.email}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="currency"><fmt:message key="profile.form.locale" /><span data-toggle="tooltip"
							title="<fmt:message key="profile.form.locale.tooltip" />" class="ml-1 badge badge-pill badge-info">?</span></label> <input name="locale" type="text"
							class="form-control" id="locale" placeholder="<fmt:message key="profile.form.locale" />" value="<c:out value="${user.locale}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="currency"><fmt:message key="profile.form.currency" /><span data-toggle="tooltip"
							title="<fmt:message key="profile.form.currency.tooltip" />" class="ml-1 badge badge-pill badge-info">?</span></label> <input name="currency"
							type="text" class="form-control" id="currency" placeholder="<fmt:message key="profile.form.currency" />"
							value="<c:out value="${user.currency}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="distance"><fmt:message key="profile.form.distance" /></label> <select id="distance" name="distance" class="form-control">
							<option value="1" <c:if test="${user.distance eq '1'}">selected="selected"</c:if>><fmt:message key="profile.form.distance.km" /></option>
							<option value="2" <c:if test="${user.distance eq '2'}">selected="selected"</c:if>><fmt:message key="profile.form.distance.mi" /></option>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="rounded"><fmt:message key="profile.form.rounded" /></label> <select id="rounded" name="rounded" class="form-control">
							<option value="0" <c:if test="${user.rounded eq '0'}">selected="selected"</c:if>><fmt:message key="no" /></option>
							<option value="1" <c:if test="${user.rounded eq '1'}">selected="selected"</c:if>><fmt:message key="yes" /></option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="pushoverUser"><fmt:message key="profile.form.pushover.user" /></label> <input name="pushoverUser" type="text" class="form-control"
							id="pushoverUser" placeholder="<fmt:message key="profile.form.pushover.user" />" value="<c:out value="${user.pushoverUserKey}" />">
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="pushoverAPIKey"><fmt:message key="profile.form.pushover.key" /></label> <input name="pushoverAPIKey" type="text"
							class="form-control" id="pushoverAPIKey" placeholder="<fmt:message key="profile.form.pushover.key" />"
							value="<c:out value="${user.pushoverAPIKey}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label for="pushbulletKey"><fmt:message key="profile.form.pushbullet.key" /></label> <input name="pushbulletKey" type="text"
							class="form-control" id="pushbulletKey" placeholder="<fmt:message key="profile.form.pushbullet.key" />"
							value="<c:out value="${user.pushbulletAPIKey}" />">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label for="dateFormat"><fmt:message key="profile.form.date_format" /></label> <select id="dateFormat" name="dateFormat" class="form-control">
							<c:forEach items="1,2,3,4,5,6,7,8,9,10" var="i">
								<fmt:message key="profile.form.date_format.${i}.val" var="dateValue" />
								<option <c:if test="${user.dateFormat eq dateValue}">selected="selected"</c:if> value="<c:out value="${dateValue}"  />"><fmt:message
										key="profile.form.date_format.${i}.label" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label for="timeFormat"><fmt:message key="profile.form.time_format" /></label> <select id="timeFormat" name="timeFormat" class="form-control">
							<c:forEach items="1,2" var="i">
								<fmt:message key="profile.form.time_format.${i}.val" var="timeValue" />
								<option <c:if test="${user.timeFormat eq timeValue}">selected="selected"</c:if> value="<c:out value="${timeValue}"  />"><fmt:message
										key="profile.form.time_format.${i}.label" /></option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<fmt:message key="${user.totpEnabled eq 1 ? 'yes': 'no' }" var="two_fa_setup" />
					<fmt:message key="profile.form.totp">
						<fmt:param value="${two_fa_setup}" />
					</fmt:message>
					<c:if test="${user.totpEnabled eq 0}">
						<a href="setup_2fa"><fmt:message key="profile.form.totp.setup" /></a>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-right">
					<input type="submit" class="btn btn-primary" value="<fmt:message key="button.save" />"> <a class="btn btn-danger" href="index"
						role="button"> <fmt:message key="button.cancel" />
					</a>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="includes/appScripts.jsp" />
</body>
</html>