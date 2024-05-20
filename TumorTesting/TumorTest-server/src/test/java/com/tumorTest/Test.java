package com.tumorTest;

import com.tumorTest.properties.AliOssProperties;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Resource
    AliOssProperties aliOssProperties;

    @org.junit.Test
    public void test1(){
        System.out.println(aliOssProperties);
    }


}
