package com.guo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.servicebase.exceptionhandler.GuliException;
import com.guo.serviceedu.entity.EduCourse;
import com.guo.serviceedu.entity.EduCourseDescription;
import com.guo.serviceedu.entity.EduVideo;
import com.guo.serviceedu.entity.vo.CourseInfoVo;
import com.guo.serviceedu.entity.vo.CoursePublishVo;
import com.guo.serviceedu.mapper.EduCourseMapper;
import com.guo.serviceedu.service.EduChapterService;
import com.guo.serviceedu.service.EduCourseDescriptionService;
import com.guo.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guo.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guoguo
 * @since 2022-07-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseMapper courseMapper;
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
   @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);
        int rows = baseMapper.insert(course);
        if(rows == 0){
            throw new GuliException(20001,"添加课程失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,description);
        description.setId(course.getId());
        courseDescriptionService.save(description);
        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
          EduCourse eduCourse = new EduCourse();
          BeanUtils.copyProperties(courseInfoVo,eduCourse);
          int rows = baseMapper.updateById(eduCourse);
          if(rows==0){
              throw new GuliException(20001,"更新失败");
          }
          EduCourseDescription eduCourseDescription  = new EduCourseDescription();
          eduCourseDescription.setId(courseInfoVo.getId());
          eduCourseDescription.setDescription(courseInfoVo.getDescription());
          courseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        return courseMapper.getPublishVo(id);
    }

    @Override
    public void deleteCourse(String courseId) {
       videoService.removeCourseByCourseId(courseId);
       chapterService.removeChapterByCourseId(courseId);
       courseDescriptionService.removeById(courseId);
       int rows = baseMapper.deleteById(courseId);
       if(rows==0){
           throw new GuliException(20001,"删除失败");
       }
    }

    @Override
    @Cacheable(key="'selectCourseList'",value="course")
    public List<EduCourse> myList() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        return this.list(wrapper);

    }
}
