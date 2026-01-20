package com.fileapi.demo.controllers;

import com.fileapi.demo.dtos.CreateFolderRequest;
import com.fileapi.demo.models.Folder;
import com.fileapi.demo.repositories.IFolderRepository;
import com.fileapi.demo.services.IFolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final IFolderRepository folderRepository;
    private final IFolderService folderService;

    @PostMapping
    public ResponseEntity<?> createFolder(@RequestBody CreateFolderRequest request) {
        Folder folder = folderService.createFolder(request);
        return ResponseEntity.created(URI.create("/folders")).body(folder);
    }

    @GetMapping
    public ResponseEntity<?> getAllFolders() {
        return ResponseEntity.ok(folderRepository.findAll());
    }
}
