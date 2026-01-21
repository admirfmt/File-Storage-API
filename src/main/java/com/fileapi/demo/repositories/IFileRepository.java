package com.fileapi.demo.repositories;

import com.fileapi.demo.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepository extends JpaRepository<File, Long> {
}
