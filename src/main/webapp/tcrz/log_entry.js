var logInfo = {
    init : function() {
        this.initTouchSlide();
    },

    initTouchSlide : function() {
        var that = this;
        // 左右滑动显示不同的模块（填写日志、历史日志、员工日志、每月考核）
        TouchSlide({ slideCell:"#leftTabBox", effect:"left",
            startFun:function(i,c){
                if (i==0) {
                    that.initWrite();
                }
                if (i==1) {
                    that.initHistory();
                }
                if (i==2) {
                    that.initStaff();
                }
                if (i==3) {
                    that.initAppraise();
                }
            },
            endFun:function(i,c){

            }
        });
    },

    initWrite : function() {
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

        writeInfo.init();
    },

    initHistory : function() {
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

        historyInfo.init();
    },

    initStaff : function() {
        $("#write img").attr("src", "modules/geogis/img/1.png");
		$("#person img").attr("src", "modules/geogis/img/2.png");
		$("#staff img").attr("src", "modules/geogis/img/33.png");
		$("#appraise img").attr("src", "modules/geogis/img/4.png");
		$("#writeColor").css("color", "#757575");
		$("#personColor").css("color", "#757575");
		$("#staffColor").css("color", "#2DA5E6");
		$("#appraiseColor").css("color", "#757575");

		$("#writeHDiv").hide();
		$("#historyHDiv").hide();
		$("#staffHDiv").show();
		$("#appraiseHDiv").hide();

		if (writeInfo.employeeCode == "16" || writeInfo.employeeCode == "25" || writeInfo.employeeCode == "60234" || writeInfo.employeeCode == "10300" || writeInfo.employeeCode == "922" || writeInfo.employeeCode == "72") {
			projectLineInfo.init();
		} else {
			staffInfo.init();
		}
    },

    initAppraise : function() {
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

        appraiseInfo.init();
    }
};

$(function () {
    logInfo.init();
});

// 获取url参数的值
function getQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.document.location.search.substr(1).match(reg);
    if(r != null) return decodeURI(r[2]); return "";
}
