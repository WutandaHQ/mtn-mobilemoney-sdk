package com.wutanda.sdk.money.mobilemoney.mtn.domains;

import java.io.Serializable;

public final class HttpResponse<T> implements Serializable {

    private final T responseBody;
    private final Integer responseStatusCode;
    private final String responseDescription;

    protected HttpResponse(final Builder<T> builder){
        this.responseBody = builder.responseBody;
        this.responseStatusCode = builder.responseStatusCode;
        this.responseDescription = builder.responseDescription;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder<T>{

        private T responseBody;
        private Integer responseStatusCode;
        private String responseDescription;

        public Builder responseBody(final T responseBody){
            this.responseBody = responseBody;
            return this;
        }

        public Builder<T> responseStatusCode(final Integer responseStatusCode){
            this.responseStatusCode = responseStatusCode;
            return this;
        }

        public Builder responseDescription(final String responseDescription){
            this.responseDescription = responseDescription;
            return this;
        }

        public HttpResponse build(){
            return new HttpResponse(this);
        }
    }


}
