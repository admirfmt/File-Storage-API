package com.fileapi.demo.repositories;

import com.fileapi.demo.models.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFolderRepository extends JpaRepository<Folder, Long> {}
