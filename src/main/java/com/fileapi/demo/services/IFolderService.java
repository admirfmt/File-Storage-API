package com.fileapi.demo.services;

import com.fileapi.demo.dtos.CreateFolderRequest;
import com.fileapi.demo.models.Folder;

public interface IFolderService {
    Folder createFolder(CreateFolderRequest request);
}

