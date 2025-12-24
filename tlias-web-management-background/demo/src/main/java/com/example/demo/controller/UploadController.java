package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Result;
import com.example.demo.utils.AliyunOSSOperator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    //上传文件
    @PostMapping("/upload") //MultipartFile具体作用: 用于接收上传的文件
    public Result upload(String name, Integer age, MultipartFile file) throws Exception {
       log.info("上传文件: {} {} {}", name, age, file); 
       if (!file.isEmpty()) {
            //生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;
            // File targetFile = new File(UPLOAD_DIR + uniqueFileName);
            // if (!targetFile.getParentFile().exists()) {
            //     targetFile.getParentFile().mkdirs();
            // }
            //保存文件
            // file.transferTo(targetFile);
            String url = aliyunOSSOperator.upload(file.getBytes(), uniqueFileName);
            return Result.success(url);
       }
       return Result.error("上传失败");
    }
}
