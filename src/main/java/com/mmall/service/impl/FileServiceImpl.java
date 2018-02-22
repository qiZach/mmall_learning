package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author zhangsiqi
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 传入上传的文件和上传路径, 将文件上传到FTP服务器
     * 并且删除web应用内上传的完文件
     *
     * @param multiFile 上传的文件
     * @param path      文件路径
     * @return 文件名
     */
    @Override
    public String upload(MultipartFile multiFile, String path) {
        // 获得文件原始名
        String fileName = multiFile.getOriginalFilename();
        // 扩展名 jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 上传文件名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件, 上传文件的文件名{}, 上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

        // 找到path所在的路径, 如果路径不存在则新建路径
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            // 赋予权限可写
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        // 指向目标文件 此时并没有真实文件
        File targetFile = new File(path, uploadFileName);

        try {
            // 此方法来转存文件到目标文件
            //multiFile.transferTo(targetFile);
            // 到此文件已经上传成功, 文件上传到upload文件夹下

            //FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 已经将targetFile上传到我们得FTP服务器上

            // 上传完后, 删除upload下面的文件
            //targetFile.delete();
        } catch (Exception e) {
            logger.error("上传文件异常", e);
            return null;
        }

        // 返回的是UUID生成的文件名
        return targetFile.getName();
    }

}
