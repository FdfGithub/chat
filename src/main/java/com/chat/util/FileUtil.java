package com.chat.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {
    @Value("${local_pre}")
    private String localPre;

    /**
     *
     * @param file 要上传的文件
     * @return 上传后的uri
     */
    public String upload(MultipartFile file) {
        //构造父目录
        File parent = new File(localPre + SessionUtil.getUserId());
        if (!parent.exists()) {
            parent.mkdirs();
        } else {
            delFile(parent);
        }
        String uuidName = getUUIDName(file.getOriginalFilename());
        try {
            //获取不重复的文件名,上传文件
            file.transferTo(new File(parent, uuidName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SessionUtil.getUserId() + "/" + uuidName;
    }


    private String getUUIDName(String fileName) {
        return UUID.randomUUID().
                toString().replace("-", "") + getSuffix(fileName);
    }


    /**
     * 后缀名判断
     */
    public boolean isImageByEndName(String fileName) {
        //后缀名数组
        String[] endNames = new String[]{
                "bmp", "gif", "jpg", "png", "tif"
        };
        for (String endName : endNames) {
            if (endName.equals(getSuffix(fileName))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获得文件后缀名
     *
     * @param fileName 带后缀的文件名
     * @return 后缀名（包括.）
     */
    public String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    /**
     * 删除文件
     *
     * @param file 要删除的文件目录
     */
    public void delFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            for (File tempFile : files) {
                delFile(tempFile);
            }
        } else {
            file.delete();
        }
    }
}
