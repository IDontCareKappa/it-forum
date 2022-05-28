package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String content;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private int thumbUpCount;

    private int thumbDownCount;

    private double rate;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @JsonBackReference
    @ManyToMany(fetch = EAGER)
    private Collection<User> usersThatReviewed = new ArrayList<>();

}
