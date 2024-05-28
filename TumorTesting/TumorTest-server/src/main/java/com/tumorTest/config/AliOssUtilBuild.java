package com.tumorTest.config;

import com.tumorTest.properties.AliOssProperties;
import com.tumorTest.uitl.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class AliOssUtilBuild {
    @Autowired
    AliOssProperties aliOssProperties;

    @Bean
    public AliOssUtil aliOssUtil(){
        return new AliOssUtil(aliOssProperties.getEndpoint(),aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),aliOssProperties.getBucketName());
    }


}
