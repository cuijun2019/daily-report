$(function () {
    // 禁止左右滑动
    $("BODY").on("swipe", function(event){
        event.preventDefault();
        event.stopPropagation();
        return;
    });
    var isCommitted = false;
    // 页面加载时，添加一个项目信息
    addProject(1);
//    var code = getQueryString("code");
//    var state = getQueryString("state");
//    var corpid = "wxf6eb3a37aa3b2042";
//    var corpsecret = "S81CfWcVFSczYZVMkOHnPwDdok1OrNgdIByzpFg82Eg";
//    var accesstoken = "";
//    $('#employeeCode').val("3223    崔军");
//    $('#employee').val("崔军");
    var employeeCode = "";
    var employee = "";
    $.ajax({
        url : "/modules/lteproject/getUserId?corpid=wxf6eb3a37aa3b2042&corpsecret=S81CfWcVFSczYZVMkOHnPwDdok1OrNgdIByzpFg82Eg&code=" + getQueryString("code"),
        type : "post",
        async : false,
        cache : false,
        dataType : 'json',
        success:function(result){
            $('#employeeCode').val(result.userId + "    " + result.employee);
            $('#employee').val(result.employee);
            employeeCode = result.userId;
            employee = result.employee;
        }
    });
    // 左右滑动显示不同的模块（填写日志、历史日志、员工日志、每月考核）
    TouchSlide({ slideCell:"#leftTabBox", effect:"left",
        startFun:function(i,c){
            var currentDate = new Date((+date)-0*24*3600*1000).Format("yyyy-MM-dd");
            var currentOneDate = currentDate.substring(0, currentDate.length-2) + "01";
            var preDate = new Date((+date)-1*24*3600*1000).Format("yyyy-MM-dd");
            if (i==0) {
                $("#write img").attr("src", "modules/geogis/img/11.png");
                $("#person img").attr("src", "modules/geogis/img/2.png");
                $("#staff img").attr("src", "modules/geogis/img/3.png");
                $("#appraise img").attr("src", "modules/geogis/img/4.png");
                $("#writeColor").css("color", "#2DA5E6");
                $("#personColor").css("color", "#757575");
                $("#staffColor").css("color", "#757575");
                $("#appraiseColor").css("color", "#757575");

                $("#writeHDiv").show();
                $("#historyHDiv").hide();
                $("#staffHDiv").hide();
                $("#appraiseHDiv").hide();
                // 自适应高度
                $(".buttonDiv").css("height", "100px");
                $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#writeHDiv").height()+70)+"px"});
            }
            if (i==1) {
		        $("#startDate").val(currentOneDate);
		        $("#endDate").val(currentDate);
                $("#write img").attr("src", "modules/geogis/img/1.png");
                $("#person img").attr("src", "modules/geogis/img/22.png");
                $("#staff img").attr("src", "modules/geogis/img/3.png");
                $("#appraise img").attr("src", "modules/geogis/img/4.png");
                $("#writeColor").css("color", "#757575");
                $("#personColor").css("color", "#2DA5E6");
                $("#staffColor").css("color", "#757575");
                $("#appraiseColor").css("color", "#757575");

                $("#writeHDiv").hide();
                $("#historyHDiv").show();
                $("#staffHDiv").hide();
                $("#appraiseHDiv").hide();

		        $(".employeeSpan").text(employee);
                personalLog(employeeCode, null, null, null, null);
                if (isPC()) {
                    $("#startDate").removeAttr("readOnly");
                    $("#endDate").removeAttr("readOnly");
                } else {
                    $("#startDate").css("readOnly", "readOnly");
                    $("#endDate").css("readOnly", "readOnly");
                }
                // 补上空白的空间，以可以左右滑动
		        $("#personSurplus").css("height","0px");
                if (($("body").height()-$("#historyHDiv").height()-70) > 0) {
                    $("#personSurplus").css("height", ($("body").height()-$("#historyHDiv").height()-70) + "px");
                }
                // 自适应高度
                $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#historyHDiv").height()+70)+"px"});
            }
            if (i==2) {
                $("#write img").attr("src", "modules/geogis/img/1.png");
                $("#person img").attr("src", "modules/geogis/img/2.png");
                $("#staff img").attr("src", "modules/geogis/img/33.png");
                $("#appraise img").attr("src", "modules/geogis/img/4.png");
                $("#writeColor").css("color", "#757575");
                $("#personColor").css("color", "#757575");
                $("#staffColor").css("color", "#2DA5E6");
                $("#appraiseColor").css("color", "#757575");

		        var str = "";
                if (employeeCode == "3223" || employeeCode == "8137" || employeeCode == "16" || employeeCode == "25" || employeeCode == "60234" || employeeCode == "10300" || employeeCode == "922" || employeeCode == "72") {
		            str = '<span id="notHaveLine">';
                    str += '</span>';
                    str += '<span id="projectLineField">';
                    str += '    <ul id="projectLine">';
                    str += '    </ul>';
                    str += '</span>';
                    str += '<span id="projectField">';
                    str += '    <ul id="project">';
                    str += '    </ul>';
                    str += '</span>';
                    str += '<span id="projectDetailField">';
                    str += '    <span id="staffLogDetail">';
                    str += '    </span>';
                    str += '</span>';
                    str += '<div id="staffSurplus">';
                    str += '</div>';
                    $("#notHaveLine").hide();
                    $("#staffDate").hide();
                    $("#projectLineSpan").html(str);
		            projectLine();
                } else {
                    str = '<span id="notHaveStaff">';
                    str += '    <div style="height:30px;padding-top:5px;border-bottom:1px solid #EEEEEE;text-align:center;color:#757575;font-weight:bold;">';
                    str += '        <span class="employeeSpan"></span>下没有项目';
                    str += '    </div>';
                    str += '</span>';
                    str += '<span id="staffField">';
                    str += '    <ul id="stafflog">';
                    str += '    </ul>';
                    str += '</span>';
                    str += '<span id="staffDetail">';
                    str += '    <span id="staffLogDetail">';
                    str += '    </span>';
                    str += '</span>';
                    str += '<div id="staffSurplus">';
                    str += '</div>';
                    $("#staffStartDate").val(currentOneDate);
                    $("#staffEndDate").val(currentDate);
                    $("#lineDate").hide();
                    $("#projectLineSpan").hide();
                    $("#searchLi").hide();
                    $("#staffSpan").html(str);
                    stafflog(employeeCode, employee, $("#staffStartDate").val(), $("#staffEndDate").val());
                }

                $("#writeHDiv").hide();
                $("#historyHDiv").hide();
                $("#staffHDiv").show();
                $("#appraiseHDiv").hide();

		        $(".employeeSpan").text(employee);
                
                if (isPC()) {
                    $("#startStaffDate").removeAttr("readOnly");
                    $("#endStaffDate").removeAttr("readOnly");
                } else {
                    $("#startStaffDate").css("readOnly", "readOnly");
                    $("#endStaffDate").css("readOnly", "readOnly");
                }

                // 补上空白的空间，以可以左右滑动
             	$("#staffSurplus").css("height","0px");
                if (($("body").height()-$("#staffHDiv").height()-70) > 0) {
                    $("#staffSurplus").css("height", ($("body").height()-$("#staffHDiv").height()-70) + "px");
                }
                // 自适应高度
                $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#staffHDiv").height()+70)+"px"});
            }
            if (i==3) {
                $("#write img").attr("src", "modules/geogis/img/1.png");
                $("#person img").attr("src", "modules/geogis/img/2.png");
                $("#staff img").attr("src", "modules/geogis/img/3.png");
                $("#appraise img").attr("src", "modules/geogis/img/44.png");
                $("#writeColor").css("color", "#757575");
                $("#personColor").css("color", "#757575");
                $("#staffColor").css("color", "#757575");
                $("#appraiseColor").css("color", "#2DA5E6");

                $("#writeHDiv").hide();
                $("#historyHDiv").hide();
                $("#staffHDiv").hide();
                $("#appraiseHDiv").show();
		        initHighCharts("", "");
                $("#date1").val(preDate);
                $("#date2").val(preDate);
                $('#appraiseStartDate').val(preDate);
                $('#appraiseEndDate').val(preDate);
                $('#searchName').val("");
                // 补上空白的空间，以可以左右滑动
		        $("#appraiseSurplus").css("height","0px");
                if (($("body").height()-$("#appraiseHDiv").height()-70) > 0) {
                    $("#appraiseSurplus").css("height", ($("body").height()-$("#appraiseHDiv").height()-70) + "px");
                }
                // 自适应高度
                $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#appraiseHDiv").height()+70)+"px"});

            }
        },
        endFun:function(i,c){

        }
    });

    //点击时隐藏文本框的外边框
    $("#projectCode1").click(function() {
        $("input").css("outline", "none");
    });
    $("#projectName1").click(function() {
        $("input").css("outline", "none");
    });
    $("#proportion1").click(function() {
        $("input").css("outline", "none");
    });
    $("#reporter").click(function() {
        $("input").css("outline", "none");
    });
    $("#employeeCode").click(function() {
        $("input").css("outline", "none");
    });
    $("#logTime").click(function() {
        $("input").css("outline", "none");
    });
    $("#context").click(function() {
        $("textarea").css("outline", "none");
    });

    var hide = false;
    $('#startDate').datetimepicker({
        language:  'zh-CN',
        minView : "month",
        format : 'yyyy-mm-dd',
        clearBtn: true,
        autoclose : 1
    }).on("show", function() {
        hide = false;
    }).on("hide", function() {
        hide = true;
    });
    // 设置开始时间，重新渲染历史日志
    $('#startDate').change(function() {
        personalLog(employeeCode, null, $('#startDate').val(), $('#endDate').val(), null);
    });
    $('#endDate').datetimepicker({
        language:  'zh-CN',
        minView : "month",
        format : 'yyyy-mm-dd',
        clearBtn: true,
        autoclose : 1
    }).on("show", function() {
        hide = false;
    }).on("hide", function() {
        hide = true;
    });
    // 设置结束时间，重新渲染历史日志
    $('#endDate').change(function() {
        personalLog(employeeCode, null, $('#startDate').val(), $('#endDate').val(), null);
    });

    $('#staffStartDate').datetimepicker({
        language:  'zh-CN',
        minView : "month",
        format : 'yyyy-mm-dd',
        clearBtn: true,
        autoclose : 1
    }).on("show", function() {
        hide = false;
    }).on("hide", function() {
        hide = true;
    });
    $('#staffStartDate').change(function() {
        stafflog(employeeCode, employee, $("#staffStartDate").val(), $("#staffEndDate").val());
    });
    $('#staffEndDate').datetimepicker({
        language:  'zh-CN',
        minView : "month",
        format : 'yyyy-mm-dd',
        clearBtn: true,
        autoclose : 1
    }).on("show", function() {
        hide = false;
    }).on("hide", function() {
        hide = true;
    });
    $('#staffEndDate').change(function() {
        stafflog(employeeCode, employee, $("#staffStartDate").val(), $("#staffEndDate").val());
    });

    $('#lineStartDate').datetimepicker({
        language:  'zh-CN',
        minView : "month",
        format : 'yyyy-mm-dd',
        clearBtn: true,
        autoclose : 1
    }).on("show", function() {
        hide = false;
    }).on("hide", function() {
        hide = true;
    });
    $('#lineStartDate').change(function() {
        personalLog('null', $("#projectCode").val(), $("#lineStartDate").val(), $("#lineEndDate").val(), "projectLine");
    });
    $('#lineEndDate').datetimepicker({
        language:  'zh-CN',
        minView : "month",
        format : 'yyyy-mm-dd',
        clearBtn: true,
        autoclose : 1
    }).on("show", function() {
        hide = false;
    }).on("hide", function() {
        hide = true;
    });
    $('#lineEndDate').change(function() {
        personalLog('null', $("#projectCode").val(), $("#lineStartDate").val(), $("#lineEndDate").val(), "projectLine");
    });
    var dateRange1 = new pickerDateRange('date1', {
        stopToday : false,
        isTodayValid : true,
        startDateId : 'appraiseStartDate', // 开始日期输入框ID
        endDateId : 'appraiseEndDate', // 结束日期输入框ID
        startDate: '',
        endDate: '',
        needCompare : false,
        defaultText : ' 结束时间 ',
        autoSubmit : true,
        success : function(obj) {
            $('#date1').val($("#appraiseStartDate").val());
            $('#date2').val($("#appraiseEndDate").val());
            initHighCharts($('#appraiseStartDate').val(), $('#appraiseEndDate').val());
            $("#listId").html("");
            return true;
        },
        inputTrigger : 'input_trigger1',
        theme : 'ta'
    });

    // 设置开始时间，重新渲染历史日志
    $('#appraiseStartDate').change(function() {
        initHighCharts($('#appraiseStartDate').val(), $('#appraiseEndDate').val());
	    $("#listId").html("");
    });
    
    // 设置开始时间，重新渲染历史日志
    $('#appraiseEndDate').change(function() {
        initHighCharts($('#appraiseStartDate').val(), $('#appraiseEndDate').val());
	    $("#listId").html("");
    });

    $(".form-control").on("click", function() {
        if (hide) {
            $('#startDate').trigger("blur");
                $('#endDate').trigger("blur");
            $('#staffStartDate').trigger("blur");
                $('#staffEndDate').trigger("blur");
            $('#lineStartDate').trigger("blur");
                $('#lineEndDate').trigger("blur");
            $('#appraiseStartDate').trigger("blur");
            $('#appraiseEndDate').trigger("blur");
        }
    });

    // 日志时间加载前一个星期
    var date=new Date();
    var today = new Array('周日','周一','周二','周三','周四','周五','周六');
    var treelist = "";
    var currentDate = "";
    $("#logTime").val(date.Format("yyyy-MM-dd"));
    $("#treelist").empty();
    for (var i=7; i >= 0; i--) {
        currentDate = new Date((+date)-i*24*3600*1000);
        treelist += "<li><font size='2px;'>" + currentDate.Format("yyyy年MM月dd日") + " " + today[currentDate.getDay()] + "</font></li>";
    }
    $("#treelist").append(treelist);
    // 设置工作性质
    var natureList = new Array('开发','测试','维护','生产','培训','部署','策划','数据分析','文档撰写','内部交流','外部交流','商务','服务交付','研究','休假','其他');
    var naturelist = "";
    for (var i=0; i < natureList.length; i++) {
        naturelist += "<li><font size='2px;'>" + natureList[i] + "</font></li>";
    }
    $("#naturelist").append(naturelist);

    // 项目编码自动补全（用于第一个项目）
    typeaheadProjectCode(1);

    // 项目名称自动补全（用于第一个项目）
    typeaheadProjectName(1);
    // 项目占比自动补全（用于第一个项目）
    typeaheadProportion(1);

    // 修改项目占比触发
    changeProportion(1);
    // 修改项目名称触发
    changeProjectName(1);
    // 修改项目编码触发
    changeProjectCode(1);

    treelistFunc();
    naturelistFunc();

    $("#saveBtn").click(function() {
        var employeeCode = $("#employeeCode").val();
        var logTime = $("#logTime").val();
        var workNature = $("#workNature").val();
        var context = $("#context").val();
        var logInfoList = collectData(".projectInfo");

        var message = validMessage(employeeCode, logTime, workNature, context, logInfoList);
        if (message != "" && message != "undefined" && message != undefined) {
            $("#popdiv").empty();
            var str = '<div style="background-color:white;width:200px;height:150px;border-radius:6px;">';
            str+='			<div style="height:90px;text-align:center;padding-top:40px;">';
            str+='				<img src="modules/geogis/img/error.png" />';
            str+='			</div>';
            str+='			<div style="height:30px;text-align:center;font-weight:bold;">';
            str+='				<font size="2px">提交信息失败</font>';
            str+='			</div>';
            str+='			<div style="height:30px;text-align:center;">';
            str+='				<font size="2px">' + message + '</font>';
            str+='			</div>'
            str += '</div>';
            $("#popdiv").append(str);
            $("#popdiv").popup("open");
            //$("#popdiv").popup({ history: false });

            var _posiLeft = 0;
            $("#popdiv").css({"left": _posiLeft + "px","top":"0px","display":"block"});

            setTimeout(function(){
                $("#popdiv").popup("close");
                $("#popdiv").empty();
            }, 2000);//两秒后关闭

            return;
        }
        if (isCommitted) {
            return;
        }
        isCommitted = true;
        $.ajax({
            url : "/modules/lteproject/saveLogInfo2?random=" + Math.random(),
            type : "post",
            data : {
                logInfoList : encode(logInfoList)
            },
            async : true,
            cache : false,
            dataType : 'json',
            success : function(data) {
                $("#popdiv").empty();
                var str = '<div style="background-color:white;width:150px;height:100px;border-radius:6px;">';
                str+='			<div style="height:60px;text-align:center;padding-top:20px;">';
                str+='				<img src="modules/geogis/img/success.png" />';
                str+='			</div>';
                str+='			<div style="height:40px;text-align:center;font-weight:bold;">';
                str+='				<font size="2px">提交成功！</font>';
                str+='			</div>';
                str += '</div>';
                $("#popdiv").append(str);
                $("#popdiv").popup("open");

                var w = $("#popdiv").width(),ww = $("body").width();
                $("#popdiv").css("left",-($("#popdiv").width()/2));
                $("#popdiv").css("bottom","");
                $("#popdiv").css("top","");
                var h = $("#popdiv").height(),hh = $(window).height();
                $("#popdiv").css("bottom",(hh-h)/2-20);

                //var _posiLeft = -($("#popdiv").width()/2);
                //$("#popdiv").css({"left": _posiLeft + "px","display":"block"});

                setTimeout(function(){
                    $("#popdiv").popup("close");
                    $("#popdiv").empty();
                }, 2000);//两秒后关闭

		        isCommitted = false;

                // 删除项目及内容，只保留一个项目
                var count = collectDataCount(".projectInfo");
                for (var i = 1; i <= count; i++) {
                    removeProject(i);
                    $("#projectCode"+i).val("");
                    $("#projectName"+i).val("");
                    $("#reporter"+i).val("");
                    $("#proportion"+i).val("");
                }
                if (collectDataCount(".projectInfo") != 0) {
                    for (var i = 1; i <= collectDataCount(".projectInfo"); i++) {
                    removeProject(i);
                    $("#projectCode"+i).val("");
                    $("#projectName"+i).val("");
                    $("#reporter"+i).val("");
                    $("#proportion"+i).val("");
                    }
                }
                addProject(1);
                $("#context").val("");
                $("#logTime").val(date.Format("yyyy-MM-dd"));
                treelistFunc();
                $("#workNature").val("");
                naturelistFunc();
            },
            complete : function() {
            }
        });
    });

    $("#resetBtn").click(function() {
        // 删除项目及内容，只保留一个项目
        var count = collectDataCount(".projectInfo");
        for (var i = 1; i <= count; i++) {
            removeProject(i);
            $("#projectCode"+i).val("");
            $("#projectName"+i).val("");
            $("#reporter"+i).val("");
            $("#proportion"+i).val("");
        }
        if (collectDataCount(".projectInfo") != 0) {
            for (var i = 1; i <= collectDataCount(".projectInfo"); i++) {
            removeProject(i);
            $("#projectCode"+i).val("");
            $("#projectName"+i).val("");
            $("#reporter"+i).val("");
            $("#proportion"+i).val("");
            }
        }
        addProject(1);
        $("#context").val("");
        $("#logTime").val(date.Format("yyyy-MM-dd"));
        treelistFunc();
        $("#workNature").val("");
        naturelistFunc();
    });
});

//数组去重
function unique(arr){
    var new_arr=[];
    for(var i=0;i<arr.length;i++) {
        var items=arr[i];
        if($.inArray(items,new_arr)==-1) {
            new_arr.push(items);
        }
    }
    return new_arr;
}
// 格式化时间
Date.prototype.Format = function(fmt) { //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}
// 获取url参数的值
function getQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.document.location.search.substr(1).match(reg);
    if(r != null) return decodeURI(r[2]); return "";
}

String.prototype.replaceAll = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
}
// 日志内容的展开/收起
function expand(i, context, status) {
    var state = $("#expand_" + i).text();
    if (state == "展开") {
        $("#context_" + i).html('<font size="2px;">' + context + '</font>');
        $("#expand_" + i).text("收起");
    } else {
        context = context.replaceAll("<br>", "\n");
        $("#context_" + i).html('<font size="2px;">' + context.substring(0, 20) + '</font>');
        $("#expand_" + i).text("展开");
    }
    if (status=="staff") {
	    $("#staffSurplus").css("height","0px");
	    if (($("body").height()-$("#staffHDiv").height()-70) > 0) {
            $("#staffSurplus").css("height", ($("body").height()-$("#staffHDiv").height()-70) + "px");
        }
        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#staffHDiv").height()+70+50)+"px"});
    } else if (status=="projectLine") {
        $("#staffSurplus").css("height","0px");
        if (($("body").height()-$("#staffHDiv").height()-70) > 0) {
            $("#staffSurplus").css("height", ($("body").height()-$("#staffHDiv").height()-70) + "px");
        }
        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#staffHDiv").height()+70)+"px"});
    } else if (status=="appraise") {
        // 补上空白的空间，以可以左右滑动
        $("#appraiseSurplus").css("height","0px");
        if (($("body").height()-$("#appraiseHDiv").height()-70) > 0) {
            $("#appraiseSurplus").css("height", ($("body").height()-$("#appraiseHDiv").height()-70) + "px");
        }
        // 自适应高度
        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#appraiseHDiv").height()+70)+"px"});
    } else {
        $("#personSurplus").css("height","0px");
        if (($("body").height()-$("#historyHDiv").height()-70) > 0) {
            $("#personSurplus").css("height", ($("body").height()-$("#historyHDiv").height()-70) + "px");
        }
        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#historyHDiv").height()+70)+"px"});
    }
}
// 历史日志的渲染
function personalLog(employeeCode, projectCode, startDate, endDate, status) {
    var data = "";
    var str = "";
    var context = "";
    var lineName = "";
    if (status=="staff") {
        $("#staffLogDetail").empty();
    } else if (status=="projectLine") {
        var date = new Date();
        var currentDate = new Date((+date)-0*24*3600*1000).Format("yyyy-MM-dd");
        var currentOneDate = currentDate.substring(0, currentDate.length-2) + "01";
        if (startDate == 'null') {
            $("#lineStartDate").val(currentOneDate);
        }
        if (endDate == 'null') {
            $("#lineEndDate").val(currentDate);
        }
        $("#projectCode").val(projectCode);
        $("#staffLogDetail").empty();
        $.ajax({
            url : "/modules/lteproject/queryLineNameByCode?projectCode=" + projectCode,
            type : "post",
            async : false,
            cache : false,
            dataType : 'json',
            success:function(result){
                lineName = result.lineName;
            }
        });
        $("#backLi").show();
        $("#searchLi").hide();
        $("#backLi a").attr("onclick", "queryProjectByLine('" + lineName + "')");
    } else {
        $("#personlog").empty();
    }
    $.ajax({
        url : "/modules/lteproject/queryOwnLogInfo?employeeCode=" + employeeCode + "&projectCode=" + projectCode + "&startDate=" + startDate + "&endDate=" + endDate,
        type : "post",
        async : false,
        cache : false,
        dataType : 'json',
        success:function(datas){
	        $("#projectField").hide();
            if (datas=="") {
                if (status=="projectLine") {
                    $("#notHaveLine").show();
                    $("#lineDate").show();
                    str += '    <div style="height:30px;padding-top:5px;border-bottom:1px solid #EEEEEE;text-align:center;color:#757575;font-weight:bold;">';
                    str += '        该项目下没工作日志';
                    str += '    </div>';
                    $("#notHaveLine").html(str);
                }
                return;
            }
            if (status=="staff") {
                str += '    <div style="height:auto;padding-top:5px;border-bottom:1px solid #EEEEEE;text-align:center;color:#757575;font-weight:bold;">';
                str += '        <span id="projectNameSpan"></span>';
                str += '    </div>';
            }
	        if (status=="projectLine") {
                $("#notHaveLine").hide();
                $("#projectLineField").hide();
                $("#projectField").hide();
                $("#projectDetailField").show();
                $("#staffDate").hide();
		        $("#lineDate").show();
                str = '    <div style="height:auto;padding-top:5px;border-bottom:1px solid #EEEEEE;text-align:center;color:#757575;font-weight:bold;">';
                str += '        <span id="projectNameSpan">' + datas[0].projectName + '</span>';
                str += '    </div>';
            }
            for (var i = 0; i < datas.length; i++) {
                data = datas[i];
		        context = data.context;
                str += '<div id="personlog_' + i + '" style="height:auto;border-bottom:1px solid #EEEEEE;">';
                str += '    <table style="width:95%;margin-left:10px;margin-top:5px;margin-bottom:5px;" border="0">';
                str += '		<tr>';
                str += '			<td colspan="2" style="font-weight:bold;">';
                if (status=="projectLine") {
                    str += '			' + data.employeeCode + '&nbsp;&nbsp;&nbsp;' + data.employee + '&nbsp;&nbsp;&nbsp;' + data.logTime.replace("-","/").replace("-","/");
                } else {
                    str += '			' + data.logTime.replace("-","/").replace("-","/");
                }
		        str += '			</td>';
                str += '		</tr>';
                str += '		<tr>';
                str += '			<td colspan="2">';
                str += '			' + data.projectCode + '&nbsp;&nbsp;&nbsp;&nbsp;' + data.projectName;
                str += '			</td>';
                str += '		</tr>';
                if (context == undefined) {
                    context = "";
                }
                if (context.length > 20) {
                    str += '		<tr>';
                    str += '			<td style="width:90%;">';
                    str += '			<span id="context_' + i + '" style="float:left;color:#757575;">' + context.substring(0, 20) + "</span>";
                    str += '			</td>';
                    str += '			<td style="vertical-align:bottom;">';
                    str += '				<a id="expand_' + i + '" style="text-decoration: none;cursor:pointer;" onclick="expand(' + i + ',\'' + context.replaceAll("\n","<br>") + '\',\'' + status + '\')">展开</a>';
                    str += '			</td>';
                    str += '		</tr>';
                } else {
                    str += '		<tr>';
                    str += '			<td colspan="2" style="color:#757575;">';
                    str += '			' + context;
                    str += '			</td>';
                    str += '		</tr>';
                }
                str += '    </table>';
                str += '</div>';
            }
            if (status=="staff" || status=="projectLine") {
                $("#staffLogDetail").append(str);
            } else {
                $("#personlog").append(str);
            }
        }
    });
    // 设置高度自适应
    if (status=="staff") {
	    $("#staffSurplus").css("height","0px");
        if (($("body").height()-$("#staffHDiv").height()-70-50) > 0) {
	        $("#staffSurplus").css("height", ($("body").height()-$("#staffHDiv").height()-70-50) + "px");
	    }
        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#staffHDiv").height()+70+50)+"px"});
    } else if (status=="projectLine") {
        $("#staffSurplus").css("height","0px");
        if (($("body").height()-$("#staffHDiv").height()-70) > 0) {
            $("#staffSurplus").css("height", ($("body").height()-$("#staffHDiv").height()-70) + "px");
        }
        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#staffHDiv").height()+70)+"px"});
    } else {
	    $("#personSurplus").css("height","0px");
	    if (($("body").height()-$("#historyHDiv").height()-70) > 0) {
	        $("#personSurplus").css("height", ($("body").height()-$("#historyHDiv").height()-70) + "px");
	    }
        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#historyHDiv").height()+70)+"px"});
    }
}
// 员工日志的渲染
function stafflog(employeeCode, employee, staffStartDate, staffEndDate) {
    var str = "";
    $("#staffDate").show();
    $("#staffField").show();
    $("#staffDetail").hide();
    $("#notHaveStaff").hide();
    $("#stafflog").empty();
    $("#backLi").hide();
    $.ajax({
        url : "/modules/lteproject/queryEmployeeProject",
        type : "post",
	    data : {
            employeeCode : employeeCode,
            staffStartDate : staffStartDate,
            staffEndDate : staffEndDate
        },
        async : false,
        cache : false,
        dataType : 'json',
        success:function(datas){
            if (datas=="") {
                $("#staffField").hide();
                $("#staffDetail").hide();
                $("#notHaveStaff").show();
                return;
            }
            var data = "";
            var employeeCode = "";
            var employee = "";
            var projectCode = "";
            var projectName = "";
            for (var i = 0; i < datas.length; i++) {
                data = datas[i];
                employeeCode = data.employeeCode;
                employee = data.employee;
                projectCode = data.projectCode;
                projectName = data.projectName;
                str += '<li style="height:auto;border-bottom:1px solid #EEEEEE;padding-left:20px;padding-top:10px;padding-bottom:10px;">';
                str += '	<a style="text-decoration: none;" onclick="staffDetail(' + employeeCode + ',\'' + employee + '\',\'' + projectCode + '\',\'' + projectName + '\')">';
                str += '	<table width="100%" border="0">';
                str += '		<tr>';
                str += '			<td style="width:85%;">';
                str += '				<font color="#757575" style="font-weight:normal;">' + employeeCode + ' ' + employee + ' 交了' + data.logCount + '条日志<br />' + projectCode + ' ' + projectName + '</font>';
                str += '			</td>';
                str += '			<td style="width:15%;text-align:center;">';
                str += '				<font color="#757575">></font>';
                str += '			</td>';
                str += '		</tr>';
                str += '	</table>';
                str += '	</a>';
                str += '</li>';
            }
            $("#stafflog").append(str);
        }
    });
    // 补上空白的空间，以可以左右滑动
    $("#staffSurplus").css("height","0px");
    if (($("body").height()-$("#staffHDiv").height()-70) > 0) {
        $("#staffSurplus").css("height", ($("body").height()-$("#staffHDiv").height()-70) + "px");
    }
    // 自适应高度
    $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#staffHDiv").height()+70)+"px"});
}
// 员工详细信息的渲染
function staffDetail(employeeCode, employee, projectCode, projectName) {
    $("#staffDate").hide();
    $("#staffField").hide();
    $("#staffDetail").show();
    $("#notHaveStaff").hide();
    $("#backLi").show();
    var curEmployeeCode = $.trim($("#employeeCode").val().replace($("#employee").val(), ""));
    $("#backLi a").attr("onclick", "stafflog('" + curEmployeeCode + "', '" + $("#employee").val() + "', '" + $("#staffStartDate").val() + "', '" + $("#staffEndDate").val() + "')");
    personalLog(employeeCode, projectCode, $("#staffStartDate").val(), $("#staffEndDate").val(), "staff");
    $("#employeeStaffSpan").text(employee);
    $("#projectNameSpan").text(projectName);
}
// 填写日志时删除项目信息
function removeProject(i) {
    $("#projectInfo" + i).remove();
    $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#writeHDiv").height()+70)+"px"});
}
// 填写日志时添加项目信息
function addProject(i) {
    $("#png" + (i-1) + " img").attr("src", "modules/geogis/img/12.png");
    $("#png" + (i-1)).attr("onclick", "removeProject("+(i-1)+")");
    var str = '<span id="projectInfo'+i+'" class="projectInfo" x-project-code="" x-project-name="" x-reporter="" x-proportion="">';
    str += '<span style="background-color:white;">&nbsp;&nbsp;&nbsp;</span>';
    str += '<input type="text" style="width:96%;height:50px;border-top-style:none;border-left-style:none;border-right-style:none;border-bottom:1px solid #EEEEEE;color:black;" class="projectCode" name="projectCode" id="projectCode'+i+'" data-provide="typeahead" data-source="" placeholder="请输入项目编码" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目编码\'" /><br />';
    str += '<span style="background-color:white;">&nbsp;&nbsp;&nbsp;</span>';
    str += '<input type="text" style="width:96%;height:50px;border-top-style:none;border-left-style:none;border-right-style:none;border-bottom:1px solid #EEEEEE;color:black;" class="projectName" name="projectName" id="projectName'+i+'" data-provide="typeahead" data-source="" placeholder="请输入项目名称" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目名称\'" /><br />';
    str += '<span style="background-color:white;">&nbsp;&nbsp;&nbsp;</span>';
    str += '<input type="text" style="width:96%;height:50px;border-top-style:none;border-left-style:none;border-right-style:none;border-bottom:1px solid #EEEEEE;color:black;" class="reporter" name="reporter" id="reporter'+i+'" placeholder="请输入汇报对象" autocomplete="off" readOnly="readOnly" /><br />';
    str += '<span style="background-color:white;">&nbsp;&nbsp;&nbsp;</span>';
    str += '<input type="text" style="width:90%;height:50px;border-top-style:none;border-left-style:none;border-right-style:none;border-bottom:1px solid #EEEEEE;color:black;" class="proportion" name="proportion" id="proportion'+i+'" data-provide="typeahead" data-source="" placeholder="请输入项目占比" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目占比\'" />';
    str += '<a id="png'+i+'" style="text-decoration:none;" onclick="addProject('+(i+1)+')"><img src="modules/geogis/img/14.png" /></a>';
    str += '<br />';
    str += '</span>';
    $("#addProject").append(str);
    $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#writeHDiv").height()+70)+"px"});
    typeaheadProjectCode(i);
    typeaheadProjectName(i);
    hideOutLine(i);
    changeProportion(i);
    changeProjectName(i);
    changeProjectCode(i);
}
//项目编码自动补全
function typeaheadProjectCode(i) {
    $('#projectCode'+i).typeahead({
        source: function (query, process) {
            //query是输入的值
            $.post("/modules/lteproject/queryContractReview?random=" + Math.random(), { name: query }, function (datas) {
                var array = [];
                for (var i = 0; i < datas.length; i++) {
                    var data = datas[i];
                    array.push(data.projectCode);
                }
                array = unique(array);
                process(array);
            });
        },
        items : "all",
        updater : function(item) {
            return item;
        },
        afterSelect: function (item) {
        },
        delay: 500
    });
}
//项目名称自动补全
function typeaheadProjectName(i) {
    $('#projectName'+i).typeahead({
        source: function (query, process) {
            //query是输入的值
            $.post("/modules/lteproject/queryContractReview?random=" + Math.random(), { name: query }, function (datas) {
                var array = [];
                for (var i = 0; i < datas.length; i++) {
                    var data = datas[i];
                    array.push(data.projectName);
                }
                array = unique(array);
                process(array);
            });
        },
        items : "all",
        updater : function(item) {
            return item;
        },
        afterSelect: function (item) {
        },
        delay: 500
    });
}
//项目占比自动补全
function typeaheadProportion(i) {
    var proportions = ['10', '20', '25', '30', '40', '50', '60', '70', '75', '80', '90', '100'];
    $('#proportion'+i).typeahead({
        source: proportions,
        items : "all",
        updater : function(item) {
            return item;
        },
        afterSelect: function (item) {
        },
        delay: 500
    });
}
//隐藏外边框
function hideOutLine(i) {
    $("#projectCode"+i).click(function() {
        $("input").css("outline", "none");
    });
    $("#projectName"+i).click(function() {
        $("input").css("outline", "none");
    });
    $("#proportion"+i).click(function() {
        $("input").css("outline", "none");
    });
}

function changeProportion(i) {
    $("#proportion"+i).change(function(){
        var proportion = $("#proportion"+i).val();
        if (proportion.indexOf("%") == -1) {
            $("#proportion"+i).val(proportion + "%");
        }
        $("#proportion"+i).parent().attr("x-proportion", $("#proportion"+i).val());
    });
}

function changeProjectName(i) {
    $("#projectName"+i).change(function(){
        var projectName = $("#projectName"+i).val();
        if (projectName != "") {
            $.ajax({
                url : "/modules/lteproject/validProjectName?projectName=" + projectName,
                type : "post",
                async : false,
                cache : false,
                dataType : 'json',
                success:function(result){
                    if (result.valid) {
                        $("#projectCode"+i).val(result.projectCode);
                        $("#reporter"+i).val(result.reporter);
                        // 设置每个项目的项目编码，项目名称，汇报对象，以便后面的保存操作
                        $("#projectName"+i).parent().attr("x-project-code", result.projectCode).attr("x-project-name", projectName).attr("x-reporter", result.reporter);
                    } else {
                        $("#projectName"+i).parent().attr("x-project-name", projectName);
                    }
                }
            });
        } else {
	        $("#projectName"+i).parent().attr("x-project-name", "");
	    }
    });
}

function changeProjectCode(i) {
    $("#projectCode"+i).change(function(){
        var projectCode = $("#projectCode"+i).val();
        if (projectCode != "" && projectCode.length > 4) {
            $.ajax({
                url : "/modules/lteproject/validProjectCode?projectCode=" + projectCode,
                type : "post",
                async : false,
                cache : false,
                dataType : 'json',
                success:function(result){
                    if (result.valid) {
                        $("#projectName"+i).val(result.projectName);
                        $("#reporter"+i).val(result.reporter);
                        // 设置每个项目的项目编码，项目名称，汇报对象，以便后面的保存操作
                        $("#projectCode"+i).parent().attr("x-project-code", projectCode).attr("x-project-name", result.projectName).attr("x-reporter", result.reporter);
                    } else {
                        $("#projectCode"+i).parent().attr("x-project-code", projectCode);
                    }
                }
            });
        } else {
	        $("#projectCode"+i).parent().attr("x-project-code", "");
	    }
    });
}
// 日志时间的样式设置
function treelistFunc() {
    var date=new Date();
    var today = new Array('周日','周一','周二','周三','周四','周五','周六');
    $("#treelist").mobiscroll().treelist({
        theme: "android-holo-light",
        lang: "zh",
        rows:3,
        headerText: function (valueText) { return "<font size='2px;'>日志时间</font>"; },
        placeholder: date.Format("yyyy年MM月dd日") + " " + today[date.getDay()],
        inputClass: 'logTimeClass',
        height: 35,
        fixedWidth: [200],
        defaultValue: [7],
        closeOnOverlay: true,
        formatResult: function (array) { //返回自定义格式结果
            var logTime = $('#treelist>li').eq(array[0]).children('font').text();
            var logTimeFormat = logTime.substring(0, logTime.indexOf("日")).replace("年", "-").replace("月", "-");
            $("#logTime").val(logTimeFormat);
            return logTime;
        },
        onBeforeShow : function (inst) {
            $(".projectCode").css("background-color", "white");
            $(".projectName").css("background-color", "white");
            $(".reporter").css("background-color", "white");
	        $(".proportion").css("background-color", "white");
            $("#employeeCode").css("background-color", "white");
            $("#context").css("background-color", "white");
        }
    });
}
// 工作性质的样式设置
function naturelistFunc() {
    $("#naturelist").mobiscroll().treelist({
        theme: "android-holo-light",
        lang: "zh",
        rows:3,
        headerText: function (valueText) { return "<font size='2px;'>工作性质</font>"; },
        placeholder: "请选择工作性质",
        inputClass: 'workNatureClass',
        height: 35,
        fixedWidth: [200],
        defaultValue: [0],
        closeOnOverlay: true,
        formatResult: function (array) { //返回自定义格式结果
            var workNature = $('#naturelist>li').eq(array[0]).children('font').text();
            return workNature;
        },
        onSelect: function (valueText, inst) {
            $("#workNature").val(valueText);
        },
        onBeforeShow : function (inst) {
            $(".projectCode").css("background-color", "white");
            $(".projectName").css("background-color", "white");
            $(".reporter").css("background-color", "white");
	        $(".proportion").css("background-color", "white");
            $("#employeeCode").css("background-color", "white");
            $("#context").css("background-color", "white");
        }
    });
}
// 验证输入信息是否正确
function validMessage(employeeCode, logTime, workNature, context, logInfoList) {
    var message = "";
    $.ajax({
        url : "/modules/lteproject/validLogInfo?random=" + Math.random(),
        type : "post",
        data : {
            employeeCode : employeeCode.substring(0, employeeCode.indexOf(" ")),
            logTime : logTime,
            workNature : workNature,
            context : context,
            logInfoList : encode(logInfoList)
        },
        async : false,
        cache : false,
        dataType : 'json',
        success:function(result){
            message = result.message;
        }
    });
    return message;
}
// 验证是否PC版
function isPC(){
    var userAgentInfo = navigator.userAgent;
    var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }
    }
    return flag;
}
// 用于获取多个项目信息（辅助）
$.fn.attributeObject = function() {
    var dom = this.get(0);
    var attr = {};
    if (dom) {
        var length = dom.attributes.length;
        for (var i = 0; i < length; i++) {
            var attribute = dom.attributes[i];
            attr[attribute.nodeName] = attribute.nodeValue;
        }
    }
    return attr;
}
// 用于获取多个项目信息（辅助）
String.prototype.startWith = function(str) {
    if (str == null || str == "" || this.length == 0
        || str.length > this.length) {
        return false;
    }
    if (this.substr(0, str.length) == str) {
        return true;
    } else {
        return false;
    }
    return true;
}
// 字符串设置为驼峰样式
function capitalize(source) {
    return source.substring(0, 1).toUpperCase() + source.substring(1);
};
// 获取多个项目信息
function collectData(expression) {
    var employeeCode = $("#employeeCode").val();
    var employee = $("#employee").val();
    var logTime = $("#logTime").val();
    var workNature = $("#workNature").val();
    var context = $("#context").val();
    var datalist = [];

    $(expression).each(function() {
        var row = $(this);
        var data = row.attributeObject();
        var obj = {};
        for (var prop in data) {
            if (prop.startWith("x-")) {
                var name = prop.replace("x-", "");
                var ns = name.split("-");
                name = ns[0];
                for (var i = 1; i < ns.length; i++) {
                    name += capitalize(ns[i]);
                }
                obj[name] = data[prop];
            }
        }
        obj.employeeCode = employeeCode.substring(0, employeeCode.indexOf(" "));
        obj.employee = employee;
        obj.logTime = logTime;
        obj.workNature = workNature;
        obj.context = context.replace("'","''");
        obj.createTime = new Date().Format("yyyy-MM-dd");

        datalist.push(obj);
    });
    return datalist;
}
// 获取项目信息的个数
function collectDataCount(expression) {
    var i = 0;
    $(expression).each(function() {
        i++;
    });
    return i;
}
// 对多个项目信息进行编码
function encode(o) {
    var useHasOwn = !!{}.hasOwnProperty;
    if (typeof o == "undefined" || o === null) {
        return "null";
    } else if ($.isArray(o)) {
        return encodeArray(o);
    } else if (isDate(o)) {
        return encodeDate(o);
    } else if (typeof o == "string") {
        return encodeString(o);
    } else if (typeof o == "number") {
        return isFinite(o) ? String(o) : "null";
    } else if (typeof o == "boolean") {
        return String(o);
    } else {
        var a = ["{"], b, i, v;
        for (i in o) {
            if (!useHasOwn || o.hasOwnProperty(i)) {
                v = o[i];
                switch (typeof v) {
                    case "undefined" :
                    case "function" :
                    case "unknown" :
                        break;
                    default :
                        if (b) {
                            a.push(',');
                        }
                        a.push(this.encode(i), ":", v === null ? "null" : this
                            .encode(v));
                        b = true;
                }
            }
        }
        a.push("}");
        return a.join("");
    }
}
// 对多个项目信息进行编码（辅助）
function isDate(v) {
    return v && typeof v.getFullYear == "function";
}
// 对多个项目信息进行编码（辅助）
function encodeArray(o) {
    var a = ["["], b, i, l = o.length, v;
    for (i = 0; i < l; i += 1) {
        v = o[i];
        switch (typeof v) {
            case "undefined" :
            case "function" :
            case "unknown" :
                break;
            default :
                if (b) {
                    a.push(',');
                }
                a.push(v === null ? "null" : encode(v));
                b = true;
        }
    }
    a.push("]");
    return a.join("");
}
// 对多个项目信息进行编码（辅助）
function pad(value, length) {
    value = String(value);
    length = parseInt(length,10) || 2;
    while (value.length < length)  { value = '0' + value; }
    return value;
}
// 对多个项目信息进行编码（辅助）
function encodeDate(o) {
    return '"' + o.getFullYear() + "-" + pad(o.getMonth() + 1) + "-"
        + pad(o.getDate()) + "T" + pad(o.getHours()) + ":"
        + pad(o.getMinutes()) + ":" + pad(o.getSeconds()) + '"';
}
// 对多个项目信息进行编码（辅助）
function encodeString(s) {
    var m = {
        "\b" : '\\b',
        "\t" : '\\t',
        "\n" : '\\n',
        "\f" : '\\f',
        "\r" : '\\r',
        '"' : '\\"',
        "\\" : '\\\\'
    };

    if (/["\\\x00-\x1f]/.test(s)) {
        return '"' + s.replace(/([\x00-\x1f\\"])/g, function(a, b) {
            var c = m[b];
            if (c) {
                return c;
            }
            c = b.charCodeAt();
            return "\\u00" + Math.floor(c / 16).toString(16)
                + (c % 16).toString(16);
        }) + '"';
    }
    return '"' + s + '"';
}
