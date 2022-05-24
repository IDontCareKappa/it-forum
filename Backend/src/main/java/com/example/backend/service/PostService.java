package com.example.backend.service;

import com.example.backend.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post savePost(Post post);

    Post getPost(Long id);

    List<Post> getPosts();

    void deletePost(Long id);

    Post updatePost(Post post);

}
