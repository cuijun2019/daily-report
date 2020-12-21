package com.etone.project.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A rudimentary XLSX -> CSV processor modeled on the POI sample program
 * XLS2CSVmra by Nick Burch from the package
 * org.apache.poi.hssf.eventusermodel.examples. Unlike the HSSF version, this
 * one completely ignores missing rows.
 * <p/>
 * Data sheets are read using a SAX parser to keep the memory footprint
 * relatively small, so this should be able to read enormous workbooks. The
 * styles table and the shared-string table must be kept in memory. The standard
 * POI styles table class is used, but a custom (read-only) class is used for
 * the shared string table because the standard POI SharedStringsTable grows
 * very quickly with the number of unique strings.
 * <p/>
 * Thanks to Eric Smith for a patch that fixes a problem triggered by cells with
 * multiple "t" elements, which is how Excel represents different formats (e.g.,
 * one word plain and one word bold).
 *
 * @author Chris Lott
 */
public class XLSX2CSV {

    // /////////////////////////////////////
    private OPCPackage xlsxPackage;
    private int minColumns;
    XSSFReader.SheetIterator sheetIterator;
    StylesTable stylesTable;
    ReadOnlySharedStringsTable readOnlyShareStringTable;

    /**
     * Creates a new XLSX -> CSV converter
     *
     * @param pkg The XLSX package to process
     * @param output The PrintStream to output the CSV to
     * @param minColumns The minimum number of columns to output, or -1 for no
     * minimum
     */
//    public XLSX2CSV(OPCPackage pkg, PrintStream output, int minColumns) {
//        this.xlsxPackage = pkg;
//        this.output = output;
//        this.minColumns = minColumns;
//    }
    //TODO catch exceptions
    public XLSX2CSV(InputStream fis) {
        try {
            xlsxPackage = OPCPackage.open(fis);

            minColumns = -1;
            readOnlyShareStringTable = new ReadOnlySharedStringsTable(
                    this.xlsxPackage);
            XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
            stylesTable = xssfReader.getStylesTable();
            sheetIterator = (XSSFReader.SheetIterator) xssfReader
                    .getSheetsData();
        } catch (Exception ex) {
            Logger.getLogger(XLSX2CSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Parses and shows the content of one sheet using the specified styles and
     * shared-strings tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public void processSheet(StylesTable styles,
            ReadOnlySharedStringsTable strings, InputStream sheetInputStream, StringBuilder output)
            throws IOException, ParserConfigurationException, SAXException {

        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();

//        PrintStream output = new PrintStream(this.outputPath + sheetName + ".csv", OUTPUT_CHARSET);

//        StringBuilder output = new StringBuilder();

        ContentHandler handler = new MyXSSFSheetHandler(styles, strings,
                this.minColumns, output);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     *
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Map<String, InputStream> process() {
        if (sheetIterator.hasNext()) {
            InputStream stream = sheetIterator.next();
            String sheetName = sheetIterator.getSheetName();

            StringBuilder output = new StringBuilder();
            try {
                processSheet(stylesTable, readOnlyShareStringTable, stream, output);
                stream.close();
            } catch (Exception ex) {
                Logger.getLogger(XLSX2CSV.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                return null;
            }
            byte[] bytes;
            try {
                bytes = output.toString().getBytes("gbk");
            } catch (UnsupportedEncodingException ex) {
                bytes = new byte[1];
                bytes[0] = 0;
                Logger.getLogger(XLSX2CSV.class.getName()).log(Level.SEVERE, null, ex);
            }
            InputStream is = new ByteArrayInputStream(bytes);
            Map<String, InputStream> csvMap = new HashMap();
            csvMap.put(sheetName, is);
            return csvMap;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream("E:/work/20130813/流量均衡相关/导入数据模板/GSM 工程参数模板new.xlsx");
        XLSX2CSV xlsx2csv = new XLSX2CSV(fis);
        Map<String, InputStream> csvMap;
        while ((csvMap = xlsx2csv.process()) != null) {
            Set<String> sheetNameSet = csvMap.keySet();
            for (String sheetName : sheetNameSet) {
                String filePath = "f:/" + sheetName + ".csv";
                FileOutputStream fos = new FileOutputStream(filePath);
                int size = 2048;
                byte[] data = new byte[size];
                InputStream in = csvMap.get(sheetName);
                int len;
                while ((len = in.read(data)) != -1) {
                    //                    System.out.println("len=" + len);
                    fos.write(data, 0, len);
                }
                in.close();
                fos.flush();
                fos.close();
//                InputStream in = csvMap.get(sheetName);
//                ImportFromCsvUtil ifcu = new ImportFromCsvUtil(in, sheetName);
            }

        }
        long end = System.currentTimeMillis();

        System.out.println("转化消耗时间：" + (double) (end - begin) / 1000 + "s");
    }

    /**
     * The type of the data value is indicated by an attribute on the cell. The
     * value is usually in a "v" element within the cell.
     */
    enum xssfDataType {

        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,}

    /**
     * Derived from http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
     * <p/>
     * Also see Standard ECMA-376, 1st edition, part 4, pages 1928ff, at
     * http://www.ecma-international.org/publications/standards/Ecma-376.htm
     * <p/>
     * A web-friendly version is http://openiso.org/Ecma/376/Part4
     */
    class MyXSSFSheetHandler extends DefaultHandler {

        /**
         * Table with styles
         */
        private StylesTable stylesTable;
        /**
         * Table with unique strings
         */
        private ReadOnlySharedStringsTable sharedStringsTable;
        /**
         * Destination for data
         */
        private final StringBuilder output;
        /**
         * Number of columns to read starting with leftmost
         */
        private final int minColumnCount;
        // Set when V start element is seen
        private boolean vIsOpen;
        // Set when cell start element is seen;
        // used when cell close element is seen.
        private xssfDataType nextDataType;
        // Used to format numeric cell values.
        private short formatIndex;
        private String formatString;
        private final DataFormatter formatter;
        private int thisColumn = -1;
        // The last column printed to the output stream
        private int lastColumnNumber = -1;
        private int rowNum = 0;
        // Gathers characters as they are seen.
        private StringBuffer value;

        /**
         * Accepts objects needed while parsing.
         *
         * @param styles Table of styles
         * @param strings Table of shared strings
         * @param cols Minimum number of columns to show
         * @param target Sink for output
         */
        public MyXSSFSheetHandler(StylesTable styles,
                ReadOnlySharedStringsTable strings, int cols, StringBuilder target) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColumnCount = cols;
            this.output = target;
            this.value = new StringBuffer();
            this.nextDataType = xssfDataType.NUMBER;
            this.formatter = new DataFormatter();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
         * java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        @Override
        public void startElement(String uri, String localName, String name,
                Attributes attributes) throws SAXException {

//            System.out.println("startElement, thisColumn:" + this.thisColumn);

            if ("inlineStr".equals(name) || "v".equals(name)) {
                vIsOpen = true;
                // Clear contents cache
                value.setLength(0);
            } // c => cell
            else if ("c".equals(name)) {
                // Get the cell reference
                String r = attributes.getValue("r");
                int firstDigit = -1;
                for (int c = 0; c < r.length(); ++c) {
                    if (Character.isDigit(r.charAt(c))) {
                        firstDigit = c;
                        break;
                    }
                }
                thisColumn = nameToColumn(r.substring(0, firstDigit));

                // Set up defaults.
                this.nextDataType = xssfDataType.NUMBER;
                this.formatIndex = -1;
                this.formatString = null;
                String cellType = attributes.getValue("t");
                String cellStyleStr = attributes.getValue("s");
                if ("b".equals(cellType)) {
                    nextDataType = xssfDataType.BOOL;
                } else if ("e".equals(cellType)) {
                    nextDataType = xssfDataType.ERROR;
                } else if ("inlineStr".equals(cellType)) {
                    nextDataType = xssfDataType.INLINESTR;
                } else if ("s".equals(cellType)) {
                    nextDataType = xssfDataType.SSTINDEX;
                } else if ("str".equals(cellType)) {
                    nextDataType = xssfDataType.FORMULA;
                } else if (cellStyleStr != null) {
                    // It's a number, but almost certainly one
                    // with a special style or format
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    this.formatIndex = style.getDataFormat();
                    this.formatString = style.getDataFormatString();
                    
                    if (this.formatString == null) {
                        this.formatString = BuiltinFormats
                                .getBuiltinFormat(this.formatIndex);
                    }
                    
                    if (this.formatString.equals("m/d/yy h:mm")) {  //写死时间格式
                        this.formatString = "yyyy-MM-dd HH:mm:ss";
                    } else {
                        if (this.formatString.equals("m/d/yy")) {
                            this.formatString = "yyyy-MM-dd";
                        }
                    }
                }
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
         * java.lang.String, java.lang.String)
         */
        /*@Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {

            String thisStr = null;

            // v => contents of a cell
            if ("v".equals(name)) {
                // Process the value contents as required.
                // Do now, as characters() may be called more than once
                switch (nextDataType) {

                    case BOOL:
                        char first = value.charAt(0);
                        thisStr = first == '0' ? "FALSE" : "TRUE";
                        break;

                    case ERROR:
                        thisStr = value.toString();
                        break;

                    case FORMULA:
                        // A formula could result in a string value,
                        // so always add double-quote characters.
                        thisStr = value.toString();
                        break;

                    case INLINESTR:
                        // TODO: have seen an example of this, so it's untested.
                        XSSFRichTextString rtsi = new XSSFRichTextString(value
                                .toString());
                        thisStr = rtsi.toString();
                        break;

                    case SSTINDEX:
                        String sstIndex = value.toString();
                        try {
                            int idx = Integer.parseInt(sstIndex);
                            XSSFRichTextString rtss = new XSSFRichTextString(
                                    sharedStringsTable.getEntryAt(idx));
                            thisStr = rtss.toString();
                        } catch (NumberFormatException ex) {
                            output.append("Failed to parse SST index '").append(sstIndex).append("': ").append(ex.toString());
                        }
                        break;

                    case NUMBER:
                        String n = value.toString();
                        if (this.formatString != null && this.formatIndex != 3 && this.formatIndex != 4) {   //不是浮点型才转
                            thisStr = formatter.formatRawCellContents(Double
                                    .parseDouble(n), this.formatIndex,
                                    this.formatString);
                        } else {
                            thisStr = n;
                        }
                        break;

                    default:
                        thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
                        break;
                }

                //如果有，号会影响文档结构的，所以对有，号的内容都加上""
                if(thisStr.indexOf(",") > 0) {
                    thisStr = '"' + thisStr + '"'; 
                }
                
                thisStr = thisStr.replaceAll("[\\n\\r]", ""); //有\n会对读取文档造成影响，暂时没有很好的解决办法
                

                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }

                for (int i = lastColumnNumber; i < thisColumn; ++i) {
                    output.append(',');
                }

                output.append(thisStr);

                // Update column
                if (thisColumn > -1) {
                    lastColumnNumber = thisColumn;
                }


            } else if ("row".equals(name)) {

                // Print out any missing commas if needed
                if (minColumns > 0) {
                    // Columns are 0 based
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    }
                    for (int i = lastColumnNumber; i < (this.minColumnCount); i++) {
                        output.append(',');
                    }
                }

                // We're onto a new row
                output.append("\n");
                lastColumnNumber = -1;
                this.rowNum++;
            }

        } */

        /**
         * Captures characters only if a suitable element is open. Originally
         * was just "v"; extended for inlineStr also.
         */
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (vIsOpen) {
                value.append(ch, start, length);
            }
        }

        /**
         * Converts an Excel column name like "C" to a zero-based index.
         *
         * @param name
         * @return Index corresponding to the specified name
         */
        private int nameToColumn(String name) {
            int column = -1;
            for (int i = 0; i < name.length(); ++i) {
                int c = name.charAt(i);
                column = (column + 1) * 26 + c - 'A';
            }
            return column;
        }
    }
}