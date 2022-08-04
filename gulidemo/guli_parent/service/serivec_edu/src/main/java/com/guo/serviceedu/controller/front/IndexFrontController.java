package com.guo.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.commonutil.R;
import com.guo.serviceedu.entity.EduCourse;
import com.guo.serviceedu.entity.EduTeacher;
import com.guo.serviceedu.service.EduCourseService;
import com.guo.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//查询热门课程和讲师
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("index")
    public R index(){

        List<EduCourse> eduCourses = courseService.myList();
        List<EduTeacher> teachers=  teacherService.myList();
        return R.success().data("eduList",eduCourses).data("teacherList",teachers);
    }
}
