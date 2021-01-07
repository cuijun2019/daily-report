var appraiseInfo = {
    action : {
		queryAppraiseEmployee : '/modules/project/queryAppraiseEmployee',
		queryEmployeeLog : '/modules/project/queryEmployeeLog'
    },

    init : function() {
		this.normalCount = 0;
		this.lateCount = 0;
		this.notCount = 0;
        this.initAppraise("", "", "");
		this.initCharts();
        this.initEvents();

		var preDate = new Date((+new Date())-1*24*3600*1000).Format("yyyy-MM-dd");
		$("#date1").val(preDate);
		$("#date2").val(preDate);
		$('#appraiseStartDate').val(preDate);
		$('#appraiseEndDate').val(preDate);
    },

	config : {
		appraiseTemplate : '<table style="width:100%;color:#313131;" border="0">'
				+ '        <tr style="height:25px;">'
				+ '                <td style="text-align:center;width:33.4%;">'
				+ '                        序号'
				+ '                </td>'
				+ '                <td style="text-align:center;width:33.3%;">'
				+ '                        员工工号'
				+ '                </td>'
				+ '                <td style="text-align:center;width:33.3%;">'
				+ '                        员工姓名'
				+ '                </td>'
				+ '        </tr>'
				+ '			{0}'
				+ '		</table>',
		appraiseContextTemplate : '<tr id="employeeLog_{0}" style="background-color:{1};height:25px;" onclick="appraiseInfo.events.employeeLog(\'{3}\',\'{5}\',\'{6}\',\'{7}\');">'
				+ '            <td style="text-align:center;">'
				+ '                    {2}'
				+ '            </td>'
				+ '            <td style="text-align:center;">'
				+ '                    {3}'
				+ '            </td>'
				+ '            <td style="text-align:center;">'
				+ '                    {4}'
				+ '            </td>'
				+ '    </tr>'
	},

	initAppraise : function(startDate, endDate, type) {
		$("#appraiseSpan").show();
		$("#employeeLog").hide();
		$('#searchName').val("");
		var that = this;
		$.ajax({
			url : this.action.queryAppraiseEmployee,
			type:"post",
			data : {
				startDate : startDate,
				endDate : endDate,
                type : type
			},
			async:false,
			cache:false,
			dataType : 'json',
			success:function(datas){
				that.normalCount = 0;
				that.lateCount = 0;
				that.notCount = 0;

				var data;
				var employeeCode = "";
				var employee = "";
				var employeeType= "";
				var normalStr = "";
				var lateStr = "";
				var notStr = "";
				var row = "";
				$("#normalId").empty();
				for (var i = 0; i < datas.length; i++) {
					data = datas[i];
					employeeCode = data.employeeCode;
					employee = data.employee;
					employeeType = data.type;
					if (employeeType == "normal") {
						++that.normalCount;
						normalStr += String.format(that.config.appraiseContextTemplate, i, "#90d4e7", i+1, employeeCode, employee, startDate, endDate, type);
					} else if (employeeType == "late") {
						++that.lateCount;
						lateStr += String.format(that.config.appraiseContextTemplate, i, "#c696e2", i+1, employeeCode, employee, startDate, endDate, type);
					} else {
						++that.notCount;
						notStr += String.format(that.config.appraiseContextTemplate, i, "#feadac", i+1, employeeCode, employee, startDate, endDate, "not");
					}
				}

				if (type == "normal") {
					row = String.format(that.config.appraiseTemplate, normalStr);
				} else if (type == "late") {
					row = String.format(that.config.appraiseTemplate, lateStr);
				} else if (type == "not") {
					row = String.format(that.config.appraiseTemplate, notStr);
				} else {
					row = String.format(that.config.appraiseTemplate, notStr + lateStr + normalStr);
				}
                var element = $(row);
				$("#normalId").append(element);
				
				historyInfo.heightAdaption("appraise");
			}
		});
	},

	initCharts : function() {
		var that = this;
		//饼状图
		var categories = ['正常', '迟交', '缺交'],
			data = [{
				drilldown: {
					name: '',
					categories: ['正常', '迟交', '缺交'],
					data: [that.normalCount, that.lateCount, that.notCount]   //数据，即this.y
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
								that.initAppraise($('#appraiseStartDate').val(), $('#appraiseEndDate').val(), "normal");
							}
							if ("迟交" == name) {
								that.initAppraise($('#appraiseStartDate').val(), $('#appraiseEndDate').val(), "late");
							}
							if ("缺交" == name) {
								that.initAppraise($('#appraiseStartDate').val(), $('#appraiseEndDate').val(), "not");
							}

							historyInfo.heightAdaption("appraise");
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
	},

	initEvents : function() {
        var that = this;
		$("#clickSearch").attr("onclick", "appraiseInfo.events.queryEmployeeLog()");
		$("#searchName").attr("onKeyUp", "appraiseInfo.events.showBtn()");
		$("#appraiseClearBtn").attr("onclick", "appraiseInfo.events.clearData()");

        $("input").click(function() {
            $("input").css("outline", "none");
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
				that.initAppraise($('#appraiseStartDate').val(), $('#appraiseEndDate').val(), "");
				that.initCharts();
				$("#listId").html("");
				return true;
			},
			inputTrigger : 'input_trigger1',
			theme : 'ta'
		});

        $('#staffStartDate').change(function() {
            that.initAppraise($('#appraiseStartDate').val(), $('#appraiseEndDate').val(), "");
			that.initCharts();
			$("#listId").html("");
        });

        $('#staffEndDate').change(function() {
            that.initAppraise($('#appraiseStartDate').val(), $('#appraiseEndDate').val(), "");
			that.initCharts();
			$("#listId").html("");
        });
    },

	goback : function(startDate, endDate) {
		this.initAppraise(startDate, endDate, "");
		this.initCharts();
	},

	events : {
		employeeLog : function(employeeCode, startDate, endDate, type) {
			$("#appraiseSpan").hide();
			$("#employeeLog").show();
			$("#appraiseBackLi").attr("onclick", "appraiseInfo.goback('" + startDate + "', '" + endDate + "')");

			$.ajax({
				url : appraiseInfo.action.queryEmployeeLog,
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
					$("#employeeLogSpan").empty();
					historyInfo.showHistoryLog(datas, "appraise");
				}
			});
			historyInfo.heightAdaption("appraise");
		},

		queryEmployeeLog : function() {
			$("#normalId tr").show();
			var index = $.trim($('#searchName').val().toString());//去掉两头空格
			if(index != ""){
				//选择包含文本框值的所有加上focus类样式，并把它（们）移到ul的最前面
				$("#normalId tr").not($("#normalId td:contains('" + index + "')").parent()).hide();
				$("#normalId tr:first").eq(0).show();
			}

			historyInfo.heightAdaption("appraise");
		},

		showBtn : function() {
			if ($("#searchName").val().length > 0) {
				$("#appraiseClearBtn").val("x");
			} else {
				$("#appraiseClearBtn").val("");
			}
		},

		clearData : function() {
			$("#searchName").val("");
			$("#appraiseClearBtn").val("");
		}
	}
};
