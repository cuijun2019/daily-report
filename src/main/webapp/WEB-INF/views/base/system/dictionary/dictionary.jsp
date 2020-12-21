<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/meta.jsp"%>
<script type="text/javascript">
var dictionary_datagrid;
var editRow = undefined;
var editRowData = undefined;
var dictionary_search_form;
var dictionaryTypeCode = undefined;
var dictionary_filter_EQS_dictionaryType__code;
$(function() {
	dictionary_search_form = $('#dictionary_search_form').form();
	//数据列表
	dictionary_datagrid = $('#dictionary_datagrid').datagrid( {
		url : '${ctx}/base/system/dictionary/query',
		pagination : true,//底部分页
		pagePosition : 'bottom',//'top','bottom','both'.
		fitColumns : true,//自适应列宽
		striped : true,//显示条纹
		pageSize : 20,//每页记录数
		singleSelect : true,//单选模式
		rownumbers : true,//显示行数
		checkbox : true,
		nowrap : true,
		border : false,
		remoteSort : false,//是否通过远程服务器对数据排序
		sortName : 'orderNo',//默认排序字段
		sortOrder : 'asc',//默认排序方式 'desc' 'asc'
		idField : 'id',
		fitColumns : false,//自适应宽度
		rowStyler : function(index, row) {
			if (row.id > 80) {
				//return 'background-color:#6293BB;color:#fff;';
			}
		},
		columns : [ [ {
			field : 'ck',
			checkbox : true,
			width : 60
		}, {
			field : 'id',
			title : '主键',
			hidden : true,
			sortable : true,
			align : 'right',
			width : 80
		}, {
			field : 'dictionaryTypeCode',
			title : '字典类型',
			width : 100,
			formatter : function(value, rowData, rowIndex) {
				return rowData.dictionaryTypeName;
			},
			editor : {
				type : 'combobox',
				options : {
					url : '${ctx}/base/system/dictionary/combobox/all',
					required : true,
					missingMessage : '请选择字典类型(如果不存在,可以选择[字典类型管理]按钮,添加字典类型)！',
					editable : false,//是否可编辑
					valueField : 'value',
					displayField : 'text',
					groupField : 'group',
					onSelect : function(record) {
						dictionaryTypeCode = record.value;
						var dictionaryTypeEditor = dictionary_datagrid.datagrid('getEditor', {
							index : editRow,
							field : 'parentDictionaryCode'
						});
						$(dictionaryTypeEditor.target).combotree('clear').combotree('reload');
						var codeEditor = dictionary_datagrid.datagrid('getEditor', {
							index : editRow,
							field : 'code'
						});
						var vallueEditor = dictionary_datagrid.datagrid('getEditor', {
							index : editRow,
							field : 'value'
						})
						//$(codeEditor.target).val(dictionaryTypeCode);
//						$(vallueEditor.target).val(dictionaryTypeCode);
//						$(codeEditor.target).validatebox('validate');
					}
				}
			}
		}, {
			field : 'parentDictionaryCode',
			title : '上级节点',
			width : 80,
			formatter : function(value, rowData, rowIndex) {
				return rowData.parentDictionaryName;
			},
			editor : {
				type : 'combotree',
				options : {
					url : '${ctx}/base/system/dictionary/combotree/select',
					onBeforeLoad : function(node, param) {
						if (dictionaryTypeCode != undefined) {
							param.dictionaryTypeCode = dictionaryTypeCode;
						}
						if (editRowData != undefined) {
							param.id = editRowData.id;
						}
					}
				}
			}
		}, {
			field : 'name',
			title : '名称',
			width : 180,
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					missingMessage : '请输入名称！',
					validType : [ 'minLength[1]', 'length[1,64]', 'legalInput' ]
				}
			}
		}, {
			field : 'code',
			title : '编码',
			width : 150,
			sortable : true,
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					missingMessage : '请输入编码！',
					validType : [ 'minLength[1]', 'length[1,36]', 'legalInput' ]
				}
			}
		}, {
			field : 'value',
			title : '属性值',
			width : 50,
			sortable : true,
			editor : {
				type : 'validatebox',
				options : {}
			}
		}, {
			field : 'remark',
			title : '备注',
			width : 280,
			editor : {
				type : 'text',
				options : {}
			}
		}, {
			field : 'orderNo',
			title : '排序',
			align : 'right',
			width : 60,
			sortable : true,
			editor : {
				type : 'numberspinner',
				options : {
					required : true
				}
			}
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 160,
			sortable : true,
			editor : {
				type : 'my97',
				options : {
					dateFmt : 'yyyy-MM-dd HH:mm:ss'
				}
			}
		} ] ],
		onDblClickRow : function(rowIndex, rowData) {
			if (editRow != undefined) {
				eu.showMsg("请先保存正在编辑的数据！");
				//dictionary_datagrid.datagrid('endEdit', editRow);
		} else {
			$(this).datagrid('beginEdit', rowIndex);
			$(this).datagrid('unselectAll');
			//bindCodeEvent(rowIndex);
		}
	},
	onBeforeEdit : function(rowIndex, rowData) {
		editRow = rowIndex;
		editRowData = rowData;
		dictionaryTypeCode = rowData.dictionaryTypeCode;
	},
	onAfterEdit : function(rowIndex, rowData, changes) {
		$.messager.progress( {
			title : '提示信息！',
			text : '数据处理中，请稍后....'
		});
		var inserted = dictionary_datagrid.datagrid('getChanges', 'inserted');
		var updated = dictionary_datagrid.datagrid('getChanges', 'updated');
		if (inserted.length < 1 && updated.length < 1) {
			editRow = undefined;
			editRowData = undefined;
			$(this).datagrid('unselectAll');
			dictionary_datagrid.datagrid('load');//重新加载列表数据
		}
		var params = {};
		params['id'] = rowData.id;
		params['dictionaryTypeCode'] = rowData.dictionaryTypeCode;
		params['name'] = rowData.name;
		params['code'] = rowData.code;
		params['value'] = rowData.value;
		params['remark'] = rowData.remark;
		params['orderNo'] = rowData.orderNo;
		
		//alert($.parseJSON(rowData));
		$.post('${ctx}/base/system/dictionary/save', params, function(data) {
			
			$.messager.progress('close');
			if (data.code == 1) {
				dictionary_datagrid.datagrid('acceptChanges');
				cancelSelect();
				dictionary_datagrid.datagrid('reload');
				eu.showMsg(data.msg);
			} else {// 警告信息
					$.messager.alert('提示信息！', data.msg, 'warning', function() {
						dictionary_datagrid.datagrid('beginEdit', editRow);
						if (data.obj) {//校验失败字段 获取焦点
								var validateEdit = dictionary_datagrid.datagrid('getEditor', {
									index : rowIndex,
									field : data.obj
								});
								$(validateEdit.target).focus();
							}
						});
				}
			}, 'json');
	},
	onLoadSuccess : function(data) {
		$(this).datagrid('clearSelections');//取消所有的已选择项
		$(this).datagrid('unselectAll');//取消全选按钮为全选状态
		editRow = undefined;
		editRowData = undefined;
		dictionaryTypeCode = undefined;

	},
	onRowContextMenu : function(e, rowIndex, rowData) {
		e.preventDefault();
		$(this).datagrid('unselectAll');
		$(this).datagrid('selectRow', rowIndex);
		$('#dictionary_menu').menu('show', {
			left : e.pageX,
			top : e.pageY
		});
	}
	});

	dictionary_filter_EQS_dictionaryType__code = $('#filter_EQS_dictionaryType__code').combobox( {
		url : '${ctx}/base/system/dictionary/combobox/all',
		multiple : false,//是否可多选
		editable : false,//是否可编辑
		width : 120,
		valueField : 'value',
		displayField : 'text',
		groupField : 'group'
	});
});

//字典编码 editor绑定change事件
function bindCodeEvent(rowIndex) {
	// 绑定事件监听
	var codeEditor = dictionary_datagrid.datagrid('getEditor', {
		index : rowIndex,
		field : 'code'
	});
	var valueEditor = dictionary_datagrid.datagrid('getEditor', {
		index : rowIndex,
		field : 'value'
	});
	codeEditor.target.bind('change', function() {
		$(valueEditor.target).val($(this).val())
	});
}
//字典类型管理
function dictionaryType() {
	//parent.layout_center_tabs 指向父级layout_center_tabs选项卡(center.jsp)
	$.fn.app('addTab', {
		title : '字典类型管理',
		url : '${ctx}/base/system/dictionary/redirect/dictionary_type',
		closable : true
	});
	//eu.addTab(parent.tabContainer, "字典类型管理", "${ctx}/base/system/dictionary/redirect/dictionary-type", true, "icon-folder");
}

//设置排序默认值
function setSortValue(target) {
	$.get('${ctx}/base/system/dictionary/maxsort', function(data) {
		if (data.code == 1) {
			$(target).numberbox( {
				value : data.obj + 1
			});
			$(target).numberbox('validate');
		}
	}, 'json');
}

//新增
function add() {
	if (editRow != undefined) {
		eu.showMsg("请先保存正在编辑的数据！");
		//结束编辑 自动保存
		//dictionary_datagrid.datagrid('endEdit', editRow);
	} else {
		cancelSelect();
		var row = {
			id : ''
		};
		dictionary_datagrid.datagrid('appendRow', row);
		editRow = dictionary_datagrid.datagrid('getRows').length - 1;
		dictionary_datagrid.datagrid('selectRow', editRow);
		dictionary_datagrid.datagrid('beginEdit', editRow);
		var rowIndex = dictionary_datagrid.datagrid('getRowIndex', row);//返回指定行的索引
		var sortEdit = dictionary_datagrid.datagrid('getEditor', {
			index : rowIndex,
			field : 'orderNo'
		});
		setSortValue(sortEdit.target);
		//bindCodeEvent(rowIndex);
	}
}

//编辑
function edit() {
	//选中的所有行
	var rows = dictionary_datagrid.datagrid('getSelections');
	//选中的行（第一次选择的行）
	var row = dictionary_datagrid.datagrid('getSelected');
	if (row) {
		if (rows.length > 1) {
			row = rows[rows.length - 1];
			eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
		}
		if (editRow != undefined) {
			eu.showMsg("请先保存正在编辑的数据！");
			//结束编辑 自动保存
			//dictionary_datagrid.datagrid('endEdit', editRow);
		} else {
			editRow = dictionary_datagrid.datagrid('getRowIndex', row);
			dictionary_datagrid.datagrid('beginEdit', editRow);
			cancelSelect();
			//bindCodeEvent(editRow);
		}
	} else {
		if (editRow != undefined) {
			eu.showMsg("请先保存正在编辑的数据！");
		} else {
			eu.showMsg("请选择要操作的对象！");
		}
	}
}

//保存
function save(rowData) {
	if (editRow != undefined) {
		dictionary_datagrid.datagrid('endEdit', editRow);
	} else {
		eu.showMsg("请选择要操作的对象！");
	}
}

//取消编辑
function cancelEdit() {
	cancelSelect();
	dictionary_datagrid.datagrid('rejectChanges');
	editRow = undefined;
	editRowData = undefined;
	dictionaryTypeCode = undefined;
}
//取消选择
function cancelSelect() {
	dictionary_datagrid.datagrid('unselectAll');
}

//删除
function del() {
	var rows = dictionary_datagrid.datagrid('getSelections');
	if (rows.length > 0) {
		if (editRow != undefined) {
			eu.showMsg("请先保存正在编辑的数据！");
			return;
		}
		$.messager.confirm('确认提示！', '您确定要删除当前选中的所有行？', function(r) {
			if (r) {
				var ids = new Object();
				for ( var i = 0; i < rows.length; i++) {
					ids[i] = rows[i].id;
				}
				var param = {
					data : JSON.stringify(ids)
				}
				$.post('${ctx}/base/system/dictionary/delete', ids, function(data) {
					if (data.code == 1) {
						dictionary_datagrid.datagrid('clearSelections');//取消所有的已选择项
						dictionary_datagrid.datagrid('reload');//重新加载列表数据
						eu.showMsg(data.msg);//操作结果提示
					} else {
						eu.showAlertMsg(data.msg, 'error');
					}
				}, 'json');
			}
		});
	} else {
		eu.showMsg("请选择要操作的对象！");
	}
}

//搜索
function search() {

        var url = '${ctx}/base/system/dictionary/query?' + 'LIKE_name=' 
            + document.getElementById("filter_LIKES_name_OR_code").value
            + '&EQ_dictionaryTypeName=' + $('#filter_EQS_dictionaryType__code').combobox('getText');
        $('#dictionary_datagrid').datagrid( {
		url : url});
}
</script>
<div class="easyui-layout" fit="true" style="margin: 0px; border: 0px; overflow: hidden; width: 100%; height: 100%;">

	<%-- 中间部分 列表 --%>
	<div data-options="region:'center',split:false,border:false" style="padding: 0px; height: 100%; width: 100%; overflow-y: hidden;">

		<%-- 列表右键 --%>
		<div id="dictionary_menu" class="easyui-menu" style="width: 120px; display: none;">
			<div onclick="dictionaryType();" data-options="iconCls:'icon-folder'">
				字典类型管理
			</div>
			<div onclick="add();" data-options="iconCls:'icon-add'">
				新增
			</div>
			<div onclick="edit();" data-options="iconCls:'icon-edit'">
				编辑
			</div>
			<div onclick="del();" data-options="iconCls:'icon-remove'">
				删除
			</div>
		</div>

		<%-- 工具栏 操作按钮 --%>
		<div id="dictionary_toolbar">
			<div style="margin-bottom: 5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-folder" plain="true" onclick="dictionaryType()">字典类型管理</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">新增</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save()">保存</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelEdit()">取消编辑</a>
				<span class="toolbar-btn-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="cancelSelect()">取消选中</a>

			</div>
			<div>
				<form id="dictionary_search_form" style="padding: 0px;">
					字典分组:
					<select id="filter_EQS_dictionaryType__code" name="filter_EQS_dictionaryType__code" class="easyui-combobox"></select>
					名称或编码:
					<input type="text" id="filter_LIKES_name_OR_code" name="filter_LIKES_name_OR_code" placeholder="请输入名称或编码..." maxLength="25" style="width: 160px"></input>
					<a href="javascript:search();" class="easyui-linkbutton" iconCls="icon-search" plain="true">查 询</a>
				</form>
			</div>
		</div>
		<table id="dictionary_datagrid" toolbar="#dictionary_toolbar" fit="true"></table>
	</div>
</div>