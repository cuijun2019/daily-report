<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>研究院日报后台</title>
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
    <%@include file="/WEB-INF/layouts/taglibs.jsp" %>
    <link rel="shortcut icon" href="${ctx}/static/image/favicon.ico"/>
    <link rel="stylesheet" href="${ctx}/static/login/type_1/css/login_lte.css">
    <script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.cookie.js"></script>
</head>
<body>
<div class="login-container">
    <div class="header"></div>
    <form name="loginForm" id="loginForm" action="" method="post">
        <input type="hidden" id="action" name="action" value="SSOLoginAction"/>
        <input type="hidden" name="return" value=""/>

        <div class="legend">统一登录中心</div>
        <!--<hr />-->
        <hr class="decoration"/>
        <div class="hint">
            <span class="error" id="reason">${reason}&nbsp;</span>
        </div>
        <fieldset>
            <div class="input-prepend" style="padding-left:1px;">
                    <span class="add-on">
                        <i class="icon-user"></i>
                    </span>
                <input type="text" id="username" name="username" placeholder="账　号" onchange="changeUserName(this.value)"
                       class="input"/>
            </div>
            <div class="input-prepend" style="padding-left:1px;">
                    <span class="add-on">
                        <i class="icon-key"></i>
                    </span>
                <input type="password" id="password" name="password" placeholder="密　码" class="input"/>
            </div>
            <div style="display: inline">
                <button type="button" class="valudatabtn-login" onclick="submitForm()">登录</button>
            </div>
        </fieldset>
    </form>
</div>
<script>
    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            submitForm();
        }
    });

    function submitForm() {
        if (!validate()) {
            $("#reason").html("账号或密码为空！");
            return;
        } else {
            $('#loginForm').submit();
        }

    }

    function validate() {
        var bl = true;
        var username = $("#username").val();
        var password = $("#password").val();

        if (username == null || username == '') {
            bl = false;
        }
        if (password == null || password == '') {
            bl = false;
        }
        return bl;
    }

    function changeUserName(userName) {
        if (typeof (userName) != 'undefined' && userName != null) {
        } else {
            $("#password").val('');
        }
    }
</script>
</body>
</html>