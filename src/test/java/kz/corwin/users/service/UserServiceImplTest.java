package kz.corwin.users.service;

import kz.corwin.users.entity.Subscription;
import kz.corwin.users.entity.SubscriptionId;
import kz.corwin.users.entity.User;
import kz.corwin.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PersistenceException;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {

    @Test
    void saveUserSucces() {
        User user1 = new User("Ivan","Ivanov");
        User savedUser1 = new User(1,"Ivan","Ivanov");
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.save(user1)).thenReturn(savedUser1);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        String result = userService.saveUser(user1);

        Assertions.assertEquals("Пользователь Ivanov Ivan успешно добавлен",result);
    }

    @Test
    void saveUserError()  {
        User user = new User("Ivan","Ivanov");
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserServiceImpl(userRepository);
        when(userRepository.save(user)).thenThrow(new PersistenceException("User Not Found"));
        Exception exception = assertThrows(PersistenceException.class, () -> {
            userService.saveUser(user);
        });
        assertTrue(exception.getMessage().contains("User Not Found"));
    }

    @Test
    void getUsers() {
        User user1 = new User("Ivan","Ivanov");
        User user2 = new User("Fedor","Fedorov");
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user1,user2));
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        List<User> users = userService.getUsers();
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getUsername(), "Ivan");
        assertEquals(users.get(0).getSurname(), "Ivanov");
        assertEquals(users.get(1).getUsername(), "Fedor");
        assertEquals(users.get(1).getSurname(), "Fedorov");
    }

    @Test
    void getUser() {
        User user = new User(1,"Ivan","Ivanov");
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        User gettingUser = userService.getUser(1);

        assertEquals(gettingUser.getUsername(),"Ivan");
        assertEquals(gettingUser.getSurname(),"Ivanov");

    }

    @Test
    void deleteUser() {
        User user = new User(1,"Ivan","Ivanov");
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        userService.deleteUser(1);
        verify(userRepository, times(1)).deleteById(user.getId());
        ArgumentCaptor<Integer> orderArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(userRepository).deleteById(orderArgumentCaptor.capture());
        Integer userIdDeleted = orderArgumentCaptor.getValue();
        assertNotNull(userIdDeleted);
        assertEquals(1, userIdDeleted);
    }

    @Test
    void updateUser() {
        User user = new User(1,"Ivan","Ivanov");
        user.setDeleted(false);
        User updatedUser = new User(1,"Fedor","Fedorov");
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.existsById(1)).thenReturn(true);

        UserServiceImpl userService = new UserServiceImpl(userRepository);

        user.setUsername("Fedor");
        user.setSurname("Fedorov");

        String result = userService.updateUser(1,user);

        Assertions.assertEquals("Пользователь Fedorov успешно изменен",result);
    }
}