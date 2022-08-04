package com.guo.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.cms.entity.CrmBanner;
import com.guo.cms.service.CrmBannerService;
import com.guo.commonutil.R;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list =  bannerService.selectAll();
        return R.success().data("list",list);
    }
}
