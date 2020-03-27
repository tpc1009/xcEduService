package com.xuecheng.manage_course.controller;

import com.xuecheng.api.source.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tpc
 * @date 2020/3/26 21:16
 */
@RestController
@RequestMapping("/sysDictionary")
public class SysDicthinaryController implements SysDicthinaryControllerApi {


    @Autowired
    private SysDictionaryService sysDictionaryService;


    //查询数据模型,等级
    @Override
    public SysDictionary getByType(String type) {

        return this.sysDictionaryService.findById(type);
    }
}
