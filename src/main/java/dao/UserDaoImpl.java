package dao;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    String url = ConnectionUtility.url;
    String dbUsername = ConnectionUtility.dbUsername;
    String dbPassword = ConnectionUtility.dbPassword;
    private static UserDao userDao;

    private UserDaoImpl() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }

        return userDao;
    }

    @Override
    public User getUser(String username) {
        User user = null;

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM ers_users WHERE ers_username = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt("ers_users_id"), rs.getString("ers_username"),
                        rs.getString("ers_password"), rs.getString("user_first_name"),
                        rs.getString("user_last_name"), rs.getString("user_email"),
                        rs.getInt("user_role_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserById(Integer id) {
        User user = null;

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM ers_users WHERE ers_users_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt("ers_users_id"), rs.getString("ers_username"),
                        rs.getString("user_first_name"), rs.getString("user_last_name"),
                        rs.getString("user_email"), rs.getInt("user_role_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM ers_users;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("ers_users_id"),
                                   rs.getString("ers_username"),
                                   rs.getString("user_first_name"),
                                   rs.getString("user_last_name"),
                                   rs.getString("user_email"),
                                   rs.getInt("user_role_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  users;
    }

    @Override
    public String getUserRole(String username) {
        String userRole = "";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT r.user_role FROM ers_users u\n" +
                    "INNER JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id\n" +
                    "WHERE ers_username = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userRole = rs.getString("user_role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userRole;
    }
}
