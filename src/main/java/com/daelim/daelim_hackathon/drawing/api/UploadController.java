package com.daelim.daelim_hackathon.drawing.api;

import com.daelim.daelim_hackathon.drawing.dto.FileNameDTO;
import com.daelim.daelim_hackathon.drawing.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {
    private final AwsS3Service awsS3Service;

    /**
     * Amazon S3에서 파일 가져오기
     * @return 성공 시 200 OK와 함께 업로드 된 파일의 파일명 반환
     */
    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFile(@PathVariable("id") String id) {
        return null;
    }

    /**
     * Amazon S3에서 파일 가져오기
     * @return 성공 시 200 OK와 함께 업로드 된 파일의 파일명 반환
     */
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFile() {
        return null;
    }

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 OK와 함께 업로드 된 파일의 파일명 반환
     */
    @PostMapping(MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(
            @RequestPart(value = "file", required = false) MultipartFile multipartFile
    ) {
        awsS3Service.uploadFile(multipartFile);
        return new ResponseEntity<>(
                FileNameDTO.builder().fileName(awsS3Service.uploadFile(multipartFile)).build(),
                HttpStatus.OK
        );
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteFile(@RequestBody FileNameDTO fileNameDTO) {
        awsS3Service.deleteFile(fileNameDTO.getFileName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
