package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.Category;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CategoryMapper;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author tpc
 * @date 2020/3/26 19:45
 */
@Service
public class CourseService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanRepository teachplanRepository;


    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId){
        return this.teachplanMapper.selectList(courseId);
    }

    //添加课程计划
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        if(teachplan==null || StringUtils.isEmpty(teachplan.getCourseid())
                || StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.NVALID_PARAM);
        }
        //取出课程id
        String courseid = teachplan.getCourseid();
        //取出父节点
        String parentid = teachplan.getParentid();
        if(StringUtils.isEmpty(parentid)){
            parentid =  getTeachplanRoot(courseid);
        }

        //取出父节点信息
        Optional<Teachplan> opt = this.teachplanRepository.findById(parentid);
        if(!opt.isPresent()){
            ExceptionCast.cast(CommonCode.INVALIDPARAM);
        }
        Teachplan teachplan1 = opt.get();
        String parent_grade = teachplan1.getGrade();
        //创建新计划
        Teachplan teachplanNew = new Teachplan();
        BeanUtils.copyProperties(teachplan,teachplanNew);
        //设置父节点级别
        teachplanNew.setParentid(parentid);
        if(parent_grade.equals("1")){
            teachplanNew.setGrade("2");
        }else {
            teachplanNew.setGrade("3");
        }
        teachplanNew.setStatus("0");//未发布
        this.teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //获取课程根结点，如果没有则添加根结点
    private String getTeachplanRoot(String courseid) {

        Optional<CourseBase> opt = this.courseBaseRepository.findById(courseid);
        if(!opt.isPresent()){
            return null;
        }
        CourseBase courseBase = opt.get();
        List<Teachplan> techplanList = this.teachplanRepository.findByCourseidAndParentid(courseid, "0");
        if(techplanList ==null || techplanList.size()<=0){
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseid);
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setStatus("0");
            teachplan.setPname(courseBase.getName());
            this.teachplanRepository.save(teachplan);
            return teachplan.getId();
        }
        return techplanList.get(0).getId();
    }
//**********************************************************************************************************************

    //查询分类信息
    public CategoryNode findByCategory(String name){
        return this.categoryMapper.findCategoryByName(name);
    }
//**********************************************************************************************************************

}
