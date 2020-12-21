package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "dtbGSMEachOperation")
public class DtbGSMEachOperation {

    @Meta(name = "CGI")
    @ImportEntity(importName = "CGI")
    private String vcCgi;

    @Meta(name = "CellID")
    @ImportEntity(importName = "CellID")
    private String vcCellId;

    @Meta(name = "Cell名称")
    @ImportEntity(importName = "Cell名称")
    private String vcCellName;

    @Meta(name = "Qsearch_I")
    @ImportEntity(importName = "Qsearch_I")
    private Integer intNoQsearchI;

    @Meta(name = "TDD_offset")
    @ImportEntity(importName = "TDD_offset")
    private Integer intTDDOffset;

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

    public Integer getIntNoQsearchI() {
        return intNoQsearchI;
    }

    public void setIntNoQsearchI(Integer intNoQsearchI) {
        this.intNoQsearchI = intNoQsearchI;
    }

    public Integer getIntTDDOffset() {
        return intTDDOffset;
    }

    public void setIntTDDOffset(Integer intTDDOffset) {
        this.intTDDOffset = intTDDOffset;
    }
}
