package com.guo.serviceedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guo.serviceedu.entity.EduSubject;
import org.springframework.web.multipart.MultipartFile;

public interface EduSubjectService extends IService<EduSubject> {
    void addSubject(MultipartFile file,EduSubjectService subjectService);


}
