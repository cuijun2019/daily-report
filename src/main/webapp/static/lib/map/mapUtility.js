/**
 *
 * @param layer 图层名称，格式->mmdp:gaosugonglu_polyline
 * @param returnColumn 要过滤的列:
 * 1、获取图层信息，不过滤
 * @param callback 获取到图层的回调函数
 */
function getWfsLayerForSearch(layer, callback) {
    var layerName = layer.split('' +':')[1];
    var xmlPara = "<?xml version='1.0' encoding='UTF-8'?>"
        + "<wfs:GetFeature service='WFS' version='1.0.0' "
        + "xmlns:wfs='http://www.opengis.net/wfs' "
        + "xmlns:gml='http://www.opengis.net/gml' "
        + "xmlns:ogc='http://www.opengis.net/ogc' "
        + "xmlns:topp='http://www.openplans.org/topp' "
        + "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
        + "xsi:schemaLocation='http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.0.0/wfs.xsd'>"
        + "<wfs:Query typeName='" + layer + "' >"
        + "</wfs:Query>"
        + "</wfs:GetFeature>";
    var request = OpenLayers.Request.POST({
        url: 'http://localhost:8980/geoserver/wfs?',
        data: xmlPara,
        callback: callback
    });
}




/**
 *
 * @param layer 图层名称，格式->mmdp:gaosugonglu_polyline
 * @param returnColumn 要过滤的列:
 * 1、不需要过滤，返回所有图元->名称
 * 2、精确匹配过滤,返回符合条件的列->名称=值
 * 3、模糊匹配过滤，返回符合条件的列->名称%值
 * @param callback 获取到图层的回调函数
 */
function getWfsLayer(layer, returnColumn, callback) {
    var filter_1_0 = new OpenLayers.Format.Filter.v1_0_0();
    //也可以使用1.1版本构造过滤条件
    var layerName = layer.split(':')[1];
    //var filter_1_1 = new OpenLayers.Filter({ version: "1.1.0" });
    var xml = new OpenLayers.Format.XML(); //构造xml格式的文件
    var filter_like;
    if (returnColumn.indexOf('=') > 0) {
        var vs = returnColumn.split('=');
        filter_like = new OpenLayers.Filter.Comparison({//比较操作符
            type: OpenLayers.Filter.Comparison.EQUAL_TO, //精确查询
            property: layerName + ":" + vs[0],
            value: vs[1]
        });
    } else if (returnColumn.indexOf('%') > 0) {
        var vs = returnColumn.split('=');
        if (vs.length < 2) vs[1] = '';
        filter_like = new OpenLayers.Filter.Comparison({//比较操作符
            type: OpenLayers.Filter.Comparison.LIKE, //精确查询
            property: layerName + ":" + vs[0],
            value: "*" + vs[1] + '*'
        });
    } else {
        filter_like = new OpenLayers.Filter.Comparison({//比较操作符
            type: OpenLayers.Filter.Comparison.LIKE, //精确查询
            property: layerName + ":" + returnColumn,
            value: "*"
        });
    }

    var filter = new OpenLayers.Filter.Logical({
        type: OpenLayers.Filter.Logical.OR,
        filters: [filter_like]
    });
    //构造指定格式的xml
    var xmlFilter = xml.write(filter_1_0.write(filter));
    //mmdp:cityA_region

    var xmlPara = "<?xml version='1.0' encoding='UTF-8'?>"
        + "<wfs:GetFeature service='WFS' version='1.0.0' "
        + "xmlns:wfs='http://www.opengis.net/wfs' "
        + "xmlns:gml='http://www.opengis.net/gml' "
        + "xmlns:ogc='http://www.opengis.net/ogc' "
        + "xmlns:topp='http://www.openplans.org/topp' "
        + "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
        + "xsi:schemaLocation='http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.0.0/wfs.xsd'>"
        + "<wfs:Query typeName='" + layer + "' >"
        + xmlFilter
        + "</wfs:Query>"
        + "</wfs:GetFeature>";
    var request = OpenLayers.Request.POST({
        url: 'http://localhost:8980/geoserver/wfs?',
        data: xmlPara,
        callback: callback
    });
}


function createRectangle(ln1, lt1, ln2, lt2) {
    var p1 = ln1 + ' ' + lt1;
    var p2 = ln1 + ' ' + lt2;
    var p3 = ln2 + ' ' + lt2;
    var p4 = ln2 + ' ' + lt1;
    var wkt = "POLYGON((" + p1 + "," + p2 + "," + p3 + "," + p4 + "," + p1 + "))";
    var wkt_c = new OpenLayers.Format.WKT();
    var geometry = wkt_c.read(wkt);
    return geometry;
}

var wkt_reader;
function toGeometryFromWkt(wkt) {
    if (wkt_reader == null) {
        wkt_reader = new OpenLayers.Format.WKT();
    }
    var geometry = wkt_reader.read(wkt);//read方法返回OpenLayers.Feature.Vector类型
    return geometry;
}

/**
 * 根据传入的点分段，以矩形包住
 * @param components 数组
 * @param startPoint 起始点
 * @param maxPoint 起始点后，最大的x值及y值
 */
function splitRoadToRegion(components, startPoint, maxPoint, minPoint) {
    if (components == null || components.length < 1) return;
    var array = new Array();
    var count = 0;
    var distance = 0.004;
    for (var i = 0; i < components.length; i++) {
        var c = components[i];
        if (c.x != null) { //是具体的点
            if (minPoint == null) {
                startPoint = {x: c.x, y: c.y};
                minPoint = {x: c.x, y: c.y};
                maxPoint = {x: c.x, y: c.y};
                continue;
            }
            //求边框
            if (maxPoint.x < c.x) maxPoint.x = c.x;
            if (maxPoint.y < c.y) maxPoint.y = c.y;
            if (minPoint.x > c.x) minPoint.x = c.x;
            if (minPoint.y > c.y) minPoint.y = c.y;

            //哪条轴偏移最大就按那条轴(x,y)
            var offsetX = Math.abs(startPoint.x - c.x);
            var offsetY = Math.abs(startPoint.y - c.y);
            if (offsetX < offsetY) {//根据纬度来划分
                var isContain = false;
                while ((maxPoint.y - minPoint.y) > distance) { //划分一段
                    count++;
                    isContain = true;
                    var seg;
                    if (startPoint.y > c.y) {  //趋势越来越小
                        seg = {x1: minPoint.x, y1: maxPoint.y - distance, x2: maxPoint.x, y2: maxPoint.y, index: count};
                        maxPoint.y = maxPoint.y - distance;
                    } else { //趋势越来越大
                        seg = {
                            x1: minPoint.x,
                            y1: minPoint.y,
                            x2: maxPoint.x,
                            y2: (minPoint.y + distance),
                            index: count
                        };
                        minPoint.y = minPoint.y + distance;
                        startPoint.y = (minPoint.y + distance);
                    }
                    startPoint.x = maxPoint.x;
                    array.push(seg);
                }
                if (isContain && startPoint != null) {
                    maxPoint.x = minPoint.x;
                    minPoint.y = startPoint.y;
                }
            } else {//根据经度来划分
                var isContain = false;
                while ((maxPoint.x - minPoint.x) > distance) { //划分一段
                    count++;
                    var seg;
                    isContain = true;
                    if (startPoint.x > c.x) {  //趋势越来越小
                        seg = {
                            x1: (maxPoint.x - distance),
                            y1: minPoint.y,
                            x2: (maxPoint.x),
                            y2: maxPoint.y,
                            index: count
                        };
                        maxPoint.x = maxPoint.x - distance;
                        startPoint.x = minPoint.x;
                    } else { //趋势越来越大
                        seg = {
                            x1: minPoint.x,
                            y1: minPoint.y,
                            x2: (minPoint.x + distance),
                            y2: maxPoint.y,
                            index: count
                        };
                        minPoint.x = minPoint.x + distance;
                        startPoint.x = minPoint.x + distance;
                    }
                    startPoint.y = maxPoint.y;
                    array.push(seg);
                }
                /*   if (isContain&& startPoint != null) {
                 minPoint.x = startPoint.x;
                 minPoint.y = startPoint.y;
                 }*/
            }

        } else if (c.components != null && c.components.length > 0) {
            if (maxPoint != null) {
                minPoint.x = startPoint.x;
                minPoint.y = startPoint.y;
                maxPoint.x = -9999999;
                maxPoint.y = -9999999;
            }
            var as = splitRoadToRegion(c.components, startPoint, minPoint, maxPoint);
            if (as != null && as.length > 0) {
                for (var j = 0; j < as.length; j++)
                    array.push(as[j]);
            }
        }
    }
    if (minPoint != null) {
        count++;
        var sg = {x1: minPoint.x, y1: minPoint.y, x2: maxPoint.x, y2: maxPoint.y, index: count};
        array.push(sg);
    }
    return array;
}

var
    splitDistance = 50, //间隔
    minDistance = 40,
    maxDistance = 60,
    centerDistance = 25,
    deviation = 10,
    distanceDegree = 0.0002;

/*    splitDistance, //间隔 长度
    minDistance, //<spliDistance
    maxDistance,// =splitDistance
    centerDistance,//1/2 splitDistance
    deviation = 50,
    distanceDegree; //宽度*/

var centers = new Array();


function roadToRegionsByParam(components, startPoint, grideParam) {
     splitDistance = parseInt(grideParam.splitDistance); //间隔 长度
     minDistance = parseInt(grideParam.minDistance); //<spliDistance
     maxDistance =parseInt(grideParam.maxDistances) ;// =splitDistance
     centerDistance =parseInt(grideParam.centerDistance) ;//1/2 splitDistance
     distanceDegree = parseFloat(grideParam.distanceDegree); //距离度
    if (components == null || components.length < 1) return;
    var array = new Array();
    var seg1 = new Array();
    var seg2 = new Array();
    var allD = 0;//划分距离为500M
    var p1, p2, p1_pre, p2_pre, center;
    for (var i = 0; i < components.length; i++) {
        var cp = components[i];
        var c = cp;
        if (c.components != null && c.components.length > 0) {
            if (c.x != null)
                startPoint = {x: c.x, y: c.y};
            var as = roadToRegionsByParam(c.components, startPoint,grideParam);
            if (as != null && as.length > 0) {
                for (var j = 0; j < as.length; j++)
                    array.push(as[j]);
            }
        }
        else if (cp.x != null) { //是具体的点
            if (startPoint == null) {
                startPoint = {x: cp.x, y: cp.y};
                continue;
            }
            //哪条轴偏移最大就按那条轴(x,y)
            var offsetX = Math.abs(startPoint.x - cp.x);
            var offsetY = Math.abs(startPoint.y - cp.y);
            var d = getDistance(startPoint.y, startPoint.x, cp.y, cp.x);
            if ((allD + d) > centerDistance && center == null) {
                var d2 = (centerDistance - allD);
                center = getDistancePoint(startPoint, c, d2); //求到中点
                centers.push(center);
            }
            if ((allD + d) > maxDistance) { //于600要截断
                var d2 = (splitDistance - allD);
                c = getDistancePoint(startPoint, c, d2);
                allD = splitDistance;//
                i--;//从截取的点继续与结束点比较
            } else {
                allD += d;
            }
            //多边的前两个点
            var p_p1 = null, p_p2 = null, p1_t = null, p2_t = null;
            if (offsetX < offsetY) {//根据纬度来划分
                //根据倾向求多边形的点顺序
                p1_t = {x: c.x - distanceDegree, y: c.y};
                p2_t = {x: c.x + distanceDegree, y: c.y};
                p1_pre = {x: startPoint.x - distanceDegree, y: startPoint.y};
                p2_pre = {x: startPoint.x + distanceDegree, y: startPoint.y};
            }
            else { //根据经度来区分
                p1_t = {x: c.x, y: c.y - distanceDegree};
                p2_t = {x: c.x, y: c.y + distanceDegree};
                p2_pre = {x: startPoint.x, y: startPoint.y + distanceDegree};
                p1_pre = {x: startPoint.x, y: startPoint.y - distanceDegree};
            }
            p1 = p1_t;
            p2 = p2_t;
            if (seg1.length > 0) { //根据前一点线方向确定当前点的方向
                p_p1 = seg1[seg1.length - 1];
                p_p2 = seg2[seg2.length - 1];
                if ((c.y > startPoint.y && c.x > startPoint.x) || (c.y < startPoint.y && c.x < startPoint.x)) {//向右倾
                    if ((p_p1.y < p_p2.y)) {  //竖向  小->大
                        if (offsetX < offsetY) {//横向  大->小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    } else if ((p_p1.y > p_p2.y)) {//竖向 大－>小
                        if (offsetX > offsetY) {//竖向 大－>小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    } else if ((p_p1.x > p_p2.x)) { //横向 大->小
                        if (offsetX < offsetY) {// 横向 大->小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    } else if ((p_p1.x < p_p2.x)) {//横向 小->大
                        if (offsetX > offsetY) {//竖向  大->小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    }
                } else if ((c.y > startPoint.y && startPoint.x > c.x) || (c.y < startPoint.y && startPoint.x < c.x)) {//向左倾
                    if ((p_p1.y > p_p2.y)) { //竖向  大－>小
                        if (offsetX > offsetY) {//竖向 大->小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    } else if ((p_p1.x > p_p2.x)) {//横向 ：大－>小
                        if (offsetX < offsetY) {//横向 改成：大->
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    }
                }
            }
            startPoint.x = c.x;
            startPoint.y = c.y;
            if (i < (components.length - 1)) { //最后一点不处理
                /*  startPoint.x = c.x;
                 startPoint.y = c.y;*/

            } else {
                //  centers.push({x: cp.x, y: cp.y});
            }
            if (seg1.length < 1) { //加入第一点
                seg1.push(p1_pre);
                seg2.push(p2_pre);
            }
            seg1.push(p1);
            seg2.push(p2);
            if (minDistance <= allD && allD <= maxDistance) { //在一个范围内，则截取为一段
                var seg = new Array();
                for (var k = 0; k < seg1.length; k++) {
                    seg.push(seg1[k]);
                }
                for (var k = (seg2.length - 1); k > -1; k--) {
                    seg.push(seg2[k]);
                }
                seg.push(seg[0]);
                array.push(seg);
                //初始化
                seg1 = new Array();
                seg2 = new Array();
                seg1.push(p1);
                seg2.push(p2);
                allD = 0;
                center = null;
            }
        }
    }
    //处理最后一段
    if (seg1.length > 0 && p1 != null) {
        var seg = new Array();
        for (var k = 0; k < seg1.length; k++) {
            seg.push(seg1[k]);
        }
        for (var k = (seg2.length - 1); k > -1; k--) {
            seg.push(seg2[k]);
        }
        seg.push(seg[0]);
        array.push(seg);
        if (center == null) {
            center = getMiddlePoint(startPoint, c);
            centers.push(center);
        }
    }
    return array;
}


function roadToRegions(components, startPoint) {
   /* splitDistance = parseInt(grideParam.splitDistance); //间隔 长度
    minDistance = parseInt(grideParam.minDistance); //<spliDistance
    maxDistance =parseInt(grideParam.maxDistances) ;// =splitDistance
    centerDistance =parseInt(grideParam.centerDistance) ;//1/2 splitDistance
    distanceDegree = parseFloat(grideParam.distanceDegree); //距离度*/
    if (components == null || components.length < 1) return;
    var array = new Array();
    var seg1 = new Array();
    var seg2 = new Array();
    var allD = 0;//划分距离为500M
    var p1, p2, p1_pre, p2_pre, center;
    for (var i = 0; i < components.length; i++) {
        var cp = components[i];
        var c = cp;
        if (c.components != null && c.components.length > 0) {
            if (c.x != null)
                startPoint = {x: c.x, y: c.y};
            var as = roadToRegions(c.components, startPoint);
            if (as != null && as.length > 0) {
                for (var j = 0; j < as.length; j++)
                    array.push(as[j]);
            }
        }
        else if (cp.x != null) { //是具体的点
            if (startPoint == null) {
                startPoint = {x: cp.x, y: cp.y};
                continue;
            }
            //哪条轴偏移最大就按那条轴(x,y)
            var offsetX = Math.abs(startPoint.x - cp.x);
            var offsetY = Math.abs(startPoint.y - cp.y);
            var d = getDistance(startPoint.y, startPoint.x, cp.y, cp.x);
            if ((allD + d) > centerDistance && center == null) {
                var d2 = (centerDistance - allD);
                center = getDistancePoint(startPoint, c, d2); //求到中点
                centers.push(center);
            }
            if ((allD + d) > maxDistance) { //于600要截断
                var d2 = (splitDistance - allD);
                c = getDistancePoint(startPoint, c, d2);
                allD = splitDistance;//
                i--;//从截取的点继续与结束点比较
            } else {
                allD += d;
            }
            //多边的前两个点
            var p_p1 = null, p_p2 = null, p1_t = null, p2_t = null;
            if (offsetX < offsetY) {//根据纬度来划分
                //根据倾向求多边形的点顺序
                p1_t = {x: c.x - distanceDegree, y: c.y};
                p2_t = {x: c.x + distanceDegree, y: c.y};
                p1_pre = {x: startPoint.x - distanceDegree, y: startPoint.y};
                p2_pre = {x: startPoint.x + distanceDegree, y: startPoint.y};
            }
            else { //根据经度来区分
                p1_t = {x: c.x, y: c.y - distanceDegree};
                p2_t = {x: c.x, y: c.y + distanceDegree};
                p2_pre = {x: startPoint.x, y: startPoint.y + distanceDegree};
                p1_pre = {x: startPoint.x, y: startPoint.y - distanceDegree};
            }
            p1 = p1_t;
            p2 = p2_t;
            if (seg1.length > 0) { //根据前一点线方向确定当前点的方向
                p_p1 = seg1[seg1.length - 1];
                p_p2 = seg2[seg2.length - 1];
                if ((c.y > startPoint.y && c.x > startPoint.x) || (c.y < startPoint.y && c.x < startPoint.x)) {//向右倾
                    if ((p_p1.y < p_p2.y)) {  //竖向  小->大
                        if (offsetX < offsetY) {//横向  大->小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    } else if ((p_p1.y > p_p2.y)) {//竖向 大－>小
                        if (offsetX > offsetY) {//竖向 大－>小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    } else if ((p_p1.x > p_p2.x)) { //横向 大->小
                        if (offsetX < offsetY) {// 横向 大->小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    } else if ((p_p1.x < p_p2.x)) {//横向 小->大
                        if (offsetX > offsetY) {//竖向  大->小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    }
                } else if ((c.y > startPoint.y && startPoint.x > c.x) || (c.y < startPoint.y && startPoint.x < c.x)) {//向左倾
                    if ((p_p1.y > p_p2.y)) { //竖向  大－>小
                        if (offsetX > offsetY) {//竖向 大->小
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    } else if ((p_p1.x > p_p2.x)) {//横向 ：大－>小
                        if (offsetX < offsetY) {//横向 改成：大->
                            p1 = p2_t;
                            p2 = p1_t;
                        }
                    }
                }
            }
            startPoint.x = c.x;
            startPoint.y = c.y;
            if (i < (components.length - 1)) { //最后一点不处理
                /*  startPoint.x = c.x;
                 startPoint.y = c.y;*/

            } else {
                //  centers.push({x: cp.x, y: cp.y});
            }
            if (seg1.length < 1) { //加入第一点
                seg1.push(p1_pre);
                seg2.push(p2_pre);
            }
            seg1.push(p1);
            seg2.push(p2);
            if (minDistance <= allD && allD <= maxDistance) { //在一个范围内，则截取为一段
                var seg = new Array();
                for (var k = 0; k < seg1.length; k++) {
                    seg.push(seg1[k]);
                }
                for (var k = (seg2.length - 1); k > -1; k--) {
                    seg.push(seg2[k]);
                }
                seg.push(seg[0]);
                array.push(seg);
                //初始化
                seg1 = new Array();
                seg2 = new Array();
                seg1.push(p1);
                seg2.push(p2);
                allD = 0;
                center = null;
            }
        }
    }
    //处理最后一段
    if (seg1.length > 0 && p1 != null) {
        var seg = new Array();
        for (var k = 0; k < seg1.length; k++) {
            seg.push(seg1[k]);
        }
        for (var k = (seg2.length - 1); k > -1; k--) {
            seg.push(seg2[k]);
        }
        seg.push(seg[0]);
        array.push(seg);
        if (center == null) {
            center = getMiddlePoint(startPoint, c);
            centers.push(center);
        }
    }
    return array;
}


function getDistancePointBy(sp, ep, distance) {
    var ym = sp.y + (ep.y - sp.y) / 2;
    var xm;
    if (ep.y == sp.y) {
        xm = ep.x - ((ep.x - sp.x) / 2);
    } else {
        xm = ep.x - (((ep.y - ym) * (ep.x - sp.x)) / (ep.y - sp.y));
    }
    if (isNaN(xm) || isNaN(xm)) { //无效的数据
        alert('无效的数据');
    }
    var d = getDistance(sp.y, sp.x, ym, xm);
    if ((distance - deviation) <= d && d <= (distance + deviation)) {
        return {x: xm, y: ym};
    }
    if ((distance - deviation) > d) { //获取的中点距离偏小
        return getDistancePointBy({x: xm, y: ym}, ep, (distance - d));
    } else {//获取的中点距离偏大
        return getDistancePointBy(sp, {x: xm, y: ym}, distance);
    }
}

function getMiddlePoint(sp, ep) {
    var d = getDistance(sp.y, sp.x, ep.y, ep.x);
    return getDistancePoint(sp, ep, d / 2);
}

function getDistancePoint(sp, ep, distance) {
    var interval = 1;
    if (distance == 0) return sp;
    while (true) {
        var p = getOffsetDistance(sp, ep, distance, interval);
        if (p != 0) {
            return p;
        }
        interval++;
    }
}

function getOffsetDistance(sp, ep, distance, interval) {
    var offsetX = Math.abs(sp.x - ep.x);
    var offsetY = Math.abs(sp.y - ep.y);
    var offset = 0.0001 * interval;
    var xm, ym;
    if (offsetX > offsetY) { //横向递增/减
        if (sp.x > ep.x)
            offset = -0.0001 * interval;
        xm = sp.x + offset;
        if (ep.y == sp.y)
            ym = sp.y;
        else
            ym = ep.y - ((ep.y - sp.y) * (ep.x - xm)) / (ep.x - sp.x);
    } else { //竖向递增/减
        if (sp.y > ep.y)
            offset = -0.0001 * interval;
        ym = sp.y + offset;
        xm = ep.x - (((ep.y - ym) * (ep.x - sp.x)) / (ep.y - sp.y));
    }
    var d = getDistance(sp.y, sp.x, ym, xm);
    if ((distance - deviation) <= d && d <= (distance + deviation)) {
        return {x: xm, y: ym};
    } else if (d > (distance + deviation)) {
        return {x: xm, y: ym};
    }
    return 0;
}

var EARTH_RADIUS = 6378.137;//地球半径
function rad(d) {
    return d * Math.PI / 180.0;
}

function getDistance(lat1, lng1, lat2, lng2) {
    var radLat1 = rad(lat1);
    var radLat2 = rad(lat2);
    var a = radLat1 - radLat2;
    var b = rad(lng1) - rad(lng2);
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
        Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
    s = s * EARTH_RADIUS;
    s = Math.round(s * 10000) / 10000;
    return (s * 1000);
}