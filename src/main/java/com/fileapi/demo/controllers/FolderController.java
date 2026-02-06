package com.fileapi.demo.controllers;

import com.fileapi.demo.dtos.CreateFolderRequest;
import com.fileapi.demo.dtos.ErrorResponse;
import com.fileapi.demo.exceptions.FolderNotFoundException;
import com.fileapi.demo.exceptions.UserNotLoggedInException;
import com.fileapi.demo.models.Folder;
import com.fileapi.demo.services.IFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final IFolderService folderService;

    @PostMapping
    public ResponseEntity<?> createFolder(@RequestBody CreateFolderRequest request) {
        try {
            Folder folder = folderService.createFolder(request);
            return ResponseEntity
                    .created(URI.create("/folders"))
                    .body(folder);
        } catch (UserNotLoggedInException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("You are not logged in. Please register or login first."));
        } catch (FolderNotFoundException ignored) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Folder not found."));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("An error occurred."));
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllFolders() {
        try {
            List<Folder> allFolders = folderService.getAllFolders();
            return ResponseEntity.ok(allFolders);
        } catch (UserNotLoggedInException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("You are not logged in. Please register or login first."));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFolderById(@PathVariable Long id) {
        try {
            Optional<Folder> folder = folderService.getFolderById(id);
            if (folder.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Folder not found."));
            }
            return ResponseEntity.ok(folder.get());
        } catch (UserNotLoggedInException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("You are not logged in. Please register or login first."));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("An error occurred."));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getFolderByName(@PathVariable String name) {
        try {
            Optional<Folder> folder = folderService.getFolderByName(name);
            if (folder.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Folder not found."));
            }
            return ResponseEntity.ok(folder.get());
        } catch (UserNotLoggedInException ignored) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("You are not logged in. Please register or login first."));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse("An error occurred."));
        }
    }
}
