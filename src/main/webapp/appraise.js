function initHighCharts(startDate, endDate) {
    var normalCount = 0;
    var lateCount = 0;
    var notCount = 0;
    $("#appraiseSpan").show();
    $("#employeeLog").hide();
    $.ajax({
        url:"/modules/lteproject/queryAppraiseEmployee",
        type:"post",
        data : {
            startDate : startDate,
            endDate : endDate
        },
        async:false,
        cache:false,
        dataType : 'json',
        success:function(datas){
            var data;
            var normalStr = "";
            var lateStr = "";
            var notStr = "";
            var employeeCode = "";
            var employee = "";
            var str = '<table style="width:100%;color:#313131;" border="0">';
            str += '        <tr style="height:25px;">';
            str += '                <td style="text-align:center;width:33.4%;">';
            str += '                        序号';
            str += '                </td>';
            str += '                <td style="text-align:center;width:33.3%;">';
            str += '                        员工工号';
            str += '                </td>';
            str += '                <td style="text-align:center;width:33.3%;">';
            str += '                        员工姓名';
            str += '                </td>';
            str += '        </tr>';
            for (var i = 0; i < datas.length; i++) {
                data = datas[i];
                employeeCode = data.employeeCode;
                employee = data.employee;
                if (data.type == "normal") {
                    ++normalCount;
                    normalStr += '    <tr style="background-color:#90d4e7;height:25px;" onclick="employeeLog(\'' + employeeCode + '\',\'' + startDate + '\',\'' + endDate + '\',\'normal\');">';
                    normalStr += '            <td style="text-align:center;">';
                    normalStr += '                    ' + (i+1);
                    normalStr += '            </td>';
                    normalStr += '            <td style="text-align:center;">';
                    normalStr += '                    ' + employeeCode;
                    normalStr += '            </td>';
                    normalStr += '            <td style="text-align:center;">';
                    normalStr += '                    ' + employee;
                    normalStr += '            </td>';
                    normalStr += '    </tr>';
                } else if (data.type == "late") {
                    ++lateCount;
                    lateStr += '    <tr style="background-color:#c696e2;height:25px;" onclick="employeeLog(\'' + employeeCode + '\',\'' + startDate + '\',\'' + endDate + '\',\'late\');">';
                    lateStr += '            <td style="text-align:center;">';
                    lateStr += '                    ' + (i+1);
                    lateStr += '            </td>';
                    lateStr += '            <td style="text-align:center;">';
                    lateStr += '                    ' + employeeCode;
                    lateStr += '            </td>';
                    lateStr += '            <td style="text-align:center;">';
                    lateStr += '                    ' + employee;
                    lateStr += '            </td>';
                    lateStr += '    </tr>';
                } else {
                    ++notCount;
                    notStr += '    <tr style="background-color:#feadac;height:25px;" onclick="employeeLog(\'' + employeeCode + '\',\'' + startDate + '\',\'' + endDate + '\',\'not\');">';
                    notStr += '            <td style="text-align:center;">';
                    notStr += '                    ' + (i+1);
                    notStr += '            </td>';
                    notStr += '            <td style="text-align:center;">';
                    notStr += '                    ' + employeeCode;
                    notStr += '            </td>';
                    notStr += '            <td style="text-align:center;">';
                    notStr += '                    ' + employee;
                    notStr += '            </td>';
                    notStr += '    </tr>';
                }
            }
            $("#normalId").html(str + notStr + lateStr + normalStr + '</table>');
            $("#normalId").hide();
            $("#lateId").hide();
            $("#notId").hide();

            showEmployee("normal");
        }
    });

    //饼状图
    var categories = ['正常', '迟交', '缺交'],
        data = [{
            drilldown: {
                name: '',
                categories: ['正常', '迟交', '缺交'],
                data: [normalCount, lateCount, notCount]   //数据，即this.y
            }
        }];

    // 创建数组
    var fuhuiData = [];
    var percentData = [];
    for (var i = 0; i < data.length; i++) {
        // 添加名称
        fuhuiData.push({
            name: categories[i],
            y: data[i].y
        });

        // 添加百分比
        for (var j = 0; j < data[i].drilldown.data.length; j++) {
            var brightness = 0.2 - (j / data[i].drilldown.data.length) / 5 ;
            percentData.push({
                name: data[i].drilldown.categories[j],
                y: data[i].drilldown.data[j]
            });
        }
    }

    // 创建图表
    $('#container').highcharts({
        chart: {
            marginTop: -20,
            marginBottom: -10,
            type: 'pie'   //图表的类型
        },
        title: {  //设置标题并将标题置于环形图表中间
            //text: '<span style="font-size:15px;font-family:Arial;color:#606060;" >100</span><span style="color:#606060;"> %</span>'+'<br><span style="font-size:18px;color:#606060;">通产日志</span>',
            text: '',
            verticalAlign: 'middle'
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        plotOptions: {
            pie: {
                size: '50%',
                innerSize: '40%',   //配置环形大小
                shadow: false,
                center: ['50%', '50%'],  //水平和垂直方向居中
                colors: [            //设置饼状图的颜色
                    '#90D4E7',  //第一个颜色
                    '#C696E2',  //第二个颜色
                    '#FEADAC'  //第三个颜色
                ],
                dataLabels: {
                    //connectorColor: '#f00',  //设置连接线的颜色
                    style: {  //设置标识文字的样式
                        color: '#424242',
                        fontSize: '18px',
                        fontWeight: 'normal'   //字体不加粗
                    }
                },
                showInLegend: true,
                cursor: 'pointer',
                events: {
                    click: function(e) {
                        var name = e.point.name;
                        if ("正常" == name) {
                            $("#normalId").show();
                            $("#lateId").hide();
                            $("#notId").hide();
                        }
                        if ("迟交" == name) {
                            $("#normalId").hide();
                            $("#lateId").show();
                            $("#notId").hide();
                        }
                        if ("缺交" == name) {
                            $("#normalId").hide();
                            $("#lateId").hide();
                            $("#notId").show();
                        }

                        // 补上空白的空间，以可以左右滑动
                        $("#appraiseSurplus").css("height","0px");
                        if (($("body").height()-$("#appraiseHDiv").height()-70) > 0) {
                            $("#appraiseSurplus").css("height", ($("body").height()-$("#appraiseHDiv").height()-70) + "px");
                        }
                        // 自适应高度
                        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#appraiseHDiv").height()+70)+"px"});
                    }
                }
            }
        },
        legend: {//控制图例显示位置
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'top',
            borderWidth: 0
        },
        series: [{
            name: '人数',  //数据列名
            data: percentData,
            dataLabels: {
                formatter: function() {
                    // display only if larger than 1
                    //return this.y > 1 ? '<b>'+ this.point.name +':</b> '+ this.y +'%'  : null;    //这串代码设置了加粗
                    return this.y > 1 ? '<span style="font-size:10px;">'+ this.point.name +': '+ this.y +'人</span>' : null;
                }
            }
        }],
        credits: {
            enabled:false // 默认值，如果想去掉版权信息Highcharts.com，设置为false即可
        }
    });

    //设置饼状图中间文字的上下间隔
    $(".highcharts-title").find("tspan").last().attr("dy",43);
}

function showEmployee(type) {
    if (type == "normal") {
        $("#normalId").show();
        $("#lateId").hide();
        $("#notId").hide();
        $("#normalTd").css({"background-color":"#FFFFFF","color":"#000000"});
        $("#lateTd").css({"background-color":"#EEEEEE","color":"#959595"});
        $("#notTd").css({"background-color":"#EEEEEE","color":"#959595"});
    }
    if (type == "late") {
        $("#normalId").hide();
        $("#lateId").show();
        $("#notId").hide();
        $("#normalTd").css({"background-color":"#EEEEEE","color":"#959595"});
        $("#lateTd").css({"background-color":"#FFFFFF","color":"#000000"});
        $("#notTd").css({"background-color":"#EEEEEE","color":"#959595"});
    }
    if (type == "not") {
        $("#normalId").hide();
        $("#lateId").hide();
        $("#notId").show();
        $("#normalTd").css({"background-color":"#EEEEEE","color":"#959595"});
        $("#lateTd").css({"background-color":"#EEEEEE","color":"#959595"});
        $("#notTd").css({"background-color":"#FFFFFF","color":"#000000"});

    }
    // 补上空白的空间，以可以左右滑动
    $("#appraiseSurplus").css("height","0px");
    if (($("body").height()-$("#appraiseHDiv").height()-70) > 0) {
        $("#appraiseSurplus").css("height", ($("body").height()-$("#appraiseHDiv").height()-70) + "px");
    }
    // 自适应高度
    $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#appraiseHDiv").height()+70)+"px"});
}

function employeeLog(employeeCode, startDate, endDate, type) {
    $.ajax({
        url : "/modules/lteproject/queryEmployeeLog",
        type : "post",
        data : {
            employeeCode : employeeCode,
            startDate : startDate,
            endDate : endDate,
            type : type
        },
        async : false,
        cache : false,
        dataType : 'json',
        success:function(datas){
            $("#employeeLog").html("");
            var data;
            var context = "";
            var str = '<li style="background-color:rgb(170,170,170);height:auto;border-bottom:1px solid #EEEEEE;padding-left:10px;padding-top:5px;padding-bottom:5px;">';
            str += '	<a style="text-decoration: none;" onclick="initHighCharts(\'' + startDate +  '\',\'' + endDate + '\')">';
            str += '	<table width="100%" border="0">';
            str += '		<tr>';
            str += '			<td style="width:85%;">';
            str += '				<font color="#FFFFFF" style="font-weight:normal;">< 返回</font>';
            str += '			</td>';
            str += '		</tr>';
            str += '	</table>';
            str += '	</a>';
            str += '</li>';
            for (var i = 0; i < datas.length; i++) {
                data = datas[i];
                context = data.context;
                str += '<div id="personlog_' + i + '" style="height:auto;border-bottom:1px solid #EEEEEE;">';
                str += '    <table style="width:95%;margin-left:10px;margin-top:5px;margin-bottom:5px;" border="0">';
                str += '		<tr>';
                str += '			<td colspan="2" style="font-weight:bold;">';
                str += '			' + data.employeeCode + '&nbsp;&nbsp;&nbsp;' + data.employee + '&nbsp;&nbsp;&nbsp;' + data.logTime.replace("-","/").replace("-","/").replace(" 00:00:00", "");
                str += '			</td>';
                str += '		</tr>';
                str += '		<tr>';
                str += '			<td colspan="2">';
                str += '			' + data.projectCode + '&nbsp;&nbsp;&nbsp;&nbsp;' + data.projectName;
                str += '			</td>';
                str += '		</tr>';
                if (context.length > 20) {
                    str += '		<tr>';
                    str += '			<td style="width:90%;">';
                    str += '			<span id="context_' + i + '" style="float:left;color:#757575;">' + context.substring(0, 20) + "</span>";
                    str += '			</td>';
                    str += '			<td style="vertical-align:bottom;">';
                    str += '				<a id="expand_' + i + '" style="text-decoration: none;cursor:pointer;" onclick="expand(' + i + ',\'' + context.replaceAll("\n","<br>") + '\',\'appraise\')">展开</a>';
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
            $("#appraiseSpan").hide();
            $("#employeeLog").show();
            $("#employeeLog").html(str);
        }
    });
    // 补上空白的空间，以可以左右滑动
    $("#appraiseSurplus").css("height","0px");
    if (($("body").height()-$("#appraiseHDiv").height()-70) > 0) {
        $("#appraiseSurplus").css("height", ($("body").height()-$("#appraiseHDiv").height()-70) + "px");
    }
    // 自适应高度
    $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#appraiseHDiv").height()+70)+"px"});
}

function queryEmployeeLog(){
    $("#normalId tr").show();
    var index = $.trim($('#searchName').val().toString());//去掉两头空格
    if(index == ""){
        // 补上空白的空间，以可以左右滑动
        $("#appraiseSurplus").css("height","0px");
        if (($("body").height()-$("#appraiseHDiv").height()-70) > 0) {
            $("#appraiseSurplus").css("height", ($("body").height()-$("#appraiseHDiv").height()-70) + "px");
        }
        // 自适应高度
        $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#appraiseHDiv").height()+70)+"px"});
        return false;
    }
    //选择包含文本框值的所有加上focus类样式，并把它（们）移到ul的最前面
    $("#normalId tr").not($("#normalId td:contains('" + index + "')").parent()).hide();
    $("#normalId tr:first").eq(0).show();

// 补上空白的空间，以可以左右滑动
    $("#appraiseSurplus").css("height","0px");
    if (($("body").height()-$("#appraiseHDiv").height()-70) > 0) {
        $("#appraiseSurplus").css("height", ($("body").height()-$("#appraiseHDiv").height()-70) + "px");
    }
// 自适应高度
    $("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#appraiseHDiv").height()+70)+"px"});
}
