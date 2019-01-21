package com.buildupchao.messager.common.enums;

/**
 * @author buildupchao
 *         Date: 2019/1/20 11:04
 * @since JDK 1.8
 */
public enum StatusEnum {
    SUCCESS(200, "Success"),
    FAIL(500, "Fail"),

    ACCOUNT_NOT_EXISTS(10001, "Account does not exist"),
    ACCOUNT_NOT_MATCH(10002, "Account does not match"),
    REPEAT_LOGIN(10003, "Can not login repeatedly"),

    OFF_LINE(20001, "Offline");

    private int code;
    private String message;

    private StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusEnum valueOf(int code) {
        StatusEnum result = null;
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getCode() == code) {
                result = statusEnum;
                break;
            }
        }
        return result;
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
}
