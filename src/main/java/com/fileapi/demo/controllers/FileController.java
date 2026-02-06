package com.fileapi.demo.controllers;

import com.fileapi.demo.dtos.ErrorResponse;
import com.fileapi.demo.dtos.UploadFileRequest;
import com.fileapi.demo.exceptions.FileNotFoundException;
import com.fileapi.demo.exceptions.FolderNotFoundException;
import com.fileapi.demo.exceptions.UserNotLoggedInException;
import com.fileapi.demo.models.File;
import com.fileapi.demo.services.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long folderId) throws Exception {

        try {
            UploadFileRequest request = new UploadFileRequest(file, folderId);
            File uploadedFile = fileService.uploadFile(request);
            return ResponseEntity.ok(uploadedFile);
        } catch (UserNotLoggedInException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("You are not logged in. Please register or login first."));
        } catch (FolderNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (IOException ignored) {
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("Failed to upload file."));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("An error occurred."));
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        try {
            File file = fileService.getFileById(id)
                    .orElseThrow(() -> new FileNotFoundException("File not found."));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getName() + "\"")
                    .body(file.getContent());
        } catch (UserNotLoggedInException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Not logged in."));
        } catch (FileNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("An error occurred."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity
                    .ok(new ErrorResponse("File deleted successfully"));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Not logged in."));
        } catch (FileNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("An error occurred."));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllFiles(Long id) {
        try {
            List<File> allFiles = fileService.getAllFiles(id);
            return ResponseEntity.ok(allFiles);
        } catch (UserNotLoggedInException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Not logged in"));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("An error occurred."));
        }
    }
}
