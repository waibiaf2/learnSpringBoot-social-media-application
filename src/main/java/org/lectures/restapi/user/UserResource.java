package org.lectures.restapi.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserResource {
    private final UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping
    public ResponseEntity<List<User>> retrieveAllUser() {
        List<User> users = userDaoService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User> > retrieveUser(
        @PathVariable Long id
    ) throws UserNotFoundException {
        User user = userDaoService.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id: " + id);

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo( methodOn(this.getClass()).retrieveAllUser());
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
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
        @Valid @RequestBody User user
    ) throws UserNotFoundException {
        User updatedUser = userDaoService.update(id, user);

        if (updatedUser == null)
            throw new UserNotFoundException("User " + id + " not found");

        return ResponseEntity.ok(updatedUser);
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
}
