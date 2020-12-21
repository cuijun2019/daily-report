function createmapObject() {
    var map = new mapObject();
    return map;
}

//创建mapObject类
function mapObject() {
}

//创建默认的省级地图
mapObject.prototype = {
    CreateProvinceMap: function (mapContainer, popupText) {
        this.Container = mapContainer;
        this.PopupText = popupText;
        var bd = new OpenLayers.Bounds(109.624692, 20.061808, 117.354359, 25.519618);
        this.loadMap(['gz:cityA_region'], bd);
    },

    CreateGuangzhouMap: function (mapContainer, popupText) {
        this.Container = mapContainer;
        this.PopupText = popupText;
        var bd = new OpenLayers.Bounds(112.9532267, 22.54005297, 114.0555622, 23.93588196);
        this.loadMap(['gz:gz_bianjie'], bd);
    },

    CreateGzCustomLayerMap: function (mapContainer, popupText, layers) {
        this.Container = mapContainer;
        this.PopupText = popupText;
        var bd = new OpenLayers.Bounds(113.107443, 22.628962, 113.88286, 23.713376);
        this.loadMap(layers, bd);
    },

    CreateCustomLayerMap: function (mapContainer, popupText, layers, b) {
        this.Container = mapContainer;
        this.PopupText = popupText;
        var bd = new OpenLayers.Bounds(b.x1, b.y1, b.x2, b.y2);
        this.loadMap(layers, bd);
    },

    loadMap: function (layers, bounds) {
        var format = 'image/png';
        //范围(左下角，右上角)
        var options = {
            controls: [
                new OpenLayers.Control.Zoom(),
                new OpenLayers.Control.Navigation({
                    handleRightClicks: true
//                    zoomWheelEnabled: false
                })
            ],
            opacity: 0,
            background: 'black',
            fractionalZoom: true,
            maxExtent: bounds,
            projection: "EPSG:2327",
            units: 'degrees'
        };


        this.map = new OpenLayers.Map(this.Container, options);
        this.map.addControl(new OpenLayers.Control.LayerSwitcher());
        this.map.addControl(new OpenLayers.Control.MousePosition());

        /*    var panel = new OpenLayers.Control.NavToolbar();
         this.map.addControl(panel);*/
        var space = layers[0].split(':')[0];
        var url = 'http://localhost:8980/geoserver/' + space +'/wms';

        this.map.mapObject = this;
        var ls = [];
        ls[0] = new OpenLayers.Layer.WMS(
            '图层', url,
            {
                LAYERS: layers,
                STYLES: '',
                format: format,
                tiled: true,
                transparent: false
            },
            {
                singleTile: true,
                ratio: 1,
                isBaseLayer: true
            }
        );
        this.map.addLayers(ls);
        this.map.zoomToMaxExtent();
    },

    SetSelectedFeature: function (vector) {
        this.selectedVector = vector;
        if (this.selectF != null) {
            this.selectF.destroy();
            this.selectF = null;
        }
        var select = new OpenLayers.Control.SelectFeature(vector,
            {
                hover: true,
                box: false,
                onSelect: this.onFeatureSelect
            });
        this.selectF = select;
        this.map.addControl(select);
        select.activate();
        select.handlers.feature.stopDown = false;
        select.handlers.feature.stopUp = false;
    },

    onFeatureSelect: function (feature) {
        return;
        if (this.popup != null) {
            this.map.removePopup(this.popup);
            this.popup.destroy();
            this.popup = null;
        }
        this.selectedFeature = feature;
        var attrs = feature.attributes;
        var text = this.map.mapObject.PopupText(feature);
        this.popup = new OpenLayers.Popup.CSSFramedCloud("chicken",
            feature.geometry.getBounds().getCenterLonLat(),
            null,
            text,
            null, true, this.map.mapObject.onPopupClose);
        this.popup.setOpacity(0.5);
        this.map.mapObject.popup = this.popup;
        feature.popup = this.popup;
        this.map.mapObject.Popup = this.popup;
        this.map.addPopup(this.popup);
    },
    onPopupClose: function () {
        this.map.removePopup(this.map.mapObject.Popup);
        //this.selectedVector.unselect(this.selectedFeature);
    }
}

function createDistanceMeasureControl() {
    var sketchSymbolizers = {
        "Point": {
            pointRadius: 4,
            graphicName: "square",
            fillColor: "white",
            fillOpacity: 1,
            strokeWidth: 1,
            strokeOpacity: 1,
            strokeColor: "#333333"
        },
        "Line": {
            strokeWidth: 2,
            strokeOpacity: 1,
            strokeColor: "#666666",
            strokeDashstyle: "dash"
        },
        "Polygon": {
            strokeWidth: 2,
            strokeOpacity: 1,
            strokeColor: "#666666",
            fillColor: "white",
            fillOpacity: 0.3
        }
    };
    var style = new OpenLayers.Style();
    style.addRules([
        new OpenLayers.Rule({symbolizer: sketchSymbolizers})
    ]);
    var styleMap = new OpenLayers.StyleMap({"default": style});

    // allow testing of specific renderers via "?renderer=Canvas", etc
    var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
    renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;

    var measure = new OpenLayers.Control.Measure(
        OpenLayers.Handler.Path, {
            persist: true,
            handlerOptions: {
                layerOptions: {
                    renderers: renderer,
                    styleMap: styleMap
                }
            }
        }
    );
    return measure;
}

