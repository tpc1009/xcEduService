package com.xuecheng.manage_course.controller;

import com.xuecheng.api.source.CourseControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tpc
 * @date 2020/3/26 19:47
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {


    @Autowired
    private CmsPageClient cmsPageClient;

    @Autowired
    private CourseService courseService;


    //查询课程计划
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return this.courseService.findTeachplanList(courseId);
    }

    //添加计划
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return  courseService.addTeachplan(teachplan);
    }

    //查询种类
    @Override
    @GetMapping("/category/list/{name}")
    public CategoryNode findCategoryByName(@PathVariable("name") String name) {
        return this.courseService.findByCategory(name);
    }
}
