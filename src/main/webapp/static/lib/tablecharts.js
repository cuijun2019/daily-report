(function () {

    /**
     * User: chenkangqiang
     * Date: 13-8-20
     * 图表控件.
     * tableDatao:图表数据源List<hashmap>
     * xFieldName:x轴字段
     * yFieldNames:y轴字段  如yFieldNames=[{fieldName:'gCount',type:'column',name:'G网流量',yAxisIndex:1},{fieldName:'tCount',type:'column',name:'T网流量'}]
     name是做为图例名，并且tipMap内存这个name时，提示标签才突出显示。
     * highchartArg:  hightchart控件参数 如 { title: {text: '上网时段分布'}, yAxis: {title: {text: '流量'}}}
     * exArg: 扩散参数 如 exArg= {tipMap:[{tInteral:'时段'},{totalBytes:'总流量'},{gBytes:'G网流量'},{tBytes:'T网流量'}], topN: {top: 10, destField:'gBytes', sort: 'desc', isShowOther: true}}
     *
     * 必填参数：tableData, xFieldName, yFieldNames
     * 选填参数：highchartArg, exArg
     *
     * 返回highcharts对象
     */


    $.fn.tableCharts = function (tableData, xFieldName, yFieldNames, highchartArg, exArg) {
        var xCategories = [];
        var yDatas = [];
        var series = [yFieldNames.length];

        var thisOption = $.extend(
            {topN: {top: -1, destField: xFieldName, sort: 'desc', isShowOther: true}}
            , exArg);
        //判断是否进行topN
        var topNtag = 0;
        if ((thisOption.topN.destField == xFieldName || !(thisOption.topN.destField)) && thisOption.topN.top > 0) {
            topNtag = 1;
        }// 如果为x轴字段，则直接取数据源的前n条数据
        else if (yFieldNames.length == 1 && thisOption.topN.destField == yFieldNames[0].fieldName && thisOption.topN.top > 0) {
            topNtag = 2;
        } //如果为y轴字段，并且y轴有且只有一组图

        //拿到y轴数据源
        for (var j = 0; j < yFieldNames.length; j++) {
            var fieldInfo = yFieldNames[j];
            var temp = [];
            for (var i = 0; i < tableData.length; i++) {
                var d = tableData[i];
                temp.push([d[xFieldName] + "", d[fieldInfo.fieldName]]);    //要转为字符串，格式化时：{point.name}才有值
            }
            //需要top过滤时
            if (topNtag != 0) {
                if (topNtag == 2) {
                    //排序
                    temp.sort(function (a, b) {
                        if (thisOption.topN.sort == "desc") {
                            return a[1] < b[1];
                        } else {
                            return a[1] > b[1];
                        }
                    })
                }
                //取前N条
                var topArray = $.grep(temp, function (n, i) {
                    return i < thisOption.topN.top;
                });
                //取余下的
                if (thisOption.topN.isShowOther) {
                    var otherArray = $.grep(temp, function (n, i) {
                        return i >= thisOption.topN.top;
                    });
                    var otherValue = 0;
                    $.each(otherArray, function (i, value) {
                        otherValue += value[1];
                    });
                    topArray.push(["其它", otherValue]);
                }
                temp = topArray;
            }
            yDatas.push(temp);
        }

        //拿到x标签
        for (var i = 0; i < yDatas[0].length; i++) {
            var d = yDatas[0][i];
            xCategories.push(d[0]);
        }

        for (var z = 0; z < yFieldNames.length; z++) {
            var serie = $.extend({
                yAxis: 0,
                dataLabels: {color: '#959595', connectorColor: '#959595'}
            }, {type: yFieldNames[z].type, name: yFieldNames[z].name, data: yDatas[z], yAxis: yFieldNames[z].yAxisIndex})
            series[z] = serie;
        }

        //如果外围参数不存在xAxis.categories,则为xAxis.categories添加默认值
        if (highchartArg && highchartArg.xAxis && !highchartArg.xAxis.categories) {
            $.extend(highchartArg.xAxis, {categories: xCategories});
        }
        var seriesTemp = {series: series};
        var chartOption = $.extend({
            chart: {
                renderTo:this.selector.replace(/#/,''),
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {text: ''},
            xAxis: {
                categories: xCategories
            },
            yAxis: [
                {title: {text: ''}}
            ],
            legend: {
                align: 'right',
                x: -70,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function () {
                    return formartTools(tableData, xFieldName, this.point.name, thisOption.tipMap, this.series.name, this.y)
                }
            },
            plotOptions: {
                column: {
                    allowPointSelect: false
                },
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name}</b>: {y:.2f} %'
                    }
                }
            },
            credits: {//右下角的文本
                enabled: false
            }
        }, highchartArg, seriesTemp);
        return new Highcharts.Chart(chartOption);
    };

    function formartTools(tabledata, xFile, xValue, tooltipMap, destField, y) {
        for (var i in tabledata) {
            if (tabledata[i][xFile] == xValue) {
                var d = tabledata[i];
                var ret = "";
                for (var n = 0; n < tooltipMap.length; n++) {
                    var r = tooltipMap[n]
                    for (var z in r) {
                        if (destField == r[z]) {
                            ret += "<b>" + r[z] + "：" + d[z] + "</b>" + "<br>";
                        }
                        else {
                            ret += r[z] + "：" + d[z] + "<br>";
                        }
                    }
                }
                return ret;
            }
            else if ((xValue == "其它")) {      //isShowOther产生的其它
                return "<b>其它：" + y.toFixed(2) + "</b>" + "<br>";
            }
        }
    }

}
    ()
    )
;
