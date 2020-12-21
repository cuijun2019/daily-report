package com.etone.project.modules.lte.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class LogInfoDto {
    private String projectCode;

    private String projectName;

    private String reporter;

    private String proportion;

    private String logTime;

    private String workNature;

    private String context;

    private String employee;

    private String employeeCode;

    public LogInfoDto(){

    }

    public LogInfoDto(String projectCode, String projectName, String reporter, String proportion, String logTime, String workNature, String context, String employee, String employeeCode) {
        super();
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.reporter = reporter;
        this.proportion = proportion;
        this.logTime = logTime;
        this.workNature = workNature;
        this.context = context;
        this.employee = employee;
        this.employeeCode = employeeCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getWorkNature() {
        return workNature;
    }

    public void setWorkNature(String workNature) {
        this.workNature = workNature;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
}
