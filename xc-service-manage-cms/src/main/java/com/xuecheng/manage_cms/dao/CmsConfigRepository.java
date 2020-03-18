package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author tpc
 * @date 2020/3/18 21:32
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
