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
@RequestMapping("/edu/teacher")
@Api(value = "讲师管理")
public class EduTeacherController {


    @Autowired
    private EduTeacherService teacherService;
    //1.查询表中所有数据

    @GetMapping("/findAll")
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
        IPage<EduTeacher> pageTeacher  = new Page<>(current,limit);
        teacherService.page(pageTeacher,null);
        return R.success().data("page",pageTeacher);
    }


    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current")long current, @PathVariable("limit")
                                  long limit, @RequestBody(required = false) TeacherQuery query){

         IPage<EduTeacher> page = new Page<>(current,limit);
         QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
         String name = query.getName();
         Integer level = query.getLevel();
         String begin = query.getBegin();
         String end = query.getEnd();
         if(!StringUtils.isEmpty(name)){
             wrapper.like("name",name);
         }
         if(!ObjectUtils.isEmpty(level)){
             wrapper.like("level",level);
         }
         if(!StringUtils.isEmpty(begin)) {
             wrapper.ge("gmt_create",begin);
         }
         if(!StringUtils.isEmpty(end)) {
             wrapper.le("gmt_create",end);
         }
        IPage<EduTeacher> page1 = teacherService.page(page, wrapper);
        return R.success().data("page",page1);
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

