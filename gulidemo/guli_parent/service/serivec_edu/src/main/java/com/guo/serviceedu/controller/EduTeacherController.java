package com.guo.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.commonutil.R;
import com.guo.serviceedu.entity.EduTeacher;
import com.guo.serviceedu.entity.vo.TeacherQuery;
import com.guo.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author guoguo
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
@Api(value = "讲师管理")
public class EduTeacherController {


    @Autowired
    private EduTeacherService teacherService;
    //1.查询表中所有数据

    @GetMapping("findAll")
    @ApiOperation(value="所有讲师列表")
    public R list(){
        return R.success().data("items",teacherService.list(null));
    }


    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/delete/{id}")
    public R removeTeacher(@ApiParam(value ="讲师ID",name="id",required = true)@PathVariable("id") String id){
         return  teacherService.removeById(id) ? R.success() : R.error();
    }

    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("limit") long limit){
        Page<EduTeacher> pageTeacher  = new Page<>(current,limit);
        IPage<EduTeacher> page = teacherService.page(pageTeacher, null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.success().data("total",total).data("records",records);
    }


    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current")long current, @PathVariable("limit")
                                  long limit, @RequestBody(required = false) TeacherQuery teacherQuery){

         IPage<EduTeacher> eduTeacherPage = new Page<>(current,limit);
         QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
         wrapper.like(!ObjectUtils.isEmpty(teacherQuery.getName()),"name",teacherQuery.getName());
         wrapper.eq(!ObjectUtils.isEmpty(teacherQuery.getLevel()),"level",teacherQuery.getLevel());
         wrapper.ge(!ObjectUtils.isEmpty(teacherQuery.getBegin()),"gmt_create",teacherQuery.getBegin());
         wrapper.le(!ObjectUtils.isEmpty(teacherQuery.getEnd()),"gmt_create",teacherQuery.getEnd());
         wrapper.orderByDesc("gmt_create");
        IPage<EduTeacher> page = teacherService.page(eduTeacherPage, wrapper);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.success().data("total",total).data("records",records);
    }

    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){
         return teacherService.save(teacher)?R.success():R.error();
    }

    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id")String id){
        return R.success().data("teacher",teacherService.getById(id));
    }
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        return teacherService.updateById(teacher)?R.success():R.error();
    }
}

