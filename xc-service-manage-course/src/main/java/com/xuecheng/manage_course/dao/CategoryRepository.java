package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tpc
 * @date 2020/3/26 21:08
 */
public interface CategoryRepository extends JpaRepository<Category,String> {
}
