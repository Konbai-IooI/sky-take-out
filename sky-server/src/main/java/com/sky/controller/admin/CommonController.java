package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.LocalFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {


    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传:{}", file);
            try {
                String originalFilename = file.getOriginalFilename();// 原始文件名

                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));//获得后缀

                String fileName = UUID.randomUUID().toString() + extension;//UUID

                LocalFileUtil.upload(file.getBytes(), fileName);//保存到本地

                String filePath = "http://localhost:8080/uploads/" + fileName;

                return Result.success(filePath);
            } catch (IOException e) {
                log.error("文件上传失败:", e);
            }
            return null;
    }
}
