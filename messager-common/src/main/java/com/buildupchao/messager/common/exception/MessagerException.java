package com.buildupchao.messager.common.exception;

import com.buildupchao.messager.common.enums.StatusEnum;

/**
 * @author buildupchao
 *         Date: 2019/1/20 11:35
 * @since JDK 1.8
 */
public class MessagerException extends GenericException {

    public MessagerException() {
    }

    public MessagerException(String message) {
        super(message);
    }

    public MessagerException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public MessagerException(Exception ex, int errorCode, String errorMessage) {
        super(errorMessage, ex);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public MessagerException(StatusEnum statusEnum) {
        super(statusEnum.getMessage());
        this.errorCode = statusEnum.getCode();
        this.errorMessage = statusEnum.getMessage();
    }

    public MessagerException(StatusEnum statusEnum, String message) {
        super(message);
        this.errorCode = statusEnum.getCode();
        this.errorMessage = message;
    }

    public MessagerException(Throwable cause) {
        super(cause);
    }

    public MessagerException(Exception ex) {
        super(ex);
    }

    public MessagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessagerException(String message, Exception ex) {
        super(message, ex);
    }

    public static boolean isResetByPeer(String msg) {
        if ("Connection reset by peer".equals(msg)) {
            return true;
        } else {
            return false;
        }
    }
}
