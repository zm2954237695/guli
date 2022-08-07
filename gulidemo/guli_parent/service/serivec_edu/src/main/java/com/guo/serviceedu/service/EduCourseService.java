package com.guo.serviceedu.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guo.serviceedu.entity.vo.CourseInfoVo;
import com.guo.serviceedu.entity.vo.CoursePublishVo;
import com.guo.serviceedu.frontVo.CourseFrontVo;
import com.guo.serviceedu.frontVo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guoguo
 * @since 2022-07-29
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    void deleteCourse(String courseId);

    List<EduCourse> myList();

    Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
