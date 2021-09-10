package services;

import dao.UserDao;
import models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserService userService = new UserServiceImpl(userDaoMock);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnInstanceOfUserServiceImpl() {
        Assert.assertNotNull(userService);
    }

    @Test
    public void shouldReturnUserWhenLoginCalled() {
        User user = new User(2,"johnDoe","qwerty","John","Doe","johndoe@email.com",2);

        doReturn(user).when(userDaoMock).getUser("johnDoe");

        User result = userService.login(user);

        Assert.assertNotNull(result);
        Assert.assertEquals((Integer) 2, result.getUserId());
        Assert.assertEquals("johnDoe", result.getUsername());
        Assert.assertEquals("qwerty", result.getPassword());
        Assert.assertEquals("John", result.getFirstName());
        Assert.assertEquals("Doe", result.getLastName());
        Assert.assertEquals("johndoe@email.com", result.getEmail());
        Assert.assertEquals((Integer) 2, result.getUserRoleId());

        verify(userDaoMock, atLeast(1)).getUser("johnDoe");
    }

    @Test
    public void shouldReturnUserWhenGetUserCalled() {
        User user = new User(2,"johnDoe","qwerty","John","Doe","johndoe@email.com",2);

        doReturn(user).when(userDaoMock).getUser("johnDoe");

        User result = userService.getUser("johnDoe");

        Assert.assertNotNull(result);
        Assert.assertEquals((Integer) 2, result.getUserId());
        Assert.assertEquals("johnDoe", result.getUsername());
        Assert.assertEquals("qwerty", result.getPassword());
        Assert.assertEquals("John", result.getFirstName());
        Assert.assertEquals("Doe", result.getLastName());
        Assert.assertEquals("johndoe@email.com", result.getEmail());
        Assert.assertEquals((Integer) 2, result.getUserRoleId());

        verify(userDaoMock, atLeast(1)).getUser("johnDoe");
    }

    @Test
    public void shouldReturnUserWhenGetUserByIdCalled() {
        User user = new User(2,"johnDoe","qwerty","John","Doe","johndoe@email.com",2);

        doReturn(user).when(userDaoMock).getUserById(2);

        User result = userService.getUserById(2);

        Assert.assertNotNull(result);
        Assert.assertEquals((Integer) 2, result.getUserId());
        Assert.assertEquals("johnDoe", result.getUsername());
        Assert.assertEquals("qwerty", result.getPassword());
        Assert.assertEquals("John", result.getFirstName());
        Assert.assertEquals("Doe", result.getLastName());
        Assert.assertEquals("johndoe@email.com", result.getEmail());
        Assert.assertEquals((Integer) 2, result.getUserRoleId());

        verify(userDaoMock, atLeast(1)).getUserById(2);
    }

    @Test
    public void shouldReturnListOfAllUsersWhenGetUsersCalled() {
        List<User> users = Arrays.asList(
                new User(2,"johnDoe","qwerty","John","Doe","johndoe@email.com",2),
                new User(3,"janeDoe","1234","Jane","Doe","janedoe@email.com",1),
                new User(4,"timmy","4321","Timmy","Smith","timmy@email.com",2),
                new User(5,"bobby","6789","Bobby","Johnson","bobby@email.com",1));
        doReturn(users).when(userDaoMock).getAllUsers();

        List<User> results = userService.getUsers();

        Assert.assertNotNull(results);
        Assert.assertEquals(4, results.size());

        verify(userDaoMock, atLeast(1)).getAllUsers();
    }

    @After
    public void tearDown() {
        userDaoMock = null;
        userService = null;
    }
}
