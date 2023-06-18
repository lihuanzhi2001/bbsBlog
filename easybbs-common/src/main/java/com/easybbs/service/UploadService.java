package com.easybbs.service;

import com.easybbs.entity.vo.ResponseVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    ResponseVO uploadImg(MultipartFile img) throws IOException;
}
