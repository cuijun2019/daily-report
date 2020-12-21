<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
	$(function() {
		loadParent();
		//loadIco();
        loadType();
	});
	//加载父级资源
	function loadParent(){
		$('#_parentId').combotree({
	        url:'${ctx}/base/security/privilege/parentresource/select',
		    multiple:false,//是否可多选
		    editable:false,//是否可编辑
		    width:200,
	        valueField:'id',
	        displayField:'text',
	        onHidePanel:function(){
	        	//防止自关联
	        	if($('#id').val() && $(this).combotree('getValue') == $('#id').val()){
                    eu.showMsg('不允许设置上级资源为自己,请重新选择!');
	        		$(this).combotree('setValue','');
	        	}
	        },
            onBeforeLoad:function(node,param){
                param.id = "${id}";
            },
            onSelect:function(node){
                //上级资源类型 菜单：0 功能：1  限制:如果上级是功能则下级只能是功能
                var parentType = node.attributes.type;
                if(parentType != undefined && parentType ==1){
                    $('#type').combobox('setValue',1).combobox('readonly',true);
                }else{
                    $('#type').combobox('readonly',false);
                }
            }

		});
	}
	//加载资源图标
	function loadIco(){
		$('#iconCls').combobox({
			url:'${ctx}/js/json/resource.json',
		    multiple:false,//是否可多选
		    editable:false,//是否可编辑
		    width:120,
	        valueField:'value',
	        displayField:'text',
	        formatter:function(row){    
	        	return $.formatString('<span class="tree-icon tree-file {0}"></span>{1}', row.value, row.text);
	        }
		});
	}
    //加载资源类型
    function loadType(){
        $('#kind').combobox({
            url:'${ctx}/base/security/privilege/resourcetypecombobox',
            multiple:false,//是否可多选
            editable:false,//是否可编辑
            width:120,
            value:'0',//默认值 ‘0’即菜单
            valueField:'value',
            displayField:'text'
        });
    }
</script>
<div>
    <form id="resource_form" method="post">
        <input type="hidden" id="id" name="id" value="111"/>
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>上级资源:</label>
            <input id="_parentId" name="parentId" class="easyui-combotree" />
        </div>
        <!-- <div>
            <label>资源图标:</label>
            <input id="iconCls" name="iconCls"
                   class="easyui-combobox"
                   data-options="tipPosition:'left',required:true,missingMessage:'请选择资源图标.',url:'${ctx}/js/json/resource.json'" />
        </div>
        -->
        <div>
            <label>资源名称:</label>
            <input type="text" id="name" name="name"
                   maxLength="20" class="easyui-validatebox" placeholder="请输入资源名称..."
                   data-options="required:true,missingMessage:'请输入资源名称.',validType:['minLength[1]']" />
        </div>
        <div>
            <label>资源编码:</label>
            <input type="text" id="code" name="code"
                   maxLength="20" class="easyui-validatebox" placeholder="请输入资源编码..."
                   data-options="validType:['minLength[1]']" />
            <%--提示小图标--%>
            <span class="tree-file icon-tip"
                  title="资源识别的唯一标识;主要用于[功能]类型的资源能够根据编码进行权限控制." ></span>
        </div>
        <div>
            <label>链接地址:</label>
            <input type="text" id="url" name="url" maxLength="255" class="easyui-validatebox" />
        </div>
        <div>
            <label>排序:</label>
            <input type="text" id="order" name="order" class="easyui-numberspinner"
                   data-options="min:1,max:99999999,size:9,maxlength:9,required:true,missingMessage:'请输入排序.'" />
        </div>
        <div>
            <label>资源类型:</label>
            <input id="kind" name="kind" class="easyui-combobox"
                   data-options="required:true,missingMessage:'请选择资源类型.'" />
            <%--提示小图标--%>
            <span class="tree-file icon-tip"
                  title="上级资源的资源类型为[功能]，则资源类型默认为[功能]，并且不可更改." ></span>
        </div>
       
    </form>
</div>