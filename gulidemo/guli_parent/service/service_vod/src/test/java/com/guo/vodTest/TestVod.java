package com.guo.vodTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    private static  String accessKeyId="LTAI5tKj9VSmXuUGMqUqyFxE";
    private static  String getAccessKeySecret="CWQMdWncOXEazMccNYOkQAF1bwQwUY";
    private static  String VideoId=  "ffb8a154ffa7418cbbb44d97370fd257";
    public static void main(String[] args) throws ClientException {

    }
    //根据视频id获取视频凭证
    public static void  getPlayAuth() throws ClientException{
        DefaultAcsClient client = AliYunVodSDKUtils.initVodClient(accessKeyId, getAccessKeySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response ;
        request.setVideoId(VideoId);
        response = client.getAcsResponse(request);
        String playAuth = response.getPlayAuth();
        System.out.println(playAuth);
    }
    //根据视频id获取视频url地址
    public static void getPlayUrl() throws ClientException {
        DefaultAcsClient client = AliYunVodSDKUtils.initVodClient(accessKeyId,getAccessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response;
        request.setVideoId(VideoId);
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> list = response.getPlayInfoList();
        list.forEach(v -> System.out.println("PlayInfo.PlayURL = "+v.getPlayURL()));
        System.out.println("VideoBase.Title =" + response.getVideoBase().getTitle() + "\n");

    }
}
