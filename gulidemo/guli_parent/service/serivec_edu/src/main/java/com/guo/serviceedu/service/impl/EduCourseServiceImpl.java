package com.guo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.servicebase.exceptionhandler.GuliException;
import com.guo.serviceedu.entity.EduCourse;
import com.guo.serviceedu.entity.EduCourseDescription;
import com.guo.serviceedu.entity.EduTeacher;
import com.guo.serviceedu.entity.EduVideo;
import com.guo.serviceedu.entity.vo.CourseInfoVo;
import com.guo.serviceedu.entity.vo.CoursePublishVo;
import com.guo.serviceedu.frontVo.CourseFrontVo;
import com.guo.serviceedu.frontVo.CourseWebVo;
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
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy—_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse,wrapper);
        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();  //当前页
        long pages = pageCourse.getPages();       //总页数
        long size = pageCourse.getSize();      //每页记录数
        long total = pageCourse.getTotal();    //总记录数
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious  = pageCourse.hasPrevious();
        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;

    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
