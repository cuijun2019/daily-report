<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/layouts/taglibs.jsp" %>
    <meta name="applicable-device" content="mobile" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>阅读</title>
    <link rel="stylesheet" href="${ctx}/wyx/read.css">
    <script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/easyui/jquery.cookie.js"></script>
    <script>
    $(window).load(function() {
        $("#status").fadeOut();
        $("#preloader").delay(350).fadeOut("slow");
    })
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
    <!--header 开始-->
    <header>
        <div class="header">
            <div class="read-back">
                <a class="new-a-back" href="javascript:history.back();" data-color="white">返回</a>
            </div>
        </div>
    </header>
    <!--header 结束-->
    <div >
    <div class="read-box">
        <div class="read-top">
            <div class="read-biaoti">
                <input type="text" class="title" id="title" placeholder="请输入文章题目或者书名"/>
            </div>
            <div class="read-content">
                <textarea cols="30" rows="15" id="readfeel" class="readfeel" placeholder="请输入读后感......"></textarea>
            </div>
            <input type="hidden" id="articleid" value="-1"/>
            <input type="hidden" id="createTime" value="-1"/>
            <input type="hidden" id="isSave" value="-1"/>
        </div>
        <div class="read-down">
            <button class="read-btn" value="提交" onclick="sendReadfeel()" />
        </div>
    </div>
  </div>  
    

</div>
<script>
    $(function() {
        getReadMsg();
    })
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
    function getDay(day) {
        var today = new Date();
        var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24 * day;
        today.setTime(targetday_milliseconds); //注意，这行是关键代码
        var tYear = today.getFullYear();
        var tMonth = today.getMonth();
        var tDate = today.getDate();
        tMonth = doHandleMonth(tMonth + 1);
        tDate = doHandleMonth(tDate);

        return tYear + "-" + tMonth + "-" + tDate;
    }
    function doHandleMonth(month) {
        var m = month;
        if (month.toString().length == 1) {
            m = "0" + month;
        }
        return m;
    }
    function getReadMsg(){
//        var d = new Date();
        var totalDay = getDay(0);
        if(getParam("date") != null){
            var url = "${ctx}/modules/education/getReadMsg?date="+getParam("date");
        }else{
            var url = "${ctx}/modules/education/getReadMsg?date="+totalDay;
        }
        $.ajax({
            url: url,
            success:function(data){
                if(typeof(data[0]) != "undefined"){
                    $("#articleid").val(data[0].id);
                    $("#title").val(data[0].article);
                    $("#readfeel").val(data[0].thought);
                    $("#createTime").val(data[0].createTime);
                    $("#isSave").val(data[0].save);
                }else{
                    return false;
                }
            }
        })
    }
    
    function sendReadfeel(){
        var articleid = $("#articleid").attr("value");
        var text = document.getElementById('title').value;
        var content = document.getElementById('readfeel').value;
        var isSave = $("#isSave").attr("value");
        var createTime =  $("#createTime").attr("value");
        var d = new Date();
        var totalDay = d.getDate(); //获取当前月的天数
        if(dateSubstr(getParam("date")) < totalDay){
            alert("不能修改之前的读后感！")
            return false;
        }
        if(text==""){
            alert("请输入文章题目或者书名！")
            return false;
        }
        if(content==""||content=="输入读后感..."){
            alert("请发表读后感！")
            return false;
        }
            $.ajax({
                url: "${ctx}/modules/education/saveOrUpdate",
                type: "POST",
                data : {"articleid":articleid,"article":text,"thought":content,"isSave":"1","createTime":createTime},
                success:function(data){
                    getReadMsg();
                    alert("保存成功！")
                }
            })
    }
    function getParam(paramName) {
        paramValue = "", isFound = !1;
        if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
            arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
            while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
        }
        return paramValue == "" && (paramValue = null), paramValue
    }
   function dateSubstr(date){
       var b = date.substr(date.length - 2);
      if(b.substr(0, 1) == "0"){
          b = b.substr(-1);
      }
       return b;
   }
</script>  
</body>  
</html>  
