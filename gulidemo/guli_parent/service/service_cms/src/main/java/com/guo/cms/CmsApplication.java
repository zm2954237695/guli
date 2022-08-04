package com.guo.cms;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.guo.cms.mapper")
@ComponentScan({"com.guo"})
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
