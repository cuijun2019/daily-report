<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>企业微信</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="Thu, 19 Nov 1900 08:52:00 GMT">
    <link rel="stylesheet" href="../modules/geogis/bootstrapvalidator-master/vendor/bootstrap/css/bootstrap.css"/>
    <script type="text/javascript" src="../modules/geogis/bootstrapvalidator-master/vendor/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="../modules/geogis/bootstrapvalidator-master/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#logId").click(function(){
                $("#project").hide();
                $("#statistics").hide();
                $("#log").show();
                $("#finalStatistics").hide();
                document.getElementById('logFrame').contentWindow.location.reload(true);
            });
            $("#statisticsId").click(function(){
                $("#project").hide();
                $("#log").hide();
                $("#statistics").show();
                $("#finalStatistics").hide();
                document.getElementById('statisticsFrame').contentWindow.location.reload(true);
            });
            $("#projectId").click(function(){
                $("#project").show();
                $("#log").hide();
                $("#statistics").hide();
                $("#finalStatistics").hide();
                document.getElementById('projectFrame').contentWindow.location.reload(true);
            });
            $("#finalStatisticsId").click(function(){
                $("#project").hide();
                $("#log").hide();
                $("#statistics").hide();
                $("#finalStatistics").show();
                document.getElementById('finalStatisticsFrame').contentWindow.location.reload(true);
            });
        });
    </script>
</head>

<body>
<ul id="myTab" class="nav nav-tabs">
    <li class="active">
        <a id="projectId" href="#project" data-toggle="tab">
            项目信息
        </a>
    </li>
    <li>
        <a id="logId" href="#log" data-toggle="tab">
            用户日志信息
        </a>
    </li>
    <li>
        <a id="statisticsId" href="#statistics" data-toggle="tab">
            日志统计信息
        </a>
    </li>
    <li>
        <a id="finalStatisticsId" href="#finalStatistics" data-toggle="tab">
            工时统计信息
        </a>
    </li>
</ul>
<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in active" id="project">
        <iframe id="projectFrame" src="project_info.html" width="100%" height="600px;" scroll="auto" frameborder="0"></iframe>
    </div>
    <div class="tab-pane fade" id="log">
        <iframe id="logFrame" src="log_info.html" width="100%" height="600px;" scroll="auto" frameborder="0"></iframe>
    </div>
    <div class="tab-pane fade" id="statistics">
        <iframe id="statisticsFrame" src="statistics_info.html" width="100%" height="600px;" scroll="auto" frameborder="0"></iframe>
    </div>
    <div class="tab-pane fade" id="finalStatistics">
        <iframe id="finalStatisticsFrame" src="finalstatistics_info.html" width="100%" height="600px;" scroll="auto" frameborder="0"></iframe>
    </div>
</div>
</body>

</html>