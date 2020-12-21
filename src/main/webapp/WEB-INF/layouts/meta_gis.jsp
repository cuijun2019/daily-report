<%@ page language="java" import="com.etone.project.utils.geos.GeoServerPropertyUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="baseURI" value="${pageContext.request.scheme}${'://'}${pageContext.request.serverName}${':'}${pageContext.request.serverPort}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/form_style.css" />
<script type="text/javascript"  src="${ctx}/static/lib/easyui/jquery.min.js"></script>
<script type="text/javascript"  src="${ctx}/static/lib/openlayer/OpenLayers.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/openlayer/theme/default/style.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/openlayer/style.css"/>
<script type="text/javascript" src="${ctx}/static/app.js"></script>
<script type="text/javascript">
    var geoURI= '${baseURI}'+'<%= GeoServerPropertyUtil.getUrl()%>';  
</script>
