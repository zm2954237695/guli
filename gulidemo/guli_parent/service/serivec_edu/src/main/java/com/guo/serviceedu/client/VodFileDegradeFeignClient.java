package com.guo.serviceedu.client;

import com.guo.commonutil.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatchVideo(List<String> videoIdList) {
        return R.error().message("删除多个视频时出错了");
    }
}
