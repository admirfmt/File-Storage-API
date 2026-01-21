package com.fileapi.demo.controllers;

import com.fileapi.demo.dtos.CreateFolderRequest;
import com.fileapi.demo.models.Folder;
import com.fileapi.demo.services.IFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final IFolderService folderService;

    @PostMapping
    public ResponseEntity<?> createFolder(@RequestBody CreateFolderRequest request) {
        Folder folder = folderService.createFolder(request);
        return ResponseEntity.created(URI.create("/folders")).body(folder);
    }

    @GetMapping
    public ResponseEntity<?> getAllFolders() {
        return ResponseEntity.ok(folderService.getAllFolders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFolderById(@PathVariable Long id) {
        Optional<Folder> folder = folderService.getFolderById(id);
        return ResponseEntity.ok(folder);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getFolderByName(@PathVariable String name) {
        Optional<Folder> folder = folderService.getFolderByName(name);
        return ResponseEntity.ok(folder);
    }
}
