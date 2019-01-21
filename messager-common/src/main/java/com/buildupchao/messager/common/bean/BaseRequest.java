package com.buildupchao.messager.common.bean;

import lombok.Data;

/**
 * @author buildupchao
 *         Date: 2019/1/19 22:36
 * @since JDK 1.8
 */
public class BaseRequest {
    private String reqNo;
    private long timestamp;

    public BaseRequest() {
        this.timestamp = ((int) (System.currentTimeMillis() / 1000));
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
