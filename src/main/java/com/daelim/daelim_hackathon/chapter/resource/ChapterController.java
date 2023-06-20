package com.daelim.daelim_hackathon.chapter.resource;

import com.daelim.daelim_hackathon.chapter.dto.ChapterModifyDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterPageRequestDTO;
import com.daelim.daelim_hackathon.chapter.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/chapters")
public class ChapterController {
    private final ChapterService chapterService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getList(@RequestBody ChapterPageRequestDTO pageRequestDTO) {
        try {
            return new ResponseEntity<>(chapterService.getChapters(pageRequestDTO), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody ChapterDTO dto) {
        try {
            return new ResponseEntity<>(chapterService.saveChapter(dto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity read(@PathVariable(value = "id") String id) {
        try {
            return new ResponseEntity<>(chapterService.getChapter(Long.parseLong(id)), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value= "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@PathVariable(value = "id") String id, @RequestBody ChapterModifyDTO dto) {
        try {
            return new ResponseEntity<>(chapterService.updateChapter(Long.parseLong(id), dto), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        try {
            return new ResponseEntity<>(chapterService.deleteChapter(Long.parseLong(id)), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
