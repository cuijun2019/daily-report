<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
var resource_treegrid;
var resource_form;
var resource_dialog;
var resource_search_form;
var resource_Id;
$(function() {
    //数据列表
    resource_treegrid = $('#resource_treegrid').treegrid({
        url:'${ctx}/base/security/privilege/query',
        striped:true,//显示条纹
        singleSelect:false,//单选模式
        rownumbers:true,//显示行数
        nowrap : false,
        border : false,
        singleSelect:true,
        remoteSort:false,//是否通过远程服务器对数据排序
        sortName:'order',//默认排序字段
        sortOrder:'asc',//默认排序方式 'desc' 'asc'
        idField : 'id',
        treeField:"name",
        fitColumns:false,//自适应宽度
        columns:[[
            {field:'id',title:'主键',hidden:true,sortable:true,align:'right',width:80},
            {field:'name',title:'资源名称',width:200},
            {field:'code',title:'资源编码',width:80},
            {field:'url',title:'链接地址',width:350},
            {field:'level',title:'层次',width:80},
            {field:'order',title:'排序',align:'right',width:60,sortable:true},
            {field:'typedesc',title:'类型',align:'center',width:100}
        ]],
        onContextMenu : function(e, row) {
            e.preventDefault();
            $(this).treegrid('select', row.id);
            $('#resource_menu').menu('show', {
                left : e.pageX,
                top : e.pageY
            });

        },
        onDblClickRow:function(row){
            edit(row);
        },
        onLoadSuccess:function(){
            //鼠标移动提示列表信息tooltip
            $(this).datagrid('showTooltip');
            //表头居中
            //eu.datagridHeaderCenter();
        }
    });

});

//设置排序默认值
function setSortValue() {
    $.get('${ctx}/base/security/privilege/maxsort', function(data) {
        if (data.code == 1) {
            $('#orderNo').numberspinner('setValue',data.obj+1);
        }
    }, 'json');
}

function formInit(){
    resource_form = $('#resource_form').form({
        url: '${ctx}/base/security/privilege/save',
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
                resource_dialog.dialog('destroy');//销毁对话框
                resource_treegrid.treegrid('reload');//重新加载列表数据
                eu.showMsg(json.msg);//操作结果提示
            }else if(json.code == 2){
                $.messager.alert('提示信息！', json.msg, 'warning',function(){
                    if(json.obj){
                        $('#resource_form input[name="'+json.obj+'"]').focus();
                    }
                });
            }else {
                eu.showAlertMsg(json.msg,'error');
            }
        },
        onLoadSuccess:function(data){
            if(data != undefined && data._parentId != undefined){
                //$('#_parentId')是弹出-input页面的对象 代表所属分组
                $('#_parentId').combotree('setValue',data._parentId);
            }
        }
    });
}
//显示弹出窗口 新增：row为空 编辑:row有值
function showDialog(row){
    var inputUrl = "${ctx}/base/security/privilege/input";
    if(row != undefined && row.id){
        inputUrl = inputUrl+"?id="+row.id;
    }

    //弹出对话窗口
    resource_dialog = $('<div/>').dialog({
        title:'资源详细信息',
        width : 500,
        height : 360,
        modal : true,
        maximizable:true,
        href : inputUrl,
        buttons : [ {
            text : '保存',
            iconCls : 'icon-save',
            handler : function() {
                resource_form.submit();
            }
        },{
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
                resource_dialog.dialog('destroy');
            }
        }],
        onClose : function() {
            $(this).dialog('destroy');
        },
        onLoad:function(){
            formInit();
            if(row){
                resource_form.form('load', row);
            } else{
                setSortValue();
                var selectedNode = resource_treegrid.treegrid('getSelected');
                if(selectedNode){
                    var initFormData = {'_parentId':[selectedNode.id],'type':selectedNode.type};
                    resource_form.form('load',initFormData );
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
    var rows = resource_treegrid.treegrid('getSelections');
    //选中的行（第一次选择的行）
    var row = resource_treegrid.treegrid('getSelected');
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
    var rows = resource_treegrid.treegrid('getSelections');

    if(rows.length >0){
        $.messager.confirm('确认提示！','您确定要删除选中的所有行(如果存在子节点，子节点也一起会被删除)？',function(r){
            if (r){
                var ids = new Object();
                for(var i=0;i<rows.length;i++){
                    ids[i] = rows[i].id;
                }
                $.post('${ctx}/base/security/privilege/delete',{ids:ids},function(data){
                    if (data.code==1){
                        resource_treegrid.treegrid('reload');	// reload the user data
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

</script>
<body>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">

    <%-- 中间部分 列表 --%>
    <div data-options="region:'center',split:false,border:false"
         style="padding: 0px; height: 100%;width:100%; overflow-y: hidden;">

        <%-- 列表右键 --%>
        <div id="resource_menu" class="easyui-menu" style="width:120px;display: none;">
            <div onclick="showDialog();" data-options="iconCls:'icon-add'">新增</div>
            <div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
            <div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
        </div>

        <%-- 工具栏 操作按钮 --%>
        <div id="resource_toolbar">
            <div style="margin-bottom:5px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showDialog()">新增</a>
                <span class="toolbar-btn-separator"></span>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                <span class="toolbar-btn-separator"></span>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
            </div>
            <%--<div>
                <form id="resource_search_form" style="padding: 0px;">
                    类型名称或编码: <input type="text" id="filter_LIKES_name_OR_code" name="filter_LIKES_name_OR_code" placeholder="请输入类型名称或编码..." maxLength="25" style="width: 160px"></input>
                    <a href="javascript:search();" class="easyui-linkbutton"
                            iconCls="icon-search" plain="true" >查 询</a>
                </form>
            </div>--%>
        </div>
        <table id="resource_treegrid" toolbar="#resource_toolbar" fit="true"></table>

    </div>
</div>
</body>
</html>