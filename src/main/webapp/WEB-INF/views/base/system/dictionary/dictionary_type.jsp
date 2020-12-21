<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/meta.jsp"%>
<script type="text/javascript">
var dictionaryType_treegrid;
var dictionaryType_form;
var dictionaryType_dialog;
var dictionaryType_search_form;
$(function() {  
    //数据列表
    dictionaryType_treegrid = $('#dictionaryType_treegrid').treegrid({
	    url:'${ctx}/base/system/dictionary/querytype',
	    fitColumns:true,//自适应列宽
	    striped:true,//显示条纹
	    pageSize:20,//每页记录数
	    singleSelect:false,//单选模式
	    rownumbers:true,//显示行数
		nowrap : false,
		border : false,
        singleSelect:true,
        remoteSort:false,//是否通过远程服务器对数据排序
		sortName:'orderNo',//默认排序字段
		sortOrder:'asc',//默认排序方式 'desc' 'asc'
		idField : 'code',
        treeField:"name",
		fitColumns:false,//自适应宽度
		columns:[ [
            {field : 'id',title : '主键',hidden : true,sortable:true,align : 'right',width : 80},
            {field : 'name',title : '名称', width : 150,sortable:true},
            {field : 'code',title : '类型编码',width : 100,sortable:true},
            {field : 'orderNo',title : '排序',align : 'right',width : 80,sortable:true } ,
            {field : 'remark', title : '备注',width : 200}
        ] ],
        onContextMenu : function(e, row) {
            e.preventDefault();
            $(this).treegrid('select', row.code);
            if(row._parentId){ //仅编辑或删除
                $('#dictionaryType_menu').menu('show', {
                    left : e.pageX,
                    top : e.pageY
                });
            } else{ //增加子项、编辑或删除
                $('#dictionaryType_group_menu').menu('show', {
                    left : e.pageX,
                    top : e.pageY
                });
            }

        },
        onDblClickRow:function(row){
            edit(row);
        }
	});

    loadGroupDictionaryType();
});

    //设置排序默认值
    function setSortValue() {
        $.get('${ctx}/base/system/dictionary/maxsorttype', function(data) {
            if (data.code == 1) { 
                $('#orderNo').numberspinner('setValue',data.obj+1);
            }
        }, 'json');
    }

    function formInit(){
        dictionaryType_form = $('#dictionaryType_form').form({
            url: '${ctx}/base/system/dictionary/savetype',
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
                    dictionaryType_dialog.dialog('destroy');//销毁对话框
                    dictionaryType_treegrid.treegrid('reload');//重新加载列表数据
                    eu.showMsg(json.msg);//操作结果提示
                }else if(json.code == 2){
                    $.messager.alert('提示信息！', json.msg, 'warning',function(){
                        if(json.obj){
                            $('#dictionaryType_form input[name="'+json.obj+'"]').focus();
                        }
                    });
                }else {
                    eu.showAlertMsg(json.msg,'error');
                }
            },
            onLoadSuccess:function(data){
                if(data && data._parentId){
                    //$('#_parentId')是弹出-input页面的对象 代表所属分组
                    $('#_parentId').combobox('setValue',data._parentId);
                }
                //如果存在子节点 即为分组节点
                if(data.children){
                    //将类别分组设置为只读 $('#_parentId')是弹出页面的对象
                    $('#_parentId').combobox('disable',true);
                }
            }
        });
    }
    //显示弹出窗口 新增：row为空 编辑:row有值
    function showDialog(row){
        //弹出对话窗口
        dictionaryType_dialog = $('<div/>').dialog({
            title:'字典类型详细信息',
            width : 500,
            height : 360,
            modal : true,
            maximizable:true,
            //href: '${ctx}/modules/dictionary_type_input.jsp',
            href : '${ctx}/base/system/dictionary/redirectp/dictionary_type_input',
            buttons : [ {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                    dictionaryType_form.submit();
                }
            },{
                text : '关闭',
                iconCls : 'icon-cancel',
                handler : function() {
                    dictionaryType_dialog.dialog('destroy');
                }
            }],
            onClose : function() {
                $(this).dialog('destroy');
            },
            onLoad:function(){
                formInit();
                if(row){
                    dictionaryType_form.form('load', row);
                } else{
                    setSortValue();
                    var selectedNode = dictionaryType_treegrid.treegrid('getSelected');
                    if(selectedNode){
                        var initFormData = {};
                        if(selectedNode._parentId){  //选中子项点击新增
                            var groupNode = dictionaryType_treegrid.treegrid('getParent',selectedNode.code);
                            initFormData = {'groupDictionaryTypeCode':[selectedNode._parentId],'code':groupNode.code};
                        }else{   //选分组点击新增
                            initFormData = {'groupDictionaryTypeCode':[selectedNode.code],'code':selectedNode.code};
                        }
                        dictionaryType_form.form('load',initFormData );
                    }
                }
            }
        }).dialog('open');

    }

    //编辑
    function edit(row){
        if(row != undefined){
            showDialog(row);
            return;
        }
        //选中的所有行
        var rows = dictionaryType_treegrid.treegrid('getSelections');
        //选中的行（第一次选择的行）
        var row = dictionaryType_treegrid.treegrid('getSelected');
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
        var rows = dictionaryType_treegrid.treegrid('getSelections');

        if(rows.length >0){
            $.messager.confirm('确认提示！','您确定要删除选中的所有行(如果存在子节点，子节点也一起会被删除)？',function(r){
                if (r){
                    var ids = new Object();
                    for(var i=0;i<rows.length;i++){
                        ids[i] = rows[i].id;
                    }
                    $.post('${ctx}/base/system/dictionary/deletetype',ids,function(data){
                        if (data.code==1){
                            dictionaryType_treegrid.treegrid('reload');	// reload the user data
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

    function loadGroupDictionaryType(){
        $('#filter_EQS_groupDictionaryType__code').combobox({
            url:'${ctx}/base/system/dictionary/group_combobox/all',
            multiple:false,//是否可多选
            editable:false,//是否可编辑
            width:120,
            valueField:'value',
            displayField:'text'
        });
    }
</script>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
	
	<%-- 中间部分 列表 --%>
	<div data-options="region:'center',split:false,border:false" 
		style="padding: 0px; height: 100%;width:100%; overflow-y: hidden;">
		
		<%-- 列表右键 --%>
		<div id="dictionaryType_group_menu" class="easyui-menu" style="width:120px;display: none;">
            <div onclick="showDialog();" data-options="iconCls:'icon-add'">新增</div>
			<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
			<div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
		</div>
        <div id="dictionaryType_menu" class="easyui-menu" style="width:120px;display: none;">
            <div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
            <div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
        </div>
		
	   <%-- 工具栏 操作按钮 --%>
	   <div id="dictionaryType_toolbar">
			<div style="margin-bottom:5px">    
		       <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showDialog()">新增</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a> 
		    </div>
		    <%--<div>
			    <form id="dictionaryType_search_form" style="padding: 0px;">
				    类型名称或编码: <input type="text" id="filter_LIKES_name_OR_code" name="filter_LIKES_name_OR_code" placeholder="请输入类型名称或编码..." maxLength="25" style="width: 160px"></input>
					<a href="javascript:search();" class="easyui-linkbutton"
							iconCls="icon-search" plain="true" >查 询</a>
                </form>
		    </div>--%>
		</div>
	   <table id="dictionaryType_treegrid" toolbar="#dictionaryType_toolbar" fit="true"></table>

	</div>
</div>