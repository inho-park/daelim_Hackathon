package com.daelim.daelim_hackathon.novel.api;

import com.daelim.daelim_hackathon.author.dto.UsernameDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.NovelDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.PageRequestDTO;
import com.daelim.daelim_hackathon.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/novels")
public class NovelController {
    private final NovelService novelService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getList(@RequestBody PageRequestDTO pageRequestDTO) {
        try {
            return new ResponseEntity<>(novelService.getNovels(pageRequestDTO), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody NovelDTO dto) {
        try {
            return new ResponseEntity<>(novelService.saveNovel(dto), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity read(@PathVariable(value = "id") String id, @RequestBody UsernameDTO usernameDTO) {
        try {
            return new ResponseEntity<>(novelService.getNovel(Long.parseLong(id), usernameDTO.getUsername()), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity modify(@PathVariable(value = "id") String id, @RequestBody NovelDTO dto) {
        try {
            return new ResponseEntity<>(novelService.modifyNovel(Long.parseLong(id), dto), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id") String id, @RequestBody UsernameDTO usernameDTO) {
        try {
            return new ResponseEntity<>(novelService.deleteNovel(Long.parseLong(id), usernameDTO.getUsername()), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
