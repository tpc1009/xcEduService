package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author tpc
 * @date 2020/3/27 20:38
 */
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}
