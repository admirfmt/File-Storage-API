package com.fileapi.demo.services;

import com.fileapi.demo.dtos.CreateFolderRequest;
import com.fileapi.demo.models.Folder;

import java.util.List;
import java.util.Optional;

public interface IFolderService {
    Folder createFolder(CreateFolderRequest request);
    Optional<Folder> getFolderById(Long id);
    Optional<Folder> getFolderByName(String name);
    List<Folder> getAllFolders();
}

