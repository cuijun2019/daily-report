<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		loadUserRole();
	});
	// 加載用户角色信息
	function loadUserRole() {
	    $('#user_role_form-roleIds').combobox({
	        multiple: true,
	        width: 200,
	        editable:false,
	        valueField: 'value',
	        displayField: 'text',
	        url: '${ctx}/base/security/role/combobox'
	    });
	}	
</script>
<div>
	<form id="user_role_form"  method="post" novalidate>
		<input type="hidden" name="id" /><br>
		<div >
			<label >关联角色:</label>
			<input id="user_role_form-roleIds" name="roleIds" class="easyui-combobox" /> 
		</div>
	</form>
</div>