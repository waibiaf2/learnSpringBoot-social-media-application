package org.lectures.restapi.user;

import jakarta.validation.Valid;
import org.lectures.restapi.post.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserResource {
    private final UserDaoService userDaoService;
    private final UserRepository userRepository;

    public UserResource(UserDaoService userDaoService, UserRepository userRepository) {
        this.userDaoService = userDaoService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> retrieveAllUser() {
        List<User> users = userDaoService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @EntityGraph(attributePaths = {"posts"})
    public /*ResponseEntity<EntityModel<User>>*/ResponseEntity<User> retrieveUser(
        @PathVariable Long id
    ) throws UserNotFoundException {
        User user = userDaoService.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id: " + id);

        /*EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUser());
        entityModel.add(linkTo.withRel("all-users"));*/

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User createdUser = userDaoService.save(user);
        System.out.println(createdUser);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdUser.getId())
            .toUri();

        return ResponseEntity
            .created(location)
            .body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody User userData
    ) throws UserNotFoundException {
        User user = userDaoService.update(id, userData);

        if (user == null)
            throw new UserNotFoundException("User " + id + " not found");

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User user = userDaoService.findOne(id);
        userDaoService.delete(id);

        if (user == null)
            throw new UserNotFoundException("User@RequestBody User user " + id + " not found");

        return new ResponseEntity<String>(
            "User Successfully Deleted",
            HttpStatus.OK
        );
    }

    @GetMapping("{id}/posts")
    public ResponseEntity<List<Post>> retrievePosts(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException("User " + id + " not found");

        List<Post> UserPosts =  user.get().getPosts();

        return ResponseEntity.ok(UserPosts);
    }
}
