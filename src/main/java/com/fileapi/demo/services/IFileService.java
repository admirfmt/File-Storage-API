package com.fileapi.demo.services;

import com.fileapi.demo.dtos.UploadFileRequest;
import com.fileapi.demo.models.File;

import java.io.IOException;

public interface IFileService {
    File uploadFile(UploadFileRequest request) throws IOException;
}
