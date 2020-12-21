<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/layouts/taglibs.jsp"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
    $(function() {
        loadTaskType();
        loadvVcQuartzCron();
        loadTaskStatus();
        loadTaskActiveFlag();

    });
    //加载父级资源
    function loadTaskType() {
        $('#_parentId_1').combotree({
            url: '${ctx}/modules/ltecustomreports/parentresource/select?',
            multiple: false,//是否可多选
            editable: false,//是否可编辑
            width: 200,
            valueField: 'id',
            textField: 'name',
            onHidePanel: function () {
                //防止自关联
                if ($('#id').val() && $(this).combotree('getValue') == $('#id').val()) {
                    eu.showMsg('不允许设置上级资源为自己,请重新选择!');
                    $(this).combotree('setValue', '');
                }
            },
            onBeforeLoad: function (node, param) {
                param.id = "${id}";
            },
            onSelect: function (node) {
                //上级资源类型 菜单：0 功能：1  限制:如果上级是功能则下级只能是功能
                var parentType = node.attributes.type;
                if (parentType != undefined && parentType == 1) {
                    $('#type').combobox('setValue', 1).combobox('readonly', true);
                } else {
                    $('#type').combobox('readonly', false);
                }
            }

        });
    }

    //加载存储过程资源
    function loadvVcQuartzCron() {
        $('#vcQuartzCron_1').combobox({
            url: '${ctx}/static/json/procedureType.json',
            width: 160,
            editable:false,
            value:'2',
            valueField: 'value',
            displayField: 'text'
        });
    }

    //任务状态
    function loadTaskStatus(){
        $('#intTaskStatus_1').combobox({
            url: '${ctx}/static/json/taskStatus.json',
            width: 160,
            editable:false,
            value:'0',
            valueField: 'value',
            displayField: 'text'
        });
    }

    //任务激活标识
    function loadTaskActiveFlag(){
        $('#bitTaskActiveFlag_1').combobox({
            url: '${ctx}/static/json/TaskActiveFlag.json',
            width: 160,
            editable:false,
            value:'0',
            valueField: 'value',
            displayField: 'text'
        });
    }
</script>

<div>
    <form id="task_form" method="post">

        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <input type="hidden" id="intLeaf" name="intLeaf" value="true"/>
        <input type="hidden" id="intTaskType" name="intTaskType" value="1"/>    <!--任务类型默认-->
        <input type="hidden" id="id" name="id"/>


        <div>
            <label>任务类型:</label>
            <input id="_parentId_1" name="nmParentId" class="easyui-combotree"  placeholder="请选择任务类型..." width="160px"/>
        </div>
        <div>
            <label>任务名称:</label>
            <input type="text" id="vcTaskName_1" name="name"   width="160px"
                   maxLength="20" class="easyui-validatebox" placeholder="请输入任务名称..."
                   data-options="required:true,missingMessage:'请输入任务名称.',validType:['minLength[1]']" />
        </div>
        <div>
            <label>定时器表达式:</label>
            <input id="vcQuartzCron_1" name="vcQuartzCron" class="easyui-combotree" data-options="required:true" placeholder="请选择定时器表达式..."/>
        </div>

       <div>
            <label>开始时间:</label>
            <input id="dtStartTime_1" name="dtStartTime" class="easyui-my97" placeholder="请输入开始时间" data-options="required:true,showSeconds:true,skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'" style="width: 140px">
        </div>
        <div>
            <label>结束时间:</label>
            <input id="dtEndTime_1" name="dtEndTime" class="easyui-my97" placeholder="请输入结束时间" data-options="required:true,showSeconds:true,skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'" style="width: 140px">
        </div>

        <div>
            <label>激活标识:</label>
            <input id="bitTaskActiveFlag_1" name="bitTaskActiveFlag" type="text" class="easyui-combobox" />
        </div>

        <div>
            <label>任务状态:</label>
            <input id="intTaskStatus_1" name="intTaskStatus" type="text" class="easyui-combobox" />
        </div>

        <div>
            <label>排序:</label>
            <input type="text" id="intOrder" name="intOrder" class="easyui-numberspinner"
                   data-options="min:1,max:999999,size:9,maxlength:9,required:true,missingMessage:'请输入排序.'" />
        </div>

    </form>
</div>