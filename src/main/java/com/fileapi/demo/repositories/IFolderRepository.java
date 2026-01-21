package com.fileapi.demo.repositories;

import com.fileapi.demo.models.Folder;
import com.fileapi.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByName(String name);
    List<Folder> findByOwner(User owner);
    Optional<Folder> findByIdAndOwner(Long id, User owner);
}
