package com.wutanda.sdk.money.mobilemoney.mtn;

import com.wutanda.sdk.money.mobilemoney.mtn.common.Environment;
import com.wutanda.sdk.money.mobilemoney.mtn.openapi.apiuser.User;
import com.wutanda.sdk.money.mobilemoney.mtn.openapi.collections.Collections;
import com.wutanda.sdk.money.mobilemoney.mtn.openapi.disbursements.Disbursements;
import com.wutanda.sdk.money.mobilemoney.mtn.openapi.remittances.Remittances;

/**
 * The MTN Mobile Money platform SDK
 */
public final class MTNMobileMoney {

    private final Environment environment;
    private final String collectionSubscriptionKey;
    private final String disbursementSubscriptionKey;
    private final String remittanceSubscriptionKey;

    protected MTNMobileMoney(final Builder builder) {
        this.environment = builder.environment;
        this.collectionSubscriptionKey = builder.collectionSubscriptionKey;
        this.remittanceSubscriptionKey = builder.remittanceSubscriptionKey;
        this.disbursementSubscriptionKey = builder.disbursementSubscriptionKey;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Environment environment;
        private String collectionSubscriptionKey;
        private String disbursementSubscriptionKey;
        private String remittanceSubscriptionKey;

        public Builder environment(final Environment environment) {
            this.environment = environment;
            return this;
        }

        public Builder collectionSubscriptionKey(final String collectionSubscriptionKey) {
            this.collectionSubscriptionKey = collectionSubscriptionKey;
            return this;
        }

        public Builder disbursementSubscriptionKey(final String disbursementSubscriptionKey) {
            this.disbursementSubscriptionKey = disbursementSubscriptionKey;
            return this;
        }

        public Builder remittanceSubscriptionKey(final String remittanceSubscriptionKey) {
            this.remittanceSubscriptionKey = remittanceSubscriptionKey;
            return this;
        }

        public MTNMobileMoney build() {
            return new MTNMobileMoney(this);
        }
    }

    public User user() {
        return new User(environment);
    }

    public Collections collections() {
        return new Collections(environment,collectionSubscriptionKey);
    }

    public Disbursements disbursements() {
        return new Disbursements(environment,disbursementSubscriptionKey);
    }

    public Remittances remittances(){
        return new Remittances(environment,remittanceSubscriptionKey);
    }

}
