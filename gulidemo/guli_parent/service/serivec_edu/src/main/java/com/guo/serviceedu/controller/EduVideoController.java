package com.guo.serviceedu.controller;


import com.guo.commonutil.R;
import com.guo.serviceedu.entity.EduVideo;
import com.guo.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author guoguo
 * @since 2022-07-29
 */
@RestController
@CrossOrigin
@RequestMapping("/serviceedu/edu-video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.success();
    }

    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable  String id){
        videoService.removeById(id);
        return R.success();
    }

}

