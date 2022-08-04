package com.guo.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.cms.entity.CrmBanner;
import com.guo.cms.mapper.CrmBannerMapper;
import com.guo.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author guoguo
 * @since 2022-08-03
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(key="'selectIndexList'",value = "banner")
    public List<CrmBanner> selectAll() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        return baseMapper.selectList(wrapper);
    }
}
