<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>TD-LTE无线网络评估平台</title>
    <%@include file="/WEB-INF/layouts/meta.jsp" %>
    <style type="text/css">
        @CHARSET "UTF-8";
        .container { margin: auto; width: auto;  max-width: 100%; height: 100%; 
        background:url(${ctx}/static/image/reg-v2-bg.png) #e3ebf0 repeat-x 0 0;}
        .login-content { 
        	width:100%;
			height:100%;
			background:url(${ctx}/static/image/reg-glow.png) no-repeat center center;
			top:0;
			left:0;
			_display:none;}
    </style>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false" class="container">
	<div class="login-content">
    <div id="loginContainer"  style="width: 250px; height: 150px;">
        <form id="loginForm" name="loginForm" class="form" action="${ctx}/login" method="post">
            <table style="width: 250px; height: 150px;">
                <tr>
                    <td colspan="2"><h2>TD-LTE无线网优分析平台</h2></td>
                </tr>
                <tr>
                    <td><span class="tree-file icon-user" title="用户名" ></span><label for="username">用户名:</label></td>
                    <td><input class="easyui-validatebox" id="username" name="username" type="text" value="admin" placeholder="请输入账号" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td><span class="tree-file icon-edit" title="密码" ></span><label for="password">密　码:</label></td>
                    <td><input iconCls="icon-user_red" class="easyui-validatebox" id="password" name="password" type="password" value="123456" placeholder="请输入密码" data-options="required:true"/></td>
                </tr>
                <c:if test="${captchable eq 'true'}">
                    <tr>
                        <td><label for="username">验证码:</label></td>
                        <td>
                            <input class="easyui-validatebox" id="captcha_key" name="captchaKey" type="input" placeholder="请输入验证码" data-options="required:true"/>
                            <p>
                                <img id="captcha" src="${ctx}/Captcha.jpg" alt="点击刷新验证码" width="75" height="24" onclick="this.src='${ctx}/Captcha.jpg?'+new Date().getTime();"/>
                            </p>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">登录</a> -->
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">登录</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="color: #ff0000;">${reason}&nbsp;</td>
                </tr>
            </table>
        </form>
    </div>
    </div>
</div>
<div data-options="region:'south',border:false" style="height:40px;" class="container">
    <%@include file="/WEB-INF/layouts/footer.jsp" %>
</div>
<script>
    $(document).ready(function () {
        $('#loginContainer').center();
        $('body').layout('panel', 'center').panel({onResize: function () {
            $('#loginContainer').center();
        }});
    });
    var submitForm = function () {
        if($('#loginForm').form('validate')){
            $('#loginForm').submit();
        }
    }
</script>
</body>
</html>