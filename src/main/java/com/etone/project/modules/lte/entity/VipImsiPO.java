package com.etone.project.modules.lte.entity;

import java.security.PrivateKey;

/**
 * Created with IntelliJ IDEA.
 * User: 郭强
 * Date: 15-8-27
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
public class VipImsiPO {

    private  String imsi;
    private  int incity;
    private String useracount;
     public VipImsiPO(String imsi, int incity,String useracount){
        this.imsi=imsi;
        this.incity=incity;
         this.useracount=useracount;
    }
    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public int getIncity() {
        return incity;
    }

    public void setIncity(int incity) {
        this.incity = incity;
    }
    public String getUseracount() {
        return useracount;
    }

    public void setUseracount(String useracount) {
        this.useracount = useracount;
    }
}
