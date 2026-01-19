package com.fileapi.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Folder {

    @Id
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "main_folder_id")
    private Folder mainFolder;

    @OneToMany(mappedBy = "mainFolder", cascade = CascadeType.ALL)
    private List<Folder> subFolders = new ArrayList<>();

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();
    private Date createdAt;
}

