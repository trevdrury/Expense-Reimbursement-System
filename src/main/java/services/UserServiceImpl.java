package services;

import dao.UserDao;
import dao.UserDaoImpl;
import models.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao;

    public UserServiceImpl(UserDao userDaoMock) {
        this.userDao = userDaoMock;
    }

    public UserServiceImpl() { userDao = UserDaoImpl.getInstance(); }

    @Override
    public User login(User user) {
        User userToLogin = userDao.getUser(user.getUsername());

        if (userToLogin == null)
            return null;

        if (!userToLogin.getPassword().equals(user.getPassword()))
            return null;

        System.out.println(userToLogin);
        return userToLogin;
    }

    @Override
    public User getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getAllUsers();
    }
}
