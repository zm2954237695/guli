package com.guo.serviceedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guo.serviceedu.entity.EduSubject;
import com.guo.serviceedu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EduSubjectService extends IService<EduSubject> {
    void addSubject(MultipartFile file,EduSubjectService subjectService);


    List<OneSubject> getAllOneTwoSubject();
}
