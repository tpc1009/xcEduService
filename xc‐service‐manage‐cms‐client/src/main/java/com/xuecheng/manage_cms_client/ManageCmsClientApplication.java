package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author tpc
 * @date 2020/3/23 19:27
 */
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages={"com.xuecheng.framework"})//扫描common包下的类
@ComponentScan(basePackages={"com.xuecheng.manage_cms_client"})//扫描本项目下的所有类
public class ManageCmsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class,args);
    }

}
