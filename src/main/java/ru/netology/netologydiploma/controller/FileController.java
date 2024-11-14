package ru.netology.netologydiploma.controller;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.netologydiploma.model.FileDetails;
import ru.netology.netologydiploma.model.PutRequest;
import ru.netology.netologydiploma.service.FileService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private static final Logger log = Logger.getLogger(FileController.class);

    @GetMapping("/list")
    public List<FileDetails> getFileList(Principal principal, @RequestParam(name = "limit") int limit) {
        log.info("getFileList by " + principal.getName() + ", limit = " + limit);
        return fileService.getFileList(principal.getName(), limit);
    }

    @PostMapping("/file")
    public void saveFile(Principal principal, @RequestParam("filename") String fileName, @RequestBody MultipartFile file) {
        log.info("saveFile " + fileName + " by " + principal.getName());
        fileService.saveFile(principal.getName(), fileName, file);
    }

    @GetMapping("/file")
    public byte[] getFile(Principal principal, @RequestParam("filename") String fileName) {
        log.info("GET Request: download file: " + fileName);
        return fileService.getFile(principal.getName(), fileName);
    }

    @PutMapping("/file")
    public void renameFile(Principal principal, @RequestParam("filename") String oldFileName, @RequestBody PutRequest putRequest) {
        log.info("PUT Request: edit filename: " + oldFileName);
        fileService.renameFile(principal.getName(), oldFileName, putRequest.getFilename());
    }

    @DeleteMapping("/file")
    public void deleteFile(Principal principal, @RequestParam("filename") String fileName) {
        log.info("DELETE Request: delete file: " + fileName);
        fileService.deleteFile(principal.getName(), fileName);
    }
}