package com.buildupchao.messager.common.util;

import com.buildupchao.messager.common.bean.BaseResponse;
import com.buildupchao.messager.common.enums.StatusEnum;

/**
 * @author buildupchao
 *         Date: 2019/1/20 14:05
 * @since JDK 1.8
 */
public class ResponseHelper {

    public static <T> BaseResponse<T> create(T data) {
        return new BaseResponse<>(data);
    }

    public static <T> BaseResponse<T> create(T data, StatusEnum statusEnum) {
        return new BaseResponse<>(statusEnum.getCode(), statusEnum.getMessage(), data);
    }

    public static <T> BaseResponse<T> create(T data, StatusEnum statusEnum, String message) {
        return new BaseResponse<>(statusEnum.getCode(), message, data);
    }

    public static <T> BaseResponse<T> createSuccess() {
        return new BaseResponse<>(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getMessage());
    }

    public static <T> BaseResponse<T> createSuccess(T data) {
        return new BaseResponse<>(StatusEnum.SUCCESS.getCode(), StatusEnum.SUCCESS.getMessage(), data);
    }

    public static <T> BaseResponse<T> createSuccess(T data, String message) {
        return new BaseResponse<>(StatusEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> BaseResponse<T> createFail(T data, String message) {
        return new BaseResponse<>(StatusEnum.FAIL.getCode(), message, data);
    }
}
