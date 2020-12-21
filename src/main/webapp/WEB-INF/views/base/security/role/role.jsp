<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/meta.jsp" %>
<script type="text/javascript">
var role_datagrid;
var role_form;
var role_search_form;
var role_dialog;
$(function() {  
	role_form = $('#role_form').form();
	role_search_form = $('#role_search_form').form();
    //数据列表
    role_datagrid = $('#role_datagrid').datagrid({  
	    url:'${ctx}/base/security/role/query',  
	    pagination:true,//底部分页
	    rownumbers:true,//显示行数
	    fitColumns:true,//自适应列宽
	    striped:true,//显示条纹
	    pageSize:20,//每页记录数
        remoteSort:false,//是否通过远程服务器对数据排序
	    sortName:'id',//默认排序字段
		sortOrder:'asc',//默认排序方式 'desc' 'asc'
		idField : 'id',
	    columns:[[  
            {field:'ck',checkbox:true},
            {field:'id',title:'主键',hidden:true,sortable:true,align:'right',width:80}, 
            {field:'resourceNames',title:'关联资源',width:165},
	        {field:'name',title:'角色名称',width:55}, 
	        {field:'remark',title:'描述',width:50}	               
	    ]],
	    onLoadSuccess:function(){
	    	$(this).datagrid('clearSelections');//取消所有的已选择项
	    	$(this).datagrid('unselectAll');//取消全选按钮为全选状态
		},
	    onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			$('#role_datagrid_menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		},
        onDblClickRow:function(rowIndex, rowData){
            edit(rowIndex, rowData);
        }
	});
    
});
</script>
<script type="text/javascript">
        function formInit(){
        	role_form = $('#role_form').form({
				url: '${ctx}/base/security/role/save',
				onSubmit: function(param){  
					$.messager.progress({
						title : '提示信息！',
						text : '数据处理中，请稍后....'
					});
					var isValid = $(this).form('validate');
					if (!isValid) {
						$.messager.progress('close');
					}
					return isValid;
			    },
				success: function(data){
					$.messager.progress('close');
					var json = $.parseJSON(data);
					if (json.code ==1){
						role_dialog.dialog('destroy');//销毁对话框 
						role_datagrid.datagrid('reload');//重新加载列表数据
						eu.showMsg(json.msg);//操作结果提示
					}else if(json.code == 2){
						$.messager.alert('提示信息！', json.msg, 'warning',function(){
							if(json.obj){
								$('#role_form input[name="'+json.obj+'"]').focus();
							}
						});
					}else {
						eu.showAlertMsg(json.msg,'error');
					}
				}
			});
		}
		//显示弹出窗口 新增：row为空 编辑:row有值 
		function showDialog(row){
			//弹出对话窗口
			role_dialog = $('<div/>').dialog({
				title:'角色详细信息',
				width : 500,
				height : 360,
				modal : true,
				maximizable:true,
				href : '${ctx}/base/security/role/input',
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						role_form.submit();
					}
				},{
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function() {
						role_dialog.dialog('destroy');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad:function(){
					formInit();
					if(row){
						role_form.form('load', row);
					}
					
				}
			}).dialog('open');
			
		}
		
		//编辑
        function edit(rowIndex, rowData){
            //响应双击事件
            if(rowIndex != undefined) {
                showDialog(rowData);
                return;
            }
			//选中的所有行
			var rows = role_datagrid.datagrid('getSelections');
			//选中的行（第一次选择的行）
			var row = role_datagrid.datagrid('getSelected');
			if (row){
				if(rows.length>1){
					row = rows[rows.length-1];
					eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
				}
				showDialog(row);
			}else{
				eu.showMsg("请选择要操作的对象！");
			}
		}
		
		//删除
		function del(){
			var rows = role_datagrid.datagrid('getSelections');
			
			if(rows.length >0){
				$.messager.confirm('确认提示！','您确定要删除选中的所有行？',function(r){
					if (r){
						var ids = new Object();
						for(var i=0;i<rows.length;i++){
							ids[i] = rows[i].id;
						}
						$.post('${ctx}/base/security/role/delete',{ids:ids},function(data){
							if (data.code==1){
								role_datagrid.datagrid('load');	// reload the user data
								eu.showMsg(data.msg);//操作结果提示
							} else {
								eu.showAlertMsg(data.msg,'error');
							}
						},'json');      
						
					}
				});
			}else{
				eu.showMsg("请选择要操作的对象！");
			}
		}
		
		//搜索
		function search(){
			//role_datagrid.datagrid('load',$.serializeObject(role_search_form));
			var params = {};
			params['LIKE_name'] = $("#LIKE_name").val();
			role_datagrid.datagrid('reload',params);
		}
		
</script>
<%-- 列表右键 --%>
<div id="role_datagrid_menu" class="easyui-menu" style="width:120px;display: none;">
	<div onclick="showDialog();" data-options="iconCls:'icon-add'">新增</div>
	<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
</div>
		
<%-- 工具栏 操作按钮 --%>
<div id="role_datagrid-toolbar">
    <div style="margin-left:10px; float: left;">
        <form id="role_search_form" style="padding: 0px;">
			角色名称:<input type="text" name="LIKE_name" placeholder="请输入角色名称..."  maxLength="25" style="width: 160px" /> 
			<a href="javascript:search();" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" >查 询</a>
		</form>
	</div>
	<div align="right">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showDialog()">新增</a>
		<span class="toolbar-btn-separator"></span>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
		<span class="toolbar-btn-separator"></span>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a> 
	</div>
</div>
<table id="role_datagrid" toolbar="#role_datagrid-toolbar" fit="true"></table>
   