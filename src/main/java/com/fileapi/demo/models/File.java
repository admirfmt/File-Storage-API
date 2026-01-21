package com.fileapi.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private byte[] content;
    private String contentType;

    private Long fileSize;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonIgnore
    private Folder folder;
}

