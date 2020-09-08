package com.huawei.sso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "")
public class IDaaSConfig {

    private String clientId = "w4KXugTkImi1HkfbvXp3cIb8kcBQiZdl";
    private String secrect = "Qnu645LJg7aX0dk7u2kTvwJaT4xlDu04eOVIk9eanxWsfpGSUYCAgeiAIS1Qchd1";
    private String redirectUrl  = "https://www.ictxuetang.com/";


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecrect() {
        return secrect;
    }

    public void setSecrect(String secrect) {
        this.secrect = secrect;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
