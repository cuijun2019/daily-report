package com.etone.project.base.type;

/**
 * 该类描述
 *
 * @author <a href="mailto:417877417@qq.com">liuyixuan</a>
 *         DateTime: 14-10-10  下午3:01
 */
public class TaskReport {
    private String Name;
    private String TableStrategy;
    private String View;
    private String CellField;
    private String Des;
    private String Xaxis;
    private String Yaxis;
    private String GisKpiField;
    private String DimFields;
    private String SgwmmeField;
    private String TacField;
    private String EnbField;
    private String GisCgiField;
    private String MeasureFields;
    private String timeStart;
    private String timeEnd;
    private String Sql;


    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getGisCgiField() {
        return GisCgiField;
    }

    public void setGisCgiField(String gisCgiField) {
        GisCgiField = gisCgiField;
    }

    public String getMeasureFields() {
        return MeasureFields;
    }

    public void setMeasureFields(String measureFields) {
        MeasureFields = measureFields;
    }

    public String getEnbField() {
        return EnbField;
    }

    public void setEnbField(String enbField) {
        EnbField = enbField;
    }

    public String getTacField() {
        return TacField;
    }

    public void setTacField(String tacField) {
        TacField = tacField;
    }

    public String getSgwmmeField() {
        return SgwmmeField;
    }

    public void setSgwmmeField(String sgwmmeField) {
        SgwmmeField = sgwmmeField;
    }

    public String getDimFields() {
        return DimFields;
    }

    public void setDimFields(String dimFields) {
        DimFields = dimFields;
    }

    public String getGisKpiField() {
        return GisKpiField;
    }

    public void setGisKpiField(String gisKpiField) {
        GisKpiField = gisKpiField;
    }

    public String getYaxis() {
        return Yaxis;
    }

    public void setYaxis(String yaxis) {
        Yaxis = yaxis;
    }

    public String getXaxis() {
        return Xaxis;
    }

    public void setXaxis(String xaxis) {
        Xaxis = xaxis;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTableStrategy() {
        return TableStrategy;
    }

    public void setTableStrategy(String tableStrategy) {
        TableStrategy = tableStrategy;
    }

    public String getView() {
        return View;
    }

    public void setView(String view) {
        View = view;
    }

    public String getCellField() {
        return CellField;
    }

    public void setCellField(String cellField) {
        CellField = cellField;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getSql() {
        return Sql;
    }

    public void setSql(String sql) {
        Sql = sql;
    }
}
