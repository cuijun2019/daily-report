<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>运营数据呈现平台</title>
    <%@include file="/WEB-INF/layouts/taglibs.jsp" %>
    <link rel="shortcut icon" href="${ctx}/static/image/favicon.ico" />
    <link rel="stylesheet" href="${ctx}/static/login/type_1/css/login_lte.css">
    <script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.cookie.js"></script>
</head>
<body >
<div class="login-container">
    <div class="header"></div>
    <form name="loginForm" id="loginForm" action="" method="post">
        <input type="hidden" id="action" name="action" value="SSOLoginAction"/>
        <input type="hidden" name="return" value=""/>

        <div class="legend">统一登录中心</div>
        <hr />
        <hr class="decoration" />
        <div class="hint">
            <span class="error" id="reason">${reason}&nbsp;</span>
        </div>
        <fieldset>
            <div class="input-prepend" style="padding-left:1px;">
                    <span class="add-on">
                        <i class="icon-user"></i>
                    </span>
                <input  type="text" id="username" name="username" value="admin" placeholder="域账号或工作账号" onchange="changeUserName(this.value)" class="input"/>
            </div>
            <div class="input-prepend" style="padding-left:1px;">
                    <span class="add-on">
                        <i class="icon-key"></i>
                    </span>
                <%--<input  type="password" id="password" name="password" value="" placeholder="密　码" class="input" onfocus="this.type='password'" autocomplete="off"/>
				--%>
                <input  type="password" id="password" name="password" value="" placeholder="密　码" class="input"/>
            </div>
            <div class="input-prepend" style="padding-left:1px;">
                <table>
                    <tr>
                        <td style="width: 50%" >
                            <input  id="captcha_key" class="valudatainput" name="captchaKey" type="input" placeholder="请输入验证码" data-options="required:true"/>
                        </td>
                        <td style="width: 50%;text-align: center;margin-right: 1px" >
                            <img id="captcha" src="${ctx}/Captcha.jpg" alt="点击刷新验证码" width="85" height="35" onclick="this.src='${ctx}/Captcha.jpg?'+new Date().getTime();"/>
                        </td>
                    </tr>
                </table>
            </div>

            <div style="display: inline">
                <label style="color: #7aafcc">
                    <input type="checkbox"  id="rmbUser" name="rmbUser"  value="1" checked="checked"/>
                    &nbsp;记住密码
                </label>
            </div>

            <div style="display: inline">
                <button type="button" class="valudatabtn-login" onFocus="submitForm()">登录</button>
            </div>
        </fieldset>
    </form>
</div>
<script>
    $(document).ready(function() {
        var user= $("#username").val();
        var rmbUser=$.cookie("rmbUser");
        if(typeof(user)!='undefined'&&user!=null){
            if(typeof(rmbUser)!='undefined'&&rmbUser!=null){
                $("#rmbUser").attr("checked", true);
                $("#username").val($.cookie(user+"userName"));
                $("#password").val($.cookie(user+"passWord"));
            }else{
                var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null;
                if (isChrome) {
                    //网页在谷歌浏览器中打开时，先将密码域设置为文本域，设置为密码域之后为当前元素加载onfocus事件，获得焦点时将当前元素重新设置为密码域
                    $("input[type='password']").attr("type", "text").focus(function () {
                        $(this).attr("type", "password");
                    });
                } else {
                    //网页在非谷歌浏览器中时为密码域加  autocomplete=“off”属性
                    $("input[type='password']").attr("autocomplete", "off");
                }
                $("#rmbUser").attr("checked", false);
                $("#username").val($.cookie(user+"userName"));
                $("#password").val("");
            }
        }
        /*var password2="";
         if ($("#password").val()) {
         for (var i=0;i<$("#password").val().length;i++) {
         password2 += "·";
         }
         }
         $("#password").val(password2);*/
        document.onkeydown=keyListener;

    });
    function keyListener(e){
        e = e?e : event;
        if(e.keyCode == 13){
            submitForm ();
        }
    }
    function submitForm () {
        if ($("input[type='checkbox']").is(':checked')) {
            var userName = $("#username").val();
            var passWord = $("#password").val();
            $.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
            $.cookie(userName+"userName", userName, { expires: 7 }); // 存储一个带7天期限的 cookie
            $.cookie(userName+"passWord", passWord, { expires: 7 }); // 存储一个带7天期限的 cookie
        } else {
            $.cookie("rmbUser", "false", { expires: -1 });
            $.cookie(userName+"userName", "", { expires: -1 });
            $.cookie(userName+"passWord", "", { expires: -1 });

        }
        var captchakey=$("#captcha_key").val();
        if(!validate()){
            $("#reason").html("账号或密码为空！");
            return;
        }else if(captchakey==null||captchakey==''){
            $("#reason").html("验证码为空！");
            return;
        }else{
            $('#loginForm').submit();
        }

        /*if('${reason}'!=null&&'${reason}'!=''){
         $.cookie(userName+"userName", "", { expires: -1 });
         $.cookie(userName+"passWord", "", { expires: -1 });
         }*/

    }

    function validate(){
        var bl=true;
        var username=$("#username").val();
        var password=$("#password").val();

        if(username==null||username==''){
            bl=false;
        }
        if(password==null||password==''){
            bl=false;
        }
        return bl;
    }

    function changeUserName(userName){
        var user=$.cookie(userName+"userName");
        if(typeof(user)!='undefined'&&user!=null){
            $("#rmbUser").attr("checked", true);
            $("#username").val($.cookie(userName+"userName"));
            $("#password").val($.cookie(userName+"passWord"));
        } else{
            $("#password").val('');
        }
    }
</script>
</body>
</html>