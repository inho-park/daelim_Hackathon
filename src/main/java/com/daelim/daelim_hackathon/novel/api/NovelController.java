package com.daelim.daelim_hackathon.novel.api;

import com.daelim.daelim_hackathon.author.dto.UsernameDTO;
import com.daelim.daelim_hackathon.drawing.dto.FileNameDTO;
import com.daelim.daelim_hackathon.drawing.service.AwsS3Service;
import com.daelim.daelim_hackathon.novel.dto.novel.ModifyDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.NovelDTO;
import com.daelim.daelim_hackathon.common.dto.PageRequestDTO;
import com.daelim.daelim_hackathon.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/novels")
public class NovelController {
    private final NovelService novelService;
    private final AwsS3Service awsS3Service;


    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getList(@RequestBody PageRequestDTO pageRequestDTO) {
        try {
            return new ResponseEntity<>(novelService.getNovels(pageRequestDTO), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody NovelDTO dto) {
        try {
            return new ResponseEntity<>(novelService.saveNovel(dto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity read(@PathVariable(value = "id") String id, @RequestBody UsernameDTO usernameDTO) {
        try {
            return new ResponseEntity<>(novelService.getNovel(Long.parseLong(id), usernameDTO.getUsername()), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@PathVariable(value = "id") String id, @RequestBody ModifyDTO dto) {
        try {
            return new ResponseEntity<>(novelService.updateNovel(Long.parseLong(id), dto), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") String id, @RequestBody UsernameDTO usernameDTO) {
        try {
            return new ResponseEntity<>(novelService.deleteNovel(Long.parseLong(id), usernameDTO.getUsername()), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 책 표지와 페이지 사이의 그림 table 을 구분 지어서 관리
     * NovelController 에서 s3 service 호출
     * @return 파일 경로에 해당하는 uuid 반환
     */
    @PostMapping(value = "/drawing/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @PathVariable("id") String id
    ) {
        try {
            awsS3Service.uploadFile(multipartFile);
            return new ResponseEntity<>(
                    FileNameDTO.builder().fileName(awsS3Service.saveNovelDrawing(
                            Long.parseLong(id),
                            multipartFile
                    )).build(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
