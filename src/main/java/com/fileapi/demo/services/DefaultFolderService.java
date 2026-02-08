package com.fileapi.demo.services;

import com.fileapi.demo.dtos.CreateFolderRequest;
import com.fileapi.demo.exceptions.FolderNotFoundException;
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

    /**
     * Create a new folder for a logged user.
     * Folder can have a parent folder to create sub-folders.
     *
     * @param request CreateFolderRequest contains folder name and optional parentFolderId
     * @return Created folder
     * @throws FolderNotFoundException if parent folder doesn't exist, or it's not owned by an user
     */
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
                    .orElseThrow(() -> new FolderNotFoundException("Parent folder not found"));
            folder.setMainFolder(parentFolder);
        }

        return folderRepository.save(folder);
    }

    /**
     * Gets a folder based on ID.
     * Returns only folders which are owned by currently logged user.
     *
     * @param id Folder-ID
     * @return Optional contains folder if it exists, and it's owned by current user.
     */
    @Override
    public Optional<Folder> getFolderById(Long id) {
        User currentUser = userService.getCurrentUser();
        return folderRepository.findByIdAndOwner(id, currentUser);
    }

    /**
     * Gets a folder based on name.
     * Returns only folders which are owned by currently logged user.
     *
     * @param name Folder name to search for
     * @return Optional contains folder if it exists, and it's owned by current user.
     */
    @Override
    public Optional<Folder> getFolderByName(String name) {
        User currentUser = userService.getCurrentUser();
        Optional<Folder> folder = folderRepository.findByName(name);

        // Verify
        if (folder.isPresent() && folder.get().getOwner().getId().equals(currentUser.getId())) {
            return folder;
        }
        return Optional.empty();
    }

    /**
     * Gets all folders with currently logged user.
     *
     * @return List all users folders
     */
    @Override
    public List<Folder> getAllFolders() {
        User currentUser = userService.getCurrentUser();
        return folderRepository.findByOwner(currentUser);
    }
}