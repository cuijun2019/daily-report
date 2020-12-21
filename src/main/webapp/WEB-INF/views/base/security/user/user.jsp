<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${ctx}/static/lib/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/static/lib/uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
/**
 * 判断是否登录用户是不是超级管理员.
 * @returns {Boolean}
 */
 
function isSuperUser(){
	//登录用户id
	var sessonUserId = "${sessionInfo.id}";

	//超级管理员id为"1"
	if(sessonUserId ==1){
		return true;
	}
	return false;
}
/**
 * 是否是超级用户id.
 */
function isSuperOwner(userId){
	//超级管理员id为"1"
	if(userId ==1){
		return true;
	}
	return false;
} 
 
/**
 * 判但是是否允许操作.
 * @param userId
 */
function isOpeated(userId){
	//if(userId == 1 && isSuperUser() == false){
	//	return false;
	//}
	if(userId == 1){
		return false;
	}
	return true;
}

function initUpload(){

    $("#uploadify").uploadify({
        //指定swf文件
        'swf': '${ctx}/static/lib/uploadify/uploadify.swf',
        //后台处理的页面
        'uploader': '${ctx}/base/security/user/uploadData',
        //选择文件到文件队列中后的每一个文件上的关闭按钮图标
        'cancelImg': '${ctx}/static/lib/uploadify/uploadify-cancel.png',
        //按钮显示的文字
        'buttonText': '添加文件',
        //显示的高度和宽度，默认 height 30；width 120
        'height': 25,
        'width': 100,
        //'method': 'POST',
        //附件大小限制
        //'fileSizeLimit' : '30720KB',
        //附件个数限制
        'uploadLimit' : 3,
        //上传文件的类型  默认为所有文件    'All Files'  ;  '*.*'
        //在浏览窗口底部的文件类型下拉菜单中显示的文本
        //'fileTypeDesc': 'Image Files',
        //允许上传的文件后缀
        'fileTypeExts': '*.csv',
        //发送给后台的其他参数通过formData指定
//        'formData': { 'map_style': ''},
        //上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
//        'queueID': 'fileQueue',
        //选择文件后自动上传
        'auto': false,
        //设置为true将允许多文件上传
        'multi': false,
        //上传成功后执行
        'onUploadSuccess': function (file, data, response) {
            $.messager.progress('close');
            //eu.showAlertMsg(data,'success');
            //eu.showMsg(data);//操作结果提示
            alert("数据导入成功！");
            //var json = $.parseJSON(data);
            //alert(json.msg);
            //$('#' + file.id).find('.data').html('图层已发布');
            //$('#tt').tabs('select','图层列表');
        },
//        'onUploadStart' : function(file) {
//            $.messager.progress({
//                title : '提示信息！',
//                text : '正在处理，请稍后....'
//            });
//        },
        'onUploadComplete':function(){
        }
    });


}

var user_datagrid;
var user_form;
var user_password_form;
var user_role_form;
var user_search_form;
var user_dialog;
var user_batch_dialog;
var user_password_dialog;
var user_role_dialog;
$(function() {user_search_form = $('#user_search_form').form();
    //数据列表
    user_datagrid = $('#user_datagrid').datagrid({  
	    url:'${ctx}/base/security/user/query',
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
            {field:'roleNames',title:'关联角色',width:200,
            	formatter:function(value,rowData,rowIndex){
            		if(isSuperOwner(rowData.id)){
            			return $.formatString('<span  style="color:red">{0}</span>','超级管理员(无需设置角色)');
            		}
            		return value;
        		}
            },
	        {field:'account',title:'登录名',width:100,sortable:true,
            	formatter:function(value,rowData,rowIndex){
            		if(isSuperOwner(rowData.id)){
            			return $.formatString('<span  style="color:red">{0}</span>',value);
            		}
            		return value;
        		}
            },
	        {field:'fullName',title:'姓名',width:80,sortable:true},
	        {field:'sexDesc',title:'性别',width:60,align:'center'},
	        {field:'telephone',title:'电话',width:80},
            {field:'room',title:'部门',width:100},
	        {field:'factory',title:'分支机构',width:100},
	        {field:'lock',title:'状态',align:'center',width:60}
	    ]],
	    onLoadSuccess:function(){
	    	$(this).datagrid('clearSelections');//取消所有的已选择项
	    	$(this).datagrid('unselectAll');//取消全选按钮为全选状态
		},
	    onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			$('#user_datagrid_menu').menu('show', {
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
			user_form = $('#user_form').form({
				url: '${ctx}/base/security/user/save',
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
						user_dialog.dialog('destroy');//销毁对话框 
						user_datagrid.datagrid('reload');//重新加载列表数据
						eu.showMsg(json.msg);//操作结果提示
					}else if(json.code == 2){
						$.messager.alert('提示信息！', json.msg, 'warning',function(){
							if(json.obj){
								$('#user_form input[name="'+json.obj+'"]').focus();
							}
						});
					}else {
						eu.showAlertMsg(json.msg,'error');
					}
				}
			});
		}

        //下载用户信息模板
        function output(){
            var $importType = $('#importType');
            var typeName = $importType.find("option:selected").text();
            var value = $importType.val();
            $('#down_temp_iframe').attr('src','${ctx}/modules/module1/import/download?type='+value+'&name='+typeName);
        }
		//显示弹出窗口 新增：row为空 编辑:row有值 
		function showDialog(row){
			var inputUrl = "${ctx}/base/security/user/input";
		    if(row != undefined && row.id){
		        inputUrl = inputUrl+"?id="+row.id;
		    }
			//弹出对话窗口
			user_dialog = $('<div/>').dialog({
				title:'用户详细信息',
				width : 500,
				height : 360,
				modal : true,
				maximizable:true,
				href : inputUrl,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						user_form.submit();
					}
				},{
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function() {
						user_dialog.dialog('destroy');
					}
				}],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad:function(){
					formInit();
					if(row){
						$('#password_div').css('display','none');
			            $('#repassword_div').css('display','none');
			            $('#password_div input').removeAttr('class');
			            $('#repassword_div input').removeAttr('class');
						user_form.form('load', row);
					}else{
						$('#password_div').css('display','block');
				        $('#repassword_div').css('display','block');
						$("input[name=status]:eq(0)").attr("checked",'checked');//状态 初始化值
					}
				}
			}).dialog('open');
		}

		function batchAdd(){
			var inputUrl = "${ctx}/base/security/user/batchAdd";
			//弹出对话窗口
			user_batch_dialog = $('<div/>').dialog({
				title:'批量添加用户',
				width : 500,
				height : 360,
				modal : true,
				maximizable:true,
				href : inputUrl,
				buttons : [ {
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function() {
                        user_batch_dialog.dialog('destroy');
					}
				}],
                onLoad:function(){
                    initUpload();
                } ,
				onClose : function() {
					$(this).dialog('destroy');
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
			var rows = user_datagrid.datagrid('getSelections');
			//选中的行（第一次选择的行）
			var row = user_datagrid.datagrid('getSelected');
			if (row){
				if(rows.length>1){
					row = rows[rows.length-1];
					eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
				}
				//判断是否允许操作
				if(isOpeated(row.id) == false){
					eu.showMsg("超级管理员用户信息不允许被其他人修改！");
					return;
				}
				
				showDialog(row);
			}else{
				eu.showMsg("请选择要操作的对象！");
			}
		}
		//初始化修改密码表单 
		function initPasswordForm(){
			user_password_form = $('#user_password_form').form({
				url: '${ctx}/base/security/user/updateUserPassword',
				onSubmit: function(param){  
			        param.upateOperate = '0';  
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
					if (json.code == 1){
						user_password_dialog.dialog('destroy');//销毁对话框 
						user_datagrid.datagrid('reload');	// reload the user data
						eu.showMsg(json.msg);//操作结果提示
					}else {
						eu.showAlertMsg(json.msg,'error');
					}
				}
			});
		}
		
		//修改用户密码 
		function editPassword(){
			//选中的所有行
			var rows = user_datagrid.datagrid('getSelections');
			//选中的行（第一条）
			var row = user_datagrid.datagrid('getSelected');
			if (row){
				if(rows.length>1){
					eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
				}
				//判断是否允许操作
				if(isOpeated(row.id) == false){
					eu.showMsg("超级管理员用户信息不允许被其他人修改！");
					return;
				}
				
				user_password_dialog = $('<div/>').dialog({
					title:'修改用户密码',
					width : 400,
					height : 200,
					modal : true,
					maximizable:true,
					href : '${ctx}/base/security/user/redirect/user_password',
					buttons : [ {
						text : '保存',
						iconCls : 'icon-save',
						handler : function() {
							user_password_form.submit();
						}
					},{
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							user_password_dialog.dialog('destroy');
						}
					}],
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad:function(){
						initPasswordForm();
						$('#user_password_form_id').val(row.id);
					}
				}).dialog('open');
				
			}else{
				eu.showMsg("请选择要操作的对象！");
			}
		}
		
		//初始化用户角色表单 
		function initUserRoleForm(){
			user_role_form = $('#user_role_form').form({
				url: '${ctx}/base/security/user/updateUserRole',
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
					if (json.code == 1){
						user_role_dialog.dialog('destroy');//销毁对话框 
						user_datagrid.datagrid('reload');	// reload the user data
						eu.showMsg(json.msg);//操作结果提示
					}else {
						eu.showAlertMsg(json.msg,'error');
					}
				}
			});
		}
		
		//修改用户角色
		function editUserRole(){
			//选中的所有行
			var rows = user_datagrid.datagrid('getSelections');
			//选中的行（第一条）
			var row = user_datagrid.datagrid('getSelected');
			if (row){
				if(rows.length>1){
					eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
				}
				//判断是否允许操作
				if(isOpeated(row.id) == false){
					eu.showMsg("超级管理员用户信息不允许被其他人修改！");
					return;
				}
				//判断是否允许操作
				if(row.id == 1){
					eu.showMsg("超级管理员无需设置角色！");
					return;
				}
				//弹出对话窗口
				user_role_dialog = $('<div/>').dialog({
					title:'用户角色信息',
					width : 400,
					height : 200,
					modal : true,
					maximizable:true,
					href : '${ctx}/base/security/user/redirect/user_role',
					buttons : [ {
						text : '保存',
						iconCls : 'icon-save',
						handler : function() {
							user_role_form.submit();
						}
					},{
						text : '关闭',
						iconCls : 'icon-cancel',
						handler : function() {
							user_role_dialog.dialog('destroy');
						}
					}],
					onClose : function() {
						$(this).dialog('destroy');
					},
					onLoad:function(){
						initUserRoleForm();
						user_role_form.form('load', row);
					}
				}).dialog('open');
				
			}else{
				eu.showMsg("请选择要操作的对象！");
			}
		}
		
		//删除用户
		function del(){
			var rows = user_datagrid.datagrid('getSelections');
			
			if(rows.length >0){
				$.messager.confirm('确认提示！','您确定要删除选中的所有行?',function(r){
					if (r){
						var ids = new Object();
						for(var i=0;i<rows.length;i++){
							ids[i] = rows[i].id;
//							if(rows[i].id == 2){
//								eu.showAlertMsg('无法删除基础用户.','error');
//								return;
//							}
						}
						$.post('${ctx}/base/security/user/delete',{ids:ids},function(data){
							if (data.code==1){
								user_datagrid.datagrid('load');	// reload the user data
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
			user_datagrid.datagrid('load',$.serializeObject(user_search_form));
		}
</script>
<body>
<div class="easyui-layout" fit="true" style="margin: 0px; border: 0px; overflow: hidden; width: 100%; height: 100%;">

    <%-- 中间部分 列表 --%>
    <div data-options="region:'center',split:false,border:false" style="padding: 0px; height: 100%; width: 100%; overflow-y: hidden;">

        <%-- 列表右键 --%>
        <div id="user_datagrid_menu" class="easyui-menu" style="width:120px;display: none;">
            <div onclick="editUserRole();" data-options="iconCls:'icon-group'">设置角色</div>
			<div onclick="showDialog();" data-options="iconCls:'icon-add'">新增</div>
			<div onclick="batchAdd();" data-options="iconCls:'icon-add'">批量添加</div>
			<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
			<div onclick="editPassword();" data-options="iconCls:'icon-lock'">修改密码</div>
			<div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
        </div>

        <%-- 工具栏 操作按钮filter_LIKES_loginName_OR_name --%>
	   <div id="user_datagrid-toolbar">
	        <div style="margin-left:10px; float: left;">
		         <form id="user_search_form" style="padding: 0px;">
					登录名或姓名:<input type="text" name="LIKE_fullName" placeholder="请输入登录名或姓名..." maxLength="25" style="width: 160px"></input>
					<a href="javascript:search();" class="easyui-linkbutton"
							iconCls="icon-search" plain="true" >查 询</a>
				</form>
			</div>
			<div align="right">
			    <a href="#" class="easyui-linkbutton" iconCls="icon-group" plain="true" onclick="editUserRole()">设置角色</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showDialog()">新增</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="batchAdd()">批量添加</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-lock" plain="true" onclick="editPassword()">修改密码</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a> 
			</div>
		</div>
        <table id="user_datagrid" toolbar="#user_datagrid-toolbar" fit="true"></table>

    </div>
</div>
</body>
</html>