package com.guo.demo.excel;


import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
       //实现excel的写
        String file = "E:\\write.xlsx";
     //第一个参数为文件路径名称 第二个参数为实体类class
      //  EasyExcel.write(file,DemoData.class).sheet("学生列表").doWrite(getData());
        EasyExcel.read(file,DemoData.class,new ExcelListener()).sheet().doRead();
    }
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setName("ho" + i);
            list.add(data);
        }
        return list;
    }
}
