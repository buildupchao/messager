package com.buildupchao.messager.common.bean;

import com.buildupchao.messager.common.enums.StatusEnum;

import java.io.Serializable;

/**
 * @author buildupchao
 *         Date: 2019/1/19 22:58
 * @since JDK 1.8
 */
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private String reqNo;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(int code, String message, String reqNo, T data) {
        this.code = code;
        this.message = message;
        this.reqNo = reqNo;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
