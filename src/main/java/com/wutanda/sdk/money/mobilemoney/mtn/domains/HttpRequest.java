package com.wutanda.sdk.money.mobilemoney.mtn.domains;

import java.io.Serializable;
import java.util.Map;

public final class HttpRequest<T> implements Serializable {

    private final T requestBody;
    private final HttpMethod httpMethod;
    private final Map<String, String> headers;
    private final ContentType contentType;
    private final String url;
    private final SSLConfiguration sslConfiguration;


    protected HttpRequest(final Builder<T> builder) {
        this.contentType = builder.contentType;
        this.headers = builder.headers;
        this.httpMethod = builder.httpMethod;
        this.requestBody = builder.requestBody;
        this.url = builder.url;
        this.sslConfiguration = builder.sslConfiguration;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }

    public SSLConfiguration getSslConfiguration() {
        return sslConfiguration;
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public static class Builder<T> {

        private T requestBody;
        private HttpMethod httpMethod;
        private Map<String, String> headers;
        private ContentType contentType;
        private String url;
        private SSLConfiguration sslConfiguration;

        public Builder sslConfiguration(final SSLConfiguration sslConfiguration){
            this.sslConfiguration = sslConfiguration;
            return this;
        }

        public Builder url(final String url){
            this.url = url;
            return this;
        }

        public Builder requestBody(final T requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder contentType(final ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder headers(final Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }


}
