<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>在线教育平台</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
    <%@include file="/WEB-INF/layouts/taglibs.jsp" %>
    <script src="${ctx}/modules/geogis/js/jquery-1.12.4.min.js"></script>
    <script src="${ctx}/modules/geogis/bootstrap/js/bootstrap.js"></script>
    <link href="${ctx}/modules/geogis/bootstrap-table-develop/docs/assets/bootstrap/css/bootstrap.css" rel="stylesheet" />

    <script type="text/javascript">
        $(function () {
            var reason = $("#reason").val();
            if (reason != "") {
                alert(reason);
                $("#reason").val("");
            }
            //点击时隐藏文本框的外边框
            $("#username").click(function() {
                $("input").css("outline", "none");
            });
            $("#password").click(function() {
                $("input").css("outline", "none");
            });
            $("#login").click(function() {
                $("button").css("outline", "none");
            });
        });

        function submitForm () {
            var message = validate();
            if(message != ""){
                alert(message);
                return;
            }else{
                $('#loginForm').submit();
            }
        }

        function validate(){
            var message="";
            var username=$("#username").val();
            var password=$("#password").val();

            if(username==null||username==''){
                message = "学号为空！";
            }
            if(password==null||password==''){
                message = "密码为空！";
            }
            return message;
        }

        function changeUserName(userName){
            if (typeof(userName)!='undefined'&&userName!="") {
            } else {
                $("#password").val("");
            }
        }
    </script>
</head>
<body>
<div style="text-align:center;padding-top:35px;background-color:#FFFFFF;">
    <form name="loginForm" id="loginForm" action="" method="post">
        <input type="hidden" id="action" name="action" value="SSOLoginAction"/>
        <input type="hidden" name="return" value=""/>
        <input type="hidden" id="reason" name="reason" value="${reason}"/>
        <fieldset>
            <div>
                <input style="width:70%;height:45px;border-top:1px solid #EFEFEF;border-left:1px solid #EEEEEE;border-right:1px solid #EEEEEE;border-bottom:1px solid #FAFAFA;padding-left:20px;" type="text" id="username" name="username" value="" placeholder="学号" autocomplete="off" onchange="changeUserName(this.value)" />
            </div>
            <div style="height:16px;">
            </div>
            <div>
                <input style="width:70%;height:45px;border-top:1px solid #EFEFEF;border-left:1px solid #EEEEEE;border-right:1px solid #EEEEEE;border-bottom:1px solid #FAFAFA;padding-left:20px;" type="password" id="password" name="password" value="" placeholder="密码" />
            </div>
            <div style="height:16px;">
            </div>
            <div>
                <button style="width:70%;height:45px;background-color:#5DAADD;color:white;border:1px solid #5DAADD;" type="button" id="login" onclick="submitForm()">登 录</button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
