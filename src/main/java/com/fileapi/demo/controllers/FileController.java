package com.fileapi.demo.controllers;

import com.fileapi.demo.dtos.UploadFileRequest;
import com.fileapi.demo.models.File;
import com.fileapi.demo.repositories.IFileRepository;
import com.fileapi.demo.repositories.IFolderRepository;
import com.fileapi.demo.services.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        File file = fileService.getFileById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok("File deleted successfully");
    }
}
