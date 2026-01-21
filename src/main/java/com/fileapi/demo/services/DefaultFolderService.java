package com.fileapi.demo.services;

import com.fileapi.demo.dtos.CreateFolderRequest;
import com.fileapi.demo.models.Folder;
import com.fileapi.demo.repositories.IFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultFolderService implements IFolderService {

    private final IFolderRepository folderRepository;

    @Override
    public Folder createFolder(CreateFolderRequest request) {
        Folder folder = new Folder();
        folder.setName(request.getName());
        folder.setCreatedAt(new Date());

        if (request.getParentFolderId() != null) {
            Folder parentFolder = folderRepository.findById(request.getParentFolderId())
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
            folder.setMainFolder(parentFolder);
        }

        return folderRepository.save(folder);
    }

    @Override
    public Optional<Folder> getFolderById(Long id) {
        return folderRepository.findById(id);
    }
}