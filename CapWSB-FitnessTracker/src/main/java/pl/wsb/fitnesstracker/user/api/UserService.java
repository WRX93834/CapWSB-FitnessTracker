package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUser(Long id);
    User findUserById(Long id);
    User findUserByEmail(String email);
    List<User> findAllUsersOlderThan(int age);
    List<User> findAllUsersOlderThan(LocalDate date);
    List<User> findAllUsers();
    void deleteUserById(Long id);
}
