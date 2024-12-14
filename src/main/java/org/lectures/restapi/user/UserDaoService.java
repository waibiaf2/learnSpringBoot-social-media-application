package org.lectures.restapi.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {
    private UserRepository userRepository;

    public UserDaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        User updatedUser = userRepository.findById(id).orElse(null);
        if (updatedUser != null) {
            updatedUser.setName(user.getName());
            updatedUser.setBirthDate(user.getBirthDate());
        }
        return updatedUser;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
