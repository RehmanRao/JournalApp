package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests  {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    UserServiceTests() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void createUserTest(){   // but this one creating user real db


        User user = new User(new ObjectId(), "testUser", "rawPass",
                null, new ArrayList<>());
        user.setRoles(Arrays.asList("USER"));

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.createUser(user);

         assertNotNull(savedUser);
        System.out.println("one is passed");
        assertNotNull(savedUser.getPassword()); // should be encoded
        System.out.println("two passed ");
    }



}
