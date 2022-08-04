package com.guo.cms.service;

import com.guo.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author guoguo
 * @since 2022-08-03
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAll();
}
