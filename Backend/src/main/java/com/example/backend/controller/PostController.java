package com.example.backend.controller;

import com.example.backend.dto.PostUtils;
import com.example.backend.model.Post;
import com.example.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id){
        log.info("Fetched post with id: {}", id);
        return ResponseEntity.ok().body(postService.getPost(id));
    }

    @GetMapping("/posts/{tag}")
    public ResponseEntity<List<Post>> getPostsByTag(@PathVariable String tag){
        log.info("Fetched posts with tag: {}", tag);
        return ResponseEntity.ok().body(postService.getPostsByTag(tag));
    }

    @GetMapping("/tags")
    public ResponseEntity<Set<String>> getTags(){
        log.info("Fetched all tags");
        return ResponseEntity.ok().body(postService.getTags());
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(){
        log.info("Fetched all posts");
        return ResponseEntity.ok().body(postService.getPosts());
    }

    @PostMapping("/post/save")
    public ResponseEntity<Post> savePost(@RequestBody PostUtils post){
        log.info("Saved post: {}", post.getTitle());
        return ResponseEntity.ok().body(postService.savePost(post));
    }

    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        log.info("Deleted post with id: {}", id);
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

}
