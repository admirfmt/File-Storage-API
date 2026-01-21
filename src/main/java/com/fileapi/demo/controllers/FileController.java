package com.fileapi.demo.controllers;

import com.fileapi.demo.dtos.UploadFileRequest;
import com.fileapi.demo.models.File;
import com.fileapi.demo.repositories.IFileRepository;
import com.fileapi.demo.repositories.IFolderRepository;
import com.fileapi.demo.services.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileRepository fileRepository;
    private final IFolderRepository folderRepository;
    private final IFileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long folderId) throws Exception {

        UploadFileRequest request = new UploadFileRequest(file, folderId);
        File uploadedFile = fileService.uploadFile(request);
        return ResponseEntity.ok(uploadedFile);
    }
}
