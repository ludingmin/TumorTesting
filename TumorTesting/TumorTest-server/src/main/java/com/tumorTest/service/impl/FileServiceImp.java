package com.tumorTest.service.impl;

import com.tumorTest.context.BaseContext;
import com.tumorTest.dto.UserDto;
import com.tumorTest.excption.FileException;
import com.tumorTest.result.Result;
import com.tumorTest.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {

    //    文件上传路径
    @Value("${tumortest.basePath}")
    private String basePath;

    /**
     * 文件上传方法
     * @param file 上传的文件对象
     * @return
     */
    @Override
    public Result fileReload(MultipartFile file) {
        //        获取文件名
        String originalFilename = file.getOriginalFilename();
//        获取后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

//        使用uuid生成文件名字，防止名字重复导致的文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

//        创建目录
        File dir = new File(basePath);
//        查看是否存在目录，不存在则创建目录
        if(!dir.exists()){
//            创建目录
            dir.mkdirs();
        }

        try {
//            存储上传的文件至该目录下
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            throw new FileException("文件上传异常");
        }
        // TODO 还需要将用户id以及basePath+fileName存储到数据库，在查询结果时才可以获取图片

        return Result.success();
    }
}
