package com.daelim.daelim_hackathon.drawing.service;

import com.daelim.daelim_hackathon.common.config.AmazonS3ResourceStorage;
import com.daelim.daelim_hackathon.drawing.dto.FileDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public FileDetail save(MultipartFile multipartFile) {
        FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(fileDetail.getPath(), multipartFile);
        return fileDetail;
    }
}
