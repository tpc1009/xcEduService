package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Category;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CategoryMapper {

   public CategoryNode findCategoryByName(String name);

}
