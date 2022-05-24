package com.example.backend.service;

import com.example.backend.exception.PostError;
import com.example.backend.exception.PostException;
import com.example.backend.model.Post;
import com.example.backend.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

        validatePost(post);

        return postRepo.save(post);
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
