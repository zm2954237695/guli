package com.guo.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.commonutil.R;
import com.guo.serviceedu.entity.EduCourse;
import com.guo.serviceedu.entity.vo.CourseInfoVo;
import com.guo.serviceedu.entity.vo.CoursePublishVo;
import com.guo.serviceedu.entity.vo.CourseQuery;
import com.guo.serviceedu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guoguo
 * @since 2022-07-29
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;


    @GetMapping("findAll")
    public R getCourseList(){
        return R.success().data("list",courseService.list(null));
    }

    @GetMapping("pageCourseCondition/{current}/{limit}")
    public R getPageCourseCondition(@PathVariable long current,
                                    @PathVariable long limit){
        Page<EduCourse> PageCourse = new Page<>(current,limit);
        IPage<EduCourse> page = courseService.page(PageCourse, null);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.success().data("total",total).data("records",records);
    }

    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current,
                                 @PathVariable long limit,
                                 @RequestBody CourseQuery courseQuery){
        Page<EduCourse> coursePage = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String status = courseQuery.getStatus();
        String title = courseQuery.getTitle();
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.eq("title",title);
        }
        wrapper.orderByDesc("gmt_create");
        IPage<EduCourse> page = courseService.page(coursePage, wrapper);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.success().data("total",total).data("records",records);
    }


    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.success().data("courseId",id);
    }

    @GetMapping("getCouserInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.success().data("courseInfoVo",courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.success();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCoureInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return R.success().data("course",coursePublishVo);
    }

    @PostMapping("publicCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.success();
    }

    @DeleteMapping("removeCourseByCourseId/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.deleteCourse(courseId);
        return R.success();
    }

}

