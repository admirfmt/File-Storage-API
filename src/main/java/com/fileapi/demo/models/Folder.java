package com.fileapi.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "folders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "main_folder_id")
    private Folder mainFolder;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();
}

