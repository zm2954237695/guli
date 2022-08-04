package com.guo.cms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.cms.entity.CrmBanner;
import com.guo.cms.service.CrmBannerService;
import com.guo.commonutil.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author guoguo
 * @since 2022-08-03
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    
    @Autowired
    private CrmBannerService bannerService;
    
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
          Page<CrmBanner> pageBanner = new Page<>(page,limit);
         IPage<CrmBanner> page1 = bannerService.page(pageBanner, null);
         return R.success().data("items",page1.getRecords()).data("total",page1.getTotal());
    }
    @PostMapping("addBanner")
    public  R addBanner(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return R.success();
    }
    @PutMapping("update")
    public R updateBanner(@RequestBody CrmBanner banner){
        bannerService.updateById(banner);
        return R.success();
    }
    @DeleteMapping("remove/{id}")
    public R deleteBanner(@PathVariable String id){
        bannerService.removeById(id);
        return R.success();
    }
    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        return R.success().data("item",bannerService.getById(id));
    }


}

