package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tpc
 * @date 2020/3/26 19:37
 */
@Mapper
public interface TeachplanMapper {

    public TeachplanNode selectList(String courseId);
}
