package com.wutanda.sdk.money.mobilemoney.mtn.exceptions;

public final class SdkException extends Exception{

    private final ErrorCode errorCode;
    private final String errorDescription;

    public SdkException(final ErrorCode errorCode,final String errorDescription){
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public static SdkException validationError(final String errorDescription){
        return new SdkException(ErrorCode.VALIDATION_ERROR,errorDescription);
    }

    public static SdkException badRequest(final String errorDescription){
        return new SdkException(ErrorCode.BAD_REQUEST,errorDescription);
    }

    public static SdkException unauthorized(final String errorDescription){
        return new SdkException(ErrorCode.AUTHORIZATION_ERROR,errorDescription);
    }

    public static SdkException internalError(final String errorDescription){
        return new SdkException(ErrorCode.INTERNAL_ERROR,errorDescription);
    }

}
