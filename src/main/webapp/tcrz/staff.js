var staffInfo = {
    action : {
		queryEmployeeProject : '/modules/lteproject/queryEmployeeProject',
		queryOwnLogInfo : '/modules/lteproject/queryOwnLogInfo'
    },

    init : function() {
		$("#staffStartDate").val(writeInfo.currentOneDate);
		$("#staffEndDate").val(writeInfo.currentDate);

        this.hide = true;
        this.initStaff();
        this.initEvents();
    },

    config : {
        staffTemplate : '<li style="height:auto;border-bottom:1px solid #EEEEEE;padding-left:20px;padding-top:10px;padding-bottom:10px;">'
                + '	<table id="staff_{0}" width="100%" border="0">'
                + '		<tr>'
                + '			<td style="width:85%;">'
                + '				<font color="#757575" style="font-weight:normal;">{1} {2} 交了{3}条日志<br />{4} {5}</font>'
                + '			</td>'
                + '			<td style="width:15%;text-align:center;">'
                + '				<font color="#757575">></font>'
                + '			</td>'
                + '		</tr>'
                + '	</table>'
                + '</li>',
        noDataTemplate : '<div style="height:30px;padding-top:5px;border-bottom:1px solid #EEEEEE;text-align:center;color:#757575;font-weight:bold;">'
				+ '        你目前还没有所属项目或该月没有填写的日志'
				+ '    </div>'
    },

    initStaff : function() {
		$("#backLi").hide();
		$("#projectLineDiv").hide();

		$("#staffDiv").show();
		$("#staffDate").show();
		$("#staffField").show();
		$("#staffDetail").hide();

        var that = this;
        $.ajax({
            url : this.action.queryEmployeeProject,
            type : "post",
            data : {
                employeeCode : writeInfo.employeeCode,
                staffStartDate : $("#staffStartDate").val(),
                staffEndDate : $("#staffEndDate").val()
            },
            async : false,
            cache : false,
            dataType : 'json',
            success : function(datas){
                $("#stafflog").empty();
                if (datas=="") {
                    var element = $(that.config.noDataTemplate);
                    $("#stafflog").append(element);
					return;
				}
                var data = "";
                var row = "";
                var element = "";
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
					row = String.format(that.config.staffTemplate, i, employeeCode, employee, data.logCount, projectCode, projectName);
                    element = $(row);
                    $("#stafflog").append(element);
					$("#staff_" + i).attr("onclick", "staffInfo.events.staffDetail('" + employeeCode + "','" + projectCode + "','" + projectName + "')");
                }
            }
        });

        historyInfo.heightAdaption("staff");
    },

    initEvents : function() {
        var that = this;
        $("input").click(function() {
            $("input").css("outline", "none");
        });

        $('#staffStartDate').datetimepicker({
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

        $('#staffEndDate').datetimepicker({
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
                $('#staffStartDate').trigger("blur");
                $('#staffEndDate').trigger("blur");
            }
        });

        $('#staffStartDate').change(function() {
            that.initStaff();
        });

        $('#staffEndDate').change(function() {
            that.initStaff();
        });
    },

    events : {
		staffDetail : function(employeeCode, projectCode, projectName) {
			$("#backLi").show();
			$("#staffDate").hide();
			$("#staffField").hide();
			$("#staffDetail").show();
			$("#backLi").attr("onclick", "staffInfo.initStaff()");
			$(".projectNameSpan").text(projectName);

			$.ajax({
				url : staffInfo.action.queryOwnLogInfo,
				type : "post",
				data : {
					employeeCode : employeeCode,
					projectCode : projectCode,
					startDate : $("#staffStartDate").val(),
					endDate : $("#staffEndDate").val()
				},
				async : false,
				cache : false,
				dataType : 'json',
				success:function(datas){
					$("#staffDetail").empty();
					if (datas=="") {
						return;
					}
					historyInfo.showHistoryLog(datas, "staff");
				}
			});

			historyInfo.heightAdaption("staff");
		}
    }
};