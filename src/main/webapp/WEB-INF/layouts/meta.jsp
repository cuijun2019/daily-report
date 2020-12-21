<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="com.etone.project.utils.geos.GeoServerPropertyUtil"%>
<%@ page import="com.etone.project.utils.DateUtils" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--<c:set var="staticStartTime" value="<%= GeoServerPropertyUtil.getStaticStartTime() %>"/>--%>
<c:set var="staticStartTime" value="<%= DateUtils.convertDateToLongString(DateUtils.getTimeHourStart(new Date())) %>"/>
<c:set var="staticEndTime" value="<%= DateUtils.convertDateToLongString(DateUtils.getTimeHourEnd(new Date())) %>"/>
<c:set var="staticStartTime2" value="<%= DateUtils.convertDateToShortString(DateUtils.getTimeHourStart(new Date())) %>"/>
<c:set var="staticEndTime2" value="<%= DateUtils.convertDateToShortString(DateUtils.getTimeHourEnd(new Date())) %>"/>

<c:set var="staticStartTime1" value="<%=DateUtils.convertDateToLongString(DateUtils.getPrevMinDate15(new Date(),15)) %>"/>
<c:set var="staticEndTime1" value="<%= DateUtils.convertDateToLongString(DateUtils.getPrevMaxDate15(new Date(),15)) %>"/>
<c:set var="baseURI" value="${pageContext.request.scheme}${'://'}${pageContext.request.serverName}${':'}${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<c:set var="baseIP" value="${pageContext.request.scheme}${'://'}${pageContext.request.serverName}"/>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
<link rel="shortcut icon" href="${ctx}/static/image/favicon_a.ico" />

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/default.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/form_style.css" />

<link id="easyuiTheme" rel="stylesheet" href="${ctx}/static/lib/easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="gray"/>/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/easyui/themes/default/my97.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/easyui/themes/icon.css"/>
<script type="text/javascript" src="${ctx}/static/lib/json2.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.my97.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/easyui/extend/easyui.extend.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/easyui/easyui-lang-zh_CN.js"></script>
<%--<script type="text/javascript" src="${ctx}/static/lib/easyui/extend/jquery.datagrid.tip_extend.js"></script>--%>
<script type="text/javascript" src="${ctx}/static/app.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/ajaxfileupload.js"></script>
<script src="${ctx}/static/lib/bootstrap/extend/bootstrap-extend.js"></script>
<%-- jQuery方法扩展 --%>
<script type="text/javascript" src="${ctx}/static/lib/extend/jquery-extend.js" ></script>
<%-- easyui扩展 --%>
<script type="text/javascript" src="${ctx}/static/lib/extend/easyui-extend.js" ></script>
<%-- highcharts 脚本 --%>
<script src="${ctx}/static/lib/higthchart/highcharts.min.js"></script>
<script src="${ctx}/static/lib/higthchart/highcharts-more.js"></script>
<%-- highcharts 封装脚本 --%>
<script src="${ctx}/static/lib/tablecharts.js"></script>
<script type="text/javascript">
    var ctx = '${ctx}';
    var configIP = '${baseIP}';
    var geoURI='${baseIP}'+':'+ '<%= GeoServerPropertyUtil.getPort()%>'+ '<%= GeoServerPropertyUtil.getUrl()%>';
</script>
