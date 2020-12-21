function createVector(style, features) {
    // 创建地图符号化
    var refresh = new OpenLayers.Strategy.Refresh({force: true, active: true});
/*    var st= $.extend({ label: "${NAME}"},style.defaultStyle);
    style.defaultStyle=st;*/
    var myStyles = new OpenLayers.StyleMap({
        "default": style,
        //数据选择事件
        "select": new OpenLayers.Style({
            //fillColor: "#66ccff",
            strokeColor: "#3399ff",
            graphicZIndex: 2,
            cursor: "pointer"
        }),
        renderers: ["Canvas", "SVG", "VML"]
    });

    //添加图层
    var vector = new OpenLayers.Layer.Vector("用户分布", {
        projection: new OpenLayers.Projection("EPSG:4326"),
        styleMap: myStyles
// strategies:[new OpenLayers.Strategy.fixed(),refresh]
//,rendererOptions: { zIndexing: true }
    });
    vector.addFeatures(features);
    vector.refreshStr=refresh;
    return vector;
}


//创建9级颜色的样式，指标值是0~8
function createDefaultStyle() {
    var rs = [];
    for (var i = 0; i < 9; i++) {
        //var index=8-i;
        var startColor = defaultColors[8];
        rs[i] = new OpenLayers.Rule({
            filter: new OpenLayers.Filter.Comparison({
                type: OpenLayers.Filter.Comparison.EQUAL_TO,
                property: "level",
                value: i
            }),
            symbolizer: {
                fillOpacity: 0.9,
                fillColor: startColor,
                strokeColor: ""
            }
        });
    }
    //添加样式过滤器
    var style = new OpenLayers.Style(
        {
            graphicWidth: 21,
            graphicHeight: 25,
            graphicYOffset: -28, // shift graphic up 28 pixels
            label: "${NAME}", // label will be foo attribute value
            fillColor: "",
            strokeColor: ""
        }, {
            rules: rs
        }
    );
    return style;
}