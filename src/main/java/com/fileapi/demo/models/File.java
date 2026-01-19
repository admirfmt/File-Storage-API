package com.fileapi.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    private Long id;

    private String name;
    private String content;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    private String contentType;
    private Long fileSize;
    private Date createdAt;
}

