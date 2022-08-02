package com.guo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.serviceedu.client.VodClient;
import com.guo.serviceedu.entity.EduVideo;
import com.guo.serviceedu.mapper.EduVideoMapper;
import com.guo.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

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


    @Autowired
    private VodClient vodClient;
    @Override
    public void removeCourseByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        List<String> collect = eduVideos.stream().map(EduVideo::getVideoSourceId).filter(Objects::nonNull).collect(Collectors.toList());
        if(!ObjectUtils.isEmpty(collect)){
            vodClient.deleteBatchVideo(collect);
        }
        QueryWrapper<EduVideo> wr = new QueryWrapper<>();
        wr.eq("course_id",courseId);
        baseMapper.delete(wr);

    }
}
