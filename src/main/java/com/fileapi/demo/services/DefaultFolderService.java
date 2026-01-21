package com.fileapi.demo.services;

import com.fileapi.demo.dtos.CreateFolderRequest;
import com.fileapi.demo.models.Folder;
import com.fileapi.demo.models.User;
import com.fileapi.demo.repositories.IFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultFolderService implements IFolderService {

    private final IFolderRepository folderRepository;
    private final IUserService userService;

    @Override
    public Folder createFolder(CreateFolderRequest request) {
        User currentUser = userService.getCurrentUser();
        Folder folder = new Folder();
        folder.setName(request.getName());
        folder.setOwner(currentUser);
        folder.setCreatedAt(new Date());

        if (request.getParentFolderId() != null) {
            Folder parentFolder = folderRepository.findByIdAndOwner(
                    request.getParentFolderId(), currentUser)
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
            folder.setMainFolder(parentFolder);
        }

        return folderRepository.save(folder);
    }

    @Override
    public Optional<Folder> getFolderById(Long id) {
        User currentUser = userService.getCurrentUser();
        return folderRepository.findByIdAndOwner(id, currentUser);
    }

    @Override
    public Optional<Folder> getFolderByName(String name) {
        User currentUser = userService.getCurrentUser();
        Optional<Folder> folder = folderRepository.findByName(name);

        // Verifiera
        if (folder.isPresent() && folder.get().getOwner().getId().equals(currentUser.getId())) {
            return folder;
        }
        return Optional.empty();
    }

    @Override
    public List<Folder> getAllFolders() {
        User currentUser = userService.getCurrentUser();
        return folderRepository.findByOwner(currentUser);
    }
}