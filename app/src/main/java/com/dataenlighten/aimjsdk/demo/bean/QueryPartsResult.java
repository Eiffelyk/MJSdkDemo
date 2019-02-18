package com.dataenlighten.aimjsdk.demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Create by eswing on 2018/11/19
 * Email:shiwen.yan@dataenlighten.com
 */
public class QueryPartsResult implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.toastMessage);
        dest.writeString(this.context);
        dest.writeTypedList(this.partList);
    }

    public QueryPartsResult() {
    }

    protected QueryPartsResult(Parcel in) {
        this.code = in.readString();
        this.toastMessage = in.readString();
        this.context = in.readString();
        this.partList = in.createTypedArrayList(PartBean.CREATOR);
    }

    public static final Creator<QueryPartsResult> CREATOR = new Creator<QueryPartsResult>() {
        @Override
        public QueryPartsResult createFromParcel(Parcel source) {
            return new QueryPartsResult(source);
        }

        @Override
        public QueryPartsResult[] newArray(int size) {
            return new QueryPartsResult[size];
        }
    };
}
