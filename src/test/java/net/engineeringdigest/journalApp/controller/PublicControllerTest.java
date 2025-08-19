package net.engineeringdigest.journalApp.controller;



import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PublicControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private PublicController publicController;

    PublicControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = new User(new ObjectId(), "testUser", "password", null, new java.util.ArrayList<>());
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = publicController.createUser(user);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("testUser", response.getBody().getUserName());
    }
}
