package com.tumorTest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tumortest.python")
@Data
public class PythonProperties {

    //预测结果的文件夹位置
    private String resultPath ;

    private String imgPath;

    private String startPath;

}
