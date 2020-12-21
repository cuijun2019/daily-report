function projectLine() {
    $("#projectField").hide();
    $("#notHaveLine").hide();
    $("#projectDetailField").hide();
    $("#projectLineField").show();
    $("#lineDate").hide();
    $("#backLi").hide();
    $("#searchLi").hide();
    $.ajax({
        url : "/modules/lteproject/queryProjectLine",
        type : "post",
        async : false,
        cache : false,
        dataType : 'json',
        success:function(datas){
            var data = "";
            var str = "";
            var lineName = "";
            for (var i = 0; i < datas.length; i++) {
                data = datas[i];
                lineName = data.lineName;
                str += '<li style="height:auto;border-bottom:1px solid #EEEEEE;padding-left:20px;padding-top:10px;padding-bottom:10px;">';
                str += '	<a style="text-decoration: none;" onclick="queryProjectByLine(\'' + lineName + '\')">';
                str += '	<table width="100%" border="0">';
                str += '		<tr>';
                str += '			<td style="width:25%;">';
                str += '				<font color="#757575" style="font-weight:normal;">' + lineName + '</font>';
                str += '			</td>';
				str += '			<td style="width:60%;">';
                str += '				<font color="#757575" style="font-weight:normal;">共有' + data.count + '个项目</font>';
                str += '			</td>';
                str += '			<td style="width:15%;text-align:center;">';
                str += '				<font color="#757575">></font>';
                str += '			</td>';
                str += '		</tr>';
                str += '	</table>';
                str += '	</a>';
                str += '</li>';
            }
            $("#projectLine").html(str);
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

function queryProjectByLine(lineName) {
    $("#projectLineField").hide();
    $("#projectField").show();
    $("#notHaveLine").hide();
    $("#projectDetailField").hide();
    $("#lineDate").hide();
    $("#backLi").show();
    $("#searchLi").show();
    $("#searchData").val("");
    $("#backLi a").attr("onclick", "projectLine()");
    $("#projectLineName").val(lineName);
    $.ajax({
        url : "/modules/lteproject/queryProjectByLine",
        type : "post",
        data : {
            lineName : lineName
        },
        async : false,
        cache : false,
        dataType : 'json',
        success:function(datas){
            var data = "";
            var str = "";
            var projectCode = "";
            var projectName = "";
            for (var i = 0; i < datas.length; i++) {
                data = datas[i];
                projectCode = data.projectCode;
                projectName = data.projectName;
                str += '<li style="height:auto;border-bottom:1px solid #EEEEEE;padding-left:20px;padding-top:10px;padding-bottom:10px;">';
                str += '	<a style="text-decoration: none;" onclick="personalLog(\'null\',\'' + projectCode + '\',\'null\',\'null\',\'projectLine\')">';
                str += '	<table width="100%" border="0">';
                str += '		<tr>';
                str += '			<td style="width:85%;">';
                str += '				<font color="#757575" style="font-weight:normal;">' + projectCode + '&nbsp;&nbsp;&nbsp;' + projectName + '</font>';
                str += '			</td>';
                str += '			<td style="width:15%;text-align:center;">';
                str += '				<font color="#757575">></font>';
                str += '			</td>';
                str += '		</tr>';
                str += '	</table>';
                str += '	</a>';
                str += '</li>';
            }
            $("#project").html(str);
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

function queryData(){
    var search = $('#searchData').val();
    var lineName = $('#projectLineName').val();

    if(search == ""){
        queryProjectByLine(lineName);
        return false;
    }

    $.ajax({
        url : "/modules/lteproject/queryProjectLikeLine",
        type : "post",
        data : {
            lineName : lineName ,
            search : search
        },
        async : false,
        cache : false,
        dataType : 'json',
        success:function(datas){
            $("#project").html();
            var data = "";
            var str = "";
            var projectCode = "";
            var projectName = "";
            for (var i = 0; i < datas.length; i++) {
                data = datas[i];
                projectCode = data.projectCode;
                projectName = data.projectName;
                str += '<li style="height:auto;border-bottom:1px solid #EEEEEE;padding-left:20px;padding-top:10px;padding-bottom:10px;">';
                str += '	<a style="text-decoration: none;" onclick="personalLog(\'null\',\'' + projectCode + '\',\'null\',\'null\',\'projectLine\')">';
                str += '	<table width="100%" border="0">';
                str += '		<tr>';
                str += '			<td style="width:85%;">';
                str += '				<font color="#757575" style="font-weight:normal;">' + projectCode + '&nbsp;&nbsp;&nbsp;' + projectName + '</font>';
                str += '			</td>';
                str += '			<td style="width:15%;text-align:center;">';
                str += '				<font color="#757575">></font>';
                str += '			</td>';
                str += '		</tr>';
                str += '	</table>';
                str += '	</a>';
                str += '</li>';
            }
            $("#project").html(str);
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
