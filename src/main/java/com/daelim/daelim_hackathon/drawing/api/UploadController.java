package com.daelim.daelim_hackathon.drawing.api;

import com.daelim.daelim_hackathon.drawing.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {
    private final AwsS3Service awsS3Service;

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @PostMapping
    public ResponseEntity uploadFile(@RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        String fileName = awsS3Service.uploadFile(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @DeleteMapping
    public ResponseEntity deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
