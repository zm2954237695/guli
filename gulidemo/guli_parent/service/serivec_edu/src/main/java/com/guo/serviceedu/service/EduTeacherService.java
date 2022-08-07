package com.guo.serviceedu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author guoguo
 * @since 2022-07-25
 */
public interface EduTeacherService extends IService<EduTeacher> {


    List<EduTeacher> myList();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
