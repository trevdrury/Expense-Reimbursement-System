package dao;

import models.User;

import java.util.List;

public interface UserDao {
    User getUser(String username);
    User getUserById(Integer id);
    List<User> getAllUsers();
    String getUserRole(String username);
}
