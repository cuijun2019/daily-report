/**
 * @fileOverview  
 * <pre>
 * 码流图设置组件
 * 2013/6/17
 * <a href="www.huawei.com">http://www.huawei.com</a>
 * Huawei Technologies Co., Ltd. Copyright 1998-2013,  All rights reserved 
 * </pre>
 * @version 1.0
 * @author chuyouguo/00175712
 */



/**
 * @description 获取整个页面中z-index最大值
 * @return {Number} 返回当前最大z-index值
 */
function getMaxZIndex() {
    var divs = $("div"),
            length = divs.length,
            max = 0;
    for (var i = 0; i < length; i++) {
        max = Math.max(max, divs[i].style.zIndex || 0);
    }

    return max + 10;
}
    
/**
 * @description 码流图设置组件
 * @class
 * @param {Object} options 配置数据，格式为
 * {
 *  id: ,                   // 组件ID
 *  data: data,             // 数据
 *  width: 600,             // 窗口宽度
 *  height: 450,            // 窗口高度
 *  listeners: {
 *      "ok": Function,     // 确定按钮监听
 *      "cancel": Function  // 取消按钮监听
 *  }
 */
function StreamSet(options) {
    if (!options.id) {
        return;
    }
    var me = this,
            headerText = ne_order_setup_win_title,
            okText = ok,
            cancelText = cancel,
            //helpText = ne_order_setup_win_help_tips,
            //closeText = close,
            streamSetClass = "sweet-cmp-streamset",
            treeListClass = "sweet-cmp-streamset-list",
            treeIconClass = "tree-icon",
            liClass = "sweet-cmp-streamset-li",
            plusClass = "plus",
            minusClass = "minus",
            checkboxClass = "checkbox",
            choosedClass = "choosed",
            unchoosedClass = "unchoosed",
            selectNoneClass = "sweet-select-none",
            maskClass = "sweet-mask",
            activeClass = "active",
            normalClass = "normal",
            invalidClass = "invalid",
            sameClass = "same",
            marginLeft = 22,
            defaultOptions = {
                width: 600,
                height: 450,
                data: []
            };
    // 配置参数
    me.options = $.extend(true, {}, defaultOptions, options);
    /**
     * @private
     * @description 码流图组件初始化
     */
    function init() {
        // 保存所有li对象
        me.liEls = {};
        createWindow();
        createHeader();
        createStreamSetTabs();
        createBottom();
        me.maskEl.appendTo("body");
        me.renderEl.draggable({
            handle: "#" + me.headerId,
            containment: "document",
            scroll: false,
            start: function() {
                refreshOffset();
            }
        }).appendTo("body");
        $(document.body).css("overflow", "hidden");
        me.streamStTabs.render(me.tabDivID);
        doLayout();
        refreshOffset();
        
        // 使用Ext的tip提示
        /*
        if (Ext) {
            new Ext.ToolTip({
                target: me.helpId,
                html: helpText
            });
            
            new Ext.ToolTip({
                target: me.closeId,
                html: closeText
            });
        }*/
    }

    /**
     * @description 创建码流设置弹出窗口
     */
    function createWindow() {
        var options = me.options,
        zIndex = getMaxZIndex();
        me.id = me.options.id;
        me.palettes = me.options.palettes;
        me.scenes = me.options.scenes;
        me.maskEl =  $("<div>").addClass(maskClass)
            .attr("onselectstart", "javascript:return false;")
            .css("z-index", zIndex);
        me.renderEl = $("<div>").addClass(streamSetClass + " " + selectNoneClass)
                .attr("onselectstart", "javascript:return false;")
                .width(options.width)
                .height(options.height)
                .css("z-index", zIndex + 1);
    }

    /**
     * @private
     * @description 创建标题
     */
    function createHeader() {
        var listeners = me.options.listeners;
        var headerClass = "sweet-cmp-streamset-header",
                id = me.headerId = me.options.id + "-title",
                helpId = me.helpId = id + "-help",
                closeId = me.closeId = id + "-close",
                headerEl = me.headerEl = $("<div>").addClass(headerClass),
                textEl = $("<em>"),
                helpEl = $("<span>").addClass("help").attr("id", helpId),
                closeEl = $("<span>").addClass("close").attr("id", closeId);
        closeEl.bind("click", function(){ if (listeners && $.isFunction(listeners.cancel)) { listeners.cancel.call(); } close(); }).appendTo(headerEl);
        // helpEl.appendTo(headerEl);
        textEl.text(headerText).appendTo(headerEl);
        headerEl.attr("id", id).appendTo(me.renderEl);
    }

    /**
     * @private
     * @description 创建内容
     */
    function createContent() {
       createSortFilterTabContent();
       Ext.getCmp(me.options.id + "-paramSetting").body.update(createSettingTabContent());
       createColorSettingContent();
    }
    
    /**
     * @private
     * @description 创建排序过滤内容
     */
    function createSortFilterTabContent(){
         var contentClass = "sweet-cmp-streamset-content",
                treeClass = "sweet-cmp-streamset-tree",
                contentEl = me.contentEl = $("<div>").addClass(contentClass),
                treeEl = me.treeEl = $("<div>").addClass(treeClass),
                ulEl = me.ulEl = $("<ul>").bind("click", _onClick)
                    .attr("depth", "-1")
                    .bind("mousedown", _onMouseDown),
                sortFilterData = me.options.data["sortFilterData"] || [];
        createTree(sortFilterData, ulEl, 0);
        ulEl.appendTo(treeEl);
        treeEl.appendTo(contentEl);
        contentEl.appendTo($("#"+me.options.id+"-sortFilterTab-panel"));
    }
    
    /**
     * @private
     * @description 创建参数设置内容
     */
    function createSettingTabContent(){
        //var height = me.renderEl.outerHeight(true),
        //        headerHeight = me.headerEl.outerHeight(true),
        //        contentHeight,
        //contentHeight = height - headerHeight - 83;
        var documentEl = $(document),
                height = me.renderEl.outerHeight(true),
                headerHeight = me.headerEl.outerHeight(true),
                bottomHeight = me.bottomEl.outerHeight(true),

        timeDisplayFormat = me.options.data.paramSettingData["timeDisplayFormat"],
        spcDisplayFormat = me.options.data.paramSettingData["spcDisplayFormat"],
                contentHeight,
                contentEl = me.contentEl,
                renderEl = me.renderEl;
        contentHeight = height - headerHeight - bottomHeight - 45;
		// add by zKF67609 begin
	    var showIpSpc = me.options.data.paramSettingData["showIpSpc"];
		// add by zKF67609 end
        var tHtml = "<div class='sweet-cmp-streamset-content-paramsetting-panel' style='overflow:auto;height:" + contentHeight + "px'>" +
                        "<div class='sweet-cmp-streamset-content-paramsetting-time'>" + 
                            "<div class='sweet-cmp-streamset-content-paramsetting-label' style='float:left;'><label>" + time_format + "</label></div>" +
                            "<div id='"+ me.options.id +"-paramsetting-time-radio' class='sweet-cmp-streamset-content-paramsetting-time-radio' style='float:left;'>" +
                                "<div><input style='vertical-align:bottom;width:15px' id='"+me.options.id+"-time-radio' type='radio' name='timeType' " + (("time" == timeDisplayFormat)? "checked='checked'":"") + " value='time'/><label for='"+me.options.id+"-time-radio'>" + eg_date_time_format + "</label></div>" +
                                "<div><input style='vertical-align:bottom;width:15px' id='"+me.options.id+"-secondsTime-radio' type='radio' name='timeType' " + (("secondsTime" == timeDisplayFormat)? "checked='checked'":"") + " value='secondsTime'/><label for='"+me.options.id+"-secondsTime-radio'>" + eg_seconds_time_format + "</label></div>" +
                                "<div><input style='vertical-align:bottom;width:15px' id='"+me.options.id+"-relativeTime-radio' type='radio' name='timeType' " + (("relativeTime" == timeDisplayFormat)? "checked='checked'":"") + " value='relativeTime'/><label for='"+me.options.id+"-relativeTime-radio'>" + eg_relative_time_format + "</label></div>" +
                            "</div>" +
                        "</div>" +
                        
                        "<div class='sweet-cmp-streamset-content-paramsetting-decimal'>" + 
                            "<div class='sweet-cmp-streamset-content-paramsetting-label' style='float:left;'><label>" + spc_format + " </label></div>" +
                            "<div id='"+ me.options.id +"-paramsetting-decimal-radio' class='sweet-cmp-streamset-content-paramsetting-decimal-radio' style='float:left;'>" +
                                "<div><input style='vertical-align:bottom;width:15px' id='"+me.options.id+"-dec-radio' type='radio' name='decimalType' " + (("dec" == spcDisplayFormat)? "checked='checked'":"") + " value='dec'/><label for='"+me.options.id+"-dec-radio'>" + eg_spc_dec_format + "</label></div>" +
                                "<div><input style='vertical-align:bottom;width:15px' id='"+me.options.id+"-dashed-radio' type='radio' name='decimalType' " + (("dashed" == spcDisplayFormat)? "checked='checked'":"") + " value='dashed'/><label for='"+me.options.id+"-dashed-radio'>" + eg_spc_dashed_format + "</label></div>" +
                                "<div><input style='vertical-align:bottom;width:15px' id='"+me.options.id+"-hex-radio' type='radio' name='decimalType' " + (("hex" == spcDisplayFormat)? "checked='checked'":"") + " value='hex'/><label for='"+me.options.id+"-hex-radio'>" + eg_spc_hex_format + "</label></div>" +
                            "</div>" +
                        "</div>" +
						// add by zKF67609 begin
						"<div class='sweet-cmp-streamset-content-paramsetting-display'>" +
							"<div class='sweet-cmp-streamset-content-paramsetting-label' style='float:left;'><label>" + show_format + " </label></div>" +
							"<div id='" + me.options.id + "-paramsetting-display-checkbox' class='sweet-cmp-streamset-content-paramsetting-display-checkbox' style='float:left;'>" +
								"<div><input style='vertical-align:bottom;width:15px' id='" + me.options.id + "-display-checkbox' type='checkbox' name='showIp' " + ((true == showIpSpc)? "checked='checked'":"")+ "/><label for='" + me.options.id + "-display-checkbox'>" + label_show_ip_spc + "</label></div>" +
							"</div>" +
						"</div>" +
						// add by zKF67609 end
                    "</div>";
        return tHtml;
    }
    
    /**
     * @private
     * @description 创建颜色设置内容
     */
    function createColorSettingContent()
    {
        var height = me.renderEl.outerHeight(true);
        var headerHeight = me.headerEl.outerHeight(true);
        var bottomHeight = me.bottomEl.outerHeight(true);
        var panel = $('#' + me.options.id + '-colorSetting').addClass('sweet-cmp-streamset-colorsettingcontent');
        panel.css('height', height - headerHeight - bottomHeight - 45 + "px");
        
        var protocolCol = $('<div>').css('text-indent', '5px').addClass('colheader').text(label_protocol_column).appendTo(panel).css('display', 'block').css('border-left','1px solid #AAAAAA');
        var protocolColWidth = me.renderEl.outerWidth(true) - 24;
        var lastCol = null;
        if (me.palettes[me.scenes['normal']])
        {
            protocolColWidth -= 180;
            lastCol = $('<div>').css('width', 179).css('text-indent', '5px').addClass('colheader').text(label_normal_color_column).appendTo(panel);
        }
        if (me.palettes[me.scenes['error']])
        {
            protocolColWidth -= 180;
            lastCol = $('<div>').css('width', 179).css('text-indent', '5px').addClass('colheader').text(label_error_color_column).appendTo(panel);
        }
        lastCol.css('width', 179 + 16);
        protocolCol.css('width', protocolColWidth - 16);
        
        //主体
        var body = $('<div>').css('clear','both').css('height', panel.outerHeight(true) - 82).css('overflow-y', 'scroll').css('border','1px solid #AAAAAA').appendTo(panel);
        var colorConfigTable = $('<table>').attr('border', '0').attr('width','100%').attr('bordercolor','#AAAAAA').appendTo(body);
        for (var key in me.options.data.colorSettingData)
        {
            var configTableLine = $('<tr>').css('border-bottom', '1px solid #CCCCCC');
            configTableLine.appendTo(colorConfigTable);
            
            var protocolNameCell = $('<td>').text(key);
            protocolNameCell.appendTo(configTableLine);

            var colors = me.options.data.colorSettingData[key];
            
            //正常颜色
            if (me.palettes[me.scenes['normal']])
            {
                var normalColorCell = $('<td>').css('width', '150').attr('colorkey', 'color').appendTo(configTableLine);
                var normalColorBox = $('<span>').addClass('sweet-cmp-streamset-colorlabel').css('backgroundColor', colors.color).attr('value', colors.color).appendTo(normalColorCell);
                normalColorBox.addClass('sweet-cmp-radius-left');
                $('<span>').attr('scene', 'normal').bind('click', onColorSelBtnClick).addClass('sweet-cmp-streamset-btn').addClass('sweet-cmp-radius-right').text('···').attr('title', label_choosecolor_tip).appendTo(normalColorCell);
                var optionBtnGroup = $('<div>').addClass('sweet-cmp-streamset-optionbtngroup').appendTo(normalColorCell);
                $('<span>').attr('title', label_color_reset_tip).attr('colorkey', 'defcolor').bind('click', setDefaultColor).addClass('sweet-cmp-streamset-btn').addClass('sweet-cmp-streamset-btn-reset').appendTo(optionBtnGroup);
                $('<span>').attr('title', label_color_toall_tip).attr('colorKey', 'color').bind('click', setAllToSameColor).addClass('sweet-cmp-streamset-btn').addClass('sweet-cmp-streamset-btn-toall').appendTo(optionBtnGroup);
            }
            
            //错误颜色
            if (me.palettes[me.scenes['error']])
            {
                var errorColorCell = $('<td>').css('width', '150').attr('colorkey', 'errcolor').appendTo(configTableLine);
                var errorColorBox = $('<span>').addClass('sweet-cmp-streamset-colorlabel').css('backgroundColor', colors.errcolor).attr('value', colors.errcolor).appendTo(errorColorCell);
                errorColorBox.addClass('sweet-cmp-radius-left');
                $('<span>').attr('scene', 'error').bind('click', onColorSelBtnClick).addClass('sweet-cmp-streamset-btn').addClass('sweet-cmp-radius-right').text('···').attr('title', label_choosecolor_tip).appendTo(errorColorCell);
                optionBtnGroup = $('<div>').addClass('sweet-cmp-streamset-optionbtngroup').appendTo(errorColorCell);
                $('<span>').attr('title', label_color_reset_tip).attr('colorkey', 'deferrcolor').bind('click', setDefaultColor).addClass('sweet-cmp-streamset-btn').addClass('sweet-cmp-streamset-btn-reset').appendTo(optionBtnGroup);
                $('<span>').attr('title', label_color_toall_tip).attr('colorKey', 'errcolor').bind('click', setAllToSameColor).addClass('sweet-cmp-streamset-btn').addClass('sweet-cmp-streamset-btn-toall').appendTo(optionBtnGroup);
            }
        }
        
        //重置所有按钮
        var resetAllBtn = $('<button>').text(label_color_resetall).attr('title', label_color_resetall_tip).bind('click' ,setAllToDefaultColor).addClass('sweet-cmp-streamset-btn').addClass('sweet-cmp-streamset-btn-resetall').appendTo(panel);
    }
    
    function setAllToSameColor()
    {
        var self = this;
        var msg = $(this).attr('colorkey') == 'color' ? label_color_toall__normal_question : label_color_toall_error_question;
        var protocol = $(this.parentNode.parentNode.parentNode.firstChild).text();
        msg = msg.replace("XXX", protocol);
        
        ShowMsgBox({
            owner : me.renderEl, 
            text : msg,
            fn : function(btn) {
                if (btn != 'ok')
                {
                    return;
                }
                
                var color = $(self.parentNode.parentNode.firstChild).attr('value');
                var colorSettingPanel = $('#' + me.id + '-colorSetting');
                var rows = colorSettingPanel.find('tr');
                for (var index = 0; index < rows.length; index++)
                {
                    var cell = null;
                    if ($(self).attr('colorkey') == 'errcolor')
                    {
                        cell = rows[index].firstChild.nextSibling.nextSibling;
                    }
                    else
                    {
                        cell = rows[index].firstChild.nextSibling;
                    }
                    $(cell.firstChild).attr('value', color);
                    $(cell.firstChild).css('background-color', color);
                }
            }
        });
    }
    
    function setDefaultColor()
    {
        var self = this;
        var key = $(this).attr('colorkey');
        var msg = key == 'defcolor' ? label_color_reset_normal_question : label_color_reset_error_question;
        var protocol = $(this.parentNode.parentNode.parentNode.firstChild).text();
        msg = msg.replace("XXX", protocol);
        
        ShowMsgBox({
            owner : me.renderEl, 
            text : msg,
            fn : function(btn) {
                if (btn != 'ok')
                {
                    return;
                }
                var protocol = self.parentNode.parentNode.parentNode.firstChild.innerHTML;
                var color = me.options.data.colorSettingData[protocol][key];
                $(self.parentNode.parentNode.firstChild).css('background-color', color);
                $(self.parentNode.parentNode.firstChild).attr('value', color);
            }
        });
    }
    
    function setAllToDefaultColor()
    {
        ShowMsgBox({
            owner : me.renderEl, 
            text : label_color_resetall_question,
            fn : function(btn) {
                if (btn != 'ok')
                {
                    return;
                }
                var colorSettingPanel = $('#' + me.id + '-colorSetting');
                var rows = colorSettingPanel.find('tr');
                for (var index = 0; index < rows.length; index++)
                {
                    var cell = rows[index].firstChild;
                    var colors = me.options.data.colorSettingData[cell.innerHTML];
                    
                    while (cell = cell.nextSibling)
                    {
                        cell.firstChild.style.backgroundColor = colors['def' + $(cell).attr('colorkey')];
                        $(cell.firstChild).attr('value', colors['def' + $(cell).attr('colorkey')]);
                    }
                }
            }
        });
    }
    
    /**
     * @private
     * @description 当颜色选择按钮触发时显示色盘面板
     */
    function onColorSelBtnClick() {
        var scene = me.scenes[$(this).attr('scene')];
        var palette = me.palettes[scene];
        
        var blurFunc = function(){this.parentNode.parentNode.removeChild(this.parentNode)};
        var colorSelPanel = $('<div>').addClass('sweet-cmp-streamset-colorselpanel');
        
        var colorSelContainer = $('<div>').attr('tabindex', 0).attr('hidefocus', true).css('max-height','130px').css('overflow','auto').appendTo(colorSelPanel);
        var maxPaletteWidth = 0;
        for (var row = 0; row < palette.length; row++)
        {
            if (maxPaletteWidth < palette[row].length * 36)
            {
                maxPaletteWidth = palette[row].length * 36;
            }
            
            for (var col = 0; col < palette[row].length; col++)
            {
                var color = palette[row][col];
                var colorbox = $('<div>').addClass('sweet-cmp-streamset-colorbox').appendTo(colorSelContainer);
                if (col == 0)
                {
                    colorbox.css('clear','both');
                }
                $('<div>').attr('style', 'cursor:pointer;width:100%;height:100%;background:' + color).attr('value', color).appendTo(colorbox);
                colorbox.bind('click', onColorBoxSelect);
                colorbox.bind('mouseover', function(){colorSelContainer.unbind('blur', blurFunc);});
                colorbox.bind('mouseout', function(){colorSelContainer.bind('blur', blurFunc);});
            }
        }
        
        colorSelPanel.css('width', maxPaletteWidth + (palette.length > 5 ? 20 : 0) + "px");
        colorSelPanel.css('height', palette.length * 26 + "px");
        colorSelPanel.css('top', $(this).position().top + $(this).outerHeight() + 'px');
        colorSelPanel.appendTo($(this).parent());
        
        var containerHeight = $('#' + me.id + '-colorSetting').outerHeight(true);
        if (colorSelPanel.position().top + colorSelPanel.outerHeight() > containerHeight)
        {
            colorSelPanel.css('top', $(this).position().top - colorSelPanel.outerHeight() + 'px');
        }
        var containerWidth = $('#' + me.id + '-colorSetting').outerWidth(true);
        if (colorSelPanel.position().left + colorSelPanel.outerWidth() > containerWidth) 
        {
            colorSelPanel.css('left', containerWidth - colorSelPanel.outerWidth() - 10 + 'px');
        }
        colorSelContainer.bind('blur', blurFunc);
        colorSelContainer.focus();
    }
    
    /**
     * @private
     * @description 色盘面板中颜色选中
     */
    function onColorBoxSelect()
    {
        var color = $(this.firstChild).attr('value');
        var panel = this.parentNode.parentNode;
        panel.parentNode.firstChild.style.backgroundColor = color;
        $(panel.parentNode.firstChild).attr('value', color);
        panel.parentNode.removeChild(panel);
    }   
    
    /**
     * @private
     * @description 创建排序过滤和参数设置tab
     */
    function createStreamSetTabs() {
        me.tabDivID = me.options.id + "-tabPanel"
        me.paramSettingData = me.options.data["paramSettingData"];
        $("<div>").attr("id", me.tabDivID).addClass("sweet-cmp-streamset-tab").appendTo(me.renderEl);
        me.streamStTabs = new Ext.TabPanel({
            activeTab: 0,
            deferredRender : false,
            items: [{
                title: stream_sortFilterTab_title,
                html: "<div id='" + me.options.id + "-sortFilterTab-panel'> </div>"
            },{
                title: stream_paramsSetting_title,
                id: me.options.id + "-paramSetting",
                html: ""
            },{
                title: label_color_tab,
                html: "<div id='" + me.options.id + "-colorSetting'></div>"
            }],
            listeners: {
                'afterrender': function(){
                    createContent();
                }
            }
        });
    }
    /**
     * @description 设置数据
     * @param {Array} data 数据
     */
    function setData(data) {
        if (!data) {
            return;
        }
        var sortFilterData = data.sortFilterData,
            pSettingData = me.paramSettingData = data.paramSettingData;
        me.options = $.extend(true, {}, defaultOptions, {"data": data});
        me.ulEl.empty();
        $.each(me.liEls, function(index, obj){
            obj.removeData("data");
        });
        me.liEls = {};
        createTree(sortFilterData, me.ulEl, 0);
        refreshOffset();
        _setParamSettingData(pSettingData);
        _setColorSettingData(data.colorSettingData);
    }
    
     /**
     * @description 设置参数设置数据
     * @param {Array} data 数据
     */
    function _setParamSettingData(data){
        if(!data){
            return;
        }
        var timeId = me.id +"-paramsetting-time-radio",
            decimalId = me.id +"-paramsetting-decimal-radio",
            $timeRadio = $("#" + timeId + " input"),
            $decimalRadio = $("#" + decimalId + " input");
        
        if($timeRadio && $timeRadio.length > 0){
             $.each($timeRadio, function(index, obj){
                if(obj.value == data["timeDisplayFormat"]){
                    obj.checked = true;
                }else{
                    obj.checked = false;
                }
            });
            
        }
        if($decimalRadio && $decimalRadio.length > 0){
            $.each($decimalRadio, function(index, obj){
                if(obj.value == data["spcDisplayFormat"]){
                    obj.checked = true;
                }else{
                    obj.checked = false;
                }
            });
        }
    }
    
    function _setColorSettingData(data) {
        var colorSettingPanel = $('#' + me.id + '-colorSetting');
        var rows = colorSettingPanel.find('tr');
        for (var index = 0; index < rows.length; index++)
        {
            var cell = rows[index].firstChild;
            var colors = data[cell.innerHTML];
            
            while (cell = cell.nextSibling)
            {
                cell.firstChild.style.backgroundColor = colors[$(cell).attr('colorkey')];
                $(cell.firstChild).attr('value', colors[$(cell).attr('colorkey')]);
            }
        }
    }
    
    /**
     * @description 处理点击事件
     * @param {Object} e 事件对象
     */
    function _onClick(e) {
        // 阻止事件冒泡
        e.stopImmediatePropagation();
        var self = $(e.target),
                data;
        // 树节点事件
        if (self.hasClass(treeIconClass)) {
            data = self.parent().parent().data("data");
            // 展开
            if (self.hasClass(minusClass)) {
                self.removeClass(minusClass).addClass(plusClass);
                data.children.hide();
            } else {
                self.removeClass(plusClass).addClass(minusClass);
                data.children.show();
            }
            refreshOffset();
        }
        // 复选框事件
        else if (self.hasClass(checkboxClass)) {
            data = self.parent().parent().data("data");
            // 选中
            if (self.hasClass(choosedClass)) {
                self.removeClass(choosedClass).addClass(unchoosedClass);
                data.data.checked = false;
            } else {
                self.removeClass(unchoosedClass).addClass(choosedClass);
                data.data.checked = true;
            }
        }
    }

    /**
     * @description 生成树
     * @param {Array} data 数据
     * @param {Object} rootEl 根节点对象
     * @param {Number} depth 深度
     */
    function createTree(data, rootEl, depth) {
        var tempData,
                tempRootEl,
                liEl,
                divEl,
                checkboxEl;
        for (var i = 0; i < data.length; i++) {
            tempData = data[i];
            liEl = $("<li>").addClass(liClass).appendTo(rootEl);
            divEl = $("<div>").addClass(treeListClass + " " + normalClass).css("margin-left", depth * marginLeft).appendTo(liEl);
            tempRootEl = $("<ul>").attr("depth", depth).appendTo(liEl);
            // 是否有子节点
            if (tempData.children && 0 < tempData.children.length) {
                // 增加折叠图标
                createTreePic().appendTo(divEl);
                createTree(tempData.children, tempRootEl, depth + 1);
            }
            // 增加文字描述
            $("<span>").text(tempData.text)
                .addClass("text").appendTo(divEl);
            // 增加复选框
            checkboxEl = $("<span>").addClass(checkboxClass).appendTo(divEl);
            if (0 < depth) {
                checkboxEl.hide();
            }
            if (tempData.checked) {
                checkboxEl.addClass(choosedClass);
            } else {
                checkboxEl.addClass(unchoosedClass);
            }

            liEl.data("data", {"data": tempData, "parent": rootEl, "children": tempRootEl});
            me.liEls[tempData.value] = liEl;
        }
    }

    /**
     * @description 增加折叠、展开图标
     */
    function createTreePic() {
        return $("<span>").addClass(treeIconClass + " " + minusClass);
    }
    
    /**
     * @description 计算所有li标签左上角、右下角坐标值
     */
    function refreshOffset() {
        var coordinates = {};
        $.each(me.liEls, function(index, obj) {
            var o = obj.children("div:first"),
                position = o.position(),
                width = o.width();
                height = o.height();
                x1 = position.top,
                x2 = position.top + height,
                y1 = position.left,
                y2 = position.left + width;
            coordinates[index] = {
                "x1": x1,
                "y1": y1,
                "x2": x2,
                "y2": y2,
                "obj": obj
            };
        });
        
        me.coordinates = coordinates;
    }
    
    /**
     * @private
     * @description 鼠标按下事件
     * @param {Object} e 事件对象
     */
    function _onMouseDown(e) {
        var target = $(e.target),
            zIndex = parseInt(me.renderEl.css("z-index"), 10);
        if (target.hasClass(treeListClass) ||
            target.hasClass("text")) {
            var self;
            if (target.hasClass("text")) {
                self = target.parent().parent();
            } else {
                self = target.parent();
            }
            me._helper = self;
            me._startY = e.pageY;
            me._startTop = self.position().top;
            me._scrollTop = me.treeEl.scrollTop();
            me._contentTop = me.contentEl.position().top;
            me._contentBottom = me._contentTop + me.contentEl.height();
            
            // 创建高亮目标元素
            if (!me.hightLightEl) {
                me.hightLightEl = $("<li>").addClass("sweet-cmp-streamset-highlight")
                    .height(me._helper.height())
                    .css({"z-index": ++zIndex})
                    .insertAfter(me._helper);
            }
            
            me._helper.width(me._helper.width())
                .css({"position": "absolute", "z-index": ++zIndex, "top": me._helper.position().top});
            _recursionSetDrag(self, "true");
            
            $(document.body).bind("mousemove", _onMouseMove)
                .bind("mouseup", _onMouseUp);
        }
    }
    
    /**
     * @description 递归设置拖动标识
     */
    function _recursionSetDrag(liEl, flag) {
        liEl.attr("drag", flag);
        var ulEl = liEl.children("ul:first"),
            liEls = ulEl.children("li");
        for (var i=0; i<liEls.length; i++) {
            _recursionSetDrag($(liEls[i]), flag);
        }
    }
    
    /**
     * @description 控制滚动条移动定时器
     */
    function scrollTimeout(value) {
        if (me.timeout) {
            return;
        }
        me.time = 0;
        me.timeout = setInterval(function() {
            me.time++;
            me.treeEl.scrollTop(me._scrollTop + me.time * value);
        }, 100);
    }
    
    /**
     * @private
     * @description 鼠标移动事件
     * @param {Object} e 事件对象
     */
    function _onMouseMove(e) {
        var top = me._startTop + e.pageY - me._startY,
            helpParentData,
            objParentData,
            helperDepth,
            objDepth,
            flag = false;
        // 只进行垂直拖动处理
        me._helper.css("top", top);
        
        // 顶端
        if (top < me._contentTop && me._contentTop - top < 30) {
            scrollTimeout(-10);
        } 
        // 末端
        else if (me._contentBottom < top && top - me._contentBottom < 10) {
            scrollTimeout(10);
        } else {
            clearInterval(me.timeout);
            me._contentTop = me.contentEl.position().top;
            me._contentBottom = me._contentTop + me.contentEl.height();
            me.timeout = null;
        }
        // 是否目标元素层级超过1
        me._oneDepth = false;
        // 是否有子节点
        me._hasChildren = false;
        // 是否同级移动
        me._sameLevel = false;

        // 是否移动到目标对象上
        $.each(me.coordinates, function(index, obj){
            // 不处理正在当前拖动的标签
            if ("true" === obj.obj.attr("drag") ||
                obj.obj.is(":hidden")) {
                return;
            }
            // added by c00175712 begin  DTS2013062501006
            if (top < me._contentTop) {
                me.hightLightEl.prependTo(me.ulEl);
                return;
            }
            if (top > me._contentBottom) {
                me.hightLightEl.appendTo(me.ulEl);
                return;
            }
            // added by c00175712 end  DTS2013062501006
            if (top > obj.x1 && top < obj.x2 &&
                top > me._contentTop && top < me._contentBottom) {
                // 不处理目标层级超过1的标签
                // if (0 < obj.obj.children("ul:first").children("li").length) {
                if (1 <= parseInt(obj.obj.children("ul:first").attr("depth"), 10)) {
                    me._oneDepth = true;
                }
                // 不处理有子节点的标签
                if (0 < me._helper.children("ul:first").children("li").length) {
                    me._hasChildren = true;
                }
                // 是否同级拖动
                helpParentData = me._helper.parent().data("data");
                objParentData = obj.obj.parent().data("data");
                helperDepth = parseInt(me._helper.parent().attr("depth"), 10);
                objDepth = parseInt(obj.obj.parent().attr("depth"), 10);
                if ((-1 === helperDepth && -1 === objDepth) ||
                    (0 === helperDepth && 0 === objDepth)) {
                    me._sameLevel = true;
                }
                // 是否拖动目标不符合条件
                if (me._oneDepth || me._hasChildren) {
                    // 判断是否同一个
                    if (me.sameEl && me.sameEl.x1 !== obj.x1) {
                        _removeSameClass();
                    }
                    if (me.invalidEl && me.invalidEl.x1 !== obj.x1) {
                        _removeInvalidClass();
                    }
                    
                    if (me._sameLevel) {
                        me.sameEl = obj;
                        _addSameClass();
                        me.hightLightEl.insertAfter(obj.obj);
                    } else {
                        me.invalidEl = obj;
                        _addInvalidClass();
                    }
                } else {
                    // 判断是否同一个
                    if (me.activeEl && me.activeEl.x1 !== obj.x1) {
                        _removeActiveClass();
                    }
                    me.activeEl = obj;
                    _addActiveClass();
                    me.hightLightEl.insertAfter(obj.obj);
                }
                refreshOffset();
                flag = true;
            }
        });
        
        // 判断是否移除
        if (!flag) {
            _removeActiveClass();
            _removeInvalidClass();
        }
    }
    
    /**
     * @private
     * @description 鼠标松开事件
     * @param {Object} e 事件对象
     */
    function _onMouseUp(e) {
        $(document.body).unbind("mousemove", _onMouseMove)
            .unbind("mouseup", _onMouseUp);
        
        // 如果鼠标未移动，不做处理
        if (me._startY === e.pageY) {
            _recover();
            return;
        }
        
        clearInterval(me.timeout);
        me.timeout = null;
        _appendTo();
        _recover();
    }
    
    /**
     * @description 恢复
     */
    function _recover() {
        _removeHighLight();
        _removeActiveClass();
        _removeInvalidClass();
        _removeSameClass();
        _recursionSetDrag(me._helper, "false");
        me._helper.css({"position": "static", "top": me._startTop, "z-index": "auto"})
            .width("auto");
        refreshOffset();
    }
    
    /**
     * @description 删除高亮新增标签
     */
    function _removeHighLight() {
        if (me.hightLightEl) {
            me.hightLightEl.remove();
            me.hightLightEl = null;
        }
    }
    
    /**
     * @description 追加到激活标签后
     */
    function _appendTo() {
        var helperData = me._helper.data("data"),
            parent,
            currentDepth,
            ulEl;
        if (!helperData) {
            return;
        }
        parent = helperData.parent;
        if (me.activeEl) {
            var obj = me.activeEl.obj,
                objData = obj.data("data"),
                rootEl = objData.children,
                depth = parseInt(rootEl.attr("depth"), 10);
            currentDepth = depth + 1;
            me._helper.appendTo(rootEl);
            
            // 新增图标
            if (0 === rootEl.parent().children("div:first").find("." + treeIconClass).length) {
                createTreePic().appendTo(rootEl.parent().children("div:first"));
            }
            
            helperData.parent = rootEl;
            // console.log(00);
        } else // if (me.invalidEl) 
        {
            // 是否拖动到树以外
            var contentTop = me.contentEl.position().top,
                contentWidth = me.contentEl.height(),
                ulTop = me.ulEl.position().top,
                width = me.ulEl.height(),
                helperTop = me._helper.position().top;
            // modified by c00175712  DTS2013062501006
            if (helperTop < ulTop || helperTop < contentTop) {
                currentDepth = 0;
                helperData.parent = me.ulEl;
                me._helper.prependTo(me.ulEl);
            } 
            else if (helperTop > ulTop + width ||
                helperTop > contentTop + contentWidth) {
                currentDepth = 0;
                helperData.parent = me.ulEl;
                me._helper.appendTo(me.ulEl);
            } else {
                currentDepth = parseInt(me.hightLightEl.parent().attr("depth"), 10) + 1;
                helperData.parent = me.hightLightEl.parent();
                me._helper.insertAfter(me.hightLightEl);
            }
            // console.log(11);
        } /*else {
            currentDepth = 0;
            helperData = me._helper.data("data");
            helperData.parent = me.ulEl;
            me._helper.appendTo(me.ulEl);
            me._helper.children("div:first").css("margin-left", 0);
            console.log(22);
        }*/
        
        me._helper.children("div:first").css("margin-left", currentDepth * marginLeft);
        ulEl = me._helper.children("ul:first").attr("depth", currentDepth);
        // 隐藏复选框
        if (1 === currentDepth) {
            me._helper.children("div:first").find("." + checkboxClass).hide();
        }
        // 显示复选框
        else {
            me._helper.children("div:first").find("." + checkboxClass).show();
        }
        // 子节点递归缩进
        _recursion(ulEl);
        // 删除原图标
        if (parent && 0 === parent.children("." + liClass).length) {
            parent.parent().find("." + treeIconClass).remove();
        }
    }
    
    /**
     * @description 递归处理子节点缩进
     */
    function _recursion(ulEl) {
        if (0 === ulEl.children("li").length) {
            return;
        }
        var liEls = ulEl.children("li"),
            depth = parseInt(ulEl.attr("depth"), 10) + 1,
            tempUlEl,
            liEl;
        for (var i=0; i<liEls.length; i++) {
            liEl = $(liEls[i]);
            liEl.children("div:first").css("margin-left", depth * marginLeft);
            tempUlEl = liEl.children("ul:first").attr("depth", depth);
            _recursion(tempUlEl);
        }
    }
    
    /**
     * @private
     * @description 添加同级拖动样式
     */
    function _addSameClass() {
        if (me.sameEl) {
            me.sameEl.obj.children("div:first").removeClass(normalClass).addClass(sameClass);
        }
    }
    
    /**
     * @private
     * @description 删除同级拖动样式
     */
    function _removeSameClass() {
        if (me.sameEl) {
            me.sameEl.obj.children("div:first").removeClass(sameClass).addClass(normalClass);
            me.sameEl = null;
        }
    }
    
    /**
     * @private
     * @description 添加禁用样式
     */
    function _addInvalidClass() {
        if (me.invalidEl) {
            me.invalidEl.obj.children("div:first").removeClass(normalClass).addClass(invalidClass);
        }
    }
    
    /**
     * @private
     * @description 删除禁用样式
     */
    function _removeInvalidClass() {
        if (me.invalidEl) {
            me.invalidEl.obj.children("div:first").removeClass(invalidClass).addClass(normalClass);
            me.invalidEl = null;
        }
    }
    
    /**
     * @private
     * @description 添加活动样式
     */
    function _addActiveClass() {
        if (me.activeEl) {
            me.activeEl.obj.children("div:first").removeClass(normalClass).addClass(activeClass);
        }
    }
    
    /**
     * @private
     * @description 删除活动样式
     */
    function _removeActiveClass() {
        if (me.activeEl) {
            me.activeEl.obj.children("div:first").removeClass(activeClass).addClass(normalClass);
            me.activeEl = null;
        }
    }
    
    /**
     * @private
     * @description 创建按钮
     */
    function createBottom() {
        var listeners = me.options.listeners,
                bottomClass = "sweet-cmp-streamset-bottom",
                buttonClass = "button-margin",
                bottomEl = me.bottomEl = $("<div>").addClass(bottomClass),
                okBtnEl = $("<button>").addClass('sweet-cmp-streamset-btn'),
                cancelBtnEl = $("<button>").addClass(buttonClass).addClass('sweet-cmp-streamset-btn');
        okBtnEl.text(okText)
            .attr("onselectstart", "javascript:return false;")
            .bind("click", function() {
            var result = true;
            if (listeners && $.isFunction(listeners.ok)) {
                result = listeners.ok.call(null, getData());
            }
            if (result || undefined === result) {
                close();
            }
        }).appendTo(bottomEl);
        cancelBtnEl.text(cancelText)
            .attr("onselectstart", "javascript:return false;")
            .bind("click", function() {
            if (listeners && $.isFunction(listeners.cancel)) {
                listeners.cancel.call();
            }
            close();
        }).appendTo(bottomEl);
        bottomEl.appendTo(me.renderEl);
    }

    /**
     * @description 显示窗口
     */
    function show() {
        var zIndex = getMaxZIndex();
        $(document.body).css("overflow", "hidden");
        me.maskEl.show().css("z-index", zIndex);
        me.renderEl.show().css("z-index", zIndex + 1);
        refreshOffset();
    }

    /**
     * @description 关闭窗口
     */
    function close() {
        $(document.body).css("overflow", "auto");
        me.maskEl.hide();
        me.renderEl.hide();
    }

    /**
     * @description 组件内部高度分配
     */
    function doLayout() {
        var documentEl = $(document),
                height = me.renderEl.outerHeight(true),
                headerHeight = me.headerEl.outerHeight(true),
                bottomHeight = me.bottomEl.outerHeight(true),
                contentHeight,
                contentEl = me.contentEl,
                renderEl = me.renderEl;
        contentHeight = height - headerHeight - bottomHeight - 25;
        contentEl.height(contentHeight - 10);
        me.treeEl.height(contentHeight - 20);
        
        renderEl.css({"top": Math.floor((documentEl.height() - renderEl.height()) / 2),
                "left": Math.floor((documentEl.width() - renderEl.width()) / 2)});
    }

    /**
     * @description 获取数据
     */
    function getData() {
		var result = {};
        var sortFilterResult = [];
        _getData(me.ulEl, sortFilterResult);
		result.sortFilterData = sortFilterResult;
		
		if(!me.paramSettingData){
			me.paramSettingData = {};
		}
		_getParamSettingData();
		result.paramSettingData = me.paramSettingData;
		
		_getColorSettingData();
		result.colorSettingData = me.colorSettingData;
		
        return result;
    }
	
	/**
     * @description 获取参数设置数据
     */
	function _getParamSettingData(){
		var timeId= me.id +"-paramsetting-time-radio",
			decimalId= me.id +"-paramsetting-decimal-radio",
			$timeRadio = $("#" + timeId + " input:checked"),
			$decimalRadio = $("#" + decimalId + " input:checked");
		
		// add by zKF67609
		var displayCheckbox = me.id + "-display-checkbox";
		var $displayCheckbox = $("#"+displayCheckbox);
		
		if($timeRadio && $timeRadio.length > 0){
			me.paramSettingData["timeDisplayFormat"] = $timeRadio[0].value;
		}
		if($decimalRadio && $decimalRadio.length > 0){
			me.paramSettingData["spcDisplayFormat"] = $decimalRadio[0].value;
		}
		
		// add by zKF67609
		if ($displayCheckbox && $displayCheckbox.length > 0)
		{
			me.paramSettingData["showIpSpc"] = $displayCheckbox.attr("checked") == "checked"? true:false;
		}
	}
	
	function _getColorSettingData()
	{
	    me.colorSettingData = {};
	    
        var colorSettingPanel = $('#' + me.id + '-colorSetting');
        var rows = colorSettingPanel.find('tr');
        for (var index = 0; index < rows.length; index++)
        {
            var cell = rows[index].firstChild;
            var key = cell.innerHTML;
            
            me.colorSettingData[key] = {};
            while (cell = cell.nextSibling)
            {
                me.colorSettingData[key][$(cell).attr('colorkey')] = $(cell.firstChild).attr('value');
            }
        }
	}
	
    /**
     * @description 返回数据
     * @param {Object} rootEl 根节点
     * @param {Array} data 数据对象
     */
    function _getData(rootEl, data) {
        var arr = rootEl.children("li"),
                tempData,
                tempArr;
        for (var i=0; i<arr.length; i++) {
            tempArr = $(arr[i]);
            tempData = tempArr.data("data");
            if (tempData.children) {
                tempData.data.children = [];
                _getData(tempData.children, tempData.data.children);
            }
            data.push(tempData.data);
        }
    } 

    init();
    return {
        show: show,
        getData: getData,
        setData: function(data) {
            setData(data);
        }
    };
}

function ShowMsgBox(options)
{
    var mask = $('<div id="umaskmask">').css('z-index', getMaxZIndex() + 10).addClass("sweet-mask");
    mask.appendTo(options.owner);
    
    var msgbox = $('<div>').addClass("sweet-cmp-streamset").width(320).height(140).css('z-index', getMaxZIndex() + 10);
    msgbox.offset({top:(options.owner.height() - 140) >> 1, left:(options.owner.width() - 320) >> 1});
    msgbox.appendTo(options.owner);
        
    function close(){
        if ($(this).attr('action'))
        {
            options.fn($(this).attr('action'));
        }
        mask.remove();
        msgbox.remove();
    }
    
    var msgboxtitle = $('<div>').addClass('sweet-cmp-streamset-header').html("<em>" + label_warning + "</em>");
    msgboxtitle.appendTo(msgbox);
    $('<span>').addClass('close').bind('click', close).appendTo(msgboxtitle);
        
    var contentPanel = $('<div>').height(80).appendTo(msgbox);
    $('<div>').addClass("sweet-msgbox-icon").appendTo(contentPanel);
    $('<div>').addClass("sweet-msgbox-text").appendTo(contentPanel).text(options.text);
    
    var buttonPanel = $('<div>').addClass("sweet-cmp-streamset-bottom").appendTo(msgbox);
    $('<button>').attr('action', 'ok').text(ok).bind('click', close).appendTo(buttonPanel);
    $('<button>').attr('action', 'cancel').text(cancel).bind('click', close).addClass("button-margin").appendTo(buttonPanel);
    
    msgbox.draggable({
        handle : msgboxtitle,
        containment : "document",
        scroll: false
    })
}