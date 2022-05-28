package com.example.backend.service;

import com.example.backend.dto.PostUtils;
import com.example.backend.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostService {

    Post savePost(PostUtils postUtils);

    Post getPost(Long id);

    List<Post> getPosts();

    void deletePost(Long id);

    Post updatePost(Long id, PostUtils post);

    List<Post> getPostsByTag(String tag);

    Set<String> getTags();

    Integer getUserPostsCount(String username);

    List<Post> getUserPosts(String username);

}
