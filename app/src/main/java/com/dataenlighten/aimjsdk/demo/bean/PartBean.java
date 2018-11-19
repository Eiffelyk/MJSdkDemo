package com.dataenlighten.aimjsdk.demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/2/26 0026.
 */

public class PartBean implements Parcelable {
    private String standardPartName;
    private String partPrice;
    private String partNumber;
    private String image;
    private String thumbnailImage;
    private String partRefOnImage;

    public String getStandardPartName() {
        return standardPartName;
    }

    public void setStandardPartName(String standardPartName) {
        this.standardPartName = standardPartName;
    }

    public String getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(String partPrice) {
        this.partPrice = partPrice;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getPartRefOnImage() {
        return partRefOnImage;
    }

    public void setPartRefOnImage(String partRefOnImage) {
        this.partRefOnImage = partRefOnImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.standardPartName);
        dest.writeString(this.partPrice);
        dest.writeString(this.partNumber);
        dest.writeString(this.image);
        dest.writeString(this.thumbnailImage);
        dest.writeString(this.partRefOnImage);
    }

    public PartBean() {
    }

    protected PartBean(Parcel in) {
        this.standardPartName = in.readString();
        this.partPrice = in.readString();
        this.partNumber = in.readString();
        this.image = in.readString();
        this.thumbnailImage = in.readString();
        this.partRefOnImage = in.readString();
    }

    public static final Creator<PartBean> CREATOR = new Creator<PartBean>() {
        @Override
        public PartBean createFromParcel(Parcel source) {
            return new PartBean(source);
        }

        @Override
        public PartBean[] newArray(int size) {
            return new PartBean[size];
        }
    };
}
