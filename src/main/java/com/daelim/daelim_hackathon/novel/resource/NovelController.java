package com.daelim.daelim_hackathon.novel.resource;

import com.daelim.daelim_hackathon.chapter.service.ChapterService;
import com.daelim.daelim_hackathon.common.dto.UsernameDTO;
import com.daelim.daelim_hackathon.drawing.dto.FileNameDTO;
import com.daelim.daelim_hackathon.drawing.dto.StringDTO;
import com.daelim.daelim_hackathon.drawing.service.AwsS3Service;
import com.daelim.daelim_hackathon.drawing.service.PapagoService;
import com.daelim.daelim_hackathon.novel.dto.NovelModifyDTO;
import com.daelim.daelim_hackathon.novel.dto.NovelDTO;
import com.daelim.daelim_hackathon.novel.dto.NovelPageRequestDTO;
import com.daelim.daelim_hackathon.novel.service.NovelService;
import com.daelim.daelim_hackathon.page.service.PageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/novels")
public class NovelController {
    private final NovelService novelService;
    private final AwsS3Service awsS3Service;
    private final PapagoService papagoService;
    private final ChapterService chapterService;
    private final PageService pageService;
    /**
     *
     * @param stringDTO
     * @return stringDTO(string = 영어로 번역된 문자열)
     */
    @PostMapping(value = "/translate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEn(@RequestBody StringDTO stringDTO) {
        try {
            return ResponseEntity.ok().body(papagoService.koToEn(stringDTO.getString()));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getList(@RequestBody NovelPageRequestDTO pageRequestDTO) {
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

    @PutMapping(value= "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@PathVariable(value = "id") String id, @RequestBody NovelModifyDTO dto) {
        try {
            return new ResponseEntity<>(novelService.updateNovel(Long.parseLong(id), dto), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") String id, @RequestBody UsernameDTO usernameDTO) {
        try {
            awsS3Service.deleteFile(novelService.deleteFile(Long.parseLong(id)));
            return new ResponseEntity<>(novelService.deleteNovel(Long.parseLong(id), usernameDTO.getUsername()), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/love/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addLike(@PathVariable(value = "id") String id) {
        try {
            return new ResponseEntity<>(novelService.love(Long.parseLong(id)), HttpStatus.OK);
        } catch (Exception e) {
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

    @GetMapping(value = "/drawing/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFile(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(
                    FileNameDTO.builder().fileName(novelService.getFileName(
                            Long.parseLong(id)
                    )).build(),
                    HttpStatus.OK
            );
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
