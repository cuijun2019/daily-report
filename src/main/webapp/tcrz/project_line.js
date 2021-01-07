var projectLineInfo = {
    action : {
		queryProjectLine : '/modules/project/queryProjectLine',
		queryProjectByLine : '/modules/project/queryProjectByLine',
		queryLineNameByCode : '/modules/project/queryLineNameByCode',
		queryOwnLogInfo : '/modules/project/queryOwnLogInfo'
    },

    init : function() {
        this.hide = true;
		this.projectLineName = "";
		this.projectCode = "";
        this.initProjectLine();
        this.initEvents();
	},

	config : {
		projectLineTemplate : '<li style="height:auto;border-bottom:1px solid #EEEEEE;padding-left:20px;padding-top:10px;padding-bottom:10px;">'
				+ '	<table id="projectLine_{0}" width="100%" border="0">'
				+ '		<tr>'
				+ '			<td style="width:25%;">'
				+ '				<font color="#757575" style="font-weight:normal;">{1}</font>'
				+ '			</td>'
				+ '			<td style="width:60%;">'
				+ '				<font color="#757575" style="font-weight:normal;">共有{2}个项目</font>'
				+ '			</td>'
				+ '			<td style="width:15%;text-align:center;">'
				+ '				<font color="#757575">></font>'
				+ '			</td>'
				+ '		</tr>'
				+ '	</table>'
				+ '</li>',
		projectTemplate : '<li style="height:auto;border-bottom:1px solid #EEEEEE;padding-left:20px;padding-top:10px;padding-bottom:10px;">'
				+ '	<table id="projectLog_{0}" width="100%" border="0">'
				+ '		<tr>'
				+ '			<td style="width:85%;">'
				+ '				<font color="#757575" style="font-weight:normal;">{1}&nbsp;&nbsp;&nbsp;{2}</font>'
				+ '			</td>'
				+ '			<td style="width:15%;text-align:center;">'
				+ '				<font color="#757575">></font>'
				+ '			</td>'
				+ '		</tr>'
				+ '	</table>'
				+ '</li>',
		noDataTemplate : '<div style="height:30px;padding-top:5px;border-bottom:1px solid #EEEEEE;text-align:center;color:#757575;font-weight:bold;">'
				+ '        该项目下没工作日志'
				+ '    </div>'
	},

	initEvents : function() {
        var that = this;
		$("#btnSearch").attr("onclick", "projectLineInfo.events.queryData()");
		$("#searchData").attr("onKeyUp", "projectLineInfo.events.showBtn()");
		$("#clearBtn").attr("onclick", "projectLineInfo.events.clearData()");

        $("input").click(function() {
            $("input").css("outline", "none");
        });

        $('#lineStartDate').datetimepicker({
            language:  'zh-CN',
            minView : "month",
            format : 'yyyy-mm-dd',
            clearBtn: true,
            autoclose : 1
        }).on("show", function() {
                that.hide = false;
            }).on("hide", function() {
                that.hide = true;
            });

        $('#lineEndDate').datetimepicker({
            language:  'zh-CN',
            minView : "month",
            format : 'yyyy-mm-dd',
            clearBtn: true,
            autoclose : 1
        }).on("show", function() {
                that.hide = false;
            }).on("hide", function() {
                that.hide = true;
            });

        $(".form-control").on("click", function() {
            if (that.hide) {
                $('#lineStartDate').trigger("blur");
                $('#lineEndDate').trigger("blur");
            }
        });

        $('#lineStartDate').change(function() {
            that.events.projectLog(that.projectCode);
        });

        $('#lineEndDate').change(function() {
            that.events.projectLog(that.projectCode);
        });
    },

	initProjectLine : function() {
		$("#backLi").hide();
		$("#projectLineDiv").show();
		$("#staffDiv").hide();

		$("#searchLi").hide();
		$("#lineDate").hide();
		$("#projectLineField").show();
		$("#projectField").hide();
		$("#projectDetailField").hide();
		// 返回时，清空查询条件
		$("#searchData").val("");

		var that = this;
		$.ajax({
			url : this.action.queryProjectLine,
			type : "post",
			async : false,
			cache : false,
			dataType : 'json',
			success:function(datas){
				var data = "";
				var row = "";
                var element = "";
				var lineName = "";
				var count = "";
				$("#projectLine").empty();
				for (var i = 0; i < datas.length; i++) {
					data = datas[i];
					lineName = data.lineName;
					count = data.count;
					row = String.format(that.config.projectLineTemplate, i, lineName, count);
					element = $(row);
					$("#projectLine").append(element);
					$("#projectLine_" + i).attr("onclick", "projectLineInfo.events.queryProjectByLine('" + lineName + "', '')");
				}
			}
		});
		historyInfo.heightAdaption("staff");
	},

	events : {
		queryProjectByLine : function (lineName, searchData) {
			$("#backLi").show();

			$("#searchLi").show();
			$("#lineDate").hide();
			$("#projectLineField").hide();
			$("#projectField").show();
			$("#projectDetailField").hide();
			$("#backLi").attr("onclick", "projectLineInfo.initProjectLine()");
			projectLineInfo.projectLineName = lineName;
			// 返回时，重新设置时间
			$("#lineStartDate").val(writeInfo.currentOneDate);
			$("#lineEndDate").val(writeInfo.currentDate);
			if (searchData == "") {
				$("#searchData").val("");
			}

			$.ajax({
				url : projectLineInfo.action.queryProjectByLine,
				type : "post",
				data : {
					lineName : lineName,
					searchData : searchData
				},
				async : false,
				cache : false,
				dataType : 'json',
				success:function(datas){
					var data = "";
					var projectCode = "";
					var projectName = "";
					var row = "";
					var element = "";
					$("#project").empty();
					for (var i = 0; i < datas.length; i++) {
						data = datas[i];
						projectCode = data.projectCode;
						projectName = data.projectName;
						row = String.format(projectLineInfo.config.projectTemplate, i, projectCode, projectName);
						element = $(row);
						$("#project").append(element);
						$("#projectLog_" + i).attr("onclick", "projectLineInfo.events.projectLog('" + projectCode + "', '" + projectName + "')");
					}
				}
			});
			historyInfo.heightAdaption("staff");
		},

		projectLog : function(projectCode, projectName) {
			$("#searchLi").hide();
			$("#lineDate").show();
			$("#projectLineField").hide();
			$("#projectField").hide();
			$("#projectDetailField").show();
			$("#backLi").attr("onclick", "projectLineInfo.events.queryProjectByLine('" + projectLineInfo.projectLineName + "', '')");
			$(".projectNameSpan").text(projectName);
			projectLineInfo.projectCode = projectCode;
			var lineStartDate = $("#lineStartDate").val();
			var lineEndDate = $("#lineEndDate").val();
			if (lineStartDate == "") {
				$("#lineStartDate").val(writeInfo.currentOneDate);
			}
			if (lineEndDate == "") {
				$("#lineEndDate").val(writeInfo.currentDate);
			}

			$.ajax({
				url : projectLineInfo.action.queryOwnLogInfo,
				type : "post",
				data : {
					projectCode : projectCode,
					startDate : $("#lineStartDate").val(),
					endDate : $("#lineEndDate").val()
				},
				async : false,
				cache : false,
				dataType : 'json',
				success:function(datas){
					$("#projectDetailField").empty();
					if (datas=="") {
						var element = $(projectLineInfo.config.noDataTemplate);
						$("#projectDetailField").append(element);
						return;
					}
					historyInfo.showHistoryLog(datas, "line");
				}
			});

			historyInfo.heightAdaption("staff");
		},

		queryData : function() {
			projectLineInfo.events.queryProjectByLine(projectLineInfo.projectLineName, $('#searchData').val());
			$('#btnSearch').focus();
			historyInfo.heightAdaption("staff");
		},

		showBtn : function() {
			if ($("#searchData").val().length > 0) {
				$("#clearBtn").val("x");
			} else {
				$("#clearBtn").val("");
			}
		},

		clearData : function() {
			$("#searchData").val("");
			$("#clearBtn").val("");
		}
	}
};
