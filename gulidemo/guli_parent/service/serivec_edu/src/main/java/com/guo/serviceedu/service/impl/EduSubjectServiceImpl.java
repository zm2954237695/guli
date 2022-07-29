package com.guo.serviceedu.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guo.serviceedu.entity.EduSubject;
import com.guo.serviceedu.entity.excel.SubjectData;
import com.guo.serviceedu.entity.subject.OneSubject;
import com.guo.serviceedu.entity.subject.TwoSubject;
import com.guo.serviceedu.listener.SubjectExcelListener;
import com.guo.serviceedu.mapper.EduSubjectMapper;
import com.guo.serviceedu.service.EduSubjectService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper,EduSubject> implements EduSubjectService {
    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) {
          try{
              InputStream in = file.getInputStream();
              EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
          } catch (Exception e){
               e.printStackTrace();
          }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
//        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
//        wrapper.eq("parent_id",0);
//        List<EduSubject> OneSubjectLists = baseMapper.selectList(wrapper);
//        QueryWrapper<EduSubject> TwoWrapper = new QueryWrapper<>();
//        TwoWrapper.ne("parent_id",0);
//        List<EduSubject> TwoSubjectLists = baseMapper.selectList(TwoWrapper);
//        List<OneSubject> res = new ArrayList<>();
//        OneSubjectLists.forEach(v -> {
//            OneSubject oneSubject = new OneSubject();
//            BeanUtils.copyProperties(v,oneSubject);
//            res.add(oneSubject);
//            List<TwoSubject> twoSubjects = new ArrayList<>();
//            TwoSubjectLists.forEach(w -> {
//                 if(w.getParentId().equals(v.getId())){
//                     TwoSubject twoSubject = new TwoSubject();
//                     BeanUtils.copyProperties(w,twoSubject);
//                     twoSubjects.add(twoSubject);
//                 }
//            });
//              oneSubject.setLists(twoSubjects);
//        });
        List<EduSubject> eduSubjects = baseMapper.selectList(null);
        List<EduSubject> OneSubjects = eduSubjects.stream()
                .filter(s -> "0".equals(s.getParentId())).collect(Collectors.toList());
        List<OneSubject> res = new ArrayList<>();
        OneSubjects.forEach(v -> {
             OneSubject oneSubject = new OneSubject();
             BeanUtils.copyProperties(v,oneSubject);
             List<TwoSubject> twoSubjects = eduSubjects.stream().filter(s ->
                             s.getParentId().equals(v.getId()))
                    .map(temp -> {
                        TwoSubject twoSubject = new TwoSubject();
                        BeanUtils.copyProperties(temp, twoSubject);
                        return twoSubject;
                    }).collect(Collectors.toList());
            oneSubject.setChildren(twoSubjects);
            res.add(oneSubject);
        });
        return res;
    }
}
