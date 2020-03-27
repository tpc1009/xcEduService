package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.dao.SysDictionaryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author tpc
 * @date 2020/3/26 21:18
 */
@Service
public class SysDictionaryService {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary findById(String id){
        Optional<SysDictionary> opt = this.sysDictionaryRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        return null;
    };
    public SysDictionary findByTpye(String type){
        SysDictionary sysDictionary = new SysDictionary();
        if(StringUtils.isNotEmpty(type)){
            sysDictionary.setDType(type);
        }
        Example<SysDictionary> example = Example.of(sysDictionary);
        List<SysDictionary> opt = this.sysDictionaryRepository.findAll(example);

        return null;
    };
}
