package com.xuecheng.test.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author tpc
 * @date 2020/3/17 20:58
 */
@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {


    @RequestMapping("/test1")
    public String test1(Map<String,Object> map){
        map.put("name"," world");
        return "test1";
    }
}
