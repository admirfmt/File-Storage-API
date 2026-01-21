package com.fileapi.demo.services;

import com.fileapi.demo.dtos.UploadFileRequest;
import com.fileapi.demo.models.File;
import com.fileapi.demo.models.Folder;
import com.fileapi.demo.repositories.IFileRepository;
import com.fileapi.demo.repositories.IFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class DefaultFileService implements IFileService {

    private final IFileRepository fileRepository;
    private final IFolderRepository folderRepository;

    @Override
    public File uploadFile(UploadFileRequest request) throws IOException {
        Folder folder = folderRepository.findById(request.getFolderId())
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        File modelFile = new File();
        modelFile.setName(request.getFile().getOriginalFilename());
        modelFile.setContent(request.getFile().getBytes());
        modelFile.setContentType(request.getFile().getContentType());
        modelFile.setFileSize(request.getFile().getSize());
        modelFile.setCreatedAt(new Date());
        modelFile.setFolder(folder);

        return fileRepository.save(modelFile);
    }
}
