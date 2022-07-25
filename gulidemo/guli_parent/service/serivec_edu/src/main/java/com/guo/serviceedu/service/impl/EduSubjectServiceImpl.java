package com.guo.serviceedu.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guo.serviceedu.entity.EduSubject;
import com.guo.serviceedu.entity.excel.SubjectData;
import com.guo.serviceedu.listener.SubjectExcelListener;
import com.guo.serviceedu.mapper.EduSubjectMapper;
import com.guo.serviceedu.service.EduSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
}
