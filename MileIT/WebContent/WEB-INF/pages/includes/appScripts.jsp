<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctxRoot" value="${pageContext.request.contextPath}" />
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="hu.thom.mileit.i18n.mileit" />

<jsp:include page="footer.jsp" />
<script src="<c:out value="${ctxRoot}" />/js/jquery-3.4.0.min.js" type="text/javascript"></script>
<script src="<c:out value="${ctxRoot}" />/js/popper.min.js" type="text/javascript"></script>
<script src="<c:out value="${ctxRoot}" />/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<c:out value="${ctxRoot}" />/js/colorpicker.js" type="text/javascript"></script>
<script src="<c:out value="${ctxRoot}" />/js/moment.min.js" type="text/javascript"></script>
<script src="<c:out value="${ctxRoot}" />/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="<c:out value="${ctxRoot}" />/js/mileit.js" type="text/javascript"></script>

<c:if test="${loadCharts eq 1}">
	<script src="<c:out value="${ctxRoot}" />/js/Chart.min.js" type="text/javascript"></script>
	<script src="<c:out value="${ctxRoot}" />/js/mileit.charts.js" type="text/javascript"></script>
</c:if>
<c:if test="${loadMap eq 1}">
	<script type="text/javascript" src="https://www.openlayers.org/api/OpenLayers.js"></script>
	<script src="<c:out value="${ctxRoot}" />/js/mileit.maps.js" type="text/javascript"></script>
</c:if>
