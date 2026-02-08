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

    /**
     * Uploads a file to specific folder.
     * The file is saved as binary data in database.
     * Only folders with currently logged user can be used.
     *
     * @param request UploadFileRequest contains MultipartFile and folderId
     * @return Uploaded file
     * @throws IOException if file upload fails
     * @throws FolderNotFoundException if folder doesn't exist
     */
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

    /**
     * Gets a file based on ID.
     * Returns only files for currently logged user.
     *
     * @param id File ID
     * @return Optional contains files which exists
     */
    @Override
    public Optional<File> getFileById(Long id) {
        User currentUser = userService.getCurrentUser();
        return fileRepository.findByIdAndOwner(id, currentUser);
    }

    /**
     * Deletes a file.
     * Only files which are owned by current user can be deleted.
     *
     * @param id File ID
     * @throws FileNotFoundException if file doesn't exist
     */
    @Override
    public void deleteFile(Long id) {
        User currentUser = userService.getCurrentUser();
        File file = fileRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
        fileRepository.delete(file);
    }

    /**
     * Gets all files for current user.
     *
     * @return List of all files
     */
    @Override
    public List<File> getAllFiles(Long id) {
        User currentUser = userService.getCurrentUser();
        return fileRepository.findByOwner(currentUser);
    }
}
