package com.guo.serviceedu.service;

import com.guo.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guo.serviceedu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guoguo
 * @since 2022-07-29
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);


}
