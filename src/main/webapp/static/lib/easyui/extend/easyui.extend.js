$.fn.extend({
    /**
     * DOM元素屏幕居中, 暂时放在这里
     */
    center: function () {
        var p = {}, el = $(this), win = $(window), doc = $(document), h = el.height(), w = el.width();
        p.top = (win.height() - h) / 2;
        p.left = (win.width() - w) / 2;
        el.css('position', 'absolute');
        el.css('left', p.left + 'px');
        el.css('top', p.top + 'px');
    }
});
/**
 * 增加url属性支持
 */
$.extend($.fn.linkbutton, {
    parseOptions: function(target){
        var t = $(target);
        return $.extend({}, $.parser.parseOptions(target,
            ['id','iconCls','iconAlign','group','url','action','title','message',{plain:'boolean',toggle:'boolean',selected:'boolean'}]
        ), {
            disabled: (t.attr('disabled') ? true : undefined),
            text: $.trim(t.html()),
            iconCls: (t.attr('icon') || t.attr('iconCls'))
        });
    }
});
// 重载EasyUI.Tree控件与后台整合相关的参数调整
/*
 $.extend($.fn.tree.defaults, {
 //重写 loader 方法，实现动态生成URL与修改data以符合tree要求

 loader: function (param, success, error) {
 var opts = $(this).tree("options");
 if (!opts.url) {
 return false;
 }
 var url = opts.url + "/" + (param.id ? param.id : '0');
 $.ajax({type: opts.method, url: url, data: '', dataType: "json", success: function (data) {
 var vdata = [];
 $.each(data, function (index, value) {
 value.attributes = {}
 var view = {
 id: value.id,
 text: value.name,
 state: value.leaf || value.url ? 'open' : 'closed',
 attributes: value
 }
 vdata[index] = view;
 });
 success(vdata);
 }, error: function () {
 error.apply(this, arguments);
 }});
 }
 });
 */
$.extend($.fn.form.defaults, {
    related: null
});
// 重载EasyUI.Form控件与后台整合相关的参数调整
$.extend($.fn.form.methods, {
    /**
     * 表单序列化为JSON字面量
     */
    serializer: function (jq) {
        var data = {};
        var elements = jq.serializeArray();
        $.each(elements, function () {
            if (data[this.name]) {
                if (!data[this.name].push) {
                    data[this.name] = [data[this.name]];
                }
                data[this.name].push(this.value || '');
            } else {
                data[this.name] = this.value || '';
            }
        });
        return data;
    },
    /**
     * 该方法用于刷新 related 指定的控件，一般为DataGrid
     */
    refresh : function(jq, options){

    }
});
// 重载EasyUI.DataGrid控件Plain模式下增加 related 的支持
// 使DataGrid向服务端请求数据时自动整合 related属性指定的查询表单中的表单项值
// 实现查询表单与DataGrid的动态关联
$.extend($.fn.datagrid, {
    parseOptions: function (jq) {
        var t = $(jq), opts =$.extend({}, $.fn.panel.parseOptions(jq), $.parser.parseOptions(jq, ["url", 'related', 'column', "toolbar", "idField", "sortName", "sortOrder", "pagePosition", "resizeHandle", {fitColumns: "boolean", autoRowHeight: "boolean", striped: "boolean", nowrap: "boolean"}, {rownumbers: "boolean", singleSelect: "boolean", checkOnSelect: "boolean", selectOnCheck: "boolean"}, {pagination: "boolean", pageSize: "number", pageNumber: "number"}, {remoteSort: "boolean", showHeader: "boolean", showFooter: "boolean"}, {scrollbarSize: "number"}]), {pageList: (t.attr("pageList") ? eval(t.attr("pageList")) : undefined), loadMsg: (t.attr("loadMsg") != undefined ? t.attr("loadMsg") : undefined), rowStyler: (t.attr("rowStyler") ? eval(t.attr("rowStyler")) : undefined)});
        if(opts.column){
            // TODO: 从用服务端获取columns定义
        }
        return opts;
    }
});
// 重载EasyUI.DataGrid控件与后台整合相关的参数调整
$.extend($.fn.datagrid.defaults, {
    /**
     * 默认绑定的查询表单，值为表单ID( form element id )
     */
    related: '#searchForm',
    /**
     * 重载loader方法
     */
    loader: function (param, success, error) {
        var opts = $(this).datagrid("options");
        if (!opts.url) {
            return false;
        }
        // 重定义查询参数
        var params = $.extend(param,{
            pagination: JSON.stringify({pageSize: param.rows || 10, currentPage: param.page || 1})
        }, $(opts.related).form('serializer'));

        $.ajax({type: opts.method, url: opts.url, data: params, dataType: "json", success: function (data) {
            var page = data.pagination ? {total: data.pagination.totalCount, rows: data.rows} : data;
            success(page);
        }, error: function () {
            error.apply(this, arguments);
        }});
    }
});