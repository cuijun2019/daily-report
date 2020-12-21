var historyInfo = {
    action : {
		queryOwnLogInfo : '/modules/lteproject/queryOwnLogInfo'
    },

    init : function() {
        $(".employeeSpan").text(writeInfo.employee);
		$("#startDate").val(writeInfo.currentOneDate);
		$("#endDate").val(writeInfo.currentDate);

        this.hide = true;
        this.initHistory();
        this.initEvents();
    },

    config : {
        historyTemplate : '<div id="{8}Log_{0}" style="height:auto;border-bottom:1px solid #EEEEEE;">'
				+ '		<table style="width:95%;margin-left:10px;margin-top:5px;margin-bottom:5px;" border="0">'
				+ '			<tr>'
				+ '				<td colspan="2" style="font-weight:bold;">'
				+ '					<span class="showEmployee">{1}&nbsp;&nbsp;&nbsp;&nbsp;{2}&nbsp;&nbsp;&nbsp;&nbsp;</span>{3}'
				+ '				</td>'
				+ '			</tr>'
				+ '		<tr>'
				+ '			<td colspan="2">'
				+ '			{4}&nbsp;&nbsp;&nbsp;&nbsp;{5}'
				+ '			</td>'
				+ '		</tr>'
				+ '		<tr id="{8}Multi_{0}">'
				+ '			<td style="width:90%;">'
				+ '				<span id="{8}Context_{0}" style="float:left;color:#757575;">{7}</span>'
				+ '			</td>'
				+ '			<td style="vertical-align:bottom;">'
				+ '				<a id="{8}Expand_{0}" style="text-decoration: none;cursor:pointer;">展开</a>'
				+ '			</td>'
				+ '		</tr>'
				+ '		<tr id="{8}Single_{0}">'
				+ '			<td colspan="2" style="color:#757575;">'
				+ '				{6}'
				+ '			</td>'
				+ '		</tr>'
				+ '    </table>'
				+ '</div>',
        noDataTemplate : '<div style="height:30px;padding-top:5px;border-bottom:1px solid #EEEEEE;text-align:center;color:#757575;font-weight:bold;">'
				+ '        无数据'
				+ '    </div>'
    },

    initHistory : function() {
        var that = this;
        $.ajax({
            url : this.action.queryOwnLogInfo,
            type : "post",
            data : {
                employeeCode : writeInfo.employeeCode,
                startDate : $("#startDate").val(),
                endDate : $("#endDate").val()
            },
            async : false,
            cache : false,
            dataType : 'json',
            success : function(datas){
                $("#historylog").empty();
                if (datas == "") {
                    var element = $(that.config.noDataTemplate);
                    $("#historylog").append(element);
                    return;
                }
                that.showHistoryLog(datas, "history");
            }
        });

        this.heightAdaption("history");
    },

	showHistoryLog : function (datas, type) {
		var data = "";
		var row = "";
		var element = "";
		var context = "";
		for (var i = 0; i < datas.length; i++) {
			data = datas[i];
			context = data.context;
			if (context == undefined) {
				context = "";
			}
			if (context.length > 20) {
				row = String.format(this.config.historyTemplate, i, data.employeeCode, data.employee, data.logTime.replace("-","/").replace("-","/"), data.projectCode, data.projectName, "", context.substring(0, 20), type);
			} else {
				row = String.format(this.config.historyTemplate, i, data.employeeCode, data.employee, data.logTime.replace("-","/").replace("-","/"), data.projectCode, data.projectName, context, "", type);
			}
			element = $(row);
			if (type == "history") {
				$("#historylog").append(element);
				$(".showEmployee").hide();
			}
			if (type == "staff") {
				$("#staffDetail").append(element);
			}
			if (type == "line") {
				$("#projectDetailField").append(element);
			}
			if (type == "appraise") {
				$("#employeeLogSpan").append(element);
				$(".showEmployee").hide();
			}
			if (context.length > 20) {
				$("#" + type + "Multi_" + i).show();
				$("#" + type + "Single_" + i).hide();
				$("#" + type + "Expand_" + i).attr("onclick", "historyInfo.events.expand(" + i + ",'" + this.replaceAll(context, "\n", "<br>") + "','" + type + "');");
			} else {
				$("#" + type + "Multi_" + i).hide();
				$("#" + type + "Single_" + i).show();
			}
		}
	},

    initEvents : function() {
        var that = this;
        $("input").click(function() {
            $("input").css("outline", "none");
        });

        $('#startDate').datetimepicker({
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

        $('#endDate').datetimepicker({
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
                $('#startDate').trigger("blur");
                $('#endDate').trigger("blur");
            }
        });

        $('#startDate').change(function() {
            that.initHistory();
        });

        $('#endDate').change(function() {
            that.initHistory();
        });
    },

    events : {
        expand : function(i, context, type) {
            var state = $("#" + type + "Expand_" + i).text();
			if (state == "展开") {
				$("#" + type + "Context_" + i).html('<font size="2px;">' + context + '</font>');
				$("#" + type + "Expand_" + i).text("收起");
			} else {
				context = historyInfo.replaceAll(context, "<br>", "\n");
				$("#" + type + "Context_" + i).html('<font size="2px;">' + context.substring(0, 20) + '</font>');
				$("#" + type + "Expand_" + i).text("展开");
			}

			if (type == "history" || type == "appraise" || type == "staff" || type == "line") {
				if (type == "line") {
					type = "staff";
				}
				historyInfo.heightAdaption(type);
			}
        }
    },

	replaceAll : function(context,s1,s2){
		return context.replace(new RegExp(s1,"gm"),s2);
	},

	heightAdaption : function (type) {
		$("#" + type + "Surplus").css("height","0px");
		if (($("body").height()-$("#" + type + "HDiv").height()-70) > 0) {
			$("#" + type + "Surplus").css("height", ($("body").height()-$("#" + type + "HDiv").height()-70) + "px");
		}
		$("#leftTabBox").css({"overflow":"hidden","position":"relative","height":($("#" + type + "HDiv").height() + 70)+"px"});
	}
};