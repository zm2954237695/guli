package com.guo.serviceedu.mapper;

import com.guo.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guo.serviceedu.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author guoguo
 * @since 2022-07-29
 */

@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
        CoursePublishVo getPublishVo(String courseId);
}
