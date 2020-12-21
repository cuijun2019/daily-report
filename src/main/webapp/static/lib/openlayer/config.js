/**
 * Created by Administrator on 16-7-28.
 */
//configIP +=":8080/wikitiles/${z}/${x}/${y}.png";
var config={
    name: "MYMAP",
    //url: ["http://192.168.1.200:10080/wikitiles/${z}/${x}/${y}.png","http://192.168.1.200:8080/ltemr/static/lib/openlayer/tiles/${z}/${x}/${y}.png"],
    url:[configIP],
    attribution: "&copy; <a href='http://www.blog.gisserver-chj.com'>blog</a> chenhj",
    CLASS_NAME: "OpenLayers.Layer.OFFLINEMAP",
    //maxExtent: [12575324,2630000,12653906,2678446],
    tmsMaxExtent:[109.34692,19.84131,117.66357,25.75195],
    center: [12622971.476333, 2647975.4854095],
    tmsparam:["http://192.168.8.68:10080/geoserver/test_tmp/wms","test_tmp:guangdong_tmp_84"],
    zoomlevel:[10,18]
}