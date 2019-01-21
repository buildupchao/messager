package com.buildupchao.messager.common.exception;

/**
 * @author buildupchao
 *         Date: 2019/1/20 11:32
 * @since JDK 1.8
 */
public class GenericException extends RuntimeException {

    int errorCode;
    String errorMessage;

    public GenericException() {
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(String message, Exception ex) {
        super(message, ex);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public GenericException(Exception ex) {
        super(ex);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
