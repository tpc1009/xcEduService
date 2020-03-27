package com.xuecheng.manage_course.dao;


import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author tpc
 * @date 2020/3/26 21:20
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
}
