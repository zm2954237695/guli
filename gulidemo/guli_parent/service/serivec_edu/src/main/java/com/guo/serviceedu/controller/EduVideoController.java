package com.guo.serviceedu.controller;


import com.guo.commonutil.R;
import com.guo.servicebase.exceptionhandler.GuliException;
import com.guo.serviceedu.client.VodClient;
import com.guo.serviceedu.entity.EduVideo;
import com.guo.serviceedu.service.EduVideoService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    //远程调用
    @Autowired
    private VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.success();
    }

    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获得视频id，再删视频;
        EduVideo videoServiceById = videoService.getById(id);
        String videoSourceId = videoServiceById.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeAlyVideo(videoSourceId);
            R r = vodClient.removeAlyVideo(videoSourceId);
            if(r.getCode()==20001){
                throw new GuliException(20001,"删除视频失败,熔断器...");
            }
        }
        videoService.removeById(id);
        return R.success();
    }

}

