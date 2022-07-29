package com.guo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.serviceedu.entity.EduChapter;
import com.guo.serviceedu.entity.EduVideo;
import com.guo.serviceedu.entity.chapter.ChapterVo;
import com.guo.serviceedu.entity.chapter.VideoVo;
import com.guo.serviceedu.mapper.EduChapterMapper;
import com.guo.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guo.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guoguo
 * @since 2022-07-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> videoList = videoService.list(videoQueryWrapper);
        System.out.println(eduChapters);
        List<ChapterVo> res = eduChapters.stream().map(v -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(v,chapterVo);
            List<VideoVo> collect = videoList.stream().filter(w -> w.getChapterId().equals(v.getId())).map(w -> {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(w, videoVo);
                return videoVo;
            }).collect(Collectors.toList());
            chapterVo.setChildren(collect);
            return chapterVo;
        }).collect(Collectors.toList());
        return res;
    }
}
