package com.guo.serviceedu.service;



import com.guo.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guo.serviceedu.entity.vo.CourseInfoVo;
import com.guo.serviceedu.entity.vo.CoursePublishVo;

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
}
