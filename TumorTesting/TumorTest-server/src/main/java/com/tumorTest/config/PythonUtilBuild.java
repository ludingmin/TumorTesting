package com.tumorTest.config;

import com.tumorTest.properties.PythonProperties;
import com.tumorTest.uitl.PythonUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PythonUtilBuild {
    @Autowired
    PythonProperties properties;
    @Bean
    public PythonUitl pythonUitl(){
        return new PythonUitl(properties.getStartPath());
    }
}
