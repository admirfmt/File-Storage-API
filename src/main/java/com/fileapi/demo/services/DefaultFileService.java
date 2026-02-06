package com.fileapi.demo.services;

import com.fileapi.demo.dtos.UploadFileRequest;
import com.fileapi.demo.exceptions.FileNotFoundException;
import com.fileapi.demo.exceptions.FolderNotFoundException;
import com.fileapi.demo.models.File;
import com.fileapi.demo.models.Folder;
import com.fileapi.demo.models.User;
import com.fileapi.demo.repositories.IFileRepository;
import com.fileapi.demo.repositories.IFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultFileService implements IFileService {

    private final IFileRepository fileRepository;
    private final IFolderRepository folderRepository;
    private final IUserService userService;

    @Override
    public File uploadFile(UploadFileRequest request) throws IOException {
        User currentUser = userService.getCurrentUser();
        Folder folder = folderRepository.findByIdAndOwner(request.getFolderId(), currentUser)
                .orElseThrow(() -> new FolderNotFoundException("Folder not found."));

        File modelFile = new File();
        modelFile.setName(request.getFile().getOriginalFilename());
        modelFile.setContent(request.getFile().getBytes());
        modelFile.setContentType(request.getFile().getContentType());
        modelFile.setFileSize(request.getFile().getSize());
        modelFile.setOwner(currentUser);
        modelFile.setCreatedAt(new Date());
        modelFile.setFolder(folder);

        return fileRepository.save(modelFile);
    }

    @Override
    public Optional<File> getFileById(Long id) {
        User currentUser = userService.getCurrentUser();
        return fileRepository.findByIdAndOwner(id, currentUser);
    }

    @Override
    public void deleteFile(Long id) {
        User currentUser = userService.getCurrentUser();
        File file = fileRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
        fileRepository.delete(file);
    }

    @Override
    public List<File> getAllFiles(Long id) {
        User currentUser = userService.getCurrentUser();
        return fileRepository.findByOwner(currentUser);
    }
}
