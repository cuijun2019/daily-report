const writeInfo = {
	action: {
		getUserId: '/modules/project/getUserId?corpid={0}&corpsecret={1}&code={2}',
		queryContractReview: '/modules/project/queryContractReview?employee={0}',
		validProjectCode: '/modules/project/validProjectCode',
		validProjectName: '/modules/project/validProjectName',
		saveLogInfo: '/modules/project/saveLogInfo',
		validLogInfo: '/modules/project/validLogInfo',
		queryLatestLogInfo: '/modules/project/queryLatestLogInfo'
	},

	init: function () {
		const that = this;
		$('#employeeCode').val("3223    崔军");
		const employeeCodes = $("#employeeCode").val();
		this.employeeCode = employeeCodes.substring(0, employeeCodes.indexOf(" "));
		this.employee = $.trim(employeeCodes.substring(employeeCodes.indexOf(" ")));

		// if(this.employeeCode==undefined) {
		//     this.employeeCode="";
		// }
		// if(this.employee==undefined) {
		//     this.employee="";
		// }
		// if (this.employeeCode == "") {
		//     $.ajax({
		//         url : String.format(this.action.getUserId, 'wwb01abb0c61c661a0', 'mpqnMIeQv44teOfgAPGxQ9jWZo1J1NDkuIOjAhlRMV8', getQueryString("code")),
		//         type : "post",
		//         async : false,
		//         cache : false,
		//         dataType : 'json',
		//         success:function(result){
		//             $('#employeeCode').val(result.userId + "    " + result.employee);
		//             that.employeeCode = result.userId;
		//             that.employee = result.employee;
		//         }
		//     });
		// }

		this.currentDate = new Date().Format("yyyy-MM-dd");
		this.currentOneDate = this.currentDate.substring(0, this.currentDate.length - 2) + "01";

		const weekArray = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
		$("#logTime").attr("placeholder", this.currentDate.replace("-", "年").replace("-", "月") + "日" + " " + weekArray[new Date(this.currentDate).getDay()]);

		this.isCommitted = false;
		this.initWrite();
		this.addProject(1);
		this.initEvents();
	},

	config: {
		projectTemplate: '<span id="projectInfo{0}" class="projectInfo" x-project-code="{2}" x-project-name="{3}" x-reporter="{4}" x-proportion="{5}">'
			+ '<div style="border-style:none none solid none;border-bottom:1px solid #EEEEEE;">'
			+ '<input type="text" style="width:85%;height:50px;padding-left:10px;border-style:none;" class="projectCode" name="projectCode" id="projectCode{0}" value="{2}" data-provide="typeahead" data-source="" placeholder="请输入项目编码" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目编码\'" />'
			+ '<input style="width:5%;border-style:none;" class="blank" readOnly="readOnly" />'
			+ '<img style="display:none;" src="/modules/geogis/img/removePro.png" id="projectCodeClearBtn{0}" />'
			+ '</div>'
			+ '<div style="border-style:none none solid none;border-bottom:1px solid #EEEEEE;">'
			+ '<input type="text" style="width:85%;height:50px;padding-left:10px;border-style:none;" class="projectName" name="projectName" id="projectName{0}" value="{3}" data-provide="typeahead" data-source="" placeholder="请输入项目名称" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目名称\'" />'
			+ '<input style="width:5%;border-style:none;" class="blank" readOnly="readOnly" />'
			+ '<img style="display:none;" src="/modules/geogis/img/removePro.png" id="projectNameClearBtn{0}" />'
			+ '</div>'
			+ '<input type="text" style="width:100%;height:50px;padding-left:10px;border-style:none none solid none;border-bottom:1px solid #EEEEEE;" class="reporter" name="reporter" id="reporter{0}" value="{4}" placeholder="请输入汇报对象" autocomplete="off" readOnly="readOnly" /><br />'
			+ '<input type="text" style="width:90%;height:50px;padding-left:10px;border-style:none none solid none;border-bottom:1px solid #EEEEEE;" class="proportion" name="proportion" id="proportion{0}" value="{5}" data-provide="typeahead" data-source="" placeholder="请输入项目占比" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目占比\'" />'
			+ '<a id="png{0}" style="text-decoration:none;" onclick="writeInfo.addProject({1})"><img src="/modules/geogis/img/14.png" /></a>'
			+ '</span>',
		projectTemplate2: '<span id="projectInfo{0}" class="projectInfo" x-project-code="{2}" x-project-name="{3}" x-reporter="{4}" x-proportion="{5}">'
			+ '<div style="border-style:none none solid none;border-bottom:1px solid #EEEEEE;">'
			+ '<input type="text" style="width:85%;height:50px;padding-left:10px;border-style:none;" class="projectCode" name="projectCode" id="projectCode{0}" value="{2}" data-provide="typeahead" data-source="" placeholder="请输入项目编码" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目编码\'" />'
			+ '<input style="width:5%;border-style:none;" class="blank" readOnly="readOnly" />'
			+ '<img style="display:none;" src="/modules/geogis/img/removePro.png" id="projectCodeClearBtn{0}" />'
			+ '</div>'
			+ '<div style="border-style:none none solid none;border-bottom:1px solid #EEEEEE;">'
			+ '<input type="text" style="width:85%;height:50px;padding-left:10px;border-style:none;" class="projectName" name="projectName" id="projectName{0}" value="{3}" data-provide="typeahead" data-source="" placeholder="请输入项目名称" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目名称\'" />'
			+ '<input style="width:5%;border-style:none;" class="blank" readOnly="readOnly" />'
			+ '<img style="display:none;" src="/modules/geogis/img/removePro.png" id="projectNameClearBtn{0}" />'
			+ '</div>'
			+ '<input type="text" style="width:100%;height:50px;padding-left:10px;border-style:none none solid none;border-bottom:1px solid #EEEEEE;" class="reporter" name="reporter" id="reporter{0}" value="{4}" placeholder="请输入汇报对象" autocomplete="off" readOnly="readOnly" /><br />'
			+ '<input type="text" style="width:90%;height:50px;padding-left:10px;border-style:none none solid none;border-bottom:1px solid #EEEEEE;" class="proportion" name="proportion" id="proportion{0}" value="{5}" data-provide="typeahead" data-source="" placeholder="请输入项目占比" autocomplete="off" onfocus="this.placeholder=\'\'" onblur="this.placeholder=\'请输入项目占比\'" />'
			+ '<a id="png{0}" style="text-decoration:none;" onclick="writeInfo.events.removeProject({0})"><img src="/modules/geogis/img/remove.png" /></a>'
			+ '</span>',
		successTemplate: '<div style="background-color:white;width:150px;height:100px;border-radius:6px;">'
			+ '			<div style="height:60px;text-align:center;padding-top:20px;">'
			+ '				<img src="/modules/geogis/img/success.png" />'
			+ '			</div>'
			+ '			<div style="height:40px;text-align:center;font-weight:bold;">'
			+ '				<font size="2px">提交成功！</font>'
			+ '			</div>'
			+ '</div>',
		failureTemplate: '<div style="background-color:white;width:200px;height:150px;border-radius:6px;">'
			+ '			<div style="height:90px;text-align:center;padding-top:40px;">'
			+ '				<img src="/modules/geogis/img/error.png" />'
			+ '			</div>'
			+ '			<div style="height:30px;text-align:center;font-weight:bold;">'
			+ '				<font size="2px">提交信息失败</font>'
			+ '			</div>'
			+ '			<div style="height:30px;text-align:center;">'
			+ '				<font size="2px">{0}</font>'
			+ '			</div>'
			+ '</div>'
	},

	initWrite: function () {
		// 设置工作性质
		const natureList = new Array('开发', '测试', '维护', '生产', '培训', '部署', '策划', '数据分析', '文档撰写', '内部交流', '外部交流', '商务', '服务交付', '研究', '休假', '其他');
		let naturelist = "";
		for (let i = 0; i < natureList.length; i++) {
			naturelist += "<li><font size='2px;'>" + natureList[i] + "</font></li>";
		}
		$("#naturelist").append(naturelist);

		this.initNaturelist();
	},

	addProject: function (i) {
		if ($("#proportion1").val() == "100") {
			$("#popdiv").empty();
			const row = String.format(writeInfo.config.failureTemplate, "项目占比之和等于100，不能添加项目");
			var element = $(row);
			$("#popdiv").append(element);
			$("#popdiv").popup("open");
			$("#popdiv").css({"left": "0px", "top": "0px", "display": "block"});

			setTimeout(function () {
				$("#popdiv").popup("close");
				$("#popdiv").empty();
			}, 2000);//两秒后关闭
			return;
		}
		if (i == 1) {
			$("#addProject").empty();
		}
		$("#png" + (i - 1) + " img").attr("src", "/modules/geogis/img/remove.png");
		$("#png" + (i - 1)).attr("onclick", "writeInfo.events.removeProject(" + (i - 1) + ")");

		this.events.fillLogInfo(this.employeeCode, i);

		$("#projectCode" + i).attr("onKeyUp", "writeInfo.events.showProjectCodeBtn(" + i + ")");
		$("#projectName" + i).attr("onKeyUp", "writeInfo.events.showProjectNameBtn(" + i + ")");
		$("#projectCodeClearBtn" + i).attr("onclick", "writeInfo.events.clearData(" + i + ")");
		$("#projectNameClearBtn" + i).attr("onclick", "writeInfo.events.clearData(" + i + ")");
		$("#leftTabBox").css({
			"overflow": "hidden",
			"position": "relative",
			"height": ($("#writeHDiv").height() + 70) + "px"
		});
		this.events.changeProjectCode(i);
		this.events.changeProjectName(i);
		this.events.changeProportion(i);

		$("input").click(function () {
			$("input").css("outline", "none");
		});
		$("textarea").click(function () {
			$("textarea").css("outline", "none");
		});
		this.initFields();
	},

	initNaturelist: function () {
		$("#naturelist").mobiscroll().treelist({
			theme: "android-holo-light",
			lang: "zh",
			rows: 3,
			headerText: function (valueText) {
				return "<font size='2px;'>工作性质</font>";
			},
			placeholder: "请选择工作性质",
			inputClass: 'workNatureClass',
			height: 35,
			fixedWidth: [200],
			defaultValue: [0],
			closeOnOverlay: true,
			formatResult: function (array) { //返回自定义格式结果
				const workNature = $('#naturelist>li').eq(array[0]).children('font').text();
				return workNature;
			},
			onSelect: function (valueText, inst) {
				$("#workNature").val(valueText);
			},
			onBeforeShow: function (inst) {
				$(".projectCode").css("background-color", "white");
				$(".projectName").css("background-color", "white");
				$(".reporter").css("background-color", "white");
				$(".proportion").css("background-color", "white");
				$("#employeeCode").css("background-color", "white");
				$("#context").css("background-color", "white");
				$(".blank").css("background-color", "white");
			}
		});
	},

	initEvents: function () {
		$("#saveBtn").attr("onclick", "writeInfo.events.save()");
		$("#resetBtn").attr("onclick", "writeInfo.events.reset()");

		$("button").click(function () {
			$("button").css("outline", "none");
		});

		$('#logTime').datetimepicker({
			language: 'zh-CN',
			minView: "month",
			endDate: writeInfo.currentDate,
			format: 'yyyy-mm-dd',
			clearBtn: true,
			autoclose: 1
		}).on("show", function () {
			// that.hide = false;
		}).on("hide", function (ev) {
			// that.hide = true;
			const date = new Date(ev.date.valueOf()).Format("yyyy-MM-dd");
			const weekArray = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
			$("#logTime").val("");
			$("#logTime").attr("placeholder", date.replace("-", "年").replace("-", "月") + "日" + " " + weekArray[new Date(date).getDay()]);
		});
	},

	initFields: function () {
		const that = this;
		$("input[id^='projectCode']").each(function (index, element) {
			$(this).typeahead({
				source: function (query, process) {
					//query是输入的值
					$.post(String.format(that.action.queryContractReview, encodeURI(encodeURI(that.employee))), {name: query}, function (datas) {
						let array = [];
						for (let i = 0; i < datas.length; i++) {
							const data = datas[i];
							array.push(data.projectCode);
						}
						array = unique(array);
						process(array);
					});
				},
				showHintOnFocus: true,
				items: "all",
				updater: function (item) {
					return item;
				},
				afterSelect: function (item) {
					const i = $(element).attr("id").replace("projectCode", "");
					const projectCode = $("#projectCode" + i).val();
					if (projectCode != "" && projectCode.length > 4) {
						$.ajax({
							url: writeInfo.action.validProjectCode,
							type: "post",
							data: {
								projectCode: projectCode
							},
							async: false,
							cache: false,
							dataType: 'json',
							success: function (result) {
								if (result.valid) {
									$("#projectName" + i).val(result.projectName);
									$("#reporter" + i).val(result.reporter);
									// 设置每个项目的项目编码，项目名称，汇报对象，以便后面的保存操作
									$("#projectCode" + i).parent().parent().attr("x-project-code", projectCode).attr("x-project-name", result.projectName).attr("x-reporter", result.reporter);
									$("#projectCodeClearBtn" + i).show();
									$("#projectNameClearBtn" + i).show();
								} else {
									$("#projectCode" + i).parent().parent().attr("x-project-code", projectCode);
								}
							}
						});
					} else if (projectCode == "") {
						$("#projectCode" + i).parent().parent().attr("x-project-code", "");
						$("#projectCodeClearBtn" + i).hide();
						$("#projectNameClearBtn" + i).hide();
					} else {
						$("#projectCode" + i).parent().parent().attr("x-project-code", "");
					}
				},
				delay: 500
			});
		});

		$("input[id^='projectName']").each(function (index, element) {
			$(this).typeahead({
				source: function (query, process) {
					//query是输入的值
					$.post(String.format(that.action.queryContractReview, encodeURI(encodeURI(that.employee))), {name: query}, function (datas) {
						let array = [];
						for (let i = 0; i < datas.length; i++) {
							const data = datas[i];
							array.push(data.projectName);
						}
						array = unique(array);
						process(array);
					});
				},
				showHintOnFocus: true,
				items: "all",
				updater: function (item) {
					return item;
				},
				afterSelect: function (item) {
					const i = $(element).attr("id").replace("projectName", "");
					const projectName = $("#projectName" + i).val();
					if (projectName !== "") {
						$.ajax({
							url: writeInfo.action.validProjectName,
							type: "post",
							data: {
								projectName: projectName
							},
							async: false,
							cache: false,
							dataType: 'json',
							success: function (result) {
								if (result.valid) {
									$("#projectCode" + i).val(result.projectCode);
									$("#reporter" + i).val(result.reporter);
									// 设置每个项目的项目编码，项目名称，汇报对象，以便后面的保存操作
									$("#projectName" + i).parent().parent().attr("x-project-code", result.projectCode).attr("x-project-name", projectName).attr("x-reporter", result.reporter);
								} else {
									$("#projectName" + i).parent().parent().attr("x-project-name", projectName);
								}
								$("#projectCodeClearBtn" + i).show();
								$("#projectNameClearBtn" + i).show();
							}
						});
					} else {
						$("#projectName" + i).parent().parent().attr("x-project-name", "");
						$("#projectCodeClearBtn" + i).hide();
						$("#projectNameClearBtn" + i).hide();
					}
				},
				delay: 500
			});
		});

		$("input[id^='proportion']").each(function (index, element) {
			$(this).typeahead({
				source: ['10', '20', '25', '30', '40', '50', '60', '70', '75', '80', '90', '100'],
				showHintOnFocus: true,
				items: "all",
				updater: function (item) {
					return item;
				},
				afterSelect: function (item) {
					const i = $(element).attr("id").replace("proportion", "");
					const proportion = $("#proportion" + i).val();
					if (proportion.indexOf("%") == -1) {
						$("#proportion" + i).val(proportion + "%");
					}
					$("#proportion" + i).parent().attr("x-proportion", $("#proportion" + i).val());
				},
				delay: 500
			});
		});
	},

	events: {
		changeProjectCode: function (i) {
			$("#projectCode" + i).change(function () {
				const projectCode = $("#projectCode" + i).val();
				if (projectCode !== "" && projectCode.length > 4) {
					$.ajax({
						url: writeInfo.action.validProjectCode,
						type: "post",
						data: {
							projectCode: projectCode
						},
						async: false,
						cache: false,
						dataType: 'json',
						success: function (result) {
							if (result.valid) {
								$("#projectName" + i).val(result.projectName);
								$("#reporter" + i).val(result.reporter);
								// 设置每个项目的项目编码，项目名称，汇报对象，以便后面的保存操作
								$("#projectCode" + i).parent().parent().attr("x-project-code", projectCode).attr("x-project-name", result.projectName).attr("x-reporter", result.reporter);
								$("#projectCodeClearBtn" + i).show();
								$("#projectNameClearBtn" + i).show();
							} else {
								$("#projectCode" + i).parent().parent().attr("x-project-code", projectCode);
							}
						}
					});
				} else if (projectCode == "") {
					$("#projectCode" + i).parent().parent().attr("x-project-code", "");
					$("#projectCodeClearBtn" + i).hide();
					$("#projectNameClearBtn" + i).hide();
				} else {
					$("#projectCode" + i).parent().parent().attr("x-project-code", "");
				}
			});
		},

		changeProjectName: function (i) {
			$("#projectName" + i).change(function () {
				const projectName = $("#projectName" + i).val();
				if (projectName != "") {
					$.ajax({
						url: writeInfo.action.validProjectName,
						type: "post",
						data: {
							projectName: projectName
						},
						async: false,
						cache: false,
						dataType: 'json',
						success: function (result) {
							if (result.valid) {
								$("#projectCode" + i).val(result.projectCode);
								$("#reporter" + i).val(result.reporter);
								// 设置每个项目的项目编码，项目名称，汇报对象，以便后面的保存操作
								$("#projectName" + i).parent().parent().attr("x-project-code", result.projectCode).attr("x-project-name", projectName).attr("x-reporter", result.reporter);
							} else {
								$("#projectName" + i).parent().parent().attr("x-project-name", projectName);
							}
							$("#projectCodeClearBtn" + i).show();
							$("#projectNameClearBtn" + i).show();
						}
					});
				} else {
					$("#projectName" + i).parent().parent().attr("x-project-name", "");
					$("#projectCodeClearBtn" + i).hide();
					$("#projectNameClearBtn" + i).hide();
				}
			});
		},

		changeProportion: function (i) {
			$("#proportion" + i).change(function () {
				const proportion = $("#proportion" + i).val();
				if (proportion.indexOf("%") == -1) {
					$("#proportion" + i).val(proportion + "%");
				}
				$("#proportion" + i).parent().attr("x-proportion", $("#proportion" + i).val());
			});
		},

		removeProject: function (i) {
			$("#projectInfo" + i).remove();
			$("#leftTabBox").css({
				"overflow": "hidden",
				"position": "relative",
				"height": ($("#writeHDiv").height() + 70) + "px"
			});
		},

		showProjectCodeBtn: function (i) {
			if ($("#projectCode" + i).val().length > 0) {
				$("#projectCodeClearBtn" + i).show();
			} else {
				$("#projectCodeClearBtn" + i).hide();
			}
		},

		showProjectNameBtn: function (i) {
			if ($("#projectName" + i).val().length > 0) {
				$("#projectNameClearBtn" + i).show();
			} else {
				$("#projectNameClearBtn" + i).hide();
			}
		},

		clearData: function (i) {
			$("#projectCode" + i).val("");
			$("#projectCode" + i).text("");
			$("#projectName" + i).val("");
			$("#projectName" + i).text("");
			$("#reporter" + i).val("");
			$("#reporter" + i).text("");
			$("#projectCodeClearBtn" + i).hide();
			$("#projectNameClearBtn" + i).hide();
			$("#projectCode" + i).parent().parent().attr("x-project-code", "").attr("x-project-name", "").attr("x-reporter", "");
		},

		save: function () {
			const employeeCode = $("#employeeCode").val();
			let logTime = $("#logTime").attr("placeholder");
			logTime = logTime.substring(0, logTime.indexOf("日")).replace("年", "-").replace("月", "-");
			const workNature = $("#workNature").val();
			const context = $("#context").val();
			const logInfoList = writeInfo.collectData(".projectInfo");
			const message = writeInfo.validMessage(employeeCode, logTime, workNature, context, logInfoList);
			if (message != "") {
				$("#popdiv").empty();
				const row = String.format(writeInfo.config.failureTemplate, message);
				const element = $(row);
				$("#popdiv").append(element);
				$("#popdiv").popup("open");
				$("#popdiv").css({"left": "0px", "top": "0px", "display": "block"});

				setTimeout(function () {
					$("#popdiv").popup("close");
					$("#popdiv").empty();
				}, 2000);//两秒后关闭
				return;
			}
			if (writeInfo.isCommitted) {
				return;
			}
			writeInfo.isCommitted = true;
			$.ajax({
				url: writeInfo.action.saveLogInfo,
				type: "post",
				data: {
					logInfoList: encode(logInfoList)
				},
				async: true,
				cache: false,
				dataType: 'json',
				success: function (data) {
					$("#popdiv").empty();
					const element = $(writeInfo.config.successTemplate);
					$("#popdiv").append(element);
					$("#popdiv").popup("open");
					$("#popdiv").css({"left": "0px", "top": "0px", "display": "block"});

					setTimeout(function () {
						$("#popdiv").popup("close");
						$("#popdiv").empty();
					}, 2000);//两秒后关闭

					writeInfo.isCommitted = false;
				}
			});
		},

		reset: function () {
			let i;
			const count = writeInfo.collectDataCount(".projectInfo");
			for (i = 1; i <= count; i++) {
				writeInfo.events.removeProject(i);
				$("#projectCode" + i).val("");
				$("#projectName" + i).val("");
				$("#reporter" + i).val("");
				$("#proportion" + i).val("");
			}
			const count2 = writeInfo.collectDataCount(".projectInfo");
			if (count2 != 0) {
				for (i = 1; i <= count2; i++) {
					writeInfo.events.removeProject(i);
					$("#projectCode" + i).val("");
					$("#projectName" + i).val("");
					$("#reporter" + i).val("");
					$("#proportion" + i).val("");
				}
			}
			writeInfo.addProject(1);
			const weekArray = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
			$("#logTime").attr("placeholder", writeInfo.currentDate.replace("-", "年").replace("-", "月") + "日" + " " + weekArray[new Date(writeInfo.currentDate).getDay()]);
			$("#workNature").val("");
			$("#context").val("");
			writeInfo.initNaturelist();
		},

		fillLogInfo: function (employeeCode, i) {
			if (i == 1) {
				$.ajax({
					url: writeInfo.action.queryLatestLogInfo,
					type: "post",
					data: {
						employeeCode: employeeCode
					},
					async: false,
					cache: false,
					dataType: 'json',
					success: function (datas) {
						let element;
						if (datas.length == 0) {
							let row = String.format(writeInfo.config.projectTemplate, i, i + 1, "", "", "", "");
							element = $(row);
							$("#addProject").append(element);
							$("#projectCodeClearBtn" + i).hide();
							$("#projectNameClearBtn" + i).hide();
						} else {
							for (let j = 0; j < datas.length; j++) {
								const data = datas[j];
								let row;
								if (j < datas.length - 1) {
									row = String.format(writeInfo.config.projectTemplate2, i + j, i + j + 1, data.projectCode, data.projectName, data.reporter, data.proportion);
								} else {
									row = String.format(writeInfo.config.projectTemplate, i + j, i + j + 1, data.projectCode, data.projectName, data.reporter, data.proportion);
								}
								element = $(row);
								$("#addProject").append(element);
								$("#naturelist_dummy").val(data.workNature);
								$("#workNature").val(data.workNature);
								$("#context").val(data.context);
								$("#projectCodeClearBtn" + (i + j)).show();
								$("#projectNameClearBtn" + (i + j)).show();
							}
						}
					}
				});
			} else {
				const row = String.format(writeInfo.config.projectTemplate, i, i + 1, "", "", "", "");
				const element = $(row);
				$("#addProject").append(element);
				$("#projectCodeClearBtn" + i).hide();
				$("#projectNameClearBtn" + i).hide();
			}
		}
	},

// 验证输入信息是否正确
	validMessage: function (employeeCode, logTime, workNature, context, logInfoList) {
		let message = "";
		$.ajax({
			url: this.action.validLogInfo,
			type: "post",
			data: {
				employeeCode: employeeCode.substring(0, employeeCode.indexOf(" ")),
				logTime: logTime,
				workNature: workNature,
				context: context,
				logInfoList: encode(logInfoList)
			},
			async: false,
			cache: false,
			dataType: 'json',
			success: function (result) {
				message = result.message;
			}
		});
		return message;
	},

// 获取多个项目信息
	collectData: function (expression) {
		let logTime = $("#logTime").attr("placeholder");
		logTime = logTime.substring(0, logTime.indexOf("日")).replace("年", "-").replace("月", "-");
		const workNature = $("#workNature").val();
		const context = $("#context").val();
		const datalist = [];

		$(expression).each(function () {
			const row = $(this);
			const data = row.attributeObject();
			const obj = {};
			for (const prop in data) {
				if (prop.startWith("x-")) {
					let name = prop.replace("x-", "");
					const ns = name.split("-");
					name = ns[0];
					for (let i = 1; i < ns.length; i++) {
						name += capitalize(ns[i]);
					}
					obj[name] = data[prop];
				}
			}
			obj.employeeCode = writeInfo.employeeCode;
			obj.employee = writeInfo.employee;
			obj.logTime = logTime;
			obj.workNature = workNature;
			obj.context = context.replace("'", "''");
			obj.createTime = new Date().Format("yyyy-MM-dd");

			datalist.push(obj);
		});
		return datalist;
	},

// 获取项目信息的个数
	collectDataCount: function (expression) {
		let i = 0;
		$(expression).each(function () {
			i++;
		});
		return i;
	}
};

String.format = function (format) {
	const args = Array.prototype.slice.call(arguments, 1);
	return format.replace(/\{(\d+)\}/g, function (m, i) {
		return args[i];
	});
};

Date.prototype.Format = function (fmt) { //author: meizz
	const o = {
		"M+": this.getMonth() + 1,                 //月份
		"d+": this.getDate(),                    //日
		"h+": this.getHours(),                   //小时
		"m+": this.getMinutes(),                 //分
		"s+": this.getSeconds(),                 //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds()             //毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (const k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

// 用于获取多个项目信息（辅助）
$.fn.attributeObject = function () {
	const dom = this.get(0);
	const attr = {};
	if (dom) {
		const length = dom.attributes.length;
		for (let i = 0; i < length; i++) {
			const attribute = dom.attributes[i];
			attr[attribute.nodeName] = attribute.nodeValue;
		}
	}
	return attr;
}
// 用于获取多个项目信息（辅助）
String.prototype.startWith = function (str) {
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
capitalize = function (source) {
	return source.substring(0, 1).toUpperCase() + source.substring(1);
}

// 对多个项目信息进行编码
encode = function (o) {
	const useHasOwn = !!{}.hasOwnProperty;
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
		let a = ["{"], b, i, v;
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
isDate = function (v) {
	return v && typeof v.getFullYear == "function";
}
// 对多个项目信息进行编码（辅助）
encodeArray = function (o) {
	let a = ["["], b, i, l = o.length, v;
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
pad = function (value, length) {
	value = String(value);
	length = parseInt(length, 10) || 2;
	while (value.length < length) {
		value = '0' + value;
	}
	return value;
}
// 对多个项目信息进行编码（辅助）
encodeDate = function (o) {
	return '"' + o.getFullYear() + "-" + pad(o.getMonth() + 1) + "-"
		+ pad(o.getDate()) + "T" + pad(o.getHours()) + ":"
		+ pad(o.getMinutes()) + ":" + pad(o.getSeconds()) + '"';
}
// 对多个项目信息进行编码（辅助）
encodeString = function (s) {
	const m = {
		"\b": '\\b',
		"\t": '\\t',
		"\n": '\\n',
		"\f": '\\f',
		"\r": '\\r',
		'"': '\\"',
		"\\": '\\\\'
	};

	if (/["\\\x00-\x1f]/.test(s)) {
		return '"' + s.replace(/([\x00-\x1f\\"])/g, function (a, b) {
			let c = m[b];
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

//数组去重
function unique(arr) {
	const new_arr = [];
	let items;
	for (let i = 0; i < arr.length; i++) {
		items = arr[i];
		if ($.inArray(items, new_arr) == -1) {
			new_arr.push(items);
		}
	}
	return new_arr;
}

//判断当前日期为当月第几周
getMonthWeek = function (a, b, c) {
	//a = d = 当前日期
	//b = 6 - w = 当前周的还有几天过完(不算今天)
	//a + b 的和在除以7 就是当天是当前月份的第几周
	const date = new Date(a, parseInt(b) - 1, c), w = date.getDay(), d = date.getDate();
	return Math.ceil((d + 6 - w) / 7);
}