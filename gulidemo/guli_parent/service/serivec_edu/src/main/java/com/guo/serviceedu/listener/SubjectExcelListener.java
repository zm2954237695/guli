package com.guo.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guo.servicebase.exceptionhandler.GuliException;
import com.guo.serviceedu.entity.EduSubject;
import com.guo.serviceedu.entity.excel.SubjectData;
import com.guo.serviceedu.service.EduSubjectService;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener(){}

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
           if(subjectData==null) {
               throw new GuliException(20001,"文件数据为空");
           }

           EduSubject eduSubject = existOneSubject(subjectData.getOneSubjectName());
           if(eduSubject==null){
               eduSubject =  new EduSubject();
               eduSubject.setParentId("0");
               eduSubject.setTitle(subjectData.getOneSubjectName()); //一级分类名称
               subjectService.save(eduSubject);
           }
           String pid  = eduSubject.getId();
         EduSubject twoSubject = existTwoSubject(subjectData.getTwoSubjectName(), pid);
           if (twoSubject==null) {
               twoSubject = new EduSubject();
               twoSubject.setParentId(pid);
               twoSubject.setTitle(subjectData.getTwoSubjectName());
               subjectService.save(twoSubject);
           }

    }

    private EduSubject existOneSubject(String name){
        LambdaQueryWrapper<EduSubject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSubject::getTitle,name);
        wrapper.eq(EduSubject::getParentId,"0");
        return subjectService.getOne(wrapper);
    }

    private EduSubject existTwoSubject(String name,String pid){
        LambdaQueryWrapper<EduSubject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSubject::getTitle,name);
        wrapper.eq(EduSubject::getParentId,pid);
        return subjectService.getOne(wrapper);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
