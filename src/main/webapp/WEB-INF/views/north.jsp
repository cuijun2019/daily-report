<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
var login_about_dialog;
var login_password_dialog;
var login_password_form;
function showAbout(){
    //弹出对话窗口
	login_about_dialog = $('<div/>').dialog({
		title:'关于我们',
		width : 400,
		height : 220,
		modal : true,
		href : 'base/main/redirect/about',
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				login_about_dialog.dialog('destroy');
			}
		}],
		onClose : function() {
			$(this).dialog('destroy');
		}
	}).dialog('open');
  	$(".panel-title").css('text-align', 'center');
}
  
 function initLoginPasswordForm(){
   login_password_form = $('#login_password_form').form({
		<%--url: '${ctx}/base/user!updateUserPassword.action',--%>
       url: '${ctx}/base/security/user/updateUserPassword.action',
       onSubmit: function(param){
	        param.upateOperate = '1';  
	        return $(this).form('validate');
	    }, 
		success: function(data){
			var json = eval('('+ data+')');  
			if (json.code == 1){
				login_about_dialog.dialog('close');	
				eu.showMsg(json.msg);//操作结果提示
			} else if(json.code == 2){
				$.messager.alert('提示信息！', json.msg, 'warning',function(){
					var userId = $('#login_password_form_id').val();
					$(this).form('clear');
					$('#login_password_form_id').val(userId);
					if(json.obj){
						$('#login_password_form input[name="'+json.obj+'"]').focus();
					}
				});
			}else {
				eu.showAlertMsg(json.msg,'error');
			}
		}
	});
}
  
   function editLoginUserPassword(){
    //弹出对话窗口
	login_about_dialog = $('<div/>').dialog({
		title:'修改用户密码',
		width : 410,
		height : 240,
		modal : true,
		href : 'base/main/redirect/password',
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				login_password_form.submit();;
			}
		},{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				login_about_dialog.dialog('destroy');
			}
		}],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad:function(){
			initLoginPasswordForm();
		}
	}).dialog('open');
   }
//注销
function logout() {
	$.messager.confirm('确认提示！', '您确定要退出系统吗？', function(r) {
		if (r) {
			window.location.href = "${ctx}/login!logout.action";
		}
	});
}
// 登出按钮处理
        $('#logout').on('click', function () {
            $.messager.confirm('确认提示！', '确认要退出系统吗？', function (r) {
                if (r) top.location.href = 'logout';
            });
        });
</script>
<div style="background-image: url('${ctx}/static/image/lte_logo_gd.jpg'); background-repeat: repeat-x;width: 1200px;height:100%;">
    
	<div style="float: right; position: absolute; bottom: 2px; right: 10px">
	    <div style="text-align: right;"><span style="color: #ffffff;">您好,${sessionInfo.loginName} [${sessionInfo.fullName}] 欢迎您！</span></div>
		<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_pfMenu" iconCls="icon-user_red"><span style="color: #ffffff;">更换皮肤</span></a>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="eu.changeTheme('bootstrap');">bootstrap</div>
			<div onclick="eu.changeTheme('default');">蓝色</div>
			<div onclick="eu.changeTheme('gray');">灰色</div>
			<div onclick="eu.changeTheme('black');">黑色</div>
			<div onclick="eu.changeTheme('metro');">metro</div>
		</div>
	
	    <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help"><span style="color: #ffffff;">控制面板</span></a>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="editLoginUserPassword();" iconCls="icon-edit">修改密码</div>
			<div class="menu-sep"></div>
			<div onclick="" data-options="iconCls:'icon-help'">帮助</div>
			<div onclick="showAbout();">关于</div>
		</div>
		
		<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_logoutMenu" iconCls="icon-back"><span style="color: #ffffff;">安全退出</span></a>
		<div id="layout_north_logoutMenu" style="width: 100px; display: none;">
			<div id="logout"  data-options="iconCls:'icon-lock'">注销</div>
		</div>
	</div>
</div>
