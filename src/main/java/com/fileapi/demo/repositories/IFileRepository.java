package com.fileapi.demo.repositories;

import com.fileapi.demo.models.File;
import com.fileapi.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFileRepository extends JpaRepository<File, Long> {
    List<File> findByOwner(User user);
    Optional<File> findByIdAndOwner(Long id, User user);
}
