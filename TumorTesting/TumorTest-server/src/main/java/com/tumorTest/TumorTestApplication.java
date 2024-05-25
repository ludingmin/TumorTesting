package com.tumorTest;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@EnableScheduling //开启定时任务
@MapperScan("com.tumorTest.mapper")
@Slf4j
public class TumorTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TumorTestApplication.class, args);
        log.info("server started");
    }
}
