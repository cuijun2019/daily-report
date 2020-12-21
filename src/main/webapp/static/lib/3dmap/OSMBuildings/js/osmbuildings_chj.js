/**
 * Created by Administrator on 2016/10/8 0008.
 */
var osmb = new OSMBuildings({
    baseURL: './OSMBuildings',
    minZoom: 13,
    maxZoom: 20,
    //position: { latitude:23.14146501, longitude:113.25575088 },
    //武汉
    position: { latitude: 30.630169, longitude:114.297804 },
    zoom: 15,
    state: true, // stores map position/rotation in url
    effects: ['shadows'],
    attribution: 'gisserver-chj.com <a href="https://gisserver-chj.com/">OSM Buildings</a>'
}).appendTo('3dMap');

osmb.addMapTiles(
    'http://www.gisserver-chj.com/wikitiles/{z}/{x}/{y}.png',
    {
        attribution: 'gisserver-chj.com <a href="https://gisserver-chj.com/">OpenStreetMap</a> 3D road building Map <a href="https://mapbox.com/">Mapbox</a>'
    }
);
/**
 * Created by Administrator on 2016/9/20 0020.
 */
var myMaps = [];

var callback = function(result) {
    myMaps=result;
    osmb.addGeoJSONTiles('http://www.gisserver-chj.com/wikitiles/{z}/{x}/{y}.json');
    console.log("Data access done!")
}
//***************************************************************************
//jsonp data request
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
                console.log(json.actionErrors);
            }
        },
        error: function(XHR, textStatus, errorThrown){
            console.log("ERREOR:" + textStatus);
            console.log("ERREOR: " + errorThrown);
        }
    });
}();

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
//***************************************************************************

var controlButtons = document.querySelectorAll('.control button');

for (var i = 0, il = controlButtons.length; i < il; i++) {
    controlButtons[i].addEventListener('click', function(e) {
        var button = this;
        var parentClassList = button.parentNode.classList;
        var direction = button.classList.contains('inc') ? 1 : -1;
        var increment;
        var property;

        if (parentClassList.contains('tilt')) {
            property = 'Tilt';
            increment = direction*10;
        }
        if (parentClassList.contains('rotation')) {
            property = 'Rotation';
            increment = direction*10;
        }
        if (parentClassList.contains('zoom')) {
            property = 'Zoom';
            increment = direction*1;
        }
        if (property) {
            osmb['set'+ property](osmb['get'+ property]()+increment);
        }
    });
}