/**
 * Created by Administrator on 2016/9/20 0020.
 */
var myMaps = [];

var callback = function(result) {
    myMaps=result;
    osmb.addGeoJSONTiles('http://www.gisserver-chj.com/wikitiles/{z}/{x}/{y}.json');

    //***************************************************************************

    osmb.on('pointermove', function(e) {
        var id = osmb.getTarget(e.detail.x, e.detail.y, function(id) {
            if (id) {
                document.body.style.cursor = 'pointer';
                osmb.highlight(id, '#f08000');
            } else {
                document.body.style.cursor = 'default';
                osmb.highlight(null);
            }
        });
    });

    osmb.on('pointerdown', function(e) {
        var id = osmb.getTarget(e.detail.x, e.detail.y, function(id) {
            if (id) {
                document.body.style.cursor = 'pointer';
                osmb.highlight(id, '#f08000');
                console.log(id);
            } else {
                document.body.style.cursor = 'default';
                osmb.highlight(null);
            }
        });
    });
    alert("done!");
}

!function(){
    $.ajax({
        url: "http://192.168.11.6:8081/get-gps-geo/Test",
        data:{
            cords:[[113,23]],
            callback:"callback",
            sttp:"geojson"
        },
        timeout:5000,
        type: "GET",
        async : false,//同步
        dataType:'jsonp',
        jsonp: 'callback',
        success: function (json) { //客户端jquery预先定义好的callback函数，成功获取跨域服务器上的json数据后，会动态执行这个callback函数
            if(json.actionErrors.length!=0){
                alert(json.actionErrors);
            }

        },
        error: function(XHR, textStatus, errorThrown){
            alert("ERREUR: " + textStatus);
            alert("ERREUR: " + errorThrown);
        }
    });
}();