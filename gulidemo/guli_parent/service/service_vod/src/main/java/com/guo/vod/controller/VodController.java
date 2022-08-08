package com.guo.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guo.commonutil.R;
import com.guo.servicebase.exceptionhandler.GuliException;
import com.guo.vod.service.VodService;
import com.guo.vod.utils.AliYunVodSDKUtils;
import com.guo.vod.utils.ConstantVodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService ;


    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file){
          String VideoId = vodService.uploadAlyiVideo(file);
          return R.success().data("VideoId",VideoId);
    }

    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try {
            DefaultAcsClient client = AliYunVodSDKUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.success();
        } catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
    @DeleteMapping("delete-batch")
    public R deleteBatchVideo(@RequestParam("videoIdList") List videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return R.success();
    }

    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable  String id){
        try{
            DefaultAcsClient client = AliYunVodSDKUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request  = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.success().data("playAuth",playAuth);
        } catch (Exception e){
             throw new GuliException(20001,"获取凭证失败");
        }
    }
}
