package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition="TEXT")
    private String content;

    @NotNull
    private String author;

    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime updatedAt;

    private String tag;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments;

}
