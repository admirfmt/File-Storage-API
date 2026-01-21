package com.fileapi.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class UploadFileRequest {
   private MultipartFile file;
   private Long folderId;
}
