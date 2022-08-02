package com.guo.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadAlyiVideo(MultipartFile file);
}
