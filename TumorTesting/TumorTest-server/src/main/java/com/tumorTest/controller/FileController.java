package com.tumorTest.controller;

import com.tumorTest.result.Result;
import com.tumorTest.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@Api(tags = "文件相关接口")
@Slf4j
public class FileController {

    @Autowired
    FileService fileService;


    @PostMapping("/reload")
    @ApiOperation("文件上传接口")
    public Result fileReload(MultipartFile file){
        return fileService.fileReload(file);
    }
}
