package com.setqt.Hiring.Utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "firebase")
public class Properties {
    private String bucketName;

    public String getBucketName() {
        return this.bucketName;
    }

    @Deprecated
    @DeprecatedConfigurationProperty(replacement = "firebase.bucket-name")
    public String getTarget() {
        return this.bucketName;
    }

    @Deprecated
    public void setTarget(String target) {
        this.bucketName = target;
    }
}
