package com.dataenlighten.aimjsdk.demo.bean;

import java.util.List;

/**
 * Create by eswing on 2018/11/19
 * Email:shiwen.yan@dataenlighten.com
 */
public class QueryPartsResult {
    private String code;
    private String toastMessage;
    private String context;
    private List<PartBean> partList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<PartBean> getPartList() {
        return partList;
    }

    public void setPartList(List<PartBean> partList) {
        this.partList = partList;
    }
}
