package com.example.backend.service;

import com.example.backend.dto.PostUtils;
import com.example.backend.exception.PostError;
import com.example.backend.exception.PostException;
import com.example.backend.model.Post;
import com.example.backend.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    @Override
    public Post savePost(PostUtils postUtils) {
        log.info("Post {} saved", postUtils.getTitle());

        Post newPost = new Post(
                null,
                postUtils.getTitle(),
                postUtils.getContent(),
                postUtils.getAuthor(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                postUtils.getTag(),
                new HashSet<>()
        );

        validatePost(newPost);

        return postRepo.save(newPost);
    }

    @Override
    public Post getPost(Long id) {
        return postRepo.findById(id)
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND));
    }

    @Override
    public List<Post> getPosts() {
        return postRepo.findAll();
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND));
        postRepo.delete(post);
    }

    @Override
    public Post updatePost(Post post) {
        postRepo.findById(post.getId())
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND));
        validatePost(post);
        return postRepo.save(post);
    }

    @Override
    public List<Post> getPostsByTag(String tag) {
        return postRepo.findAllByTag(tag);
    }

    @Override
    public Set<String> getTags() {
        Set<String> tags = new HashSet<>();
        List<Post> posts = postRepo.findAll();
        posts.forEach(post -> tags.add(post.getTag()));
        return tags;
    }

    private void validatePost(Post post) {
        if (ObjectUtils.isEmpty(post.getTitle())){
            throw new PostException(PostError.POST_TITLE_EMPTY);
        } else if (ObjectUtils.isEmpty(post.getAuthor())){
            throw new PostException(PostError.POST_AUTHOR_EMPTY);
        } else if (ObjectUtils.isEmpty(post.getContent())){
            throw new PostException(PostError.POST_CONTENT_EMPTY);
        } else if (post.getTitle().length() > 100){
            throw new PostException(PostError.POST_TITLE_TO_LONG);
        }
    }
}
