package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;



    // ✅ Update journal
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        System.out.println("put");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {
            User updatedUser = userService.updateUser(userName,user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    // ✅ Get all journals
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users= userService.getAllUsers();
        //return ResponseEntity.ok(journals);  // 200 OK
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // ✅ Get journal by id
    @GetMapping("/by-username/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with username: " + userName);
        }
    }


    // ✅ Create a new journal




    // ✅ Delete journal
    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            userService.deleteUserByUserName(userName);
            return ResponseEntity.ok("User with username '" + userName + "' deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}


/* You can later replace all these try-catch blocks by writing a @ControllerAdvice with a global exception handler.*/