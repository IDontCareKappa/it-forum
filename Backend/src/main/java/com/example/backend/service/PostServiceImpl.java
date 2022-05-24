package com.example.backend.service;

import com.example.backend.model.Post;
import com.example.backend.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    @Override
    public Post savePost(Post post) {
        log.info("Post {} saved", post.getTitle());
        return postRepo.save(post);
    }

    @Override
    public Post getPost(Long id) {
        Optional<Post> post = postRepo.findById(id);
        if (post.isPresent()) {
            //log.info("Found post {} in database", post.get().getTitle());
            return post.get();
        }
        return null;
    }

    @Override
    public List<Post> getPosts() {
        return postRepo.findAll();
    }

    @Override
    public void deletePost(Long id) {
        Optional<Post> post = postRepo.findById(id);
        post.ifPresent(postRepo::delete);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepo.save(post);
    }
}
