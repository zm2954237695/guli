package com.guo.serviceedu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.commonutil.R;
import com.guo.serviceedu.entity.EduCourse;
import com.guo.serviceedu.entity.chapter.ChapterVo;
import com.guo.serviceedu.frontVo.CourseFrontVo;
import com.guo.serviceedu.frontVo.CourseWebVo;
import com.guo.serviceedu.service.EduChapterService;
import com.guo.serviceedu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("eduservice/coursefront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse  = new Page<>(page,limit);
        Map<String,Object> map = courseService.getFrontCourseList(pageCourse,courseFrontVo);
        return R.success().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){
       CourseWebVo courseWebVo =  courseService.getBaseCourseInfo(courseId);
       List<ChapterVo> chapterVideoByCourseId = chapterService.getChapterVideoByCourseId(courseId);
    }
}
