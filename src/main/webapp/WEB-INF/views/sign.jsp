<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/layouts/taglibs.jsp" %>
    <meta name="applicable-device" content="mobile" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>每日签到</title>
    <link rel="stylesheet" href="${ctx}/static/sign/sign.css">
    <script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.cookie.js"></script>
    <script>
    $(window).load(function() {
        $("#status").fadeOut();
        $("#preloader").delay(350).fadeOut("slow");
    })
    </script>
    <script type="text/javascript">
    $(document).ready(function(){

    });
    </script>
</head>  
  
<body>  
<div class="mobile">   
  <!--页面加载 开始-->  
  <div id="preloader">  
    <div id="status">  
      <p class="center-text"><span>拼命加载中···</span></p>  
    </div>  
  </div>  
  <!--页面加载 结束-->   

  <div >  
    <div class="qiandap-box" style="padding-bottom:30px;">
      <div class="qiandao-biaoti">签到</div>
      <div class="qiandao-u2">
          <div class="u2_line"></div>
      </div>
        <div class="qiandao-con clear">
        <div class="qiandao-left">  
          <div class="qiandao-main" id="js-qiandao-main">
            <ul class="qiandao-list" id="js-qiandao-list">
            </ul>
          </div>  
        </div>
          <div class="qiandao-right">
                  <div class="qiandao-qihuan">
                      <a id="readId" href="#"><img src="../static/image/u11_normal.png" style="height: 25px;"/></a>
                      <div>阅读</div>
                  </div>
                  <div class="qiandao-qihuan">
                      <a id="readAloudId" href="#"><img src="../static/image/u13_normal.png" style="height: 25px;"/></a>
                      <div>朗读</div>
                  </div>
                  <div class="qiandao-qihuan">
              <a href="http://wdr.etonetech.com/wyx/comment.html"><img src="../static/image/u17_normal.png" style="height: 25px;"/></a>
                  <div>点评</div>
              </div>
              <div class="qiandao-qihuan">
                  <a href="http://wdr.etonetech.com/wyx/statistics.html"><img src="../static/image/u21_normal.png" style="height: 25px;"/></a>
                  <div>统计</div>
              </div>
          </div>
    </div>  
  </div>  
    

</div>
<script type="text/javascript">

</script>
<script>
    window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function() {
        if (window.orientation === 180 || window.orientation === 0) {
//            alert('竖屏状态！');
            window.location.reload();
        }
        if (window.orientation === 90 || window.orientation === -90 ){
//            alert('横屏状态！');
            window.location.reload();
        }
    }, false);
$(function() {
    var curDate = new Date();
    var month = curDate.getMonth() + 1;
    var strDate = curDate.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var curDateFormat = curDate.getFullYear() + "-" + month + "-" + strDate;
    $("#readAloudId").attr("href", "http://wdr.etonetech.com/wyx/education.html?date=" + curDateFormat);
    //$("#readId").attr("href", "${ctx}/base/read?date=" + curDateFormat);
    $("#readId").attr("href", "http://wdr.etonetech.com/wyx/read.html?date=" + curDateFormat);

    var questdata = getQuestFinish();

    var widthUl = $('#js-qiandao-main').width();
    // li的宽度和高度  
    var widthLi = widthUl/7;
    if(widthLi < 45){
        $('#js-qiandao-list').css('marginTop','40px');    
    }else if(widthLi > 45 && widthLi < 50){  
        $('#js-qiandao-list').css('marginTop','50px');    
    }  
    var signFun = function() {  
  

        var $dateBox = $("#js-qiandao-list"),
            $currentDate = $(".current-date"),
            $qiandaoBnt = $("#js-just-qiandao"),
            _html = '',
            _handle = true,
            myDate = new Date();
        $currentDate.text(myDate.getFullYear() + '年' + parseInt(myDate.getMonth() + 1) + '月' + myDate.getDate() + '日');

        var monthFirst = new Date(myDate.getFullYear(), parseInt(myDate.getMonth()), 1).getDay();

        var d = new Date(myDate.getFullYear(), parseInt(myDate.getMonth() + 1), 0);
        var totalDay = d.getDate(); //获取当前月的天数

        for (var i = 0; i < 42; i++) {
            _html += ' <li style="width:'+widthLi+'px;height:'+widthLi+'px;line-height:'+widthLi+'px"><div class="qiandao-icon"></div></li>'
        }
        $dateBox.html(_html) //生成日历网格

        var $dateLi = $dateBox.find("li");

        for (var i = 0; i < totalDay; i++) {
            $dateLi.eq(i + monthFirst).addClass("date" + parseInt(i + 1)).attr('data-num',parseInt(i + 1));
        } //生成当月的日历且含已签到
          
        for(var i = 0; i < 42; i++){
            var liNum = $dateLi.eq(i).attr('data-num');  
            if(liNum != 'undefined' && liNum != '' && liNum != null){
                $('.date'+liNum).append(liNum);
            }
        } //    嵌入天
          
        // 没有天的li背景变色  
        for(var i = 0; i < $dateLi.length; i++){
            if(i < monthFirst || i>(totalDay+monthFirst-1)){
                $dateLi.eq(i).css('background','#eee');
            }
        }
  
    }();
  

    $("li").click(function(e){
        var tar = $(this);
        var date =  $(this).attr("data-num");
        var now = new Date();
        var day = now.getDate();
        if(date != "undefined" && date <= day){
	    var curDate = new Date();
                    var month = curDate.getMonth() + 1;
                    if (month >= 1 && month <= 9) {
                        month = "0" + month;
                    }
                    if (date >= 0 && date <= 9) {
                        date = "0" + date;
                    }
                    var curDateFormat = curDate.getFullYear() + "-" + month + "-" + date;
            $.ajax({
                url: "${ctx}/modules/education/getQuest?date="+curDateFormat,
                success:function(data){
//                    console.log(data);
//                    console.log(data.read[0]);
                    var questdiv;
                    //var readhref = "${ctx}/base/read?date="+date;
		    var readhref = "http://wdr.etonetech.com/wyx/read.html?date="+curDateFormat;
                    var talkhref = "http://wdr.etonetech.com/wyx/readAloud_info.html?date=" + curDateFormat;
                    var dianpinghref = "http://wdr.etonetech.com/wyx/comment.html";
 		    if($('#quest_div').size()==0){
                        questdiv = $('<div id="quest_div" class="quest_div" ></div>').appendTo('body');//不存在则创建了放到body中
                        if(typeof(data.read[0]) != "undefined"){
                            questdiv.append("<p class='first'><a href='"+readhref+"'>一、阅读</a><img style='padding-left:30%' src='${ctx}/static/image/gou.png' /></p>");

                        }else{
                            questdiv.append("<p class='first'><a href='"+readhref+"'>一、阅读</a><img style='padding-left:30%' src='${ctx}/static/image/u45_normal.png' /></p>");
                        }
                        if(typeof(data.talk[0]) != "undefined"){
                            questdiv.append("<p class='other'><a href='"+talkhref+"'>二、朗读</a><img style='padding-left:30%' src='${ctx}/static/image/gou.png' /></p>");
                        }else{
                            questdiv.append("<p class='other'><a href='"+talkhref+"'>二、朗读</a><img style='padding-left:30%' src='${ctx}/static/image/u45_normal.png' /></p>");
                        }
//                        questdiv.append("<p class='other'><a href='"+talkhref+"'>二、朗读</a><span style='font-family:应用字体;font-size:13px;font-weight:bold;font-style:normal;text-decoration:none;color:#FF0000;padding-left: 40%;'>X</span></p>");
                        if(typeof(data.dianping[0]) != "undefined"){
                            questdiv.append("<p class='other'><a href='"+dianpinghref+"'>三、点评</a><img style='padding-left:30%' src='${ctx}/static/image/gou.png' /></p>");
                        }else{
                            questdiv.append("<p class='other'><a href='"+dianpinghref+"'>三、点评</a><img style='padding-left:30%' src='${ctx}/static/image/u45_normal.png' /></p>");
                        }
//                        questdiv.append("<p class='other'>3、点评<span style='font-family:应用字体;font-size:13px;font-weight:bold;font-style:normal;text-decoration:none;color:#FF0000;padding-left: 40%;'>√</span></p>");;
                    }else{
                        questdiv = $('#quest_div');
                        var div_object = document.getElementById("quest_div");
                        div_object.innerHTML="";
                        if(typeof(data.read[0]) != "undefined"){
                            questdiv.append("<p class='first'><a href='"+readhref+"'>一、阅读</a><img style='padding-left:30%' src='${ctx}/static/image/gou.png' /></p>");
                        }else{
                            questdiv.append("<p class='first'><a href='"+readhref+"'>一、阅读</a><img style='padding-left:30%' src='${ctx}/static/image/u45_normal.png' /></p>");
                        }
                        if(typeof(data.talk[0]) != "undefined"){
                            questdiv.append("<p class='other'><a href='"+talkhref+"'>二、朗读</a><img style='padding-left:30%' src='${ctx}/static/image/gou.png' /></p>");
                        }else{
                            questdiv.append("<p class='other'><a href='"+talkhref+"'>二、朗读</a><img style='padding-left:30%' src='${ctx}/static/image/u45_normal.png' /></p>");
                        }
//                        questdiv.append("<p class='other'><a href='"+talkhref+"'>二、朗读</a><span style='font-family:应用字体;font-size:13px;font-weight:bold;font-style:normal;text-decoration:none;color:#FF0000;padding-left: 40%;'>X</span></p>");
//                        questdiv.append("<p class='other'>3、点评<span style='font-family:应用字体;font-size:13px;font-weight:bold;font-style:normal;text-decoration:none;color:#FF0000;padding-left: 40%;'>√</span></p>");
                        if(typeof(data.dianping[0]) != "undefined"){
                            questdiv.append("<p class='other'><a href='"+dianpinghref+"'>三、点评</a><img style='padding-left:30%' src='${ctx}/static/image/gou.png' /></p>");
                        }else{
                            questdiv.append("<p class='other'><a href='"+dianpinghref+"'>三、点评</a><img style='padding-left:30%' src='${ctx}/static/image/u45_normal.png' /></p>");
                        }
                    }
                    questdiv.css('position','absolute');//设置position
                    var taroff = tar.offset();
                    var width = tar.width();
                    var height = tar.height();
                    var pos;
			var curdate = string2date(curDateFormat).getDay();
                        if (curdate==4 || curdate==5 || curdate==6) {
                            $(".quest_div").css("background", "url('/static/image/u15_normal.png') no-repeat");
                            pos = {
                                top : taroff.top + width/2,
                                left : taroff.left - 110
                            }
                        } else {
                            $(".quest_div").css("background", "url(../static/image/u14_normal.png) no-repeat");
                            pos = {
                                top : taroff.top + width/2,
                                left : taroff.left
                            }
                        }
                    questdiv.css(pos).show();//设置left，top，并显示出来
                    questdiv.click(function(){
                        questdiv.hide();
                    })
                }
            })
        }

    });
});

function string2date(str){
  return new Date(Date.parse(str.replace(/-/g,  "/")));
}

function getQuestFinish(){
    var d = new Date();
    var totalDay = d.getDate();
    var $dateBox = $("#js-qiandao-list");
    var $dateLi = $dateBox.find("li");
//    var arr = new Array()
    for(i=0;i<totalDay;i++){
        var date = i+1;
        $.ajax({
            url: "${ctx}/modules/education/getQuestFinish?date="+date,
            success:function(data){
                if(typeof(data[0]) != "undefined" && typeof(data[1]) != "undefined" && typeof(data[2]) != "undefined"){
//                    arr.push(data[0].time)
                    var dateli = $(".date"+data[0].isFinish);
                    dateli.addClass("qiandao");
//                    console.log(dateli.attr('data-num'))
                }
//                console.log(data[0].time)
            }
        })
//        setTimeout("alert('5 seconds!')",5000)
    }

//    return arr;
}
</script>  
</body>  
</html>  
