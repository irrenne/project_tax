package com.epam.tax.test.services.mock;

import com.epam.tax.dao.impl.UserDaoImpl;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockTest {

    private UserServiceImpl userService;

    @Mock
    private UserDaoImpl userDaoMock;

    @Before
    public void initializeMock() throws Exception {
        userService = spy(new UserServiceImpl());
        Field reportDaoField = UserServiceImpl.class
                .getDeclaredField("userDao");
        reportDaoField.setAccessible(true);
        reportDaoField.set(userService, userDaoMock);
    }

    @Test
    public void saveUserTest() throws Exception {
        User userMock = mock(User.class);
        doReturn(true).when(userDaoMock).insertUser(userMock);
        assertTrue(userService.insert(userMock));
    }

    @Test
    public void updateUserTest() throws Exception {
        User userMock = mock(User.class);
        doReturn(true).when(userDaoMock).updateUser(userMock);
        assertTrue(userService.update(userMock));
    }

    @Test
    public void deleteUserTest() throws Exception {
        User userMock = mock(User.class);
        doReturn(true).when(userDaoMock).deleteUser(userMock);
        assertTrue(userService.delete(userMock));
    }

    @Test
    public void getUserByIdTest() throws Exception {
        User userMock = mock(User.class);
        Long id = 1L;
        doReturn(userMock).when(userDaoMock).getUserById(id);
        Assert.assertEquals(userMock, userService.getById(id));
    }

    @Test
    public void getUserByLoginTest() throws Exception {
        User userMock = mock(User.class);
        String login = "login";
        doReturn(userMock).when(userDaoMock).getUserByLogin(login);
        Assert.assertEquals(userMock, userService.getByLogin(login));
    }

    @Test
    public void getReportsTest() throws Exception {
        User userMock1 = mock(User.class);
        User userMock2 = mock(User.class);
        List<User> users = List.of(new User[]{userMock1, userMock2});

        doReturn(users).when(userDaoMock).findAllUsers();
        Assert.assertEquals(users, userService.findAll());
    }

}
