package com.fileapi.demo.services;

import com.fileapi.demo.dtos.UploadFileRequest;
import com.fileapi.demo.models.File;
import com.fileapi.demo.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IFileService {
    File uploadFile(UploadFileRequest request) throws IOException;
    Optional<File> getFileById(Long id);
    void deleteFile(Long id);
    List<File> getAllFiles(Long id);
}
