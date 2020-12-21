package com.etone.project.utils.geos;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import sun.net.ftp.FtpClient;

/**
 * geoserver工具类
 * 
 * @author guojian
 * @version $$Revision: 14079 $$
 */
public class RestUtil {

	public static String entryKey(String key, Boolean value) {
		return "<entry key=\"" + key + "\">" + (value == null ? true : value)
				+ "</entry>";
	}

	/**
	 * Reads the contents of a File into one String. Watch the size!
	 * 
	 * @param file
	 *            a File to read
	 * @return as String the content of the file.
	 * 
	 * @throws java.io.IOException
	 */
	public static String readFileAsString(File file) throws java.io.IOException {
		byte[] buffer = new byte[(int) file.length()];
		FileInputStream f = new FileInputStream(file);
		try {
			f.read(buffer);
			return new String(buffer);
		} finally {
			f.close();
		}
	}

	/**
	 * @return a {@link String} with the content of the {@link URL}. Do not use
	 *         this on long files! Returns <code>null</code> if an error occured
	 *         or the file doesn't exists.<br/>
	 *         A newline-character is added at every new line.
	 * @throws IOException
	 */
	public static String readURLasString(URL url) throws IOException {

		InputStream openStream = url.openStream();
		try {

			InputStreamReader inStream = new InputStreamReader(openStream);
			try {

				BufferedReader inReader = new BufferedReader(inStream);
				try {
					String oneLine = inReader.readLine();
					if (oneLine == null)
						return "";
					StringBuffer content = new StringBuffer();
					while (oneLine != null) {
						content.append(oneLine);
						oneLine = inReader.readLine();
						if (oneLine != null)
							content.append("\n");
					}
					return content.toString();
				} finally {
					inReader.close();
				}

			} finally {
				inStream.close();
			}
		} finally {
			openStream.close();
		}
	}

   /* public static String creatShapFile(List<Map> actual,String alldir,String layerName){
        try {
            StringBuilder typeValue = new StringBuilder();

            // 地图图层数据表的字段
            typeValue.append(",intGridId:String,fLon_lb:String,fLat_lb:String");
            final SimpleFeatureType TYPE = DataUtilities.createType(layerName, layerName+":Polygon" + typeValue.toString());

            List<SimpleFeature> features = new ArrayList<SimpleFeature>();
            GeometryFactory geometryFactory = new GeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

            //2k米栅格距离
            //Float m2Lng = 0.009865f;// 1km转成经度
            //Float m2Lat = 0.009017f;// 1km转成纬度
            //Float m2Lng = 0.00049325f;// 50m转成经度
            //Float m2Lat = 0.00045085f;// 50m转成纬度
            Float m2Lng = 0.0049325f;// 500m转成经度
            Float m2Lat = 0.0045085f;// 500m转成纬度

            for (Map dto : actual) {
                Float dblong = (Float)dto.get("fLon_lb");// 栅格左下角的纬度
                Float dblat = (Float)dto.get("fLat_lb");// 栅格左下角的纬度
                Float rdblong = (Float)dto.get("fLon_rt");// 栅格左下角的纬度
                Float rdblat = (Float)dto.get("fLat_rt");// 栅格左下角的纬度

                // 栅格的4个顶点
                Coordinate leftBottom = new Coordinate(dblong, dblat);
                Coordinate leftTop = new Coordinate(dblong, rdblat);
                Coordinate rightTop = new Coordinate(rdblong, rdblat);
                Coordinate rightBottom = new Coordinate(rdblong, dblat);
                // 注意要形成闭合的多边形
                Coordinate[] coordinates = new Coordinate[] { leftBottom, leftTop, rightTop, rightBottom, leftBottom };

                // 生成多边形
                Polygon polygon = geometryFactory.createPolygon(coordinates);

                Object[] obj = { polygon, dto.get("intGridId"),dto.get("fLon_lb"), dto.get("fLat_lb") };
                SimpleFeature feature = featureBuilder.buildFeature(null, obj);
                features.add(feature);
            }
            SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
            // 这个文件路径一定要是geoserver的data路径
            //String filePath = GeoServerPropertyUtil.getDataPath() + "/swxtGsm";
            File newFile = new File(alldir);
            // if (!newFile.exists()) {
            // newFile.mkdirs();
            // }
            newFile = new File(alldir + "/"+layerName+"_region.shp");

            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);
            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            newDataStore.setStringCharset(Charset.forName("GBK"));

            newDataStore.createSchema(TYPE);
            newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

            Transaction transaction = new DefaultTransaction("create");
            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    transaction.commit();
                } catch (Exception problem) {
                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }  */

    public String reloadGeo(String layerName, String columnName) throws Exception {
        try {
            String surl = GeoServerPropertyUtil.getUrl();
            String username = GeoServerPropertyUtil.getUserName();
            String password = GeoServerPropertyUtil.getPassword();
            GsRest gsRest = new GsRest(surl, username, password);

            String wsName = "swxtGsm";
            boolean isCreatedWs = CreateWorkspace(gsRest, wsName);
            if (!isCreatedWs) {
                return "";// 创建工作空间不成功就返回
            }

            String dsName = "swxtGsm";
            boolean isCreatedDs = CreateDataStore(gsRest, wsName, dsName, "swxtGsm");
            if (!isCreatedDs)
                return "";// 创建存储空间不成功就返回

            String ftName = layerName;// 前面生成的shapefile文件名字，不要后缀名
            boolean isCreatedLayer = CreateLayer(gsRest, wsName, dsName, ftName);

            if (!isCreatedLayer) {
                return "";
            }

            // 专题渲染
            // 一个分段就是一个rule 比如0-1，1-2，那么就有两个rule
            // String styleName = "gsmResStyle";
            List<String> stylesList = gsRest.getStyles();
            boolean isStyleExists = stylesList.contains(layerName);

            if (isStyleExists) {
                boolean isDeletestyle = gsRest.deleteSld(layerName, true);
                if (isDeletestyle) {
                    // gsRest.reload();
                    System.out.println(layerName + " 样式文件已经删除！\n\r");
                }
            }

            // 创建样式并应用到图层
            CreateBetweenStyleAndUsed(gsRest, layerName, columnName, ftName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 创建工作空间(命名空间)
     *
     * @param gsRest
     *            连接geoserver对象
     * @param wsName
     *            工作空间名字
     * @return 是否创建成功
     * @throws Exception
     */
    private boolean CreateWorkspace(GsRest gsRest, String wsName) throws Exception {
        try {
            List<String> workSpacesList = gsRest.getWorkspaces();
            // 先判断有没有这个工作空间 如果没有就要创建
            if (!workSpacesList.contains(wsName)) {
                boolean isCreated = gsRest.createWorkspace(wsName);
                if (!isCreated) {
                    System.out.println(wsName + " 工作空间生成不成功\n\r");
                    return false;
                }
                gsRest.reload();// 刷新，让geoserver知道有这个工作空间了
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 创建存储空间
     *
     * @param gsRest
     *            连接geoserver对象
     * @param wsName
     *            工作空间名字
     * @param dsName
     *            存储空间名字
     * @param folderName
     *            实际地图数据存储的文件夹名字
     * @return 是否创建成功
     * @throws Exception
     */
    private boolean CreateDataStore(GsRest gsRest, String wsName, String dsName, String folderName) throws Exception {

        List<String> storesList = gsRest.getDatastores(wsName);

        // 先判断有没有这个存储空间 如果没有就要创建
        if (!storesList.contains(dsName)) {
            String relpath = "file:data/" + folderName;// swxtGsm这个是前面生成shapefile所放在文件夹名字
            boolean isCreated = gsRest.createDatastoreShapefile(wsName, dsName, wsName, relpath, "GBK", true, true, null);
            if (!isCreated) {
                System.out.println(dsName + " 存储空间生成不成功\n\r");
                return false;
            }
            gsRest.reload();// 刷新，让geoserver知道有这个存储空间
        }
        return true;
    }

    /**
     * 创建图层
     *
     * @param gsRest
     *            连接geoserver对象
     * @param wsName
     *            工作空间名字
     * @param dsName
     *            存储空间名字
     * @param layerName
     *            图层名字
     * @return 是否创建成功
     * @throws Exception
     */
    private boolean CreateLayer(GsRest gsRest, String wsName, String dsName, String layerName) throws Exception {
        List<String> featureTypesList = gsRest.getFeatureTypes(wsName, dsName);
        if (featureTypesList.contains(layerName)) {

            // 图层是引用一个图层配置的，所以要先删除图层才可以删除图层配置文件
            if (gsRest.deleteLayer(layerName)) {
                boolean isDeleteedFt = gsRest.deleteFeatureType(wsName, dsName, layerName);
                if (isDeleteedFt) {
                    // gsRest.reload();
                    System.out.println("【" + layerName + " 】图层配置已经删除了！\n\r");
                }
            }
        }
        String srs = "EPSG:4326";// 这个是坐标系的代号，都用这个，下面的是投影坐标系的wkt表示方法，也是固定的。
        String nativeWKT = "GEOGCS[&quot;GCS_WGS_1984&quot;, &#xd;DATUM[&quot;D_WGS_1984&quot;, &#xd;SPHEROID[&quot;WGS_1984&quot;, 6378137.0, 298.257223563]], &#xd;PRIMEM[&quot;Greenwich&quot;, 0.0], &#xd;UNIT[&quot;degree&quot;, 0.017453292519943295], &#xd; AXIS[&quot;Longitude&quot;, EAST], &#xd; AXIS[&quot;Latitude&quot;, NORTH]]";
        boolean isCreated = gsRest.createFeatureType(wsName, dsName, layerName, srs, nativeWKT);
        if (isCreated) {
            System.out.println("【" + layerName + " 】图层配置已经重新生成了！\n\r");
            gsRest.reload();
        } else {
            System.out.println(layerName + " 图层配置生成不成功！\n\r");
            return false;
        }
        return true;
    }

    /**
     * 创建范围类型的样式
     *
     * @param gsRest
     *            连接geoserver对象
     * @param styleName
     *            样式名字
     * @param columnName
     *            根据哪个字段来创建样式
     * @param layerName
     *            要应用样式的图层名字
     * @throws Exception
     */
    private void CreateBetweenStyleAndUsed(GsRest gsRest, String styleName, String columnName, String layerName) throws Exception {
        StringBuilder styleXmlStr = new StringBuilder();
        styleXmlStr.append("<StyledLayerDescriptor><NamedLayer><Name>");
        styleXmlStr.append(styleName);// 样式的名字，后面给具体的图层赋值要用到这个名字
        styleXmlStr.append("</Name><UserStyle><FeatureTypeStyle>");

        // 分段 注意有没有等于号
        GetRuleString(styleXmlStr, columnName, 1.9, 2.1,"#00FF00");// 1<=x<=3
        GetRuleString(styleXmlStr, columnName, 2.9, 3.1,"#80FFFF");// 4<=x<=6
        GetRuleString(styleXmlStr, columnName, 3.9, 4.1,"#FFFF00");
        GetRuleString(styleXmlStr, columnName, 4.9, 5.1,"#9FB6CD");
        GetRuleString(styleXmlStr, columnName, 5.9, 6.1,"#FF0000");
//		GetPoleRule(styleXmlStr, columnName, 15, false);// x>15

        styleXmlStr.append("</FeatureTypeStyle> </UserStyle></NamedLayer></StyledLayerDescriptor>");
        boolean flag = gsRest.createSld(styleName, styleXmlStr.toString());

        if (flag) {
            System.out.println(styleName + " 样式文件已经重新生成！\n\r");
            boolean isAdded = gsRest.addStyleToLayer(styleName, layerName, true);// 要设置成默认的，要不然geoserver会自动设置一个默认的样式
            System.out.print("设置样式成功与否：" + isAdded + "\n\r");
        }
    }

    private void GetRuleString(StringBuilder styleXmlStr, String columnName, double minValue, double maxValue,String colorString) {
        styleXmlStr.append("<Rule><Filter><PropertyIsBetween><PropertyName>");
        styleXmlStr.append(columnName);// 渲染的属性名字 //INTCELLGRIDID
        styleXmlStr.append("</PropertyName><LowerBoundary><Literal>");
        styleXmlStr.append(minValue);// 属性值
        styleXmlStr.append("</Literal></LowerBoundary>");

        styleXmlStr.append("<UpperBoundary><Literal>");
        styleXmlStr.append(maxValue);// 属性值
        styleXmlStr.append("</Literal></UpperBoundary></PropertyIsBetween></Filter>");

        styleXmlStr.append("<PolygonSymbolizer><Fill>");
        styleXmlStr.append("<CssParameter name='fill'>");

        styleXmlStr.append(colorString);// 颜色
        styleXmlStr.append("</CssParameter></Fill>");

        // 一定要有Stroke 没有值可以留空<Stroke/>
        GetStroke(styleXmlStr,colorString);
        styleXmlStr.append("</PolygonSymbolizer></Rule>");
    }


    /**
     * 边的颜色
     *
     * @param styleXmlStr
     */
    private void GetStroke(StringBuilder styleXmlStr,String colorString) {
        // 一定要有Stroke 没有值可以留空<Stroke/>
        styleXmlStr.append("<Stroke> <CssParameter name='stroke'>"+colorString+"</CssParameter> ");
        styleXmlStr.append("<CssParameter name='stroke-width'>1</CssParameter> </Stroke>");
    }

    //生成DC小区图层
    /*public static String generateShapFile2(List<Map> actual,String alldir,String layerName){
        try {
            StringBuilder typeValue = new StringBuilder();

            typeValue.append(",intenbid:String,intcellid:String,vcci:String,vcroadname:String,vcenbname:String,vccity:String,vcarea:String,lonb:String,latb:String,intgrid:String,vccellname:String,intpci:String,vcpowerinfo:String,intpcarrierfr:String,vccovertype:String,vcazimuth:String,intcityid:String,vcgridname:String");
            final SimpleFeatureType TYPE = DataUtilities.createType(layerName, layerName+":Polygon" + typeValue.toString());

            List<SimpleFeature> features = new ArrayList<SimpleFeature>();
            GeometryFactory geometryFactory = new GeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

            double len_info=0.0005;//扇形长度
           // double angle=1;//扇形方向
            //double angle_width=20;//扇形宽度
            //int loopi=0;//扇区最小单位
            double x1;
            double y1;
            double x2;
            double y2;
           // List pointList = new ArrayList();

            for (Map dto : actual) {
                Double dblong = Double.valueOf(dto.get("lonb").toString());// 小区经度
                Double dblat = Double.valueOf(dto.get("latb").toString());// 小区纬度
                Double  vcazimuth = Double.valueOf(dto.get("vcazimuth").toString());//方位角
               // String vccovertype= dto.get("roomside").toString();
                Polygon polygon = null;

                // 小区顶点
                Coordinate leftBottom = new Coordinate(dblong, dblat);
                    x1= dblong+len_info*Math.cos(7*Math.PI/12-vcazimuth*Math.PI/180) ;
                     y1=dblat+ len_info*Math.sin(7*Math.PI/12-vcazimuth*Math.PI/180);
                  //  y1 = dblat + len_info * Math.sin((angle + loopi) * Math.PI / 180) / (69.093 * 1609);
                   // x1 = dblong + len_info * Math.cos((angle + loopi) * Math.PI / 180)/ (69.093 * 1609 * Math.cos((y1 + dblat) * Math.PI / (180 * 2)));


                    Coordinate lefttop = new Coordinate(x1,y1);
                x2= dblong+len_info*Math.cos(5*Math.PI/12-vcazimuth*Math.PI/180) ;
                y2=dblat+ len_info*Math.sin(5*Math.PI/12-vcazimuth*Math.PI/180);

                Coordinate rightBottom = new Coordinate(x2,y2);


                // 注意要形成闭合的多边形
               // Coordinate[] coordinates = new Coordinate[] { leftBottom, (Coordinate) pointList.get(0),(Coordinate) pointList.get(1),(Coordinate) pointList.get(2),(Coordinate) pointList.get(3), leftBottom };
                Coordinate[] coordinates = new Coordinate[] { leftBottom, lefttop,rightBottom,leftBottom };
                     polygon = geometryFactory.createPolygon(coordinates);


                    Object[] obj = { polygon, dto.get("intenbid"),dto.get("intcellid"),dto.get("vcci"),dto.get("vcenbname"),dto.get("vcroadname"),dto.get("vccity")
                            ,dto.get("vcarea"),dto.get("intgrid"),dto.get("vccellname"),dto.get("intpci"),dto.get("vcpowerinfo"),dto.get("intpcarrierfr"),dto.get("vccovertype"),dto.get("lonb"),dto.get("vcazimuth"),dto.get("latb"),dto.get("intcityid"),dto.get("vcgridname")};
                    SimpleFeature feature = featureBuilder.buildFeature(null, obj);
                    features.add(feature);







            }
            SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
            // 这个文件路径一定要是geoserver的data路径
            //String filePath = GeoServerPropertyUtil.getDataPath() + "/swxtGsm";
            File newFile = new File(alldir);
            // if (!newFile.exists()) {
            // newFile.mkdirs();
            // }
            newFile = new File(alldir + "/"+layerName+"_region.shp");

            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);
            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            newDataStore.setStringCharset(Charset.forName("GBK"));

            newDataStore.createSchema(TYPE);
            newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

            Transaction transaction = new DefaultTransaction("create");
            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    transaction.commit();
                } catch (Exception problem) {
                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    //生成DC小区图层
    public static String generateShapFile3(List<Map> actual,String alldir,String layerName){
        try {
            StringBuilder typeValue = new StringBuilder();

            typeValue.append(",intenbid:String,intsectorid:String,vccellid2:String,vccellname:String,vcenbname:String,vccity:String,intmcc:String,lonb:String,latb:String,intgridid:String,vccovertype:String,intpci:String,intmod3:String,fgridlonb:String,fgridlatb:String,vcazimuth:String,intcityid:String,vcgridname:String");
            final SimpleFeatureType TYPE = DataUtilities.createType(layerName, layerName+":Polygon" + typeValue.toString());

            List<SimpleFeature> features = new ArrayList<SimpleFeature>();
            GeometryFactory geometryFactory = new GeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

            double len_info=0.0005;//扇形长度
            // double angle=1;//扇形方向
            //double angle_width=20;//扇形宽度
            //int loopi=0;//扇区最小单位
            double x1;
            double y1;
            double x2;
            double y2;
            // List pointList = new ArrayList();

            for (Map dto : actual) {
                Double dblong = Double.valueOf(dto.get("lonb").toString());// 小区经度
                Double dblat = Double.valueOf(dto.get("latb").toString());// 小区纬度
                Double  vcazimuth = Double.valueOf(dto.get("vcazimuth").toString());//方位角
                // String vccovertype= dto.get("roomside").toString();
                Polygon polygon = null;

                // 小区顶点
                Coordinate leftBottom = new Coordinate(dblong, dblat);
                x1= dblong+len_info*Math.cos(7*Math.PI/12-vcazimuth*Math.PI/180) ;
                y1=dblat+ len_info*Math.sin(7*Math.PI/12-vcazimuth*Math.PI/180);
                //  y1 = dblat + len_info * Math.sin((angle + loopi) * Math.PI / 180) / (69.093 * 1609);
                // x1 = dblong + len_info * Math.cos((angle + loopi) * Math.PI / 180)/ (69.093 * 1609 * Math.cos((y1 + dblat) * Math.PI / (180 * 2)));


                Coordinate lefttop = new Coordinate(x1,y1);
                x2= dblong+len_info*Math.cos(5*Math.PI/12-vcazimuth*Math.PI/180) ;
                y2=dblat+ len_info*Math.sin(5*Math.PI/12-vcazimuth*Math.PI/180);

                Coordinate rightBottom = new Coordinate(x2,y2);


                // 注意要形成闭合的多边形
                // Coordinate[] coordinates = new Coordinate[] { leftBottom, (Coordinate) pointList.get(0),(Coordinate) pointList.get(1),(Coordinate) pointList.get(2),(Coordinate) pointList.get(3), leftBottom };
                Coordinate[] coordinates = new Coordinate[] { leftBottom, lefttop,rightBottom,leftBottom };
                polygon = geometryFactory.createPolygon(coordinates);


                Object[] obj = { polygon, dto.get("intenbid"),dto.get("intsectorid"),dto.get("vccellid2"),dto.get("vccellname"),dto.get("vcenbname"),dto.get("vccity")
                        ,dto.get("intmcc"),dto.get("lonb"),dto.get("latb"),dto.get("intgridid"),dto.get("vccovertype"),dto.get("intpci"),dto.get("intmod3"),
                        dto.get("fgridlonb"),dto.get("fgridlatb"),dto.get("vcazimuth"),dto.get("intcityid"),dto.get("vcgridname")};
                SimpleFeature feature = featureBuilder.buildFeature(null, obj);
                features.add(feature);







            }
            SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
            // 这个文件路径一定要是geoserver的data路径
            //String filePath = GeoServerPropertyUtil.getDataPath() + "/swxtGsm";
            File newFile = new File(alldir);
            // if (!newFile.exists()) {
            // newFile.mkdirs();
            // }
            newFile = new File(alldir + "/"+layerName+".shp");

            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);
            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            newDataStore.setStringCharset(Charset.forName("GBK"));

            newDataStore.createSchema(TYPE);
            newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

            Transaction transaction = new DefaultTransaction("create");
            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    transaction.commit();
                } catch (Exception problem) {
                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //生成GT小区图层
    public static String generateShapFile1(List<Map> actual,String alldir,String layerName){
        try {
            StringBuilder typeValue = new StringBuilder();

            // 地图图层数据表的字段
            typeValue.append(",intenbid:String,intcellid:String,vcci:String,vcenbname:String,vcroadname:String,vccity:String,vccellname:String,intpci:String,vcdirection:String,intangle:String,lonb:String,latb:String,intcityid:String");
            final SimpleFeatureType TYPE = DataUtilities.createType(layerName, layerName+":Polygon" + typeValue.toString());

            List<SimpleFeature> features = new ArrayList<SimpleFeature>();
            GeometryFactory geometryFactory = new GeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

            double len_info=0.0005;//扇形长度
            // double angle=1;//扇形方向
            //double angle_width=20;//扇形宽度
            //int loopi=0;//扇区最小单位
            double x1;
            double y1;
            double x2;
            double y2;
            // List pointList = new ArrayList();

            for (Map dto : actual) {
                Double dblong = Double.valueOf(dto.get("lonb").toString());// 小区经度
                Double dblat = Double.valueOf(dto.get("latb").toString());// 小区纬度
                Double  vcazimuth = Double.valueOf(dto.get("intangle").toString());//方位角
               // String vccovertype= dto.get("roomside").toString();
                Polygon polygon = null;

                // 小区顶点
                Coordinate leftBottom = new Coordinate(dblong, dblat);
                x1= dblong+len_info*Math.cos(7*Math.PI/12-vcazimuth*Math.PI/180) ;
                y1=dblat+ len_info*Math.sin(7*Math.PI/12-vcazimuth*Math.PI/180);
                //  y1 = dblat + len_info * Math.sin((angle + loopi) * Math.PI / 180) / (69.093 * 1609);
                // x1 = dblong + len_info * Math.cos((angle + loopi) * Math.PI / 180)/ (69.093 * 1609 * Math.cos((y1 + dblat) * Math.PI / (180 * 2)));


                Coordinate lefttop = new Coordinate(x1,y1);
                x2= dblong+len_info*Math.cos(5*Math.PI/12-vcazimuth*Math.PI/180) ;
                y2=dblat+ len_info*Math.sin(5*Math.PI/12-vcazimuth*Math.PI/180);

                Coordinate rightBottom = new Coordinate(x2,y2);


                // 注意要形成闭合的多边形
                // Coordinate[] coordinates = new Coordinate[] { leftBottom, (Coordinate) pointList.get(0),(Coordinate) pointList.get(1),(Coordinate) pointList.get(2),(Coordinate) pointList.get(3), leftBottom };
                Coordinate[] coordinates = new Coordinate[] { leftBottom, lefttop,rightBottom,leftBottom };
                polygon = geometryFactory.createPolygon(coordinates);

                Object[] obj = { polygon, dto.get("intenbid"),dto.get("intcellid"),dto.get("vcci"),dto.get("vcenbname"),dto.get("vcroadname"),dto.get("vccity"),dto.get("lonb"), dto.get("latb"),dto.get("vccellname"),dto.get("intpci"),dto.get("vcdirection"),dto.get("intangle"),dto.get("intcityid") };
                SimpleFeature feature = featureBuilder.buildFeature(null, obj);
                features.add(feature);









            }
            SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
            // 这个文件路径一定要是geoserver的data路径
            //String filePath = GeoServerPropertyUtil.getDataPath() + "/swxtGsm";
            File newFile = new File(alldir);
            // if (!newFile.exists()) {
            // newFile.mkdirs();
            // }
            newFile = new File(alldir + "/"+layerName+"_region.shp");

            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);
            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            newDataStore.setStringCharset(Charset.forName("GBK"));

            newDataStore.createSchema(TYPE);
            newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

            Transaction transaction = new DefaultTransaction("create");
            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    transaction.commit();
                } catch (Exception problem) {
                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    } */
}
