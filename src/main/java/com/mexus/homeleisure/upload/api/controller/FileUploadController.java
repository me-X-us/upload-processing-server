package com.mexus.homeleisure.upload.api.controller;

import com.mexus.homeleisure.upload.api.response.FileUploadResponse;
import com.mexus.homeleisure.upload.api.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/files")
public class FileUploadController {

    private final FileService fileService;

    /*
    Upload File
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public FileUploadResponse uploadProfileImage(
            @RequestParam("media") MultipartFile file
    ) {
        return new FileUploadResponse(this.fileService.storeFile(file), file.getContentType(), file.getSize());
    }
}
