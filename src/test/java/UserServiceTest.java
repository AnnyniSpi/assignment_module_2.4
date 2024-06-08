import dev.annyni.dao.UserDao;
import dev.annyni.dao.impl.UserDaoImpl;
import dev.annyni.entity.User;
import dev.annyni.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserDao userDao;
    private UserService userService;
    private User testUser;

    @BeforeEach
    void init() {
        userDao = Mockito.mock(UserDaoImpl.class);
        userService = new UserService(userDao);

        testUser = User.builder()
                .id(1)
                .name("Test")
                .events(new ArrayList<>())
                .build();
    }

    @Test
    void createTest() {
        Mockito.when(userDao.save(any(User.class))).thenReturn(testUser);

        User user = userService.create(testUser);

        Mockito.when(userDao.findByID(anyInt())).thenReturn(Optional.ofNullable(user));
        Optional<User> byId = userService.getById(user.getId());

        System.out.println();

        assertEquals(1, byId.get().getId());
        assertEquals("Test", byId.get().getName());

        verify(userDao, times(1)).save(testUser);
    }

    @Test
    void updateTest() {
        User updateUser = User.builder()
                .id(1)
                .name("Test")
                .build();

        Mockito.when(userDao.update(any(User.class))).thenReturn(updateUser);

        User user = userService.update(testUser);

        assertEquals(1, user.getId());
        assertEquals("Update", user.getName());

        verify(userDao, times(1)).save(updateUser);
    }

    @Test
    void findByIdTest(){
        Mockito.when(userDao.findByID(anyInt())).thenReturn(Optional.ofNullable(testUser));

        Optional<User> byId = userService.getById(1);

        assertNotNull(byId);
        assertEquals("Test", byId.get().getName());
    }

    @Test
    void getAllTest(){
        List<User> users = new ArrayList<>();
        users.add(testUser);

        Mockito.when(userDao.findAll()).thenReturn(users);

        List<User> all = userService.getAll();

        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals("Test", all.get(0).getName());
    }

    @Test
    void deleteTest(){
        Mockito.when(userDao.delete(anyInt())).thenReturn(true);
        boolean result = userService.delete(1);
        assertTrue(result);
        verify(userDao, times(1)).delete(1);

    }
}
