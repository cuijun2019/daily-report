package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 * modify 2014-1-4
 */
@ImportTable(tableName = "dtbTDEachOperation")
public class DtbTDEachOperation {

    @Meta(name = "CGI")
    @ImportEntity(importName = "CGI")
    private String vcCgi;
    
    @Meta(name = "CellID")
    @ImportEntity(importName = "CellID", notNull = true)
    private String vcCellId;
    
    @Meta(name = "Cell名称")
    @ImportEntity(importName = "Cell名称")
    private String vcCellName;
    
    @Meta(name = "3G最小接入电平")
    @ImportEntity(importName = "3G最小接入电平")
    private Double int3GMinConRsrp;
    
    @Meta(name = "空闲态异系统TD到GSM重选门限")
    @ImportEntity(importName = "空闲态异系统TD到GSM重选门限")
    private Double intFreeReChThreshold;
    
    @Meta(name = "CS业务本系统门限")
    @ImportEntity(importName = "CS业务本系统门限")
    private Double intCsBusThisThreshold;
    
    @Meta(name = "CS业务异系统门限")
    @ImportEntity(importName = "CS业务异系统门限")
    private Double intCsBusDiffThreshold;
    
    @Meta(name = "H业务本系统门限")
    @ImportEntity(importName = "H业务本系统门限")
    private Double intHBusThisThreshold;
    
    @Meta(name = "H业务异系统门限")
    @ImportEntity(importName = "H业务异系统门限")
    private Double intHBusDiffThreshold;
    
    @Meta(name = "PS非H业务本系统门限")
    @ImportEntity(importName = "PS非H业务本系统门限")
    private Double intPsNonHThisThreshold;
    
    @Meta(name = "PS非H业务异系统门限")
    @ImportEntity(importName = "PS非H业务异系统门限")
    private Double intPsNonHDiffThreshold;
    
    @Meta(name = "PCCPCH发射功率")
    @ImportEntity(importName = "PCCPCH发射功率")
    private Double dbPccpchPower;

    public String getVcCgi() {
        return vcCgi;
    }

    public void setVcCgi(String vcCgi) {
        this.vcCgi = vcCgi;
    }

    public String getVcCellId() {
        return vcCellId;
    }

    public void setVcCellId(String vcCellId) {
        this.vcCellId = vcCellId;
    }

    public String getVcCellName() {
        return vcCellName;
    }

    public void setVcCellName(String vcCellName) {
        this.vcCellName = vcCellName;
    }

    public Double getInt3GMinConRsrp() {
        return int3GMinConRsrp;
    }

    public void setInt3GMinConRsrp(Double int3GMinConRsrp) {
        this.int3GMinConRsrp = int3GMinConRsrp;
    }

    public Double getIntFreeReChThreshold() {
        return intFreeReChThreshold;
    }

    public void setIntFreeReChThreshold(Double intFreeReChThreshold) {
        this.intFreeReChThreshold = intFreeReChThreshold;
    }

    public Double getIntCsBusThisThreshold() {
        return intCsBusThisThreshold;
    }

    public void setIntCsBusThisThreshold(Double intCsBusThisThreshold) {
        this.intCsBusThisThreshold = intCsBusThisThreshold;
    }

    public Double getIntCsBusDiffThreshold() {
        return intCsBusDiffThreshold;
    }

    public void setIntCsBusDiffThreshold(Double intCsBusDiffThreshold) {
        this.intCsBusDiffThreshold = intCsBusDiffThreshold;
    }

    public Double getIntHBusThisThreshold() {
        return intHBusThisThreshold;
    }

    public void setIntHBusThisThreshold(Double intHBusThisThreshold) {
        this.intHBusThisThreshold = intHBusThisThreshold;
    }

    public Double getIntHBusDiffThreshold() {
        return intHBusDiffThreshold;
    }

    public void setIntHBusDiffThreshold(Double intHBusDiffThreshold) {
        this.intHBusDiffThreshold = intHBusDiffThreshold;
    }

    public Double getIntPsNonHThisThreshold() {
        return intPsNonHThisThreshold;
    }

    public void setIntPsNonHThisThreshold(Double intPsNonHThisThreshold) {
        this.intPsNonHThisThreshold = intPsNonHThisThreshold;
    }

    public Double getIntPsNonHDiffThreshold() {
        return intPsNonHDiffThreshold;
    }

    public void setIntPsNonHDiffThreshold(Double intPsNonHDiffThreshold) {
        this.intPsNonHDiffThreshold = intPsNonHDiffThreshold;
    }

    public Double getDbPccpchPower() {
        return dbPccpchPower;
    }

    public void setDbPccpchPower(Double dbPccpchPower) {
        this.dbPccpchPower = dbPccpchPower;
    }
    
}
