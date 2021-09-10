package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Response;
import models.User;
import services.UserService;
import services.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class UserController {
    private static UserController userController;
    UserService userService;

    private UserController() {
        userService = new UserServiceImpl();
    }

    public static UserController getInstance() {
        if (userController == null)
            userController = new UserController();

        return userController;
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        User userToLogin = new ObjectMapper().readValue(requestBody, User.class);

        User user = userService.login(userToLogin);

        if (user != null) {
            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("userObj", user);

            out.println(new ObjectMapper().writeValueAsString(new Response("login successful", true, user)));
        } else {
            out.println(new ObjectMapper().writeValueAsString(new Response("invalid username or password", false, null)));
        }
    }

    public void checkSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        User user = (User) req.getSession().getAttribute("userObj");

        if (user != null) {
            out.println(new ObjectMapper().writeValueAsString(new Response("session found", true, user)));
        } else {
            out.println(new ObjectMapper().writeValueAsString(new Response("no session found", false, null)));
        }
    }

    public void getUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (req.getParameterMap().containsKey("username")) {
            String username = req.getParameter("username");

            out.println(new ObjectMapper().writeValueAsString(new Response("User retrieved",
                    true, userService.getUser(username))));
        } else if (req.getParameterMap().containsKey("authorId")) {
            String authorId = req.getParameter("authorId");

            out.println(new ObjectMapper().writeValueAsString(new Response("User retrieved",
                    true, userService.getUserById(Integer.parseInt(authorId)))));
        } else {
            out.println(new ObjectMapper().writeValueAsString(new Response("Users retrieved",
                    true, userService.getUsers())));
        }
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        req.getSession().setAttribute("userObj", null);

        out.println(new ObjectMapper().writeValueAsString(new Response("Session terminated", true, null)));
    }
}
