package com.guo.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadAlyiVideo(MultipartFile file);

    void removeMoreAlyVideo(List<String> videoIdList);
}
