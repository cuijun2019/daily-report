<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		$.extend($.fn.validatebox.defaults.rules, {
        /*必须和某个字段相等*/
        equalTo: { validator: function (value, param) { return $(param[0]).val() == value; }, message: '字段不匹配' }
       });
		loadSex();
		loadTips();
	});
	//性别
	function loadSex(){
		$('#sex').combobox({
	    	url: '${ctx}/static/json/sex.json',
	        width: 120,
	        editable:false,
	        value:'2',
	        valueField: 'value',
	        displayField: 'text'
	    });
	}
	
	//图层地区
	function loadTips(){
		$('#cityName').combobox({
	    	url: '${ctx}/static/json/mapInfo.json',
	        width: 120,
	        editable:false,
	        value:'gd',
	        valueField: 'value',
	        displayField: 'text',
            onSelect:function(rec){
                $('#intCityId').val(rec.id);
            }
	    });
	}
</script>
<div>
	<form id="user_form"  method="post" novalidate>
			<input type="hidden" id="user_form_id" name="id" />
			<!-- 用户版本控制字段 version -->
            <input type="hidden" id="version" name="version" />
		    <!-- 	
		    <div class="fitem">
				<label>角色设置:</label>
				<select id="roleIds" name="roleIds" class="easyui-combobox"></select> 
			</div> 
			-->
			<div>
				<label>登录名:</label> 
				<input type="text" id="account" name="account" maxLength="36"
					class="easyui-validatebox"
					data-options="required:true,missingMessage:'请输入登录名.',validType:['minLength[1]','legalInput']"/> 
		</div>
			<div id="password_div">
			<label>密码:</label> 
			<input type="password" id="password"
				name="password" class="easyui-validatebox" maxLength="36"
				data-options="required:true,missingMessage:'请输入密码.',validType:['safepass']"> 
		</div>
		<div id="repassword_div">
			<label>确认密码:</label> 
			<input type="password" id="repassword"
				name="repassword" class="easyui-validatebox" required="true"
				missingMessage="请再次填写密码." validType="equalTo['#password']"
				invalidMessage="两次输入密码不匹配.">
		</div>
		<div>
				<label>姓名:</label>
				<input name="fullName" type="text" maxLength="6" class="easyui-validatebox" data-options="validType:['CHS','length[2,6]']" />
			</div>
			<div>
				<label>性别:</label>
				<input id="sex" name="sex" type="text" class="easyui-combobox" />
			</div>
			<%--<div>
				<label>邮箱:</label>
				<input name="email" type="text" class="easyui-validatebox" validType="email" maxLength="255" />
			</div>--%>
            <div>
                <label>部门:</label>
                <input name="room" type="text" class="easyui-validatebox">
            </div>
            <div>
                <label>分支机构:</label>
                <input name="factory" type="text" class="easyui-validatebox">
            </div>
			<div>
				<label>电话:</label>
				<input name="telephone" type="text" class="easyui-validatebox">
			</div>
			<%--<div>

				<input id="tips" name="tips" type="hidden"  value="ltemr_gd" class="easyui-validatebox" />
                <input id="tipAnswer" name="tipAnswer" type="hidden"  value="110" class="easyui-validatebox" />
                <input id="ouPath" name="ouPath" type="hidden"  value="113.7396862885,23.0325850195" class="easyui-validatebox" />

                <label>区域:</label>
                <input id="cityName" name="cityName" type="text"  class="easyui-combobox" />
                <input id="intCityId" name="intCityId" type="hidden" value="86020"/>
				&lt;%&ndash;提示小图标&ndash;%&gt;
            	<span class="tree-file icon-tip"
                  title="用户使用的GIS图层标示;主要用于[GIS功能]类型的图层呈现." ></span>
			</div>--%>
			<!-- <div>
			    <label>状态:</label>
				<input type="radio" name="lock" style="width: 20px;" value="0" /> 启用 
	            <input type="radio" name="lock" style="width: 20px;" value="1" /> 停用
			</div>
			 -->
		</form>
</div>