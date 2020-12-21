/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etone.project.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * A XLS -> CSV processor, that uses the MissingRecordAware EventModel code to
 * ensure it outputs all columns and rows.
 *
 * @author Nick Burch
 */
public class XLS2CSV implements HSSFListener {

    private int minColumns;
    private POIFSFileSystem fs;
    private PrintStream output;
    
    private Map<String, ByteArrayOutputStream> outputMap;
    
    private int lastRowNumber;
    private int lastColumnNumber;
    /**
     * Should we output the formula, or the value it has?
     */
    private boolean outputFormulaValues = true;
    /**
     * For parsing Formulas
     */
    private SheetRecordCollectingListener workbookBuildingListener;
    private HSSFWorkbook stubWorkbook;
    // Records we pick up as we process
    private SSTRecord sstRecord;
    private FormatTrackingHSSFListener formatListener;
    /**
     * So we known which sheet we're on
     */
    private int sheetIndex = -1;
    private BoundSheetRecord[] orderedBSRs;
    private ArrayList boundSheetRecords = new ArrayList();
    // For handling formulas with string results
    private int nextRow;
    private int nextColumn;
    private boolean outputNextStringRecord;
    private final String OUTPUT_CHARSET = "GBK";

    /**
     * Creates a new XLS -> CSV converter
     *
     * @param fs The POIFSFileSystem to process
     * @param output The PrintStream to output the CSV to
     * @param minColumns The minimum number of columns to output, or -1 for no
     * minimum
     */
    public XLS2CSV(POIFSFileSystem fs, PrintStream output, int minColumns) {
        this.fs = fs;
        this.output = output;
        this.minColumns = minColumns;
        this.outputMap = new HashMap();
    }

    public XLS2CSV(String inputFilePath) throws Exception {
        fs = new POIFSFileSystem(new FileInputStream(inputFilePath));
//        output = new PrintStream(outputFilePath, OUTPUT_CHARSET);
        minColumns = -1;
        this.outputMap = new HashMap();
    }
    
    public XLS2CSV(InputStream in) {
        try {
            fs = new POIFSFileSystem(in);
        } catch (IOException ex) {
            Logger.getLogger(XLS2CSV.class.getName()).log(Level.SEVERE, null, ex);
        }

        minColumns = -1;
        this.outputMap = new HashMap();
    }

    /**
     * Creates a new XLS -> CSV converter
     *
     * @param filename The file to process
     * @param minColumns The minimum number of columns to output, or -1 for no
     * minimum
     * @throws IOException
     * @throws FileNotFoundException
     */
    public XLS2CSV(String filename, int minColumns) throws IOException,
            FileNotFoundException {
        this(new POIFSFileSystem(new FileInputStream(filename)), System.out,
                minColumns);
    }

    /**
     * Initiates the processing of the XLS file to CSV
     */
    public Map<String, ByteArrayOutputStream> process(){
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(
                this);
        formatListener = new FormatTrackingHSSFListener(listener);

        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();

        if (outputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            workbookBuildingListener = new SheetRecordCollectingListener(
                    formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
        }
        try {
            factory.processWorkbookEvents(request, fs);
        } catch (IOException ex) {
            Logger.getLogger(XLS2CSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.outputMap;
    }
    
    public Map<String, InputStream> getCsvMap() {
        if(this.outputMap == null || this.outputMap.isEmpty()) {
            this.process();
        }
        
        HashMap<String, InputStream> csvMap = new HashMap();
        Set<String> sheetNameSheet = this.outputMap.keySet();
        
        for(String sheetName : sheetNameSheet) {
            ByteArrayOutputStream baos = this.outputMap.get(sheetName);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            csvMap.put(sheetName, bais);
        }
        
        return csvMap;
    }

    /**
     * Main HSSFListener method, processes events, and outputs the CSV as the
     * file is processed.
     */
    @Override
    public void processRecord(Record record) {
        int thisRow = -1;
        int thisColumn = -1;
        String thisStr = null;

        switch (record.getSid()) {
            case BoundSheetRecord.sid:
                boundSheetRecords.add(record);
                break;
            case BOFRecord.sid:
                BOFRecord br = (BOFRecord) record;
                if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
                    // Create sub workbook if required
                    if (workbookBuildingListener != null && stubWorkbook == null) {
                        stubWorkbook = workbookBuildingListener
                                .getStubHSSFWorkbook();
                    }

                    // Output the worksheet name
                    // Works by ordering the BSRs by the location of
                    // their BOFRecords, and then knowing that we
                    // process BOFRecords in byte offset order
                    sheetIndex++;
                    if (orderedBSRs == null) {
                        orderedBSRs = BoundSheetRecord
                                .orderByBofPosition(boundSheetRecords);
                    }
            //新的sheet开始了
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    this.outputMap.put(orderedBSRs[sheetIndex].getSheetname(), baos);
            try {
                this.output = new PrintStream(baos, true, "gbk");
                    
//                    output.println();
//                    output.println(orderedBSRs[sheetIndex].getSheetname() + " ["
//                            + (sheetIndex + 1) + "]:");
            } catch (UnsupportedEncodingException ex) {
                this.output = new PrintStream(baos);
                Logger.getLogger(XLS2CSV.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
                break;

            case SSTRecord.sid:
                sstRecord = (SSTRecord) record;
                break;

            case BlankRecord.sid:
                BlankRecord brec = (BlankRecord) record;

                thisRow = brec.getRow();
                thisColumn = brec.getColumn();
                thisStr = "";
                break;
            case BoolErrRecord.sid:
                BoolErrRecord berec = (BoolErrRecord) record;

                thisRow = berec.getRow();
                thisColumn = berec.getColumn();
                thisStr = "";
                break;

            case FormulaRecord.sid:
                FormulaRecord frec = (FormulaRecord) record;

                thisRow = frec.getRow();
                thisColumn = frec.getColumn();

                if (outputFormulaValues) {
                    if (Double.isNaN(frec.getValue())) {
                        // Formula result is a string
                        // This is stored in the next record
                        outputNextStringRecord = true;
                        nextRow = frec.getRow();
                        nextColumn = frec.getColumn();
                    } else {
                        thisStr = formatListener.formatNumberDateCell(frec);
                    }
                } else {
                    thisStr = HSSFFormulaParser.toFormulaString(stubWorkbook,
                            frec.getParsedExpression());
                }
                break;
            case StringRecord.sid:
                if (outputNextStringRecord) {
                    // String for formula
                    StringRecord srec = (StringRecord) record;
                    thisStr = srec.getString();
                    thisRow = nextRow;
                    thisColumn = nextColumn;
                    outputNextStringRecord = false;
                }
                break;

            case LabelRecord.sid:
                LabelRecord lrec = (LabelRecord) record;

                thisRow = lrec.getRow();
                thisColumn = lrec.getColumn();
                thisStr = lrec.getValue();
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lsrec = (LabelSSTRecord) record;

                thisRow = lsrec.getRow();
                thisColumn = lsrec.getColumn();
                if (sstRecord == null) {
                    thisStr = "(No SST Record, can't identify string)";
                } else {
                    thisStr = sstRecord.getString(lsrec.getSSTIndex())
                            .toString();
                }
                break;
            case NoteRecord.sid:
                NoteRecord nrec = (NoteRecord) record;

                thisRow = nrec.getRow();
                thisColumn = nrec.getColumn();
                // TODO: Find object to match nrec.getShapeId()
                thisStr = "(TODO)";
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;

                thisRow = numrec.getRow();
                thisColumn = numrec.getColumn();

                // Format
                thisStr = formatListener.formatNumberDateCell(numrec);
                
                //对于数值类型如果有,号，把它消灭
                if(thisStr != null) {
                    thisStr = thisStr.replace(",", "");
                }
                
                break;
            case RKRecord.sid:
                RKRecord rkrec = (RKRecord) record;

                thisRow = rkrec.getRow();
                thisColumn = rkrec.getColumn();
                thisStr = "(TODO)";
                break;
            default:
                break;
        }

        // Handle new row
        if (thisRow != -1 && thisRow != lastRowNumber) {
            lastColumnNumber = -1;
        }

        // Handle missing column
        if (record instanceof MissingCellDummyRecord) {
            MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
            thisRow = mc.getRow();
            thisColumn = mc.getColumn();
            thisStr = "";
        }

        // If we got something to print out, do so
        if (thisStr != null) {
            if (thisColumn > 0) {
                output.print(',');
            }
            if(thisStr.indexOf(",") > 0) {
                thisStr = '"' + thisStr + '"';  //对于内内容含有,号的加双引号
            }
            output.print(thisStr);
        }

        // Update column and row count
        if (thisRow > -1) {
            lastRowNumber = thisRow;
        }
        if (thisColumn > -1) {
            lastColumnNumber = thisColumn;
        }

        // Handle end of row
        if (record instanceof LastCellOfRowDummyRecord) {
            // Print out any missing commas if needed
            if (minColumns > 0) {
                // Columns are 0 based
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }
                for (int i = lastColumnNumber; i < (minColumns); i++) {
                    output.print(',');
                }
            }

            // We're onto a new row
            lastColumnNumber = -1;

            // End the row
            output.println();
        }
    }

    public static void main(String[] args) throws Exception {
        
        XLS2CSV xls2csv = new XLS2CSV("E:/work/20130813/流量均衡相关/导入数据模板/GSM 工程参数模板new.xls");
        Map<String, ByteArrayOutputStream> csvMap = xls2csv.process();
        
        Set<String> sheetNameSet = csvMap.keySet();
            for (String sheetName : sheetNameSet) {
                ByteArrayOutputStream baos = csvMap.get(sheetName);
                String filePath = "f:/" + sheetName + ".csv";
                FileOutputStream fos = new FileOutputStream(filePath);

                baos.writeTo(fos);

                fos.flush();
                fos.close();
                
//                BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray())));
//                String s;
//                while((s = br.readLine()) != null) {
//                    System.out.println(s);
//                }
//                br.close();
                
                baos.close();
            }
    }
}