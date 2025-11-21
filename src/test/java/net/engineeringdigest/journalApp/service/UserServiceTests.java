//package net.engineeringdigest.journalApp.service;
//import net.engineeringdigest.journalApp.entity.User;
//import net.engineeringdigest.journalApp.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import java.util.Arrays;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//class UserServiceTests  {
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//
//    private User user(String username, String rawPassword) {
//        User u = new User();
//        u.setUserName(username);
//        u.setPassword(rawPassword);
//        return u;
//    }
//    @Test
//    void getAllUsers_returnsListFromRepository() {
//        List<User> stored = Arrays.asList(user("abdur", "x"), user("fatima", "y"));
//        when(userRepository.findAll()).thenReturn(stored);
//
//        // Act
//        List<User> result = userService.getAllUsers();
//
//        // Assert
//        assertEquals(2, result.size());
//        assertEquals("abdur", result.get(0).getUserName());
//        verify(userRepository, times(1)).findAll();
//    }
//
//
//    @Test
//    void getUserByUserName_returnsUser() {
//        // Arrange
//        User abdur = user("abdur", "secret");
//        when(userRepository.findByuserName("abdur")).thenReturn(abdur);
//
//        // Act
//        User result = userService.getUserByUserName("abdur");
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("abdur", result.getUserName());
//        verify(userRepository).findByuserName("abdur");
//    }
//
//
//
//}
