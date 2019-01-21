package com.buildupchao.messager.common.enums;

/**
 * @author buildupchao
 *         Date: 2019/1/20 11:04
 * @since JDK 1.8
 */
public enum StatusEnum {
    SUCCESS(200, "Success"),
    FAIL(500, "Fail");

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
