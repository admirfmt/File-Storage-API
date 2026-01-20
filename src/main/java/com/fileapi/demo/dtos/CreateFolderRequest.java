package com.fileapi.demo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFolderRequest {
    private String name;
    private Long parentFolderId;
}
