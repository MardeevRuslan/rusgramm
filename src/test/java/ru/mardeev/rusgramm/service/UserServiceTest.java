package ru.mardeev.rusgramm.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import ru.mardeev.rusgramm.entity.User;
import ru.mardeev.rusgramm.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @Test
    public void testIsValidLogin_ValidCredentials() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User(username, password, "USER");

        when(userRepository.findById(username)).thenReturn(Optional.of(user));

        assertTrue(userService.isValidLogin(username, password));
    }

    @Test
    public void testIsValidLogin_InvalidCredentials() {
        String username = "testUser";
        String password = "testPassword";

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertFalse(userService.isValidLogin(username, password));
    }

    @Test
    public void testCreateUser_UserDoesNotExist() {
        String username = "testUser";
        String password = "testPassword";

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertTrue(userService.createUser(username, password));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        String username = "testUser";
        String password = "testPassword";

        when(userRepository.findById(username)).thenReturn(Optional.of(new User()));

        assertFalse(userService.createUser(username, password));
        verify(userRepository, never()).save(any(User.class));
    }

}

