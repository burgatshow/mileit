<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="hu.thom.mileit.i18n.mileit" />

<footer class="footer">
	<div class="container mt-3">
		<div class="row">
			<div class="col-md-12 text-center">
				<fmt:message key="copyright">
					<fmt:param value="${v}" />
				</fmt:message>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<small class="text-left"> <fmt:message key="copyright.icons">
						<fmt:param value="https://www.flaticon.com/authors/mynamepong" />
						<fmt:param value="mynamepong" />
						<fmt:param value="https://www.flaticon.com/" />
						<fmt:param value="www.flaticon.com" />
						<fmt:param value="http://creativecommons.org/licenses/by/3.0/" />
						<fmt:param value="CC 3.0 BY" />
					</fmt:message>
				</small>
			</div>
			<div class="col-md-6 text-right">
				<small> <a href="https://github.com/burgatshow/mileit/issues/new" title="<fmt:message key="copyright.github.bug" />" target="_blank"><fmt:message
							key="copyright.github.bug" /></a> | <a href="https://github.com/burgatshow/mileit" title="<fmt:message key="copyright.github.repo" />"
					target="_blank"><fmt:message key="copyright.github.repo" /></a>
				</small>
			</div>
		</div>
	</div>
</footer>
