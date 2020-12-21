/**
 * Created by chj on 2016/8/9.
 */
var myMap = {
    mapObj:null,
    proj:new OpenLayers.Projection("EPSG:4326"),
    gwms:[],
    initialize: function (domId,config) {
        this.mapObj = new OpenLayers.Map({
            div:"map",
            projection: "EPSG:900913",
            displayProjection: new OpenLayers.Projection("EPSG:4326"),
            maxExtent: [-20037508.34,-20037508.34,20037508.34,20037508.34],
            maxResolution:156543.0339,
            uints:'m',
            center:config.center,
            numZoomLevels:18,
            isBaseLayer:true
        });
        var layer = new OpenLayers.Layer.OFFLINEMAP();
        this.mapObj.addLayer(layer);
        return this;
    },
    init_wms:function(tmsparam){
        //geoserver地图服务
        var tmp = new OpenLayers.Layer.WMS(
            "wms",
            tmsparam[0] +"?service=WMS",
            {
                layers: tmsparam[1],
                type: "png",
                visibility: true,
                //url处理器，根据wms的guich
                getURL: this.get_wms_url1,
                format: "image/png",
                isBaseLayer: false,
                transparent: "true",
                opacity:.5
            }
        );
        this.mapObj.addLayer(tmp);
    },
    getMap:function(){
        return this.mapObj;
    },
    addLayer:function(layer){
        this.mapObj.addLayer(layer);
    },
    removeLayer:function(layer){
        this.mapObj.removeLayer(layer);
    },
    cernterat_84:function(x,y,z){
        var position = new OpenLayers.LonLat(x,y).transform( this.proj, this.mapObj.getProjectionObject());
        this.mapObj.setCenter(position, z );
    },
    get_wms_url1:function () {
        var proj = this.proj;
        var bounds = new OpenLayers.Bounds(config.tmsMaxExtent);
        bounds.transform(this.mapObj.getProjectionObject(),proj);
        var url = this.url;
        url += "&REQUEST=GetMap";
        url += "&SERVICE=WMS";
        url += "&VERSION=1.1.1";
        url += "&LAYERS=" + this.layers;
        url += "&FORMAT=" + this.format;
        url += "&TRANSPARENT=TRUE";
        url += "&SRS=" + "EPSG:4326";
        url += "&BBOX=" + bounds.toBBOX();
        url += "&WIDTH=" + this.tileSize.w;
        url += "&HEIGHT=" + this.tileSize.h;
        return url;
    },
    button1Func:function(){
        console.log("hander");
    },
    button2Func:function(){
        console.log("marker");
    },
    button3Func:function(){
        console.log("polyline");
    },
    button4Func:function(){
        console.log("circle");
    },
    button5Func:function(){
        console.log("polygon");
    },
    button6Func:function(){
        console.log("rectangle");
    },
    button7Func:function(){
        console.log("eraser");
    },
    OBJECT_NAME: 'myMap'
} ;
var myMap1 = {
    mapObj:null,
    proj:new OpenLayers.Projection("EPSG:4326"),
    gwms:[],
    initialize: function (domId,config) {
        this.mapObj = new OpenLayers.Map({
            div:"map2",
            projection: "EPSG:900913",
            displayProjection: new OpenLayers.Projection("EPSG:4326"),
            maxExtent: [-20037508.34,-20037508.34,20037508.34,20037508.34],
            maxResolution:156543.0339,
            uints:'m',
            center:config.center,
            numZoomLevels:18,
            isBaseLayer:true
        });
        var layer = new OpenLayers.Layer.OFFLINEMAP();
        this.mapObj.addLayer(layer);
        return this;
    },
    init_wms:function(tmsparam){
        //geoserver地图服务
        var tmp = new OpenLayers.Layer.WMS(
            "wms",
            tmsparam[0] +"?service=WMS",
            {
                layers: tmsparam[1],
                type: "png",
                visibility: true,
                //url处理器，根据wms的guich
                getURL: this.get_wms_url1,
                format: "image/png",
                isBaseLayer: false,
                transparent: "true",
                opacity:.5
            }
        );
        this.mapObj.addLayer(tmp);
    },
    getMap:function(){
        return this.mapObj;
    },
    addLayer:function(layer){
        this.mapObj.addLayer(layer);
    },
    removeLayer:function(layer){
        this.mapObj.removeLayer(layer);
    },
    cernterat_84:function(x,y,z){
        var position = new OpenLayers.LonLat(x,y).transform( this.proj, this.mapObj.getProjectionObject());
        this.mapObj.setCenter(position, z );
    },
    get_wms_url1:function () {
        var proj = this.proj;
        var bounds = new OpenLayers.Bounds(config.tmsMaxExtent);
        bounds.transform(this.mapObj.getProjectionObject(),proj);
        var url = this.url;
        url += "&REQUEST=GetMap";
        url += "&SERVICE=WMS";
        url += "&VERSION=1.1.1";
        url += "&LAYERS=" + this.layers;
        url += "&FORMAT=" + this.format;
        url += "&TRANSPARENT=TRUE";
        url += "&SRS=" + "EPSG:4326";
        url += "&BBOX=" + bounds.toBBOX();
        url += "&WIDTH=" + this.tileSize.w;
        url += "&HEIGHT=" + this.tileSize.h;
        return url;
    },
    OBJECT_NAME: 'myMap'
} ;