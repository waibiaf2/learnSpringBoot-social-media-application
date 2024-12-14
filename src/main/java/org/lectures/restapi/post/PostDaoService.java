package org.lectures.restapi.post;

import org.lectures.restapi.user.User;
import org.lectures.restapi.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostDaoService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostDaoService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post savePost(Long userId, Post post) {
        User user = userRepository.findById(userId).orElse(null);
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        Post updatedPost = postRepository.findById(post.getId()).orElse(null);

        if (post != null) {
            updatedPost.setTitle(post.getTitle());
            updatedPost.setBody(post.getBody());
            return postRepository.save(post);
        }

        return null;
    }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
