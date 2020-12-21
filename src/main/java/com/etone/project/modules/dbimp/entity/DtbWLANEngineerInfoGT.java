package com.etone.project.modules.dbimp.entity;

import com.etone.ee.modules.data.Meta;
import com.etone.project.utils.ImportEntity;
import com.etone.project.utils.ImportTable;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
@ImportTable(tableName = "dtbWLANEngineerInfo_GT")
public class DtbWLANEngineerInfoGT {
    
    @Meta(name = "HOTSPOT_ID")
    @ImportEntity(importName = "HOTSPOT_ID", notNull = true)
    private String vcHostPotId;
    
    @Meta(name = "HOTSPOT_名称")
    @ImportEntity(importName = "HOTSPOT_名称")
    private String vcHostPotName;
    
    @Meta(name = "地址")
    @ImportEntity(importName = "地址")
    private String vcAddress;
    
    @Meta(name = "经度")
    @ImportEntity(importName = "经度")
    private Double dbLong;
    
    @Meta(name = "纬度")
    @ImportEntity(importName = "纬度")
    private Double dbLat;
    
    @Meta(name = "AP数量")
    @ImportEntity(importName = "AP数量")
    private Long intApNum;
    
    @Meta(name = "覆盖类型")
    @ImportEntity(importName = "覆盖类型")
    private String vcCoverType;

    public String getVcHostPotId() {
        return vcHostPotId;
    }

    public void setVcHostPotId(String vcHostPotId) {
        this.vcHostPotId = vcHostPotId;
    }

    public String getVcHostPotName() {
        return vcHostPotName;
    }

    public void setVcHostPotName(String vcHostPotName) {
        this.vcHostPotName = vcHostPotName;
    }

    public String getVcAddress() {
        return vcAddress;
    }

    public void setVcAddress(String vcAddress) {
        this.vcAddress = vcAddress;
    }

    public Double getDbLong() {
        return dbLong;
    }

    public void setDbLong(Double dbLong) {
        this.dbLong = dbLong;
    }

    public Double getDbLat() {
        return dbLat;
    }

    public void setDbLat(Double dbLat) {
        this.dbLat = dbLat;
    }

    public Long getIntApNum() {
        return intApNum;
    }

    public void setIntApNum(Long intApNum) {
        this.intApNum = intApNum;
    }

    public String getVcCoverType() {
        return vcCoverType;
    }

    public void setVcCoverType(String vcCoverType) {
        this.vcCoverType = vcCoverType;
    }
}
