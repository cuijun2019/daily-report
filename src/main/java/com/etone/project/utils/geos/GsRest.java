package com.etone.project.utils.geos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.xalan.internal.xsltc.trax.OutputSettings;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sun.misc.BASE64Encoder;
/**
 * geoserver远程操作控制类
 * @author guojian
 * @version $$Revision: 14079 $$
 */
public class GsRest {

	@Override
	public String toString() {
		return "GsRest [restUrl=" + restUrl + ", username=" + username + "]";
	}

	final static Pattern workspaceNameRegEx = Pattern.compile(
			"<workspace>.*?<name>(.*?)</name>.*?</workspace>", Pattern.DOTALL);
	final static Pattern datastoreNameRegEx = Pattern.compile(
			"<dataStore>.*?<name>(.*?)</name>.*?</dataStore>", Pattern.DOTALL);
	final static Pattern coverageNameRegEx = Pattern.compile(
			"<coverage>.*?<name>(.*?)</name>.*?</coverage>", Pattern.DOTALL);
	final static Pattern coverageStoreNameRegEx = Pattern.compile(
			"<coverageStore>.*?<name>(.*?)</name>.*?</coverageStore>",
			Pattern.DOTALL);

	static final Pattern featuretypesNameRegEx = Pattern.compile(
			"<featureType>.*?<name>(.*?)</name>.*?</featureType>",
			Pattern.DOTALL + Pattern.MULTILINE);

	final static Pattern layerNamesRegExPattern = Pattern.compile(
			"<layer>.*?<name>(.*?)</name>.*?</layer>", Pattern.DOTALL
					+ Pattern.MULTILINE);
	final static Pattern coverageNamesRegExPattern = Pattern.compile(
			"<coverage>.*?<name>(.*?)</name>.*?</coverage>", Pattern.DOTALL
					+ Pattern.MULTILINE);

	/**
	 * <code>
           <styles>
                        <style>
                        <name>point</name>
                        <atom:link rel="alternate" href="http://localhost:8085/geoserver/rest/styles/point.xml" type="application/xml"/>
                        </style>
                        <style>
                        <name>line</name>
                        <atom:link rel="alternate" href="http://localhost:8085/geoserver/rest/styles/line.xml" type="application/xml"/>
                        </style>
                        ...
                </styles>
                </code>
	 */
	static final Pattern stylesNameRegEx = Pattern.compile(
			"<style>.*?<name>(.*?)</name>.*?</style>", Pattern.DOTALL
					+ Pattern.MULTILINE);

	public final String METHOD_DELETE = "DELETE";
	public final String METHOD_GET = "GET";
	public final String METHOD_POST = "POST";

	public final String METHOD_PUT = "PUT";

	private String password;

	private String restUrl;

	private String username;

	/**
	 * Creates a {@link GsRest} instance to work on a Geoserver that allows
	 * anonymous read- and write access.
	 * 
	 * @param gsBaseUrl
	 *            The base URL of Geoserver. Usually ending with "../geoserver"
	 */
	public GsRest(String gsBaseUrl) {

		if (!gsBaseUrl.endsWith("rest")) {
			if (!gsBaseUrl.endsWith("/"))
				gsBaseUrl += "/";
			this.restUrl = gsBaseUrl + "rest";
		}

		this.username = null;
		this.password = null;
	}

	/**
	 * Creates a {@link GsRest} instance to work on a Geoserver which needs
	 * authorization (default in 2.0.2).
	 * 
	 * @param gsBaseUrl
	 *            The base URL of Geoserver. Usually ending with "../geoserver"
	 * @param username
	 *            plain text username
	 * @param password
	 *            plain text password
	 */
	public GsRest(String gsBaseUrl, String username, String password) {

		if (gsBaseUrl != null && !gsBaseUrl.endsWith("/"))
			gsBaseUrl += "/";

		this.restUrl = gsBaseUrl + "rest";
		this.username = username;
		this.password = password;
	}

	/**
	 * This method does not upload a shapefile via zip. It rather creates a
	 * reference to a Shapefile that has already exists in the GS data
	 * directory. <br/>
	 * 
	 * TODO: This is buggy and always puts the coveragestore in the default
	 * workspace. Therefore we set the default workspace defore every command,
	 * and reset it afterwards. This will change the default workspace for a
	 * moment!
	 * 
	 * @param relpath
	 *            A path to the file, relative to gsdata dir, e.g.
	 *            "file:data/water.shp"
	 */

	public boolean createCoverageGeoTiff(String wsName, String csName,
			String csNamespace, String relpath, Configure autoConfig)
			throws IOException {

		String oldDefault = getDefaultWs();

		try {
			setDefaultWs(wsName);

			if (relpath == null)
				throw new IllegalArgumentException(
						"parameter relpath may not be null");

			if (autoConfig == null)
				autoConfig = Configure.first;

			String urlParamter = "<url>" + relpath + "</url>";

			// String namespaceParamter = "<entry key=\"namespace\">" + dsName
			// + "</entry>";

			String typeParamter = "<type>GeoTIFF</type>";

			String xml = "<coverageStore><name>" + csName
					+ "</name><enabled>true</enabled>" + typeParamter
					+ urlParamter + "</coverageStore>";

			int returnCode = sendRESTint(METHOD_POST, "/workspaces/" + wsName
					+ "/coveragestores?configure=" + autoConfig.toString(), xml);
			return 201 == returnCode;
		} catch (IOException e) {
			setDefaultWs(oldDefault);
			throw e;
		} finally {
			reload();
		}

	}


	public boolean setDefaultWs(String wsName) throws IOException {
		String xml = "<workspace><name>" + wsName + "</name></workspace>";

		return 200 == sendRESTint(METHOD_PUT, "/workspaces/default.xml", xml);
	}

	/**
	 * Returns the name of the default workspace
	 * 
	 * @throws IOException
	 */
	public String getDefaultWs() throws IOException {
		String xml = sendRESTstring(METHOD_GET, "/workspaces/default", null);
		List<String> workspaces = parseXmlWithregEx(xml, workspaceNameRegEx);
		return workspaces.get(0);
	}

	public boolean createDatastorePg(String workspace, String dsName,
			String dsNamespace, String host, String port, String db,
			String user, String pwd, boolean exposePKs) throws IOException {

		String dbType = "postgis";

		return createDbDatastore(workspace, dsName, dsNamespace, host, port,
				db, user, pwd, dbType, exposePKs);
	}

	public boolean createDatastoreShapefile(String workspace, String dsName,
			String dsNamespace, String relpath, String chartset)
			throws IOException {

		return createDatastoreShapefile(workspace, dsName, dsNamespace,
				relpath, chartset, null, null, null);
	}

	/**
	 * - <dataStore> <name>xxx</name> <description>xxx</description>
	 * <type>Shapefile</type> <enabled>true</enabled> - <workspace>
	 * <name>ws</name> <atom:link rel="alternate"
	 * href="http://localhost:8085/geoserver/rest/workspaces/ws.xml"
	 * type="application/xml"/> </workspace> - <connectionParameters> <entry
	 * key="memory mapped buffer">true</entry> <entry
	 * key="create spatial index">true</entry> <entry
	 * key="charset">ISO-8859-1</entry> <entry
	 * key="url">file:data/ad2/soils.shp</entry> <entry
	 * key="namespace">http://ws</entry> </connectionParameters> -
	 * <featureTypes> <atom:link rel="alternate" href=
	 * "http://localhost:8085/geoserver/rest/workspaces/ws/datastores/xxx/featuretypes.xml"
	 * type="application/xml"/> </featureTypes> </dataStore>
	 */
	/**
	 * This method does not upload a shapefile via zip. It rather creates a
	 * reference to a Shapefile that has already exists in the GS data
	 * directory.
	 * 
	 * @param charset
	 *            defaults to UTF-8 if not set. Charset, that any text content
	 *            is stored in.
	 * 
	 * @param relpath
	 *            A path to the file, relative to gsdata dir, e.g.
	 *            "file:data/water.shp"
	 */
	public boolean createDatastoreShapefile(String workspace, String dsName,
			String dsNamespace, String relpath, String charset,
			Boolean memoryMappedBuffer, Boolean createSpatialIndex,
			Configure autoConfig) throws IOException {
			
		if (autoConfig == Configure.first)
			autoConfig = null;

		if (relpath == null)
			throw new IllegalArgumentException(
					"parameter relpath may not be null");

		String createSpatialIndexParam = RestUtil.entryKey(
				"create spatial index", createSpatialIndex);

		String memoryMappedBufferParamter = RestUtil.entryKey(
				"memory mapped buffer", memoryMappedBuffer);

		String charsetParamter = "<entry key=\"charset\">"
				+ (charset == null ? "UTF-8" : charset) + "</entry>";

		String urlParamter = "<entry key=\"url\">" + relpath + "</entry>";

		String namespaceParamter = "<entry key=\"namespace\">" + dsName
				+ "</entry>";

		String typeParamter = "<type>Shapefile</type>";

		String xml = "<dataStore><name>" + dsName
				+ "</name><enabled>true</enabled>" + typeParamter
				+ "<connectionParameters>" + createSpatialIndexParam
				+ memoryMappedBufferParamter + charsetParamter + urlParamter
				+ namespaceParamter + typeParamter
				+ "</connectionParameters></dataStore>";

		String configureParam = autoConfig == null ? "" : "?configure="
				+ autoConfig.toString();

		int returnCode = sendRESTint(METHOD_POST, "/workspaces/" + workspace
				+ "/datastores.xml" + configureParam, xml);
		return 201 == returnCode;
	}

	public boolean createDbDatastore(String workspace, String dsName,
			String dsNamespace, String host, String port, String db,
			String user, String pwd, String dbType, boolean exposePKs)
			throws IOException {

		String exposePKsParamter = "<entry key=\"Expose primary keys\">"
				+ exposePKs + "</entry>";

		String xml = "<dataStore><name>" + dsName
				+ "</name><enabled>true</enabled><connectionParameters><host>"
				+ host + "</host><port>" + port + "</port><database>" + db
				+ "</database><user>" + user + "</user><passwd>" + pwd
				+ "</passwd><dbtype>" + dbType + "</dbtype><namespace>"
				+ dsNamespace + "</namespace>" + exposePKsParamter
				+ "</connectionParameters></dataStore>";

		int returnCode = sendRESTint(METHOD_POST, "/workspaces/" + workspace
				+ "/datastores", xml);
		return 201 == returnCode;
	}

	// /**
	// * Create a link between a PostGIS table and the given GeoServer
	// datastore.<br/>
	// *
	// * Works: <code>curl -u admin:geoserver -v -XPOST -H
	// 'Content-type:text/xml'</code>
	// *
	// * @param wsName
	// * the GeoServer workspace name
	// * @param dsName
	// * the GeoServer datastore name
	// * @param postGISTableName
	// * the name of the PostGIS table to associate
	// * @return <code>true</code> if uploaded, false otherwise
	// * @throws IOException
	// *
	// * @author Eric Grosso, 10.5.2011
	// */
	// public boolean createfeatureTypePg(String wsName, String dsName, String
	// postGISTableName) throws IOException {
	//
	// String xml = "<featureType><name>" + postGISTableName +
	// "</name></featureType>";
	//
	// int sendRESTint = sendRESTint(METHOD_POST, "/workspaces/" + wsName +
	// "/datastores/" + dsName + "/featuretypes",
	// xml, "text/xml", null);
	//
	// return 201 == sendRESTint;
	// }

	/**
	 * Create a <em>Featuretype</em> based on an existing datastore.
	 * 
	 * @param wsName
	 *            the GeoServer workspace name
	 * @param dsName
	 *            the GeoServer datastore name
	 * @param ftName
	 *            the featureTypeName you want to create, e.g. the name of a
	 *            PostGIS table or the name of a Shapefile (without .shp)
	 * @param srs
	 *            <code>null</code> or <code>EPSG:????</code> syntax.
	 * @param nativeWKT
	 *            <code>null</code> or WKT declaration of the CRS.
	 * 
	 * @return <code>true</code> if the creation was successful.
	 * 
	 * @throws IOException
	 */
	public boolean createFeatureType(String wsName, String dsName,
			String ftName, String srs, String nativeWKT) throws IOException {

		// "<entry key=\"namespace\"><name>" + dsName
		// + "</name></entry>";

		String nameTitleParam = "<name>" + ftName + "</name><title>" + ftName
				+ "</title>";

		String enabledTag = "<enabled>" + true + "</enabled>";

		String srsTag = srs != null ? "<srs>" + srs + "</srs>" : "";

		String nativeCrsTag = nativeWKT != null ? "<nativeCRS>" + nativeWKT
				+ "</nativeCRS>" : "";

		String prjPolTag = "<projectionPolicy>FORCE_DECLARED</projectionPolicy>";

		String xml = "<featureType>" + nameTitleParam + srsTag + prjPolTag
				+ enabledTag + nativeCrsTag + "</featureType>";

		int sendRESTint = sendRESTint(METHOD_POST, "/workspaces/" + wsName
				+ "/datastores/" + dsName + "/featuretypes", xml);
		return 201 == sendRESTint;
	}

	/**
	 * Uploads an SLD to the Geoserver
	 * 
	 * @param stylename
	 * @param sldString
	 *            SLD-XML as String
	 * @return <code>true</code> successfully uploaded
	 */
	public boolean createSld(String stylename, String sldString)
			throws IOException {

		return null != createSld_location(stylename, sldString);
	}

	/**
	 * Add an existing style to a layer.
	 * 
	 * @param styleName
	 *            name of the stlye to associate with the layer.
	 * @param layername
	 *            name of the layer to associate with the style.
	 * @return <code>true</code> for operation success.
	 * @throws java.io.IOException
	 * @see #createSld
	 */
	public boolean addStyleToLayer(String styleName, String layername)
			throws IOException {
		return addStyleToLayer(styleName, layername, false);
	}

	/**
	 * Add an existing style to a layer.
	 * 
	 * @param styleName
	 *            name of the stlye to associate with the layer.
	 * @param layername
	 *            name of the layer to associate with the style.
	 * @param asDefault
	 *            Set <code>true</code> if this shall be setup as a default
	 *            style
	 * @return <code>true</code> for operation success.
	 * @throws java.io.IOException
	 * @see #createSld
	 */
	public boolean addStyleToLayer(String styleName, String layername,
			Boolean asDefault) throws IOException {
		String xml = "<style><name>" + styleName + "</name></style>";
		int result = sendRESTint(METHOD_POST, "/layers/" + layername
				+ "/styles.xml", xml);
		if (result != 201)
			return false;

		if (asDefault) {
			xml = "<layer><defaultStyle><name>" + styleName
					+ "</name></defaultStyle><enabled>true</enabled></layer>";
			return 200 == sendRESTint(METHOD_PUT, "/layers/" + layername, xml);
		}
		return true;
	}

	public List<String> getStylesForLayer(String layername) throws IOException {
		String xml = sendRESTstring(METHOD_GET, "/layers/" + layername
				+ "/styles", null);
		return parseXmlWithregEx(xml, stylesNameRegEx);
	}

	/**
	 * @param stylename
	 * @param sldString
	 * @return REST location URL string to the new style
	 * @throws IOException
	 */
	public String createSld_location(String stylename, String sldString)
			throws IOException {

		String location = sendRESTlocation(METHOD_POST, "/styles/" + "?name="
				+ stylename, sldString, "application/vnd.ogc.sld+xml",
				"application/vnd.ogc.sld+xml");
		return location;
	}

	public boolean createWorkspace(String workspaceName) throws IOException {
		return 201 == sendRESTint(METHOD_POST, "/workspaces",
				"<workspace><name>" + workspaceName + "</name></workspace>");
	}

	/**
	 * Deletes a datastore
	 * 
	 * @param wsName
	 *            name of the workspace
	 * @param dsName
	 *            name of the datastore
	 * @param recusively
	 *            delete all contained featureytpes also
	 */
	public boolean deleteDatastore(String wsName, String dsName,
			boolean recusively) throws IOException {
		if (recusively == true) {
			List<String> layerNames = getLayersUsingDataStore(wsName, dsName);

			for (String lName : layerNames) {
				if (!deleteLayer(lName))
					throw new RuntimeException("Could not delete layer "
							+ wsName + ":" + dsName + ":" + lName);
			}
			if (getDatastores(wsName).contains(dsName)) {
				List<String> ftNames = getFeatureTypes(wsName, dsName);
				for (String ftName : ftNames) {
					// it happens that this returns false, e.g maybe for
					// notpublished featuretypes!?
					deleteFeatureType(wsName, dsName, ftName);
				}
			}
		}
		return 200 == sendRESTint(METHOD_DELETE, "/workspaces/" + wsName
				+ "/datastores/" + dsName, null);
	}

	/**
	 * Deletes a coveragestore
	 * 
	 * @param wsName
	 *            name of the workspace
	 * @param csName
	 *            name of the coveragestore
	 * @param recusively
	 *            delete all contained coverages also
	 * @throws IOException
	 */
	public boolean deleteCoveragestore(String wsName, String csName,
			boolean recusively) throws IOException {
		if (recusively == true) {
			deleteLayersUsingCoveragestore(wsName, csName);

			List<String> covNames = getCoverages(wsName, csName);
			//
			for (String ftName : covNames) {
				// it happens that this returns false, e.g maybe for
				// notpublished featuretypes!?
				deleteCoverage(wsName, csName, ftName);
			}
		}
		return 200 == sendRESTint(METHOD_DELETE, "/workspaces/" + wsName
				+ "/coveragestores/" + csName, null);
	}

	private void deleteLayersUsingCoveragestore(String wsName, String csName)
			throws IOException {
		List<String> layerNames = getLayersUsingCoverageStore(wsName, csName);

		for (String lName : layerNames) {
			if (!deleteLayer(lName))
				throw new RuntimeException("Could not delete layer " + lName);
		}
	}

	public boolean deleteCoverage(String wsName, String csName, String covName)
			throws IOException {

		deleteLayersUsingCoveragestore(wsName, csName);

		int result = sendRESTint(METHOD_DELETE, "/workspaces/" + wsName
				+ "/coveragestores/" + csName + "/coverages/" + covName, null);

		return result == 200;
	}

	/**
	 * To avoid
	 * "org.geoserver.rest.RestletException: java.lang.IllegalArgumentException: Unable to delete resource referenced by layer"
	 * use deleteLayer first.
	 */
	public boolean deleteFeatureType(String wsName, String dsName, String ftName) {
		try {
			return sendRESTint(METHOD_DELETE, "/workspaces/" + wsName
					+ "/datastores/" + dsName + "/featuretypes/" + ftName, null) == 200;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteLayer(String lName) {
		try {

			int result = sendRESTint(METHOD_DELETE, "/layers/" + lName, null);
			return result == 200;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteSld(String styleName, Boolean... purge_)
			throws IOException {
		Boolean purge = null;
		if (purge_.length > 1)
			throw new IllegalArgumentException(
					"only one purge paramter allowed");
		if (purge_.length == 1) {
			purge = purge_[0];
		}
		if (purge == null)
			purge = false;
		int result = sendRESTint(METHOD_DELETE, "/styles/" + styleName
				+ ".sld?purge=" + purge.toString(), null);
		// + "&name=" + styleName
		return result == 200;
	}

	/**
	 * Deletes a workspace recursively.
	 * 
	 * @param wsName
	 *            name of the workspace to delete recursively.
	 */
	public boolean deleteWorkspace(String wsName) throws IOException {
		return deleteWorkspace(wsName, true);
	}

	/**
	 * Deletes a workspace recursively. If the workspace could not be deleted
	 * (e.g. didn't exist, or not recursively deleting and not empty) returns
	 * <code>false</code>
	 * 
	 * @param wsName
	 *            name of the workspace to delete, including all content.
	 */
	public boolean deleteWorkspace(String wsName, boolean recursive) {

		try {

			if (recursive) {

				reload();

				// Selete all datastores
				// recusively
				List<String> datastores = getDatastores(wsName);
				for (String dsName : datastores) {
					if (!deleteDatastore(wsName, dsName, true))
						throw new IOException("Could not delete dataStore "
								+ dsName + " in workspace " + wsName);
				}

				// Selete all datastores
				// recusively
				List<String> coveragestores = getCoveragestores(wsName);
				for (String csName : coveragestores) {
					if (!deleteCoveragestore(wsName, csName, true))
						throw new IOException("Could not delete coverageStore "
								+ csName + " in workspace " + wsName);
				}

			}

			return 200 == sendRESTint(METHOD_DELETE, "/workspaces/" + wsName,
					null, "application/xml", "application/xml");
		} catch (Exception e) {
			e.printStackTrace();
			// Workspace didn't exist
			return false;
		} finally {
			try {
				reload();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * A list of coveragestores
	 */
	public List<String> getCoveragestores(String wsName) throws IOException {
		try {
			String coveragesXml = sendRESTstring(METHOD_GET, "/workspaces/"
					+ wsName + "/coveragestores.xml", null);
			List<String> coveragestores = parseXmlWithregEx(coveragesXml,
					coverageStoreNameRegEx);
			return coveragestores;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	/**
	 * A list of datastorenames
	 */
	public List<String> getDatastores(String wsName) {
		try {

			String datastoresXml = sendRESTstring(METHOD_GET, "/workspaces/"
					+ wsName + "/datastores.xml", null);
			List<String> datastores = parseXmlWithregEx(datastoresXml,
					datastoreNameRegEx);
			return datastores;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	/**
	 * A list of all workspaces
	 */
	public List<String> getWorkspaces() {
		try {
			String xml = sendRESTstring(METHOD_GET, "/workspaces", null);
			List<String> workspaces = parseXmlWithregEx(xml, workspaceNameRegEx);
			return workspaces;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	/**
	 * Tell this instance of {@link GsRest} to not use authorization
	 */
	public void disableAuthorization() {
		this.password = null;
		this.username = null;
	}

	/**
	 * Tell this {@link GsRest} instance to use authorization
	 * 
	 * @param username
	 *            cleartext username
	 * @param password
	 *            cleartext password
	 */
	public void enableAuthorization(String username, String password) {
		this.password = password;
		this.username = username;
	}

	public String getDatastore(String wsName, String dsName)
			throws MalformedURLException, ProtocolException, IOException {
		return sendRESTstring(METHOD_GET, "/workspaces/" + wsName
				+ "/datastores/" + dsName, null);
	}

	public String getFeatureType(String wsName, String dsName, String ftName)
			throws IOException {
		return sendRESTstring(METHOD_GET, "/workspaces/" + wsName
				+ "/datastores/" + dsName + "/featuretypes/" + ftName, null);
	}

	/**
	 * Returns a list of all featuretypes inside a a datastore
	 * 
	 * @param wsName
	 * @param dsName
	 * @throws IOException
	 */
	public List<String> getFeatureTypes(String wsName, String dsName) {
		try {
			String xml = sendRESTstring(METHOD_GET, "/workspaces/" + wsName
					+ "/datastores/" + dsName + "/featuretypes", null);
			return parseXmlWithregEx(xml, featuretypesNameRegEx);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}

	}

    /**
     * 获取图层属性
     * @param wsName
     * @param dsName
     * @return
     */
    public Map<String,String> getFeatureAttr(String wsName, String dsName) {
        Map<String , String> attributesMap=new HashMap<String, String>();
        try {
            String xml = sendRESTstring(METHOD_GET, "/workspaces/" + wsName
                    + "/datastores/" + dsName + "/featuretypes", null);
            Document document = DocumentHelper.parseText(xml);
            Element root= document.getRootElement();
            List nodes = root.elements("featureType");
            for (Iterator it = nodes.iterator(); it.hasNext();) {
            //遍历子节点
                Element elm = (Element) it.next();
               //获取featuretype子节点集合
                List nodes2=elm.elements();
                //获取atom节点
                Element atom=(Element)nodes2.get(1);
                Element eleName=(Element)nodes2.get(0);
                String href=atom.attribute("href").getText();
                String restaddr=href.substring(href.indexOf("rest/") + 4);
                attributesMap.put(eleName.getText(),this.findGpsPropries(restaddr)) ;
             }
            return attributesMap;
        } catch (Exception e) {
            System.out.println("获取属性名称集合异常！");
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 查询出图层的xml文件 a取得相应的属性名称
     */
    public String findGpsPropries(String url){
        String attr=null;
        try{
            String xml = sendRESTstring(METHOD_GET, url, null);

            Document document = DocumentHelper.parseText(xml);
            Element root=document.getRootElement();
            Element attributes=root.element("attributes");
            List nodes = attributes.elements("attribute");
            if(nodes.size()>=2){
                    //取得属性集合中的第二个属性名称  attribute
                   Element attribute=(Element)nodes.get(1);
                    //获取属性的名称
                     List nodes2=attribute.elements();
                    Element at=(Element)nodes2.get(0);
//                    System.out.println(at.getText());
                    attr=java.net.URLDecoder.decode(at.getText(),"GB2312");

            }
//            System.out.println(xml);
        }catch(Exception e)
        {
           /* System.out.println("获取属性名称异常！");
            e.printStackTrace();*/
        }
       return attr;
    }


	/**
	 * Returns a {@link List} of all layer names
	 * 
	 * @param
	 */
	public List<String> getLayerNames() throws IOException {
		String xml = sendRESTstring(METHOD_GET, "/layers", null);
        return parseXmlWithregEx(xml, layerNamesRegExPattern);
	}

	/**
	 * Returns a list of all coverageNames inside a a coveragestore
	 */
	public List<String> getCoverages(String wsName, String csName) {
		try {

			String xml = sendRESTstring(METHOD_GET, "/workspaces/" + wsName
					+ "/coveragestores/" + csName + "/coverages", null);

			return parseXmlWithregEx(xml, coverageNameRegEx);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}

	}

	/**
	 * Returns a list of all layers using a specific dataStore
	 */

	public List<String> getLayersUsingCoverageStore(String wsName, String csName) {
		try {

			final Pattern pattern = Pattern.compile(
					"<layer>.*?<name>(.*?)</name>.*?/rest/workspaces/" + wsName
							+ "/coveragestores/" + csName
							+ "/coverages/.*?</layer>", Pattern.DOTALL
							+ Pattern.MULTILINE);

			List<String> coveragesUsingStore = new ArrayList<String>();
			for (String cName : getLayerNames()) {
				String xml = sendRESTstring(METHOD_GET, "/layers/" + cName,
						null);
				// System.out.println(xml);

				Matcher matcher = pattern.matcher(xml);
				if (matcher.find())
					coveragesUsingStore.add(cName);
			}

			return coveragesUsingStore;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	/**
	 * Returns a list of all layers using a specific dataStore
	 */
	public List<String> getLayersUsingDataStore(String wsName, String dsName) {
		try {
			final Pattern layersUsingStoreRegEx = Pattern.compile(
					"<layer>.*?<name>(.*?)</name>.*?/rest/workspaces/" + wsName
							+ "/datastores/" + dsName
							+ "/featuretypes/.*?</layer>", Pattern.DOTALL
							+ Pattern.MULTILINE);

			List<String> layersUsingDs = new ArrayList<String>();
			for (String lName : getLayerNames()) {
				String xml = sendRESTstring(METHOD_GET, "/layers/" + lName,
						null);
				// System.out.println(xml);

				Matcher matcher = layersUsingStoreRegEx.matcher(xml);
				if (matcher.find())
					layersUsingDs.add(lName);
			}

			return layersUsingDs;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	/**
	 * @return A list of all stylenames stored in geoserver. Includes "default"
	 *         stylenames like <code>point</code>, <code>line</code>,etc.
	 */
	public List<String> getStyles() throws IOException {
		String xml = sendRESTstring(METHOD_GET, "/styles", null);
		return parseXmlWithregEx(xml, stylesNameRegEx);
	}

	/**
	 * @return <code>true</code> if authorization is used for requests
	 */
	public boolean isAuthorization() {
		return password != null && username != null;
	}

	private List<String> parseXmlWithregEx(String xml, Pattern pattern) {
		ArrayList<String> list = new ArrayList<String>();

		Matcher nameMatcher = pattern.matcher(xml);

		while (nameMatcher.find()) {
			String name = nameMatcher.group(1);
			//这里根据地址获得xml文件
            list.add(name.trim());
		}

		return list;
	}

	public boolean purgeSld(String styleName) throws IOException {
		return deleteSld(styleName, true);
	}
	
	 private HttpURLConnection sendREST(String method, String urlAppend, Reader postDataReader, String contentType,
             String accept) throws MalformedURLException, IOException {
     boolean doOut = !METHOD_DELETE.equals(method) && postDataReader != null;
     // boolean doIn = true; // !doOut

     String link = restUrl + urlAppend;
     URL url = new URL(link);
     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
     connection.setDoOutput(doOut);
     // uc.setDoInput(false);
     if (contentType != null && !"".equals(contentType)) {
             connection.setRequestProperty("Content-type", contentType);
             connection.setRequestProperty("Content-Type", contentType);
     }
     if (accept != null && !"".equals(accept)) {
             connection.setRequestProperty("Accept", accept);
     }

     connection.setRequestMethod(method.toString());

     if (isAuthorization()) {
             String userPasswordEncoded = new BASE64Encoder().encode((username + ":" + password).getBytes());
             connection.setRequestProperty("Authorization", "Basic " + userPasswordEncoded);
     }

     connection.connect();
     if (connection.getDoOutput()) {
             Writer writer = new OutputStreamWriter(connection.getOutputStream());
             char[] buffer = new char[1024];

             Reader reader = new BufferedReader(postDataReader);
             int n;
             while ((n = reader.read(buffer)) != -1) {
                     writer.write(buffer, 0, n);
             }

             writer.flush();
             writer.close();
     }
     return connection;
}


	private HttpURLConnection sendREST(String method, String urlAppend,
			URL zip, String contentType, String accept)
			throws MalformedURLException, IOException {
		boolean doOut = !METHOD_DELETE.equals(method) && zip != null;
		// boolean doIn = true; // !doOut

		String link = restUrl + urlAppend;
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(doOut);
		// uc.setDoInput(false);
		if (contentType != null && !"".equals(contentType)) {
			connection.setRequestProperty("Content-type", contentType);
			connection.setRequestProperty("Content-Type", contentType);
		}
		if (accept != null && !"".equals(accept)) {
			connection.setRequestProperty("Accept", accept);
		}

		connection.setRequestMethod(method.toString());

		if (isAuthorization()) {
			String userPasswordEncoded = new BASE64Encoder().encode((username
					+ ":" + password).getBytes());
			connection.setRequestProperty("Authorization", "Basic "
					+ userPasswordEncoded);
		}

		connection.connect();
		if (connection.getDoOutput()) {
			OutputStream out = connection.getOutputStream();
			InputStream in = zip.openStream();
			// Writer writer = new
			// OutputStreamWriter(connection.getOutputStream());
			char[] buffer = new char[1024];
			byte[] ioBuf = new byte[4096];
			int bytesRead;
			// Reader reader = new BufferedReader(postDataReader);
			int n;
			while ((bytesRead = in.read(ioBuf)) != -1)
				out.write(ioBuf, 0, bytesRead);
			out.close();
			in.close();

			// writer.flush();
			// writer.close();
		}
		return connection;
	}

	private HttpURLConnection sendREST(String method, String urlAppend,
			InputStream os, String contentType, String accept)
			throws MalformedURLException, IOException {
		boolean doOut = !METHOD_DELETE.equals(method) && os != null;
		// boolean doIn = true; // !doOut

		String link = restUrl + urlAppend;
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(doOut);
		// uc.setDoInput(false);
		if (contentType != null && !"".equals(contentType)) {
			connection.setRequestProperty("Content-type", contentType);
			connection.setRequestProperty("Content-Type", contentType);
		}
		if (accept != null && !"".equals(accept)) {
			connection.setRequestProperty("Accept", accept);
		}

		connection.setRequestMethod(method.toString());

		if (isAuthorization()) {
			String userPasswordEncoded = new BASE64Encoder().encode((username
					+ ":" + password).getBytes());
			connection.setRequestProperty("Authorization", "Basic "
					+ userPasswordEncoded);
		}

		connection.connect();
		if (connection.getDoOutput()) {
			OutputStream out = connection.getOutputStream();
			// Writer writer = new
			// OutputStreamWriter(connection.getOutputStream());
			char[] buffer = new char[1024];
			byte[] ioBuf = new byte[4096];
			int bytesRead;
			// Reader reader = new BufferedReader(postDataReader);
			int n;
			while ((bytesRead = os.read(ioBuf)) != -1)
				out.write(ioBuf, 0, bytesRead);
			out.close();
			os.close();

			// writer.flush();
			// writer.close();
		}
		return connection;
	}

	private HttpURLConnection sendREST(String method, String urlEncoded,
			String postData, String contentType, String accept)
			throws MalformedURLException, IOException {
		StringReader postDataReader = postData == null ? null
				: new StringReader(postData);
		return sendREST(method, urlEncoded, postDataReader, contentType, accept);
	}

	public int sendRESTint(String method, String url, String xmlPostContent)
			throws IOException {
		return sendRESTint(method, url, xmlPostContent, "application/xml",
				"application/xml");
	}

	/**
	 * @param method
	 *            e.g. 'POST', 'GET', 'PUT' or 'DELETE'
	 * @param urlEncoded
	 *            e.g. '/workspaces' or '/workspaces.xml'
	 * @param contentType
	 *            format of postData, e.g. null or 'text/xml'
	 * @param accept
	 *            format of response, e.g. null or 'text/xml'
	 * @param postData
	 *            e.g. xml data
	 * @throws IOException
	 * @return null, or response of server
	 */
	public int sendRESTint(String method, String urlEncoded, String postData,
			String contentType, String accept) throws IOException {
		HttpURLConnection connection = sendREST(method, urlEncoded, postData,
				contentType, accept);

		return connection.getResponseCode();
	}

	/**
	 * @param method
	 *            e.g. 'POST', 'GET', 'PUT' or 'DELETE'
	 * @param urlEncoded
	 *            e.g. '/workspaces' or '/workspaces.xml'
	 * @param contentType
	 *            format of postData, e.g. null or 'text/xml'
	 * @param accept
	 *            format of response, e.g. null or 'text/xml'
	 * @param postData
	 *            e.g. xml data
	 * @return null, or location field of the response header
	 */
	public String sendRESTlocation(String method, String urlEncoded,
			String postData, String contentType, String accept)
			throws IOException {
		HttpURLConnection connection = sendREST(method, urlEncoded, postData,
				contentType, accept);

		return connection.getHeaderField("Location");
	}

	/**
	 * Sends a REST request and return the answer as a String.
	 * 
	 * @param method
	 *            e.g. 'POST', 'GET', 'PUT' or 'DELETE'
	 * @param urlEncoded
	 *            e.g. '/workspaces' or '/workspaces.xml'
	 * @param contentType
	 *            format of postData, e.g. null or 'text/xml'
	 * @param accept
	 *            format of response, e.g. null or 'text/xml'
	 * @param is
	 *            where to read the data from
	 * @throws IOException
	 * @return null, or response of server
	 */
	public String sendRESTstring(String method, String urlEncoded, Reader is,
			String contentType, String accept) throws IOException {
		HttpURLConnection connection = sendREST(method, urlEncoded, is,
				contentType, accept);

		// Read response
		InputStream in = connection.getInputStream();
		try {

			int len;
			byte[] buf = new byte[1024];
			StringBuffer sbuf = new StringBuffer();
			while ((len = in.read(buf)) > 0) {
				sbuf.append(new String(buf, 0, len));
			}
			return sbuf.toString();
		} finally {
			in.close();
		}
	}
	
	public String sendRESTstring(String method, String urlEncoded, Reader is,
			String contentType, String accept,URL zip) throws IOException {
		HttpURLConnection connection = sendREST(method, urlEncoded,zip,
				contentType, accept);

		// Read response
		InputStream in = connection.getInputStream();
		try {

			int len;
			byte[] buf = new byte[1024];
			StringBuffer sbuf = new StringBuffer();
			while ((len = in.read(buf)) > 0) {
				sbuf.append(new String(buf, 0, len));
			}
			return sbuf.toString();
		} finally {
			in.close();
		}
	}
	
	public String sendRESTstring(String method, String urlEncoded, Reader is,
			String contentType, String accept,InputStream os) throws IOException {
		HttpURLConnection connection = sendREST(method, urlEncoded,os,
				contentType, accept);

		// Read response
		InputStream in = connection.getInputStream();
		try {

			int len;
			byte[] buf = new byte[1024];
			StringBuffer sbuf = new StringBuffer();
			while ((len = in.read(buf)) > 0) {
				sbuf.append(new String(buf, 0, len));
			}
			return sbuf.toString();
		} finally {
			in.close();
		}
	}

	public String sendRESTstring(String method, String url,
			String xmlPostContent) throws IOException {
		return sendRESTstring(method, url, xmlPostContent, "application/xml",
				"application/xml");
	}

	/**
	 * Sends a REST request and return the answer as a String
	 * 
	 * @param method
	 *            e.g. 'POST', 'GET', 'PUT' or 'DELETE'
	 * @param urlEncoded
	 *            e.g. '/workspaces' or '/workspaces.xml'
	 * @param contentType
	 *            format of postData, e.g. null or 'text/xml'
	 * @param accept
	 *            format of response, e.g. null or 'text/xml'
	 * @param postData
	 *            e.g. xml data
	 * @throws IOException
	 * @return null, or response of server
     * 只需调用这个方法便可得到xml文件
	 */
	public String sendRESTstring(String method, String urlEncoded,
			String postData, String contentType, String accept)
			throws IOException {
		HttpURLConnection connection = sendREST(method, urlEncoded, postData,
				contentType, accept);

		// Read response
		InputStream in = connection.getInputStream();
		try {

			int len;
			byte[] buf = new byte[1024];
			StringBuffer sbuf = new StringBuffer();
			while ((len = in.read(buf)) > 0) {
				sbuf.append(new String(buf, 0, len));
			}
			return sbuf.toString();
		} finally {
			in.close();
		}
	}

	/**
	 * Works: curl -u admin:geoserver -v -XPUT -H 'Content-type:
	 * application/zip' --data-binary
	 * 
	 * @/home/stefan/Desktop/arabicData.zip http:/
	 *                                      /localhost:8085/geoserver/rest
	 *                                      /workspaces
	 *                                      /ws/datastores/test1/file.shp
	 */
	private URL zip;

	public String uploadShape(String workspace, String dsName, URL zip)
			throws IOException {

		InputStream os = zip.openStream();
		this.zip = zip;
		try {
			InputStreamReader postDataReader = new InputStreamReader(os);

			String returnString = sendRESTstring(METHOD_PUT, "/workspaces/"
					+ workspace + "/datastores/" + dsName + "/file.shp",
					postDataReader, "application/zip", null,zip);

			// "?configure=all"
			return returnString;
		} finally {
			os.close();
		}
	}
	
	public String uploadShape(String workspace, String dsName, InputStream os)
		throws IOException {
		try {
			InputStreamReader postDataReader = new InputStreamReader(os);
		
			String returnString = sendRESTstring(METHOD_PUT, "/workspaces/"
					+ workspace + "/datastores/" + dsName + "/file.shp",
					postDataReader, "application/zip", null,os);
		
			// "?configure=all"
			return returnString;
		} finally {
			os.close();
		}
	}

	/**
	 * @throws IOException
	 */
	public boolean createCoverage(String wsName, String csName, String cName)
			throws IOException {

		String xml = "<coverage><name>" + cName + "</name><title>" + cName
				+ "</title></coverage>";

		int sendRESTint = sendRESTint(METHOD_POST, "/workspaces/" + wsName
				+ "/coveragestores/" + csName + "/coverages", xml);

		return 201 == sendRESTint;
	}

	public boolean reload() throws IOException {
		return 201 == sendRESTint(METHOD_POST, "/reload", null);
	}

}
