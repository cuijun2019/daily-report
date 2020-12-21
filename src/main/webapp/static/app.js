/**
 * 为字符串对像增加format方法，使用如下:
 * var mapStyle = "http://localhost:8080/module/{id}/{type}".format({id:10, type:'normal'}); // 按照字面量进行格式化，参数可以是datagrid的row
 * var indexStyle = "http://localhost:8080/module/{0}/{1}".format(10, 'normal'); // 按照参数的index进行格式化
 * @returns {*}
 */
String.prototype.format = function () {
    var s = this, i = arguments.length;
    while (i--) {
        var v = arguments[i];
        if (typeof v === 'object' && arguments.length == 1) {
            for (var k in v) {
                s = s.replace(new RegExp('\\{' + k + '\\}', 'gm'), v[k]);
            }
        } else {
            s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), v);
        }
    }
    return s;
};

/**
 * 主界面应用程序，多页面应用
 */
(function ($) {
    // 只有top frame才对app进行初始化
    if (top == self) {
        // 私有方法
        var inited = false, messageTitle = "温馨提示";
        // 公共方法
        /**
         * 构造器, 建立框架(frame or iframe)间通信
         * @param options 对应$.fn.app.methods中定义的方法
         * @param params 对options方法的参数
         */
        $.fn.app = function (options, params) {
            $.fn.app.methods['init'](window, params);
            if (typeof options === 'string') {
                var method = $.fn.app.methods[options];
                if (method) {
                    return method(window, params);
                }
            }
        };
        /**
         * 警告提示框
         * @param message 提示信息
         * @param icon 警告图标
         */
        $.fn.app.alert = function (message, icon) {
            if (icon) {
                $.messager.alert(messageTitle, message, icon);
            } else {
                $.messager.alert(messageTitle, message);
            }
        };
        /**
         * 确认提示框
         * @param message 提示信息
         * @param callback 回调函数
         */
        $.fn.app.confirm = function (message, callback) {
            $.messager.confirm(messageTitle, message, function (r) {
                if (r) {
                    callback.call();
                }
            });
        };
        /**
         * 消息提示框，会自动隐藏
         * @param message 提示信息
         */
        $.fn.app.show = function (message) {
            $.messager.show({
                title: messageTitle,
                msg: message,
                showType: 'slide',
                style: {
                    right: '',
                    top: document.body.scrollTop + document.documentElement.scrollTop,
                    bottom: ''
                }
            });
        };
        /**
         * UUID工具类，除生成UUID外，还可以生成随机字符串，常用于随机生成ID值
         */
        $.fn.app.uuid = (function () {
            var CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");
            /**
             * @param len 结果长度
             * @param radix 随机数基数
             */
            return function (len, radix) {
                var chars = CHARS, slot = [], D = Math.random;
                radix = radix || chars.length;
                if (len) {
                    for (var i = 0; i < len; i++) slot[i] = chars[0 | D() * radix]
                } else {
                    var A = 0, E;
                    slot[8] = slot[13] = slot[18] = slot[23] = "-";
                    slot[14] = "4";
                    for (i = 0; i < 36; i++) if (!slot[i]) {
                        E = 0 | D() * 16;
                        slot[i] = chars[(i == 19) ? (E & 3) | 8 : E & 15]
                    }
                }
                return slot.join("");
            }
        })();
        /**
         * 应用公共接口方法定义
         * @type {{init: Function, addTab: Function, addInnerTab}}
         * 新增一个内嵌Tab
         */
        $.fn.app.methods = {
            /**
             *
             * @param jq
             * @param param
             */
            init: function (jq, param) {
                if (inited) return;
                var opts = $.extend({}, $.fn.app.defaults, param);
                var panels = ['appPanel', 'navigationPanel', 'menuPanel', 'tabPanel'];
                $.each(panels, function (index, value) {
                    jq[value] = $(opts[value]);
                });
                inited = true;
            },
            /**
             * 添加选项卡
             * @param jq 构造函数中传入，是本页的全局对象window
             * @param param
             */
            addTab: function (jq, param) {
                var tabControl = jq.tabPanel;
                if (tabControl) {
                    var baseId = param.id,
                    	iconCls = param.iconCls,
                        id = 'tabpanel_{0}'.format(baseId),
                        title = param.title,
                        closable = param.closable,
                        url = param.url,
                        wrapperId = 'frame_{0}'.format(baseId),
                        wrapperTemplate = '<iframe id="{0}" name="{1}" frameborder="0" src="{2}" style="width:100%;height:100%;"></iframe>',
                        content = wrapperTemplate.format(wrapperId, wrapperId, url);
                    var wrapper = $('#' + wrapperId);
                    if (wrapper.length == 0) {
                        tabControl.tabs('add', {
                        	iconCls: iconCls,
                            id: id,
                            title: title,
                            content: content,
                            closable: closable,
                            tools: [
                                {
                                    iconCls: 'icon-mini-refresh',
                                    handler: function () {
                                        $('#' + wrapperId).attr('src', url);
                                    }
                                }
                            ]
                        });
                        // 隐藏滚动条
                        $('#' + id).css("overflow", 'hidden');
                    } else {
                        jq.tabPanel.tabs('select', title);
                    }
                }
            },
            /**
             * 添加内嵌选项卡
             * @param jq 构造函数中传入，是本页的全局对象window
             * @param param
             */
            addInnerTab: function (jq, param) {
                var tabControl = jq.tabPanel;
                if (tabControl) {
                    var baseId = param.id,
                        iconCls = param.iconCls,
                        id = 'tabpanel_{0}'.format(baseId),
                        title = param.title,
                        closable = param.closable,
                        url = param.url,
                        wrapperId = 'frame_{0}'.format(baseId),
                        wrapperTemplate = '<iframe id="{0}" name="{1}" frameborder="0" src="{2}" style="width:100%;height:100%;"></iframe>',
                        content = wrapperTemplate.format(wrapperId, wrapperId, url);
                    var wrapper = $('#' + wrapperId);
                    if (wrapper.length == 0) {
                        tabControl.tabs('add', {
                            iconCls: iconCls,
                            id: id,
                            title: title,
                            content: content,
                            closable: closable,
                            tools: [
                                {
                                    iconCls: 'icon-mini-refresh',
                                    handler: function () {
                                        $('#' + wrapperId).attr('src', url);
                                    }
                                }
                            ]
                        });
                        // 隐藏滚动条
                        $('#' + id).css("overflow", 'hidden');
                    } else {
                        jq.tabPanel.tabs('select', title);
                    }
                }
            },
            /**
             * 获得页面所在的frame id, 以便在各个面页间传递与回调应用
             * @param jq
             * @returns {*}
             */
            selfId: function (jq) {
                return jq.tabPanel.tabs('getSelected').find('iframe').attr('id');
            }
        };
        /**
         * 默认配置
         * @type {{appPanel: string, navigationPanel: string, menuPanel: string, tabPanel: string}}
         */
        $.fn.app.defaults = {
            appPanel: 'body',
            navigationPanel: '#navigationContainer',
            menuPanel: '#menuContainer',
            tabPanel: '#tabContainer'
        }
    } else {
        // 构造器, 建立框架(frame or iframe)间通信
        $.fn.app = top.$.fn.app;
    }
})(jQuery);

/**
 * 业务模块处理
 */
(function ($) {
    // 私有方法
    var inited = false;
    // 初始化控件
    var initControl = {
        'easyui-layout': function (jq, ctrls) {
            //console.log(ctrls);
        },
        /**
         * 初始化所有grid的toolbar事件, 默认绑定CRUD，导出导入事件
         * @param jq $.fn.page
         * @param ctrls 页面上所有datagrid组件
         */
        'easyui-datagrid': function (jq, ctrls) {
            // 表格数据重载
            var reload = function (ctrl) {
                ctrl.datagrid('reload');
            }
            /**
             * 默认事件
             * @type {{create: Function, edit: Function, remove: Function, export: Function, import: Function}}
             */
            var actions = {
                /**
                 * 新建事件处理器 create handler
                 * @param e
                 */
                'create': function (e) {
                    var opts = e.data, ctrl = opts.ctrl, id = 'create_{0}'.format(ctrl.attr('id')), title = opts.title
                        , url = opts.url, searchForm = ctrl.attr('related');
                    if (searchForm && url.indexOf('{') != -1) {
                        var data = $(searchForm).form('serializer'), model = {};
                        $.each(data, function (k, v) {
                            var pair = k.split('_');
                            pair.length == 2 ? model[pair[1]] = v : model[k] = v;
                        });
                        url = url.format(model);
                    }
                    $.fn.app('addTab', {
                        id: id,
                        title: title,
                        url: url,
                        closable: true
                    });
                },
                /**
                 * 编辑事件处理器 edit handler
                 * @param e 事件 event
                 */
                'edit': function (e) {
                    var opts = e.data, ctrl = opts.ctrl, id = 'edit_{0}'.format(ctrl.attr('id')), title = opts.title
                        , url = opts.url, rows = ctrl.datagrid('getSelections');
                    switch (rows.length) {
                        case 0:
                            $.fn.app.alert('请选择记录进行编辑!', 'warning');
                            break;
                        case 1:
                            url = url.format(rows[0]);
                            $.fn.app('addTab', {
                                id: id,
                                title: title,
                                url: url,
                                closable: true
                            });
                            break;
                        default :
                            $.fn.app.alert('只能选择一条记录进行编辑!', 'warning');
                            break;
                    }
                },
                /**
                 *  删除事件处理器 remove handler
                 * @param e 事件 event
                 */
                'remove': function (e) {
                    var opts = e.data, ctrl = opts.ctrl, message = opts.message || '请确认是否需要进行删除操作！';
                    var url = opts.url, rows = ctrl.datagrid('getSelections');
                    if (rows.length == 0) {
                        $.fn.app.alert('请至少选择一条需要删除的记录!', 'warning');
                    } else {
                        $.fn.app.confirm(message, (function (c, r, u) {
                            return function () {
                                var fieldName = c.attr('idField'), params = $.map(r, function (v, i) {
                                    return {name: 'ids', value: v[fieldName]};
                                });
                                $.ajax({type: 'post', url: u, data: params, dataType: "json", success: function (data) {

                                }, error: function () {

                                }});
                            }
                        })(ctrl, rows, url));
                    }
                },
                'export': function (e) {
                    var opts = e.data, title = opts.title;
                    $.fn.app.alert('暂未实现!', 'warning');
                },
                'import': function (e) {
                    var opts = e.data, title = opts.title;
                    $.fn.app.alert('暂未实现!', 'warning');
                }
            };
            // 扫描页面上所有easyui控件
            $.each(ctrls, function (ic, ctrlEl) {
                var ctrl = $(ctrlEl), id = ctrl.attr('id'), tid = ctrl.attr('toolbar');
                if (id) {
                    if (tid) {
                        // 获得toolbar上所有的按钮
                        var btns = $('{0} > a[action]'.format(tid));
                        $.each(btns, function (ib, btnEl) {
                            var btn = $(btnEl), action = btn.attr('action'), handler = actions[action]
                                , url = btn.attr('url'), title = btn.attr('title'), message = btn.attr('message');
                            if (handler) {
                                btn.on('click', {ctrl: ctrl, url: url, title: title, message: message}, handler);
                            }
                        });
                    }
                } else {
                    $.fn.app.show('系统会为DataGrid自动绑定CURD及导出、导入按钮事件，但要求必须为DataGrid设置id属性!');
                }
            });
        },
        'easyui-tree': function (jq, ctrls) {
            // console.log(ctrls);
        },
        'easyui-form': function (jq, ctrls) {
            // console.log(ctrls);
        }
    };

    // 构造器, 业务模块处理, 根据页面按钮自动绑定CRUD，import, export，图表事件处理, 以满足大部分需求，
    // 其余事件请在模块业务上自行扩展
    $.fn.page = function (options, params) {
        $.fn.page.methods['init'](this, params);
        if (typeof options === 'string') {
            var method = $.fn.app.methods[options];
            if (method) {
                method(this, params);
            }
        }
    }
    // 公共方法
    $.fn.page.methods = {
        init: function (jq) {
            if (inited) return;
            var types = $.fn.page.defaults.controls;
            $.each(types, function (k, v) {
                var ctrls = $('.{0}'.format(v));
                if (initControl[v] && ctrls.length > 0) initControl[v](jq, ctrls);
            });
            inited = true;
            window['selfId'] = $.fn.app('selfId');
        }
    }
    // 默认配置
    $.fn.page.defaults = {
        // 定义页面上可能出现的控件, 逐步完善
        controls: ['easyui-layout', 'easyui-datagrid', 'easyui-tree', 'easyui-form']
    }
})(jQuery);

// 页面加载完成后，自动进行初始化
$(document).ready(function () {
    //top === self ? $.fn.app() : $.fn.page();
});