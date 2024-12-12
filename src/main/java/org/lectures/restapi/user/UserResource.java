package org.lectures.restapi.user;

import jakarta.servlet.Servlet;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<User> retrieveUser(@PathVariable Integer id) throws UserNotFoundException {
        User user = userDaoService.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id: " + id);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User createdUser = userDaoService.save(user);

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
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) throws UserNotFoundException {
        User updatedUser = userDaoService.update(id, user);

        if (updatedUser == null)
            throw new UserNotFoundException("User " + id + " not found");

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        User user = userDaoService.findOne(id);
        userDaoService.delete(id);

        if (user == null)
            throw new UserNotFoundException("User " + id + " not found");

        return new ResponseEntity<String>(
            "User Successfully Deleted",
            HttpStatus.OK
        );
    }
}
