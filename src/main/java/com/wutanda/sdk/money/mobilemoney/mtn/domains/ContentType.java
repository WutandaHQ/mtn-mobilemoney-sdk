package com.wutanda.sdk.money.mobilemoney.mtn.domains;

public enum ContentType {

    APPLICATION_JSON("application/json"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    TEXT_XML("text/xml"),
    TEXT_PLAIN("text/plain");

    private String contentType;

    ContentType(final String contentType){
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
