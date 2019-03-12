<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="hu.thom.mileit.i18n.mileit" />

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
	<div class="container-fluid">
		<a class="navbar-brand" href="."> <fmt:message key="appname" /></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar" aria-controls="navbarColor01" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbar">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item <c:if test="${not empty page and page eq 'index' }">active</c:if>"><a class="nav-link" href="index"> <fmt:message
							key="menu.dashboard" /> <span class="sr-only"><fmt:message key="menu.sr.current" /></span></a></li>
				<li class="nav-item dropdown <c:if test="${not empty page and (page eq 'cars' or page eq 'maintenance') }">active</c:if>"><a class="nav-link dropdown-toggle"
					href="cars" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <fmt:message key="menu.cars" />
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<a class="dropdown-item" href="cars"> <fmt:message key="menu.cars.cars" />
						</a> <a class="dropdown-item" href="maintenance"><fmt:message key="menu.car.maintenance" /></a> <a class="dropdown-item" href="cars"> <fmt:message
								key="menu.car.maintenance.tyres" />
						</a>
					</div></li>
				<li class="nav-item dropdown <c:if test="${not empty page and page eq 'refuels' }">active</c:if>"><a class="nav-link dropdown-toggle"
					href="refuels" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <fmt:message key="menu.refuels" />
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<a class="dropdown-item" href="refuels"> <fmt:message key="menu.refuels" />
						</a> <a class="dropdown-item" href="payment_methods"><fmt:message key="menu.refuels.payment" /></a> <a class="dropdown-item" href="locations">
							<fmt:message key="menu.refuels.locations" />
						</a>
					</div></li>
				<li class="nav-item <c:if test="${not empty page and page eq 'stats' }">active</c:if>"><a class="nav-link" href="stats"> <fmt:message
							key="menu.stats" />
				</a></li>
			</ul>

			<ul class="navbar-nav ml-auto">
				<li class="nav-item <c:if test="${not empty page and page eq 'profile' }">active</c:if>"><a class="nav-link" href="profile"> <fmt:message
							key="menu.loggedin">
							<fmt:param value="${user.username}" />
						</fmt:message>
				</a></li>
				<li class="nav-item"><a class="nav-link"><span class="badge badge-light"><c:out value="${v}" /></span></a></li>
				<li class="nav-item"><a class="nav-link" href="logout"> <fmt:message key="menu.logout" />
				</a></li>
			</ul>
		</div>
	</div>
</nav>