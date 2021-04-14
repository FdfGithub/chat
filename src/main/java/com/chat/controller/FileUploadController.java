package com.chat.controller;

import com.chat.common.Result;
import com.chat.common.ServerResponse;
import com.chat.util.FileUtil;
import com.chat.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Value("${headUrl_pre}")
    private String headUrlPre;

    @Autowired
    private FileUtil fileUtil;
    //上传文件
    @PostMapping
    public Result upload(MultipartFile file){
        SessionUtil.authLogin();
        String uri = fileUtil.upload(file);
        if (StringUtils.isEmpty(uri)){
            throw new RuntimeException("系统错误");
        }
        return ServerResponse.createSuccess("上传成功",headUrlPre+uri);
    }
}
