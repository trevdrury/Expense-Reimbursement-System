package services;

import models.User;

import java.util.List;

public interface UserService {
    User login(User user);
    User getUser(String username);
    User getUserById(Integer id);
    List<User> getUsers();
}
