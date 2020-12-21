var _Tip8 = "";
var _Tip9 = "";
var _Tip10 = "";
var _SpcNames = "";
var _metaData = "";
var _options = "";
var flowChart = null;
var _summary = null;

var SequenceDiagram = function () {
    this.constructor.apply(this, arguments);
};
SequenceDiagram.prototype = {
    /**
     *  config :
     *  {
     *      id : html标签id,
     *      data : [{}],
     *      mappings : {srcNode, dstNode, name, time}
     *  }
     */
    constructor: function (config) {
        this.data = [];
        this.nodes = [];
        this.render = null;
        this.colorCfg = {};
        this.nullCell = document.createElement("div");
        this.nodeCell = document.createElement("div");
        this.timeCell = document.createElement("div");
        this.bodyCell = document.createElement("div");
        this.__init(config);
        this.renderer(config.render);
        this.startIndex = 0;
    },

    __init: function (config) {
        if (!config.data) {
            return;
        }

        if (!config.mappings) {
            config.mappings = {srcNode: '源地址(S)', dstNode: '目的地址(S)', name: '消息名(S)', time: '时间(S)'};
        }

        var hasUfdrData = false;

        this.data = [];
        this.nodes = [];
        var set = {};
        for (var index = 0; index < config.data.length; index++) {
            config.data[index].index = index;
            this.data.push({
                name: config.data[index][config.mappings.name],
                time: config.data[index][config.mappings.time],
                srcNode: config.data[index][config.mappings.srcNode],
                dstNode: config.data[index][config.mappings.dstNode],
                origData: config.data[index]
            });

            if (!set[config.data[index][config.mappings.srcNode]] && config.data[index].direction != 2) {
                set[config.data[index][config.mappings.srcNode]] = true;
                this.nodes.push({'text': config.data[index][config.mappings.srcNode], value: config.data[index][config.mappings.srcNode], children: null, checked: true});
            }

            if (!set[config.data[index][config.mappings.dstNode]] && config.data[index].direction != 2) {
                set[config.data[index][config.mappings.dstNode]] = true;
                this.nodes.push({'text': config.data[index][config.mappings.dstNode], value: config.data[index][config.mappings.dstNode], children: null, checked: true});
            }

            this.colorCfg[config.data[index].protocolname] = {
                'color': config.data[index].color,
                'defcolor': config.data[index].color,
                'errcolor': config.data[index].errorcolor,
                'deferrcolor': config.data[index].errorcolor
            };
            if (config.data[index].direction) {
                hasUfdrData = true;
            }
        }

        if (hasUfdrData) {
            this.nodes.unshift({'text': 'UE/MS', value: 'UE/MS', children: null, checked: true});
            this.nodes.push({'text': 'SERVER', value: 'SERVER', children: null, checked: true});
        }

        this.nodesMappings = {};
        this.nodesTipsMappingsHex = {};
        this.nodesTipsMappingsDec = {};
        this.nodesTipsMappingsDashed = {};
        //this.nodesList = {};
        this.__initNodesMappings(null, this.nodes);
        this.options = {timeDisplayFormat: "time", spcDisplayFormat: 'dec'};
        this.visibleRowCount = this.__getVisibleRowCount();
    },

    __initNodesMappings: function (ref, nodes) {
        for (var index = 0; index < nodes.length; index++) {
            //this.nodesList[nodes[index].text] = nodes[index];
            var localRef = ref;
            if (localRef == null) {
                localRef = {text: this.__findAllNodesText(nodes[index]), value: nodes[index].text};
                this.nodesTipsMappingsHex[localRef.text] = this.__findAllNodesTips(nodes[index], "spcNamesHex");
                this.nodesTipsMappingsDec[localRef.text] = this.__findAllNodesTips(nodes[index], "spcNamesDec");
                this.nodesTipsMappingsDashed[localRef.text] = this.__findAllNodesTips(nodes[index], "spcNamesDashed");
            }
            this.nodesMappings[nodes[index].text] = localRef;
            if (nodes[index].children) {
                this.__initNodesMappings(localRef, nodes[index].children);
            }
        }
    },

    __findAllNodesText: function (node) {
        var text = node.text;
        var children = node.children;
        if (null != children && children.length > 0) {
            for (var index = 0; index < children.length; index++) {
                text += "/" + this.__findAllNodesText(children[index]);
            }
        }
        return text;
    },

    __findAllNodesTips: function (node, spcNamesKey) {
        var tips = [];
        tips = tips.concat(_SpcNames[spcNamesKey][0][node.text]);
        var children = node.children;
        if (null != children && children.length > 0) {
            for (var index = 0; index < children.length; index++) {
                tips = tips.concat(this.__findAllNodesTips(children[index], spcNamesKey));
            }
        }
        return tips;
    },

    getNodes: function () {
        return this.nodes;
    },
    setNodes: function (nodes) {
        this.nodes = nodes;
        this.nodesMappings = {};
        this.nodesTipsMappingsHex = {};
        this.nodesTipsMappingsDec = {};
        this.nodesTipsMappingsDashed = {};
        //this.nodesList = {};
        this.__initNodesMappings(null, this.nodes);
        this.visibleRowCount = this.__getVisibleRowCount();
    },

    getOptions: function () {
        return this.options;
    },

    setOptions: function (options) {
        Ext.apply(this.options, options);
    },

    getColorCfg: function () {
        return this.colorCfg;
    },

    setColorCfg: function (colors) {
        for (var p in colors) {
            for (var c in colors[p]) {
                this.colorCfg[p][c] = colors[p][c];
            }
        }
    },

    refresh: function () {
        //this.startIndex = 0;
        this.__resize();
    },
    scrollTo: function (index, self) {
        var visibleRowIndex = this.visibleRowMap[index];
        if (typeof visibleRowIndex == 'undefined') {
            return;
        }
        if (visibleRowIndex + this.pageSize > this.visibleRowCount) {
            visibleRowIndex = this.visibleRowCount - this.pageSize;
        }
        if (visibleRowIndex < 0) {
            visibleRowIndex = 0;
        }
        this.startIndex = visibleRowIndex;

        var oldValue = this.slider.getValue();
        this.slider.setValue(this.slider.maxValue - visibleRowIndex);
        if (oldValue == this.slider.maxValue - visibleRowIndex) {
            this.__update();
            this.__scroll();
        }

        if (!self) {
            //if (this.target)
            //{
            //    this.target.style.background = '';
            //}
            //
            //this.target = document.getElementById('msg_a' + index);
            //if (this.target)
            //{
            //    this.target.style.background = "#AAEEFF";
            //    this.bodyCell.scrollLeft = parseInt(this.target.parentNode.style.left);
            //}
            var target = document.getElementById('msg_a' + index);
            this.bodyCell.scrollLeft = parseInt(target.parentNode.style.left);
            this.__hlNode(index);
        }
    },

    renderer: function (render) {
        var me = this;
        if (!render) {
            return;
        }
        this.render = typeof render == 'object' ? render : document.getElementById(render);
        this.render.style.position = "relative";
        this.render.onresize = function () {
            me.__resize.apply(me, arguments);
        };
        if (window.addEventListener)
            window.addEventListener("resize", function () {
                me.__resize.apply(me, arguments);
            });
        this.render.appendChild(this.nullCell);
        this.render.appendChild(this.nodeCell);
        this.render.appendChild(this.timeCell);
        this.render.appendChild(this.bodyCell);
        this.bodyCell.onscroll = function () {
            me.__scroll.apply(me, arguments);
        };
        this.bodyCell.onmousewheel = function () {
            me.__onMouseWheel.apply(me, arguments);
        };
        if (this.bodyCell.addEventListener) {
            this.bodyCell.addEventListener("scroll", function () {
                me.__scroll.apply(me, arguments);
            });
            this.bodyCell.addEventListener("DOMMouseScroll", function () {
                me.__onMouseWheel.apply(me, arguments);
            });
        }

        this.__resize();
    },
    __onMouseWheel: function (e) {
        var scrollToValue = 0;
        if (typeof event != 'undefined') {
            e = event;
            inc = e.wheelDelta / 120;
            scrollToValue = this.startIndex - inc;
        }
        else {
            scrollToValue = this.startIndex + e.detail / 3;
        }

        if (scrollToValue < 0) {
            scrollToValue = 0;
        }

        if (this.visibleRow[scrollToValue]) {
            this.scrollTo(this.visibleRow[scrollToValue].origData.index, this);
        }
    },
    __onNameDblclick: function (index) {
        return;
        var msg = this.data[index].origData;
        var url = msg.url; //window.location.toString().replace(/[^\/\\]+$/, msg.url);
        getDetailText(msg.name, url);
    },

    __onNameClick: function (index) {
        //document.getElementById('anchor' + index).scrollIntoView();
        index = parseInt(index);
        //if (this.target)
        //{
        //    this.target.style.background = "";
        //}
        //this.target = document.getElementById('msg_a' + index);
        //this.target.style.background = "#AAEEFF";

        this.__hlNode(index);
//        try {
//            document.getElementById('anchor' + index).scrollIntoView();
//        } catch (e) {
//        }
//        var msg = this.data[index].origData;
//
//        var rootNode = Ext.getCmp('detailpanel').getRootNode();
//        rootNode.loader.dataUrl = msg.url;
//        rootNode.loader.pagingModel = 'remote';

//        var loader = Ext.getCmp('detailpanel').getLoader();
//        var rootNode = loader.getRootNode();
//        rootNode.reload();
//        rootNode.loader.pagingModel = 'local';


//        $.ajax({type: "post", url: msg.url, data: {orgDecode: true}, dataType: "text",
//            success: function (data) {
//                Ext.getCmp('signalPanel').body.update(data);
//            },
//            error: function () {
//                $.messager.show("警告", "失败");
//            }
//        });
    },

    __hlNode: function (index) {
        if (this.target && this.target.parentNode) {
            this.target.style.background = '';
            this.target.style.color = this.target.oldcolor;
            this.target.parentNode.nextSibling.style.height = '3px';
            this.target.parentNode.nextSibling.style.top = (parseInt(this.target.parentNode.nextSibling.style.top) + 2) + "px";
        }
        this.target = document.getElementById('msg_a' + index);
        if (this.target) {
            this.target.style.background = "#2A90FF";
            this.target.oldcolor = this.target.style.color;
            this.target.style.color = "#FFFFFF";
            this.target.parentNode.nextSibling.style.height = '7px';
            this.target.parentNode.nextSibling.style.top = (parseInt(this.target.parentNode.nextSibling.style.top) - 2) + "px";
        }
    },

    __scroll: function () {
        var scrollTop = this.bodyCell.scrollTop;
        var scrollLeft = this.bodyCell.scrollLeft;
        if (this.nodeCell.firstChild) {
            this.nodeCell.firstChild.style.left = -scrollLeft + "px";
        }
        if (this.timeCell.firstChild) {
            this.timeCell.firstChild.style.top = -scrollTop + "px";
        }
    },

    __resize: function () {
        var me = this;

        var nodeHeight = 30;
        if (this.options.showIpSpc) {
            nodeHeight = 100;
            for (var index = 0; index < this.nodes.length; index++) {
                if (!this.nodes[index].checked || !this.nodes[index].hasMsg) {
                    continue;
                }

                var content = onShowIpSpc(me, this.nodesMappings[this.nodes[index].text].text);
                var extendHeight = (content.split("<br>").length - 1) * 25;
                if (extendHeight > nodeHeight) {
                    nodeHeight = extendHeight;
                }
            }
        }

        var timeWidth = 0;
        switch (this.options.timeDisplayFormat) {
            case "secondsTime":
            case "relativeTime":
                timeWidth = 100;
                break;
            default:
                timeWidth = 190;
                break;
        }
        with (this.nullCell.style) {
            position = "absolute";
            overflow = "hidden";
            width = timeWidth + "px";
            height = nodeHeight + "px";
            background = "#EEEEEE";
        }

        with (this.nodeCell.style) {
            position = "absolute";
            overflow = "hidden";
            left = timeWidth + "px";
            width = (this.render.clientWidth < timeWidth ? 0 : this.render.clientWidth - timeWidth) + "px";
            height = nodeHeight + "px";
            background = "#EEEEEE";
            color = '#369cda';
        }

        with (this.timeCell.style) {
            position = "absolute";
            overflow = "hidden";
            textOverflow = "ellipsis";
            whiteSpace = "nowrap";
            top = nodeHeight + "px";
            width = timeWidth + "px";
            height = (this.render.clientHeight < nodeHeight ? 0 : this.render.clientHeight - nodeHeight) + "px";
            background = "#EEEEEE";
            color = '#369cda';
        }

        with (this.bodyCell.style) {
            overflowX = "scroll";
            overflowY = "hidden";
            position = "absolute";
            whiteSpace = "nowrap";
            left = timeWidth + "px";
            top = nodeHeight + "px";
            width = (this.render.clientWidth < timeWidth ? 0 : this.render.clientWidth - timeWidth) + "px";
            height = (this.render.clientHeight < nodeHeight ? 0 : this.render.clientHeight - nodeHeight) + "px";
            background = "#EEEEEE";
        }

        if (!this.slider) {
            this.startIndex = 0;
            this.slider = Ext.getCmp('flowchartslider');
            this.slider.on('change', function (slider, index) {
                me.startIndex = this.maxValue - index;
                me.__update();
                me.__scroll();
            });
        }
        this.pageSize = Math.floor((this.render.clientHeight - (nodeHeight + 20)) / 35);
        if (this.visibleRowCount - this.pageSize > 0) {
            this.slider.setMaxValue(this.visibleRowCount - this.pageSize);
            this.scrollTo(this.visibleRow[this.startIndex].origData.index, this);
            this.slider.enable();
        }
        else {
            var oldValue = this.slider.getValue();
            this.slider.setValue(this.slider.maxValue);
            this.slider.syncThumb();
            this.slider.disable();
            if (oldValue == this.slider.maxValue) {
                this.__update();
                this.__scroll();
            }
        }
    },
    __getVisibleRowCount: function () {
        var tempNodesMappings = {};
        for (var index = 0; index < this.nodes.length; index++) {
            this.nodes[index].hasMsg = false;
            tempNodesMappings[this.nodes[index].text] = this.nodes[index];
        }
        var visibleRowCount = 0;
        this.visibleRow = [];
        this.visibleRowMap = {};
        for (var index = 0; index < this.data.length; index++) {
            var row = this.data[index];
            var srcNode = this.nodesMappings[row.srcNode].value;
            var dstNode = this.nodesMappings[row.dstNode].value;
            if (!tempNodesMappings[srcNode].checked
                || !tempNodesMappings[dstNode].checked
                || srcNode == dstNode) {
                continue;
            }
            tempNodesMappings[srcNode].hasMsg = true;
            tempNodesMappings[dstNode].hasMsg = true;
            visibleRowCount++;
            this.visibleRowMap[index] = this.visibleRow.length;
            this.visibleRow.push(row);
        }
        return visibleRowCount;
    },
    __update: function () {
        var me = this;

        var spcNodeLineHeight = this.visibleRowCount * 35;
        if (spcNodeLineHeight > this.bodyCell.clientHeight)
            spcNodeLineHeight = this.bodyCell.clientHeight;

        //列结点
        var tempBody = [];
        var tempNodes = {};
        var tempTime = [];
        var nodeOffset = 0;
        var nodeWidth = 200;
        var bodyWidth = 0;
        var temp = [];
        temp.push("<div style='position:relative'>");
        for (var index = 0; index < this.nodes.length; index++) {
            tempNodes[this.nodes[index].text] = this.nodes[index];
            if (!this.nodes[index].checked || !this.nodes[index].hasMsg) {
                continue;
            }

            var width = nodeWidth;
            if (nodeOffset == 0) {
                var tmp = document.createElement("div");
                tmp.innerHTML = this.nodesMappings[this.nodes[index].text].text;
                tmp.style.position = "absolute";
                tmp.style.top = "-100px";
                document.body.appendChild(tmp);
                width = tmp.clientWidth + 20;
                document.body.removeChild(tmp);
                if (width > nodeWidth)
                    width = nodeWidth;
                else if (width < 100)
                    width = 100;
            }

            //结点名
            temp.push("<div style='position:absolute;left:" + nodeOffset + "px;width:" + width + "px;text-align:center;line-height:30px;height:30px;cursor:default;white-space:nowrap;text-overflow:ellipsis;overflow:hidden' type='nodeName'>");
            temp.push("<span>" + this.nodesMappings[this.nodes[index].text].text + "</span>");
            temp.push("</div>");

            if (me.options.showIpSpc) {
                var content = onShowIpSpc(me, this.nodesMappings[this.nodes[index].text].text);
                temp.push("<div style='position:absolute;left:" + nodeOffset + "px;top:25px;width:" + width + "px;text-align:center;line-height:20px;cursor:default;white-space:nowrap;text-overflow:ellipsis;overflow:hidden' type='nodeName'>");
                temp.push("<span>" + content + "</span>");
                temp.push("</div>");
            }

            //结点线
            tempBody.push("<div style='position:absolute;left:" + (nodeOffset + (width >> 1)) + "px;width:3px;height:" + spcNodeLineHeight + "px;background:#7b8dac'></div>");

            this.nodes[index].pos = (nodeOffset + (width >> 1));
            nodeOffset += width;
            if (width < nodeWidth)
                nodeOffset += (nodeWidth >> 1) - (width >> 1);
        }
        bodyWidth = nodeOffset;
        nodeOffset -= (nodeWidth >> 1);
        temp.push("</div>");

        //消息序列
        var rowOffset = 0;
        var drawMsgCount = 0;
        for (var index = this.startIndex; index < this.visibleRow.length; index++) {
            var row = this.visibleRow[index];
            var srcNode = this.nodesMappings[row.srcNode].value;
            var dstNode = this.nodesMappings[row.dstNode].value;
            if (!tempNodes[srcNode].checked
                || !tempNodes[dstNode].checked
                || srcNode == dstNode) {
                //网元隐藏的消息忽略
                continue;
            }

            var from = tempNodes[srcNode].pos;
            var to = tempNodes[dstNode].pos;

            //时间轴
            tempTime.push("<div style='text-align:right;height:35px;line-height:35px;padding-right:10px'>");
            switch (this.options.timeDisplayFormat) {
                case "secondsTime":
                    tempTime.push(row.origData.secondsTime);
                    break;
                case "relativeTime":
                    tempTime.push(row.origData.relativeTime);
                    break;
                default:
                    tempTime.push(row.time);
                    break;
            }
            tempTime.push("</div>");

            //消息名称和方向线
            var left = 0;
            var top = rowOffset + 20;
            var width = 0;
            var directPos = 0;
            var direct = 0;
            var color = '#000000';
            if (this.colorCfg[row.origData.protocolname]) {
                if (row.origData.isErrorColor) {
                    color = this.colorCfg[row.origData.protocolname].errcolor;
                }
                else {
                    color = this.colorCfg[row.origData.protocolname].color;
                }
            }

            if (from < to) {
                //向右
                left = from + 5;
                width = to - from - 7;
                directPos = left + width - 3;
                direct = -1;
                //color="darkblue";
            }
            else {
                //向左
                left = to + 5;
                width = from - to - 7;
                directPos = left;
                direct = 1;
                //color="green";
            }

            //消息名
            tempBody.push("<div style='margin:0px 50px 0px 50px;position:relative;text-overflow:ellipsis;overflow:hidden;left:" + (left + 8 - 50) + "px;width:" + (width - 16) + "px;top:3px;height:35px;line-height:18px;text-align:center' title='" + this.visibleRow[index].name + "'>");
            tempBody.push("<a href='javascript:void(0)' id='msg_a" + this.visibleRow[index].origData.index + "' style='color:" + color + ";text-decoration:none' index='" + this.visibleRow[index].origData.index + "'>" + this.visibleRow[index].name + "</a>");
            tempBody.push("</div>");

            //方向线
            tempBody.push("<div style='position:absolute;left:" + (left + 2) + "px;width:" + (width - 4) + "px;top:" + top + "px;height:3px;background:" + color + "'></div>");

            //指针
            for (var count = 0; count < 5; count++) {
                tempBody.push("<div style='position:absolute;left:" + (directPos + count * direct) + "px;top:" + (top - count) + "px;width:3px;height:" + (count * 2 + 3) + "px;background:" + color + "'></div>");
                if (this.visibleRow[index].origData.protocolname == 'UFDR') {
                    tempBody.push("<div style='position:absolute;left:" + (left + count) + "px;top:" + (top - count) + "px;width:3px;height:" + (count * 2 + 3) + "px;background:" + color + "'></div>");
                    tempBody.push("<div style='position:absolute;left:" + ((left + width) - count) + "px;top:" + (top - count) + "px;width:3px;height:" + (count * 2 + 3) + "px;background:" + color + "'></div>");
                }
            }

            //虚线
            tempBody.push("<div style='position:absolute;left:0px;width:" + (nodeOffset) + "px;top:" + (rowOffset + 21) + "px;border-bottom:1px dotted gray'></div>");

            rowOffset += 35;
            drawMsgCount++;
            if (drawMsgCount == this.pageSize) {
                break;
            }
        }

        var timePanel = document.createElement("div");
        timePanel.style.position = "relative";
        timePanel.innerHTML = tempTime.join("");

        var bodyPanel = document.createElement("div");
        bodyPanel.style.position = "relative";
        bodyPanel.style.width = bodyWidth + "px";
        bodyPanel.innerHTML = tempBody.join("");

        this.nodeCell.innerHTML = temp.join("");

        this.bodyCell.innerHTML = "";
        this.bodyCell.appendChild(bodyPanel);

        this.timeCell.innerHTML = "";
        this.timeCell.appendChild(timePanel);

        //追加事件
        var clickItems = this.bodyCell.getElementsByTagName('a');

        for (var index = 0; index < clickItems.length; index++) {
            clickItems[index].ondblclick = function () {
                me.__onNameDblclick(this.attributes['index'].nodeValue);
            };
            clickItems[index].onclick = function () {
                me.__onNameClick(this.attributes['index'].nodeValue);
            };
            //clickItems[index].onblur = function(){ this.style.background = ""; };
        }

        var nodeItems = this.nodeCell.firstChild.getElementsByTagName("div");
        for (var index = 0; index < nodeItems.length; index++) {
            if (!me.options.showIpSpc) {
                nodeItems[index].firstChild.onmouseover = function () {
                    var content = _Tip8 + "<br>";
                    var ipTitle = _Tip9 + '<br>';
                    var neTitle = _Tip10 + '<br>';
                    var ipRegex = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/;
                    var numRegex = /^\d+$/;

                    var ipContent = "";
                    var spcContent = "";
                    var content = _Tip8 + "<br>";
                    var name = this.innerHTML;

                    var ipspcs = null;
                    switch (me.options.spcDisplayFormat) {
                        case "hex":
                            ipspcs = me.nodesTipsMappingsHex[name];
                            break;
                        case "dec":
                            ipspcs = me.nodesTipsMappingsDec[name];
                            break;
                        case "dashed":
                            ipspcs = me.nodesTipsMappingsDashed[name];
                            break;
                    }
                    for (var i = 0; i < ipspcs.length; i++) {
                        var tmp = ipspcs[i];
                        if (ipRegex.test(tmp)) {
                            // 是ip
                            ipContent += '&nbsp;&nbsp;&nbsp;' + tmp + '<br />';
                        }
                        else {
                            // spc
                            if (-1 == parseInt(tmp)) {
                                spcContent += "&nbsp;&nbsp;&nbsp;None<br />";
                            }
                            else {
                                //tmp = '0x' + parseInt(tmp).toString(16).toLocaleUpperCase();
                                spcContent += '&nbsp;&nbsp;&nbsp;' + tmp + '<br />';
                            }
                        }
                    }

                    if (!ipRegex.test(name) && !numRegex.test(name)) {
                        var nameList = name.split("/");
                        for (var nameIndex = 0; nameIndex < nameList.length; nameIndex++) {
                            content += '<div style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden">&nbsp;&nbsp;&nbsp;' + nameList[nameIndex] + '</div>';
                        }
                    }
                    content += '&nbsp;&nbsp;&nbsp;' + '<br>';
                    if (ipContent != "") {
                        content += ipTitle + ipContent + '<br>';
                    }
                    if (spcContent != "") {
                        content += neTitle + spcContent + '<br>';
                    }

                    var tmp = document.createElement("div");
                    tmp.innerHTML = content;
                    tmp.style.position = "absolute";
                    tmp.style.top = "-100px";
                    document.body.appendChild(tmp);
                    var tipsWidth = tmp.clientWidth + 20;
                    document.body.removeChild(tmp);
                    if (tipsWidth > 500)
                        tipsWidth = 500;

                    Ext.QuickTips.init();
                    Ext.QuickTips.register({
                        target: this,
                        text: content,
                        width: tipsWidth,
                        dismissDelay: 0
                    });
                };
            }
        }
    }
};

var streamSet = null;
function onSetup() {
    if (streamSet) {
        streamSet.show();
    }
    else {
        var initData = Ext.decode(Ext.encode({
            sortFilterData: flowChart.getNodes(),
            paramSettingData: flowChart.getOptions(),
            colorSettingData: flowChart.getColorCfg()
        }));
        streamSet = new StreamSet({
            id: "id",
            width: 600,
            height: 450,
            data: initData,
            palettes: palettes,
            scenes: scenes,
            listeners: {
                "ok": function (data) {
                    flowChart.setNodes(Ext.decode(Ext.encode(data.sortFilterData)));
                    flowChart.setOptions(Ext.decode(Ext.encode(data.paramSettingData)));
                    flowChart.setColorCfg(Ext.decode(Ext.encode(data.colorSettingData)));
                    flowChart.refresh();
                },
                'cancel': function () {
                    var resetData = Ext.decode(Ext.encode({
                        sortFilterData: flowChart.getNodes(),
                        paramSettingData: flowChart.getOptions(),
                        colorSettingData: flowChart.getColorCfg()
                    }));
                    streamSet.setData(resetData);
                }
            }
        });
    }
}
function pcapDownload() {
    var sids = [];
    var pids = [];
    var ds = [];
    for(var i=0;i<_summary.length;i++){
        sids.push(_summary[i].会话ID);
        pids.push(_summary[i].协议ID_H);
        var data = new Date(_summary[i].开始时间);
        var strDate = timeFormat(data,"yyyyMMdd");
        ds.push(strDate);
    }
//    $.ajax({
//        async: true,
//        type: 'post',
//        url: 'downloadPcapPre',
//        data: {sids: sids.join("_"), pids: pids.join("_"), ds: ds.join("_")},
//        success: function (result) {
//            if (result && result.code == "0") {
//                var fileName = result.result;
//                window.open('/ssmp/file/exportAndDelete?fileName=' + fileName);
//            } else {
//            }
//        },
//        error: function (result) {
//            $.messager.alert("警告", "导出PCAP失败");
//        }
//    });


}

function timeFormat(date,format) {
    var o = {
        "M+" : date.getMonth() + 1, //month
        "d+" : date.getDate(), //day
        "h+" : date.getHours(), //hour
        "m+" : date.getMinutes(), //minute
        "s+" : date.getSeconds(), //second
        "q+" : Math.floor((date.getMonth() + 3) / 3), //quarter
        "S" : date.getMilliseconds()
        //millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (date.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

function InitSummaryTable() {
    var tableContent = [];
    tableContent.push("<table id='summary_table' border='1' bordercolor='#AAAAAA'>");
    tableContent.push("<tr class='summary_title'><td colspan='2'>" + summary_info + "</td></tr>");
    for (var index = 0; index < _summary.length; index++) {
        tableContent.push("<tr class='summary_tr'>");
        tableContent.push("<th class='summary_th'>" + _summary[index].header + "</th>");
        tableContent.push("<td class='summary_td'>" + _summary[index].text + "</td>");
        tableContent.push("</tr>");
    }
    tableContent.push("</table>");
    return tableContent.join("");
}

function ShowDetailText(allText) {
    var detailArray = allText.split(new RegExp("Message Name : ", "g"));
    if (detailArray[0] == "") {
        detailArray.shift();
    }
    var detailContent = [];
    for (var index = 0; index < detailArray.length; index++) {
        var isAbis = true;
        var detail = ("Message Name : " + detailArray[index]).split(/\r?\n/g);
        var name = "";
        var time = "";
        var link = "";
        var source = "";
        var destination = "";
        var slc = "";
        try {
            var name = detail[0].match(/^Message Name : (.*)/)[1].trim();
            detail.shift();
            var time = detail[0].match(/^Time Stamp : (.*)/)[1].trim();
            detail.shift();
            link = detail[0].match(/^Link : (.*)/)[1].trim();
            detail.shift();
            source = detail[0].match(/ : (.*)/)[1].trim();
            detail.shift();
            destination = detail[0].match(/ : (.*)/)[1].trim();
            detail.shift();
            isAbis = false;
            slc = detail[0].match(/^    SLC : (.*)/)[1].trim();
            detail.shift();
        } catch (e) {
        }

        //锚点
        detailContent.push("<a id='anchor" + index + "' name='anchor" + index + "'></a>");

        //导航
        detailContent.push("<div style='font-weight:bold;border:1px solid gray;height:25px;line-height:25px;border-bottom:0px;width:200px;text-align:center'>");
        if (index == 0) {
            detailContent.push("<span style='color:gray;font-weight:bold'>" + label_first + "</span> | ");
            detailContent.push("<span style='color:gray;font-weight:bold'>" + label_previous + "</span> | ");
        }
        else {
            detailContent.push("<a href='javascript:document.getElementById(\"anchor0\").scrollIntoView();flowChart.scrollTo(0)'>" + label_first + "</a> | ");
            detailContent.push("<a href='javascript:document.getElementById(\"anchor" + (index - 1) + "\").scrollIntoView();flowChart.scrollTo(" + (index - 1) + ")'>" + label_previous + "</a> | ");
        }
        if (index == detailArray.length - 1) {
            detailContent.push("<span style='color:gray;font-weight:bold'>" + label_next + "</span> | ");
            detailContent.push("<span style='color:gray;font-weight:bold'>" + label_last + "</span>");
        }
        else {
            detailContent.push("<a href='javascript:document.getElementById(\"anchor" + (index + 1) + "\").scrollIntoView();flowChart.scrollTo(" + (index + 1) + ")'>" + label_next + "</a> | ");
            detailContent.push("<a href='javascript:document.getElementById(\"anchor" + (detailArray.length - 1) + "\").scrollIntoView();flowChart.scrollTo(" + (detailArray.length - 1) + ")'>" + label_last + "</a>");
        }
        detailContent.push("</div>");

        //正文
        detailContent.push("<table width='100%' border='1' bordercolor='gray' style='table-layout:fixed;margin-bottom:5px;line-height:20px;border-collapse:collapse;padding-bottom:20px;table-layout:fixed'>");
        detailContent.push("<tr><th style='padding:5px 10px;background:#EEEEEE' width='120'><b>" + label_message_name + "</th><td style='padding:5px 10px'>");
        detailContent.push("<a href='javascript:flowChart.scrollTo(" + index + ")'>");
        detailContent.push(name);
        detailContent.push("</a></td></tr>");
        detailContent.push("<tr><th style='padding:5px 10px;background:#EEEEEE'><b>" + label_timestamp + "</th><td style='padding:5px 10px'>" + time + "</td></tr>");

        if (!isAbis) {
            detailContent.push("<tr><th style='padding:5px 10px;background:#EEEEEE'><b>" + label_link + "</th><td style='padding:5px 10px'>" + link + "</td></tr>");
            if (/\d+\.\d+\.\d+\.\d+(\/\.*)?/.test(source)) {
                detailContent.push("<tr><th style='padding:5px 10px;background:#EEEEEE'><b>" + label_ipsrc + "</th><td style='padding:5px 10px'>" + source + "</td></tr>");
                detailContent.push("<tr><th style='padding:5px 10px;background:#EEEEEE'><b>" + label_ipdst + "</th><td style='padding:5px 10px'>" + destination + "</td></tr>");
            }
            else {
                detailContent.push("<tr><th style='padding:5px 10px;background:#EEEEEE'><b>" + label_linkopc + "</th><td style='padding:5px 10px'>" + source + "</td></tr>");
                detailContent.push("<tr><th style='padding:5px 10px;background:#EEEEEE'><b>" + label_linkdpc + "</th><td style='padding:5px 10px'>" + destination + "</td></tr>");
                if (slc != "") {
                    detailContent.push("<tr><th style='padding:5px 10px;background:#EEEEEE'><b>" + label_slc + "</th><td style='padding:5px 10px'>" + slc + "</td></tr>");
                }
            }
        }
        detailContent.push("<tr><td style='padding:5px 10px;background:#EEEEEE' valign='top'><b>" + label_decodetext + "</td>");
        detailContent.push("<td><div style='padding:5px 10px;overflow-x:auto;font-family:monospace;white-space:nowrap'>");
        detailContent.push(detail.join("\n").trim().replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/ /g, "&nbsp;").replace(/\n/g, "<br />"));
        detailContent.push("</div></td></tr>");
        detailContent.push("</table>");
    }
    Ext.getCmp('detailpanel').body.update(detailContent.join(""));
}
function InitDetailText() {
    if (isEncrypt) {
        return
    }
    var iframe = document.createElement("iframe");
    if (iframe.addEventListener) {
        iframe.addEventListener('load', function () {
            ShowDetailText(this.contentDocument.body.firstChild.innerHTML);
            document.body.removeChild(this);
        });
    }
    else {
        iframe.onreadystatechange = function () {
            if ('complete' == this.readyState) {
                ShowDetailText(this.contentDocument.body.firstChild.innerHTML);
                document.body.removeChild(this);
            }
        };
    }

    if (isUserplane) {
        iframe.src = 'resources/UserPlaneDetails.txt';
    }
    else {
        iframe.src = 'resources/CallflowChartDetails.txt';
    }

    document.body.appendChild(iframe);
}

function onShowIpSpc(me, name) {
    var content = "";
    var ipTitle = _Tip9 + "<br>";
    var neTitle = _Tip10 + "<br>";
    var ipRegex = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/;
    var numRegex = /^\d+$/;
    var ipContent = "";
    var spcContent = "";
    var ipspcs = null;
    switch (me.options.spcDisplayFormat) {
        case "hex":
            ipspcs = me.nodesTipsMappingsHex[name];
            break;
        case "dec":
            ipspcs = me.nodesTipsMappingsDec[name];
            break;
        case "dashed":
            ipspcs = me.nodesTipsMappingsDashed[name];
            break;
    }
    for (var i = 0; i < ipspcs.length; i++) {
        var tmp = ipspcs[i];
        if (ipRegex.test(tmp)) {
            // ip
            ipContent += '&nbsp;&nbsp;&nbsp;' + tmp + '<br>';
        }
        else {
            // spc
            if (-1 == parseInt(tmp)) {
                spcContent += "&nbsp;&nbsp;&nbsp;None<br>";
            }
            else {
                spcContent += '&nbsp;&nbsp;&nbsp;' + tmp + '<br>';
            }
        }
    }
    if (ipContent != "") {
        content += ipTitle + ipContent;
    }
    if (spcContent != "") {
        content += neTitle + spcContent;
    }

    return content;
}


/**
 *
 * 流程图呈现类
 *
 * @author  sKF47876
 * @version  [版本号, 2012-6-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
var Chart = function (title, data, canvas, spcNamesDec, spcNamesHex, spcNamesDashed, metaData, options, summary) {
    _Tip8 = neName;
    _Tip9 = ipAddress;
    _Tip10 = spc;
    _SpcNames = { spcNamesDec: spcNamesDec, spcNamesHex: spcNamesHex, spcNamesDashed: spcNamesDashed };
    _metaData = metaData;
    _options = options;
    _summary = summary;
    this.render = canvas;
    this.data = data;
    if (isUserplane) {
        document.title = userplanewintitle;
        this.initUserplaneViewport();
    }
    else {
        document.title = signplanewintitle;
        this.initSignallingViewport();
    }
};

Chart.prototype.paint = function () {
//    InitDetailText();
    if (!isUserplane) {
        this.flowchart = flowChart = new SequenceDiagram({data: this.data, mappings: {srcNode: "源地址(S)", dstNode: "目的地址(S)", name: '消息名(S)', time: '时间(S)'}});
        this.flowchart.renderer(this.render);
        this.flowchart.setNodes(_metaData);
        this.flowchart.setOptions(_options);
        this.flowchart.refresh();
    }
    else {
        flowChart = {scrollTo: function () {
        }};
    }
};

Chart.prototype.initUserplaneViewport = function () {
    var ladderTabItems = [
        {title: label_detail_resolve, html: '', id: 'detailpanel', autoScroll: true},
        {title: callsummary, autoScroll: true, html: InitSummaryTable() }
    ]

    var ladderTab = new Ext.TabPanel({
        region: "center",
        activeTab: 0,
        items: ladderTabItems
    });

    new Ext.Viewport({
        layout: 'border',
        items: [ladderTab]
    });
};

Chart.prototype.initSignallingViewport = function () {
    var ladderTabItems = [];
    ladderTabItems.push({
        xtype: 'panel', region: 'center', layout: 'border', border: false,
        items: [
            {
                region: 'north', height: 30, padding: '6px 6px 6px 6px', bodyStyle: "background:#EEEEEE", html: "<div><img style='float:right;cursor: pointer' id='setup' src='/ltemr/static/flowchart/images/exportinstal.png' title='" + ne_setup + "' onclick='onSetup()'/>" +
                "<img style='float:right;cursor: pointer;margin:0px 5px;width:18px;height:19px;' src='/ltemr/static/flowchart/images/down.png' title='PCAP下载' onclick='pcapDownload()'/></div>"
            },
            {
                region: 'center', layout: 'border',
                items: [
                    {
                        region: 'center', border: false, html: '<div id="flowchart" style="width:100%;height:100%"></div>'
                    },
                    {
                        region: 'east', xtype: 'slider', animate: false, id: 'flowchartslider', vertical: true, minValue: 0, maxValue: 1, value: 0
                    }
                ],
                listeners: {
                    resize: function () {
                        try {
                            flowChart.__resize();
                        } catch (e) {
                        }
                    }
                }
            }
        ]
    });
    if (!isEncrypt) {
        //解码树
        var testRoot = new Ext.tree.AsyncTreeNode({
            text: '查询结果列表',
            cls: "folder"
        });
        var treeData = [{"id":0,"text":"General information","leaf":false,"children":[{"id":0,"text":"Number","leaf":true,"children":null},{"id":0,"text":"Packet Length","leaf":true,"children":null},{"id":0,"text":"Captured Length","leaf":true,"children":null},{"id":0,"text":"Captured Time","leaf":true,"children":null}]},{"id":0,"text":"Frame 2166: 73 bytes on wire (584 bits), 73 bytes captured (584 bits)","leaf":false,"children":[{"id":0,"text":"Encapsulation type: Ethernet (1)","leaf":true,"children":null},{"id":0,"text":"Arrival Time: Feb  1, 2016 10:35:17.000627865 CST","leaf":true,"children":null},{"id":0,"text":"Time shift for this packet: 0.000000000 seconds","leaf":true,"children":null},{"id":0,"text":"Epoch Time: 1454294117.000627865 seconds","leaf":true,"children":null},{"id":0,"text":"Time delta from previous captured frame: 0.000000000 seconds","leaf":true,"children":null},{"id":0,"text":"Time delta from previous displayed frame: 0.000000000 seconds","leaf":true,"children":null},{"id":0,"text":"Time since reference or first frame: 0.000000000 seconds","leaf":true,"children":null},{"id":0,"text":"Frame Number: 2166","leaf":true,"children":null},{"id":0,"text":"Frame Length: 73 bytes (584 bits)","leaf":true,"children":null},{"id":0,"text":"Capture Length: 73 bytes (584 bits)","leaf":true,"children":null},{"id":0,"text":"Frame is marked: False","leaf":true,"children":null},{"id":0,"text":"Frame is ignored: False","leaf":true,"children":null},{"id":0,"text":"Protocols in frame: eth:ip:sctp:sgsap","leaf":true,"children":null}]},{"id":0,"text":"Ethernet II, Src: 10:51:72:0d:6c:ff (10:51:72:0d:6c:ff), Dst: 00:d0:d0:a1:1e:8c (00:d0:d0:a1:1e:8c)","leaf":false,"children":[{"id":0,"text":"Destination: 00:d0:d0:a1:1e:8c (00:d0:d0:a1:1e:8c)","leaf":false,"children":[{"id":0,"text":"Address: 00:d0:d0:a1:1e:8c (00:d0:d0:a1:1e:8c)","leaf":true,"children":null},{"id":0,"text":".... ..0. .... .... .... .... = LG bit: Globally unique address (factory default)","leaf":true,"children":null},{"id":0,"text":".... ...0 .... .... .... .... = IG bit: Individual address (unicast)","leaf":true,"children":null}]},{"id":0,"text":"Source: 10:51:72:0d:6c:ff (10:51:72:0d:6c:ff)","leaf":false,"children":[{"id":0,"text":"Address: 10:51:72:0d:6c:ff (10:51:72:0d:6c:ff)","leaf":true,"children":null},{"id":0,"text":".... ..0. .... .... .... .... = LG bit: Globally unique address (factory default)","leaf":true,"children":null},{"id":0,"text":".... ...0 .... .... .... .... = IG bit: Individual address (unicast)","leaf":true,"children":null}]},{"id":0,"text":"Type: IP (0x0800)","leaf":true,"children":null}]},{"id":0,"text":"Internet Protocol Version 4, Src: 10.126.136.14 (10.126.136.14), Dst: 10.125.155.4 (10.125.155.4)","leaf":false,"children":[{"id":0,"text":"Version: 4","leaf":true,"children":null},{"id":0,"text":"Header length: 20 bytes","leaf":true,"children":null},{"id":0,"text":"Differentiated Services Field: 0x28 (DSCP 0x0a: Assured Forwarding 11; ECN: 0x00: Not-ECT (Not ECN-Capable Transport))","leaf":false,"children":[{"id":0,"text":"0010 10.. = Differentiated Services Codepoint: Assured Forwarding 11 (0x0a)","leaf":true,"children":null},{"id":0,"text":".... ..00 = Explicit Congestion Notification: Not-ECT (Not ECN-Capable Transport) (0x00)","leaf":true,"children":null}]},{"id":0,"text":"Total Length: 164","leaf":true,"children":null},{"id":0,"text":"Identification: 0x39a8 (14760)","leaf":true,"children":null},{"id":0,"text":"Flags: 0x00","leaf":false,"children":[{"id":0,"text":"0... .... = Reserved bit: Not set","leaf":true,"children":null},{"id":0,"text":".0.. .... = Don't fragment: Not set","leaf":true,"children":null},{"id":0,"text":"..0. .... = More fragments: Not set","leaf":true,"children":null}]},{"id":0,"text":"Fragment offset: 0","leaf":true,"children":null},{"id":0,"text":"Time to live: 60","leaf":true,"children":null},{"id":0,"text":"Protocol: SCTP (132)","leaf":true,"children":null},{"id":0,"text":"Header checksum: 0x0bf9 [correct]","leaf":false,"children":[{"id":0,"text":"Good: True","leaf":true,"children":null},{"id":0,"text":"Bad: False","leaf":true,"children":null}]},{"id":0,"text":"Source: 10.126.136.14 (10.126.136.14)","leaf":true,"children":null},{"id":0,"text":"Source or Destination Address: 10.126.136.14 (10.126.136.14)","leaf":true,"children":null},{"id":0,"text":"Source Host: 10.126.136.14","leaf":true,"children":null},{"id":0,"text":"Source or Destination Host: 10.126.136.14","leaf":true,"children":null},{"id":0,"text":"Destination: 10.125.155.4 (10.125.155.4)","leaf":true,"children":null},{"id":0,"text":"Source or Destination Address: 10.125.155.4 (10.125.155.4)","leaf":true,"children":null},{"id":0,"text":"Destination Host: 10.125.155.4","leaf":true,"children":null},{"id":0,"text":"Source or Destination Host: 10.125.155.4","leaf":true,"children":null}]},{"id":0,"text":"Stream Control Transmission Protocol, Src Port: sgsap (29118), Dst Port: sgsap (29118)","leaf":false,"children":[{"id":0,"text":"Source port: 29118","leaf":true,"children":null},{"id":0,"text":"Destination port: 29118","leaf":true,"children":null},{"id":0,"text":"Verification tag: 0x0cb4ee97","leaf":true,"children":null},{"id":0,"text":"Port: 29118","leaf":true,"children":null},{"id":0,"text":"Port: 29118","leaf":true,"children":null},{"id":0,"text":"Checksum: 0xe9f55957 (not verified)","leaf":true,"children":null},{"id":0,"text":null,"leaf":false,"children":[{"id":0,"text":"Chunk type: DATA (0)","leaf":false,"children":[{"id":0,"text":"0... .... = Bit: Stop processing of the packet","leaf":true,"children":null},{"id":0,"text":".0.. .... = Bit: Do not report","leaf":true,"children":null}]},{"id":0,"text":"Chunk flags: 0x03","leaf":false,"children":[{"id":0,"text":".... ...1 = E-Bit: Last segment","leaf":true,"children":null},{"id":0,"text":".... ..1. = B-Bit: First segment","leaf":true,"children":null},{"id":0,"text":".... .0.. = U-Bit: Ordered delivery","leaf":true,"children":null},{"id":0,"text":".... 0... = I-Bit: Possibly delay SACK","leaf":true,"children":null}]},{"id":0,"text":"Chunk length: 27","leaf":true,"children":null},{"id":0,"text":"TSN: 2792917541","leaf":true,"children":null},{"id":0,"text":"Stream Identifier: 0x000b","leaf":true,"children":null},{"id":0,"text":"Stream sequence number: 46477","leaf":true,"children":null},{"id":0,"text":"Payload protocol identifier: not specified (0)","leaf":true,"children":null}]}]},{"id":0,"text":"SGs Application Part (SGsAP)","leaf":false,"children":[{"id":0,"text":"SGSAP Message Type: SGsAP-RELEASE-REQUEST (0x1b)","leaf":true,"children":null},{"id":0,"text":null,"leaf":false,"children":[{"id":0,"text":"Element ID: 0x01","leaf":true,"children":null},{"id":0,"text":"Length: 8","leaf":true,"children":null},{"id":0,"text":null,"leaf":true,"children":null},{"id":0,"text":".... 1... = Odd/even indication: Odd number of identity digits","leaf":true,"children":null},{"id":0,"text":".... .001 = Mobile Identity Type: IMSI (1)","leaf":true,"children":null},{"id":0,"text":"BCD Digits: 460007732270827","leaf":true,"children":null}]}]}];
        if(this.data.length>0)
            testRoot.loader = new Ext.tree.TreeLoader({
//                dataUrl: this.data[0].url,
                root:  treeData,
                pagingModel: 'local'
            });

        var tree = new Ext.tree.TreePanel({
            id: 'detailpanel',
            title: '解码树',
            border: false,
            autoScroll: true,
            loader:new Ext.tree.TreeLoader(),//
            root:new Ext.tree.AsyncTreeNode({
                id:"root",
                text:"根节点",//节点名称
                expanded:true,//展开
                leaf:false,//是否为叶子节点
                children:treeData

            }),
            rootVisible: false
        });


        ladderTabItems.push({
            xtype: 'tabpanel', region: 'south', activeTab: 0, height: 300, split: true, collapseMode: 'mini',
            items: [ tree, { autoScroll: true, title: '原始信令', id: 'signalPanel',html:"00D0D0A11E8C1051720D6CFF0800452800A439A800003C840BF90A7E880E0A7D9B0471BE71BE0CB4EE97E9F559570003001BA6788A25000BB58D000000001B01084906007723728072"}]
        });
    }

    var ladderdiagramTab = {
        xtype: 'panel', layout: 'border', title: callflowdiagram,
        items: ladderTabItems
    };

    var tabList = [];
    tabList.push(ladderdiagramTab);
//    if (_summary.length > 0)
//    if (_summary) {
//    var summaryTab = {xtype: 'panel', region: 'center', layout: 'border', border: false, autoScroll: true, html: InitSummaryTable() };
//    tabList.push(summaryTab);
//    }


    //    会话列表
    var sessionFileds = [];
    var sessionColumns = [];
    for (var obj in _summary[0]) {
        sessionFileds.push(obj);
        sessionColumns.push({header: obj, width: 75, sortable: true, dataIndex: obj});
    }
    var sessionGrid = new Ext.grid.GridPanel({
//            xtype: 'panel', region: 'center', layout: 'border', border: false, autoScroll: true,
            xtype: 'tabpanel', region: 'center', activeTab: 0, split: true, collapseMode: 'mini',
            store: new Ext.data.JsonStore({
                fields: sessionFileds,
                data: _summary
            }),
            columns: sessionColumns,
            stripeRows: true,
//            autoExpandColumn: 'company',
            title: '会话列表',
            // config options for stateful behavior
            stateful: true,
            stateId: 'grid'
        }
    );

//    消息列表
    var msgFileds = [];
    var msgColumns = [];
    for (var obj in this.data[0]) {
        if (obj.indexOf("(S)") > -1) {
            msgFileds.push(obj);
            msgColumns.push({header: obj.replace('(S)', ''), width: 200, sortable: true, dataIndex: obj});
        }
    }
    var msgGrid = new Ext.grid.GridPanel({
            xtype: 'tabpanel', region: 'south', activeTab: 0, height: 300, split: true, collapseMode: 'mini',
            store: new Ext.data.JsonStore({
                fields: msgFileds,
                data: this.data
            }),
            columns: msgColumns,
            stripeRows: true,
//            autoExpandColumn: 'company',
            title: '消息列表',
            // config options for stateful behavior
            stateful: true,
            stateId: 'grid'
        }
    );

    var infoTab = {
        xtype: 'panel', layout: 'border', title: "会话信息",
        items: [sessionGrid, msgGrid ]
    };
    tabList.push(infoTab);

    new Ext.Viewport({
        layout: 'border',
        items: [
            {
                xtype: 'tabpanel', region: 'center', activeTab: 0,
                items: tabList
            }
        ]
    });
};