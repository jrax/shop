package com.qf.entity;

import java.io.Serializable;

public class TGoodsType implements Serializable {
    private Integer id;

    private String gtypeName;

    private Integer gtypeParentid;

    private String gtypePic;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGtypeName() {
        return gtypeName;
    }

    public void setGtypeName(String gtypeName) {
        this.gtypeName = gtypeName == null ? null : gtypeName.trim();
    }

    public Integer getGtypeParentid() {
        return gtypeParentid;
    }

    public void setGtypeParentid(Integer gtypeParentid) {
        this.gtypeParentid = gtypeParentid;
    }

    public String getGtypePic() {
        return gtypePic;
    }

    public void setGtypePic(String gtypePic) {
        this.gtypePic = gtypePic == null ? null : gtypePic.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}