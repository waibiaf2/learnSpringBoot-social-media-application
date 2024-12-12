package org.lectures.restapi.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static final List<User> users = new ArrayList<>();
    private static Integer usersCount = 0;

    static {
        users.add(new User(++usersCount, "Colline", LocalDate.now().minusYears(20)));
        users.add(new User(++usersCount, "Andrew", LocalDate.now().minusYears(15)));
        users.add(new User(++usersCount, "Charles", LocalDate.now().minusYears(10)));
        users.add(new User(++usersCount, "Robert", LocalDate.now().minusYears(17)));
    };

    public List<User> findAll() {
        return users;
    }

    public User findOne(Integer id) {
        Predicate<? super User> predicate =
            user -> Objects.equals(user.getId(), id);

        return users.stream()
            .filter(predicate).findFirst().orElse(null);
    }

    public User save(User user) {
        user.setId(++usersCount);
       users.add(user);

       return user;
    }

    public User update(Integer id, User user) {
        User userToUpdate = findOne(id);
        if (userToUpdate != null) {
            userToUpdate.setName(user.getName());
            userToUpdate.setBirthDate(user.getBirthDate());
        }
        return userToUpdate;
    }

    public void delete(Integer id) {
        Predicate<? super User > predicate = user -> Objects.equals(user.getId(), id);
        users.removeIf(predicate);
    }
}
