<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctxRoot" value="${pageContext.request.contextPath}" />
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" />

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="hu.thom.mileit.i18n.mileit" />

<jsp:include page="footer.jsp" />
<script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
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
