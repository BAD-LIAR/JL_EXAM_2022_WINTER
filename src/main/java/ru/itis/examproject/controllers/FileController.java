package ru.itis.examproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.examproject.dto.RequestDto;
import ru.itis.examproject.service.FileService;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> getFileType1(@RequestBody RequestDto dto){
        fileService.generateDoc(dto);
        return ResponseEntity.ok().build();
    }


}
