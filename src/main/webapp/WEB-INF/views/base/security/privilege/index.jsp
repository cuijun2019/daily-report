<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WEB框架演示项目</title>
    <%@include file="/WEB-INF/layouts/meta.jsp" %>
    <link rel="stylesheet" href="${ctx}/static/lib/uploadify/uploadify.css"/>
    <script type="text/javascript" src="${ctx}/static/lib/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/uploadify/swfobject.js"></script>
</head>
<body class="easyui-layout">

<div data-options="region:'west',broder:false,collapsible:false" title="权限列表" style="width:200px;">
    <ul id="treeContainer" class="easyui-tree" url="${ctx}/base/security/privilege/menu" animate="true" lines="true"></ul>
</div>

<div data-options="region:'north',border:false" style="text-align: right; padding: 10px;">
    <form id="searchForm">
        <input id="symbol" name="LIKE_name" class="input" type="input" placeholder="模糊查询"/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="gridReload(true);">查询</a>
        <input type="hidden" id="parentId" name="EQ_parentId" value="0">
    </form>
</div>
<%-- 通过指定searchFrom使DataGrid与查询表达关联 --%>
<div data-options="region:'center',border:false">
    <table id="privilege" class="easyui-datagrid" url="${ctx}/base/security/privilege/query"
           column="#" title="权限信息" related="#searchForm" fit="true" fitColumns="true" pagination="true"
           rownumbers="true" singleSelect="false" toolbar="#toolbar">
        <thead>
        <tr>
            <th field="ck" checkbox="true"></th>
            <th data-options="field:'id',width:100">ID</th>
            <th data-options="field:'name',width:100">名称</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" title="添加权限" url="${ctx}/base/security/privilege/create/{parentId}" action="create">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" title="编辑权限" url="${ctx}/base/security/privilege/edit/{id}" action="edit">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" url="${ctx}/base/security/privilege/delete" action="remove" message="">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true" url="${ctx}/base/security/privilege/export" action="export">导出</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" plain="true" url="${ctx}/base/security/privilege/import" action="import">导入</a>
    </div>
</div>
<script>
    var gridReload = function (flag) {
        if (flag === true) {
            $('#parentId').val('');
        } else {
            $('#searchForm')[0].reset();
        }
        $('#privilege').datagrid('load');
    }
    $(document).ready(function () {
        $('#treeContainer').tree({
            onClick: function (node) {
                var data = node.attributes;
                $('#parentId').val(data.id);
                gridReload(false);
            }
        });
    });
</script>
<!-- <div id="fileQueue" ></div>
        <input type="file" name="uploadify" id="uploadify" />
        <p>
        <a href="javascript:jQuery('#uploadify').uploadifyUpload()">开始上传</a>&nbsp;
        <a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消所有上传</a>
        </p> 
          $(document).ready(function() {alert('${ctx}/static/lib/uploadify/uploadify.swf');
            $("#uploadify").uploadify({
                'swf'       : '${ctx}/static/lib/uploadify/uploadify.swf',
                'script'         : 'servlet/Upload',
                'cancelImg'      : '${ctx}/static/lib/uploadify/uploadify-cancel.png',
                'folder'         : 'uploads',
                'queueID'        : 'fileQueue',
                'auto'           : false,
                'multi'          : true,
                'buttonImg' 	 : '${ctx}/static/lib/uploadify/uploadify-cancel.png',
                'simUploadLimit' : 2,
                'buttonText'     : '选择附件'
            });
        });
        -->
</body>
</html>