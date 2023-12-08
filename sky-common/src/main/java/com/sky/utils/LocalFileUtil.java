package com.sky.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Data
@Slf4j
@AllArgsConstructor
public class LocalFileUtil {

    private static String localFilePath = "D:\\Project\\FIle";

    /**
     * 文件保存到本地
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public static String upload(byte[] bytes, String objectName) {
        try (FileOutputStream fos = new FileOutputStream(localFilePath + "\\" + objectName)) {
            fos.write(bytes);
            log.info("文件保存到本地路径: {}", localFilePath + "\\" + objectName);
            return localFilePath + "\\" + objectName;
        } catch (IOException e) {
            log.error("保存文件到本地失败: {}", e.getMessage());
            return null;
        }
    }
}
