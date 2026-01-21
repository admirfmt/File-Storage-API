package com.fileapi.demo.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Date createdAt;
}
