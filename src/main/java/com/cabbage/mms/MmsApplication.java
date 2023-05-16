package com.cabbage.mms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// @EnableJpaRepositories 这个应该是用hibernate才有用
@EnableScheduling
@MapperScan(basePackages = "com.cabbage.mms.Mapper")
@EnableCaching
@EnableTransactionManagement
public class MmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmsApplication.class, args);
    }
    // localhost:80/findCompleted?pageNum=4

}
