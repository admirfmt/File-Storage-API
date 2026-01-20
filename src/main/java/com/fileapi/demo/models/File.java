package com.fileapi.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String content;
    private String contentType;

    private Long fileSize;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;
}

