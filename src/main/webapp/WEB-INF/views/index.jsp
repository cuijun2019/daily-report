<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>运营数据呈现平台</title>
    <%@include file="/WEB-INF/layouts/meta.jsp" %>
</head>
<body class="easyui-layout">
<%-- 顶部区域，用户信息，登出等处理 --%>
<div data-options="region:'north',border:false,split:false,href:'${ctx}/base/main/redirect/north'"
     style="overflow: hidden;height:57px;background-image: url('${ctx}/static/image/lte_bg.jpg');">
</div>
<%-- 左部树型菜单 --%>
<div data-options="region:'west',split:true" title="工作台菜单" style="width:200px;">
    <ul id="menuContainer" class="easyui-tree" ></ul>
    <div id="mm" class="easyui-menu" style="width:120px;">
        <div onclick="reload()" data-options="iconCls:'icon-reload'">刷新</div>
    </div>
</div>
<%-- 底部栏 --%>
<div data-options="region:'south',border:false" style="height:25px;background-color:#f2f2f2;">
    <%@include file="/WEB-INF/layouts/footer.jsp" %>
</div>
<div data-options="region:'center',border:false" style="overflow: hidden;">
    <div id="tabContainer" class="easyui-tabs" data-options="fit:true">
    </div>
</div>
<script>
    var redirect = "<%=request.getParameter("redirect") %>";
    $(document).ready(function () {
        <%--$.fn.app('addTab', {--%>
            <%--id: 1,--%>
            <%--title: '首页',--%>
            <%--url: '${ctx}/modules/ltesoft/lte_portal.jsp',--%>
            <%--closable: false--%>
        <%--});--%>
        // 登出按钮处理
        $('#logout').on('click', function () {
            $.messager.confirm('Confirm', '确认要退出系统吗？', function (r) {
                if (r) top.location.href = '${ctx}/logout';
            });
        });

        $('#menuContainer').tree({
        	url: '${ctx}/base/security/privilege/menu',   
        	animate : "true",
        	lines : "true",
        	onLoadSuccess : function(node, data){
                //获取根节点
               // $('#menuContainer').tree('expandAll');
                var rooNode = $("#menuContainer").tree('getRoots');
                //调用expand方法
                $.each(rooNode,function(index,object){
                    if(object.id!="22")  {
                    $("#menuContainer").tree('expand',object.target);
                    }
                })

                //$('#menuContainer').tree('expandAll');
             //   var node = $('#menuContainer').tree('getSelected');


//                if(node.id == 22){
//                    var node22 = $('#menuContainer').tree('find', 22);
//                    $('#menuContainer').tree('collapse', node22.target);
//
//                }

        	},
            /**
             * 右键刷新，方便测试
             */
            onContextMenu: function (e, node) {
                e.preventDefault();
                $('#treeContainer').tree('select', node.target);
                $('#mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            },

            onExpand: function(node){

				 //var node = $('#menuContainer').tree('getRoot');
                $('#menuContainer').tree('expand', node.target);
            },
            onClick: function (node) {
                var data = node.attributes;
                if (data.url) {
                    $.fn.app('addTab', {
                        id: data.id,
                        title: data.name,
                        url: '${ctx}{0}'.format(data.url),
                        closable: true
                    });
                }
            }
        });
        
        
		
    });
    var reload = function () {
        var tree = $('#menuContainer').tree();
        //tree.reload(tree.getRoot());
    }






</script>
</body>
</html>