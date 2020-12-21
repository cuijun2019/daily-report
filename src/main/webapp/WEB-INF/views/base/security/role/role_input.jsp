<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/taglibs.jsp"%>
<script type="text/javascript">
    var json;
	$(function() {
		loadResource();
	});
	//加载资源
    function loadResource(){
    	//树 请求数据
    	$.ajax( {
			url : "${ctx}/base/security/privilege/tree",
			type : "post",
			async : false,//是否异步方式 
			success : function(data) { 
				 json = data;
		    }
		});
		
		$('#resourceIds').combotree({
			data : json,
			cascadeCheck : false,
			multiple : true,
			panelHeight : 300,
			//width:250,
			onClick : function(node){
				var tree = $('#resourceIds').combotree('tree');//combotree组件中的tree组件
				eu.myCascadeCheck(tree,node);
			},
			onCheck:function(node, checked) {
			    checkedNode = node;
			}
		});
		var checkedNode;//被checked节点
		//选择框绑定点击事件
		//$('[span^="tree-checkbox"]').bind("click", function() {
		 $('.tree-checkbox').bind("click", function() {
			 window.setTimeout(function() {
			     var tree = $('#resourceIds').combotree('tree');//combotree组件中的tree组件
			     eu.myCascadeCheck(tree,checkedNode);
			 }, 1);
	     });
    }
</script>
<div>
	<form id="role_form" method="post" novalidate>
	    <input type="hidden"  name="id" />
	    <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
			<label>关联资源:</label>
		    <input id="resourceIds" name="resourceIds"  style="width:250px" />
		</div> 
		<div>
			<label>角色名称:</label>
		    <input name="name" type="text" class="easyui-validatebox"
				maxLength="100" data-options="required:true,missingMessage:'请输入角色名称.',validType:['minLength[1]','legalInput']">
		</div>
		<div>
			<label >描述:</label>
			<textarea maxLength="100" name="remark"></textarea>
		</div>
	</form>
</div>