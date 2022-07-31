package com.guo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.serviceedu.entity.EduVideo;
import com.guo.serviceedu.mapper.EduVideoMapper;
import com.guo.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author guoguo
 * @since 2022-07-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public void removeCourseByCourseId(String courseId) {
        baseMapper.delete(new QueryWrapper<EduVideo>().eq("course_id",courseId));
    }
}
