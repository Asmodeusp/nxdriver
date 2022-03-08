package com.saimawzc.freight.dto.account;

import org.json.JSONObject;

import java.io.Serializable;

/***
 * 组织机构
 * **/
public class AuthorityDto implements Serializable {

    private String id;
    private String parentId;
    private String companyName;
    private JSONObject jsonObject;
    private int curremtleve;

    public int getCurremtleve() {
        return curremtleve;
    }

    public void setCurremtleve(int curremtleve) {
        this.curremtleve = curremtleve;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
