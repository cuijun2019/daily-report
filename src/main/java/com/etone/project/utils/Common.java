package com.etone.project.utils;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-6-29
 * Time: 下午9:36
 * To change this template use File | Settings | File Templates.
 */
public class Common {
    private static final Logger logger = LoggerFactory.getLogger(Common.class);

    public static String buildXmlFile(Document doc) {
        String xmlString = "";
        String path = getRealPath() + File.separator + "WEB-INF" + File.separator + "uploadfile" + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            XMLOutputter docWriter = new XMLOutputter();
            docWriter.setFormat(format);
            docWriter.output(doc, new FileOutputStream(path + getDateString3(new Date()) + ".xml"));
            xmlString = docWriter.outputString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlString;
    }

    public static String getRealPath() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        if (url == null) {
            url = Common.class.getResource("/");
        }
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            logger.error("", e);
        }
        while (file != null) {
            if (file.getName().equals("WEB-INF")) {
                file = file.getParentFile();
                break;
            }
            file = file.getParentFile();
        }
        return file.getAbsolutePath();
    }

    public static String getDateString2(Date date) {
        return getDateString(date, "yyyyMM");
    }

    public static String getDateString3(Date date) {
        return getDateString(date, "yyyyMMdd");
    }

    public static String getDateString(Date date) {
        return getDateString(date, "yyyy-MM-dd");
    }

    public static Date getYMDDate(String s) {
        if (s == null) {
            return null;
        }
        // 2007-1-26
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        if (!judgeString(s))
            return null;
        try {
            Date date = simpledateformat.parse(s);
            return date;
        } catch (Exception _ex) {
            _ex.printStackTrace();
            //log.error("DateFormat Error,PLZ Follow This :yyyy-MM-dd", _ex);
            return null;
        }
    }

    public static String getDateString(Date date, String sFormat) {
        SimpleDateFormat format = new SimpleDateFormat(sFormat);
        if (date != null) {
            return format.format(date);
        }
        return "";
    }

    public static Date getDate(String s) {
        try {
            if (!judgeString(s))
                return null;
            if (s.length() < 11) {
                s = s + " 01:01:01";
            }
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpledateformat.parse(s);
        } catch (Exception _ex) {
            _ex.printStackTrace();
            return null;
        }
    }

    public static boolean judgeString(String str) {
        boolean flag = false;
        if (str != null && !(str.trim()).equals("")
                && !str.equalsIgnoreCase("null")) {
            flag = true;
        }
        return flag;
    }

    public static String addInteger(String dateStr, int amount) {
        Date myDate = null;
        String myDateStr = "";
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpledateformat.parse(dateStr);

            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, amount);
                myDate = calendar.getTime();
                myDateStr = simpledateformat.format(myDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myDateStr;
    }

    public static String convertCity(String cityCode) {
        String cityName = "";
        if ("gd".equalsIgnoreCase(cityCode)) {
            cityName = "省公司";
        } else if ("cz".equalsIgnoreCase(cityCode)) {
            cityName = "潮州";
        } else if ("dg".equalsIgnoreCase(cityCode)) {
            cityName = "东莞";
        } else if ("fs".equalsIgnoreCase(cityCode)) {
            cityName = "佛山";
        } else if ("gz".equalsIgnoreCase(cityCode)) {
            cityName = "广州";
        } else if ("zh".equalsIgnoreCase(cityCode)) {
            cityName = "珠海";
        } else if ("hz".equalsIgnoreCase(cityCode)) {
            cityName = "惠州";
        } else if ("jm".equalsIgnoreCase(cityCode)) {
            cityName = "江门";
        } else if ("jy".equalsIgnoreCase(cityCode)) {
            cityName = "揭阳";
        } else if ("mm".equalsIgnoreCase(cityCode)) {
            cityName = "茂名";
        } else if ("mz".equalsIgnoreCase(cityCode)) {
            cityName = "梅州";
        } else if ("qy".equalsIgnoreCase(cityCode)) {
            cityName = "清远";
        } else if ("st".equalsIgnoreCase(cityCode)) {
            cityName = "汕头";
        } else if ("sw".equalsIgnoreCase(cityCode)) {
            cityName = "汕尾";
        } else if ("sg".equalsIgnoreCase(cityCode)) {
            cityName = "韶关";
        } else if ("sz".equalsIgnoreCase(cityCode)) {
            cityName = "深圳";
        } else if ("yj".equalsIgnoreCase(cityCode)) {
            cityName = "阳江";
        } else if ("yf".equalsIgnoreCase(cityCode)) {
            cityName = "云浮";
        } else if ("zj".equalsIgnoreCase(cityCode)) {
            cityName = "湛江";
        } else if ("zq".equalsIgnoreCase(cityCode)) {
            cityName = "肇庆";
        } else if ("zs".equalsIgnoreCase(cityCode)) {
            cityName = "中山";
        } else if ("hy".equalsIgnoreCase(cityCode)) {
            cityName = "河源";
        }
        return cityName;
    }

    public static Document convertStringToDocument(String xmlString) {
        StringReader sr = null;
        InputSource is = null;
        Document doc = null;
        String[] resultInfo = new String[3];
        try {
            sr = new StringReader(xmlString);
            is = new InputSource(sr);
            doc = (new SAXBuilder()).build(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return doc;
    }

    public static String[] convertStringToXml(String xmlString) {
        StringReader sr = null;
        InputSource is = null;
        Document doc = null;
        String[] resultInfo = new String[3];
        try {
            sr = new StringReader(xmlString);
            is = new InputSource(sr);
            doc = (new SAXBuilder()).build(is);
            Element root = doc.getRootElement();
            List jiedian = root.getChildren();
            Element et = null;
            for(int i=0;i < jiedian.size(); i++) {
                et = (Element) jiedian.get(i);//循环依次得到子元素
                resultInfo[i] = et.getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resultInfo;
    }

    public static void copyFile(InputStream inputStream, String descPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            int pos = descPath.lastIndexOf(File.separator);
            if (pos > -1) {
                File folder = new File(descPath.substring(0, pos));
                if (!folder.exists() || !folder.isDirectory()) {
                    folder.mkdirs();
                }
            }
            File file = new File(descPath);
            if (file.exists()) {
                file.delete();
            }

            in = new BufferedInputStream(inputStream, 1024);
            out = new BufferedOutputStream(new FileOutputStream(file), 1024);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void mkdir(String path) {
        try {
            int pos = path.lastIndexOf(File.separator);
            if (pos > -1) {
                File folder = new File(path.substring(0, pos));
                if (!folder.exists() || !folder.isDirectory()) {
                    folder.mkdirs();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下划线分隔的字符串，转换为驼峰式字符串
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        Pattern p = Pattern.compile("_[0-9|a-z]");
        Matcher m = p.matcher(param);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String firstChar =  m.group().substring(1, 2);
            m.appendReplacement(sb, firstChar.toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String convertInteger(String name) {
        boolean flag = true;
        if (name.contains(".")) {
            String[] numbers = name.split("\\.");
            char[] ch = numbers[1].toCharArray();
            for (int i = 0; i < ch.length; i++) {
                if (!"0".equals(String.valueOf(ch[i]))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                name = name.substring(0, name.indexOf("."));
            }
        }
        return name;
    }

    /**
     * @param list
     *            若list保存的为对象，则抛出异常
     * @param flag
     *            标识符
     * @return 若flag 为"1", 返回格式为 '1', '2', '3'; 否则返回格式 1, 2, 3
     */
    public static String ListToString(List<?> list, String flag) {
        try {
            String str = list.toString();
            if (judgeString(flag) && flag.equals("1")) {
                str = str.replaceAll("(\\[|\\])", "'");
                return str.replace(", ", "', '");
            } else {
                return str.substring(1, str.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Double roundDoubleW(Double value) {
        double d = 0.00d;
        try {
            d = Double.parseDouble(String.format("%.2f", value / 10000));
        } catch (Exception e) {
            // do noting;
        }

        return d;
    }

    public static Double roundDouble(Double value) {
        double d = 0.00d;
        try {
            d = Double.parseDouble(String.format("%.2f", value));
        } catch (Exception e) {
            // do noting;
        }

        return d;
    }

    public static Double roundDouble(String value) {
        double d = 0.00d;
        try {
            d = Double.parseDouble(String.format("%.2f", Double.parseDouble(value)));
        } catch (Exception e) {
            // do noting;
        }

        return d;
    }

    public static Double roundDoubleW(String value) {
        double d = 0.00d;
        try {
            d = Double.parseDouble(String.format("%.2f", Double.parseDouble(value) / 10000));
        } catch (Exception e) {
            // do noting;
        }

        return d;
    }

    public static boolean isNumber(String str){
        try {
            if (str.startsWith("-")) {
                str = str.substring(1);
            }
            if (str.contains("E")) {
                str = new BigDecimal(str).toPlainString();
            }
            String reg = "^[0-9]+(.[0-9]+)?$";
            return str.matches(reg);
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Long> stringToList(String s) {
        ArrayList<Long> slist = new ArrayList<Long>();
        String[] a = null;
        if (s != null && !s.equals("")) {
            a = s.split(",");
            for (int i = 0; i < a.length; i++) {
                slist.add(Common.StringToLong(a[i].trim()));
            }
        }
        return slist;
    }

    public static Long StringToLong(String s) {
        Long i = new Long(0);
        if (s == null || "".equals(s))
            i = new Long(0);
        else
            try {
                i = new Long(s.trim());
            } catch (Exception _ex) {
            }
        return i;
    }

    /**
     * 判断String类型是否为空或者空字符串
     *
     * @param s
     * @return true，非空；false，空
     */
    public static boolean StringIsNotNull(String s) {
        if (s != null && !"".equals(s.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 使用正则表达式
     *
     * @param input 输入字符
     * @param reg 正则表达式
     * @param true:有表达式内容
     * **/
    public static boolean checkReg(String input, String reg) {
        if (judgeString(input) && judgeString(reg)) {
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(input);
            return m.matches();
        }
        return false;
    }

    public static String formatDouble(Double value) {
        String str = String.valueOf(value);
        int index = str.indexOf(".");
        int dot = 0;
        if (index != -1) {
            dot = Integer.parseInt(str.substring(index + 1, index + 2));
            if (dot < 5) {
                str = str.substring(0, index);
            } else {
                str = str.substring(0, index) + ".5";
            }
        }
        if ("0".equals(str)) {
            return "0.5";
        }
        return str;
    }

    public static Object exec(String cmd) {
        try {
            String[] cmdA = { "/bin/sh", "-c", cmd };
            Process process = Runtime.getRuntime().exec(cmdA);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String pwdString = Common.exec("sox /opt/tomcat/webapps/ROOT/file/upload/1bR_uFV5qVTY2GJ25Jf7P5uZ2gnuv_wRhnbt1i9gurkKBV-KHB_F134oEOo6qiW9W.amr /opt/tomcat/webapps/ROOT/file/upload/1.mp3").toString();
        String netsString = Common.exec("sox /opt/tomcat/webapps/ROOT/file/upload/1WVXczlTrafI-ClUWX6tHTsIw0U4c2QmbwTJJ_Mv6s91Ppjw8aYNjely1sG32Ribh.amr /opt/tomcat/webapps/ROOT/file/upload/2.mp3").toString();

        System.out.println("==========获得值=============");
        System.out.println(pwdString);
        System.out.println(netsString);
    }

    public static String aa() {
        try {
            System.out.println("try:");
            return "1";
        } catch (Exception e) {
            System.out.println("catch:");
            e.printStackTrace();
        } finally {
            System.out.println("finally:");
        }
        return "2";
    }
}
