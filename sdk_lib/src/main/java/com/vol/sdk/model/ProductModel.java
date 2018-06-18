package com.vol.sdk.model;

/**
 * Created by Administrator on 2017-9-26.
 */

public class ProductModel {
    private int status;  //订购状态 0 没有订购 1 已订购该产品
    private String desc;  //描述信息
    private String payUrl;  //订购地址，通过此地址生成二维码
    private String startTime; //产品开始时间
    private String endTime; //产品结束时间

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "status=" + status +
                ", desc='" + desc + '\'' +
                ", payUrl='" + payUrl + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
