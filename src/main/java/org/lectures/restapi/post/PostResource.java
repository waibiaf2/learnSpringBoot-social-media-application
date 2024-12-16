package org.lectures.restapi.post;

import org.lectures.restapi.user.User;
import org.lectures.restapi.user.UserDaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/{userId}")
public class PostResource {
    private final PostDaoService postDaoService;
    private final UserDaoService userDaoService;

    public PostResource(PostDaoService postDaoService, UserDaoService userDaoService) {
        this.postDaoService = postDaoService;
        this.userDaoService = userDaoService;
    }

   /* @GetMapping("posts")
    public ResponseEntity<List<Post> > getPosts(@PathVariable long userId) {
        User user = userDaoService.findOne(userId);
        List<Post> posts = postDaoService.getAllPosts(user);
        return ResponseEntity.ok(posts);
    }*/

    @GetMapping("posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable long id) {
        Post post = postDaoService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping("posts")
    public ResponseEntity<Post> createPost(
        @PathVariable Long userId,
        @RequestBody Post post
    ) {
        Post savedPost = postDaoService.savePost(userId, post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("posts/{id}")
    public ResponseEntity<String> updatePost(
        @PathVariable Long id,
        @RequestBody Post post
    ) {
        Post updatedPost = postDaoService.updatePost(post);
        return ResponseEntity.ok("Post update successfully");
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postDaoService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}
