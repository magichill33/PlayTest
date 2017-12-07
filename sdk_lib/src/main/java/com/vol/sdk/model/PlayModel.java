package com.vol.sdk.model;

/**
 * Created by Administrator on 2017-9-19.
 */

public class PlayModel {
    //取值为0返回playUrl值为播放串，1用户为付费playUrl的值为付费地址，用户可以根据此地址生成二维码图片，2未知错误，请重新走获取播放串流程
    private int status = 0;
    private String playUrl;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    @Override
    public String toString() {
        return "PlayModel{" +
                "status=" + status +
                ", playUrl='" + playUrl + '\'' +
                '}';
    }
}
