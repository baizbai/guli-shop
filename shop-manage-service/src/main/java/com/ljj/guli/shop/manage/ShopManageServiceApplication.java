package com.ljj.guli.shop.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.ljj.guli.shop.manage.mapper")
public class ShopManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopManageServiceApplication.class, args);
    }

}
