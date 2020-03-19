package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author tpc
 * @date 2020/3/19 19:59
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate ,String> {
}
