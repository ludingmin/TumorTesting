package com.tumorTest.service;

import com.tumorTest.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Result fileReload(MultipartFile file);
}
