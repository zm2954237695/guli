package com.guo.serviceedu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    private String title;

    private String id;

    private List<VideoVo> children = new ArrayList<>();
}
