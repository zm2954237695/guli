package com.guo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.serviceedu.entity.EduTeacher;
import com.guo.serviceedu.mapper.EduTeacherMapper;
import com.guo.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author guoguo
 * @since 2022-07-25
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    @Cacheable(key="'selectTeacherList'",value = "teacher")
    public List<EduTeacher> myList() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        return this.list(wrapper);
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper  =new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher,wrapper);
        Map<String,Object> map = new HashMap<>();
        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();  //当前页
        long pages = pageTeacher.getPages();       //总页数
        long size = pageTeacher.getSize();      //每页记录数
        long total = pageTeacher.getTotal();    //总记录数
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious  = pageTeacher.hasPrevious();

        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
