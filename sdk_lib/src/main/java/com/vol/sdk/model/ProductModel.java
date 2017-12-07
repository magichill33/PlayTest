package com.vol.sdk.model;

/**
 * Created by Administrator on 2017-9-26.
 */

public class ProductModel {
    private int status;  //订购状态 0 没有订购 1 已订购该产品
    private String desc;  //描述信息
    private String payUrl;  //订购地址，通过此地址生成二维码

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

    @Override
    public String toString() {
        return "ProductModel{" +
                "status=" + status +
                ", desc='" + desc + '\'' +
                ", payUrl='" + payUrl + '\'' +
                '}';
    }
}
