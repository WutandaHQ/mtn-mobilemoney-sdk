package com.wutanda.sdk.money.mobilemoney.mtn.domains;

public final class SSLConfiguration {

    private final String keyStorePath;
    private final String keyStorePassword;
    private final String trustStorePath;
    private final String trustStorePassword;

    protected SSLConfiguration(final Builder builder){
        this.keyStorePassword = builder.keyStorePassword;
        this.keyStorePath = builder.keyStorePath;
        this.trustStorePassword = builder.trustStorePassword;
        this.trustStorePath = builder.trustStorePath;
    }

    public String getKeyStorePath() {
        return keyStorePath;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public String getTrustStorePath() {
        return trustStorePath;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String keyStorePath;
        private String keyStorePassword;
        private String trustStorePath;
        private String trustStorePassword;

        public Builder keyStorePath(final String keyStorePath){
            this.keyStorePath = keyStorePath;
            return this;
        }

        public Builder keyStorePassword(final String keyStorePassword){
            this.keyStorePassword = keyStorePassword;
            return this;
        }

        public Builder trustStorePath(final String trustStorePath){
            this.trustStorePath = trustStorePath;
            return this;
        }

        public Builder trustStorePassword(final String trustStorePassword){
            this.trustStorePassword = trustStorePassword;
            return this;
        }

        public SSLConfiguration build(){
            return new SSLConfiguration(this);
        }

    }

}
