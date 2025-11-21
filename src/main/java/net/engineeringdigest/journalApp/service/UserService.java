package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserByUserName(String userName) {
        return userRepository.findByuserName(userName);
    }
    // just added
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepository.save(user);
    }
    public User updateUser(String username, User inputUser) {
        User existingUser = userRepository.findByuserName(username);
        if (existingUser == null) {
            throw new RuntimeException("User with username " + username + " not found.");
        }

        // Only update password if a new one is provided
        if (inputUser.getPassword() != null && !inputUser.getPassword().isEmpty()) {
            String rawPassword = inputUser.getPassword();
            existingUser.setPassword(passwordEncoder.encode(rawPassword));
        }

        // Update other fields as needed
        existingUser.setRoles(Arrays.asList("USER"));
        existingUser.setJournalEntries(inputUser.getJournalEntries());
        return userRepository.save(existingUser);
    }

    public void deleteUserByUserName(String userName) {

         userRepository.deleteByUserName(userName);

    }}

/*go to:
View → Appearance → Navigation Bar
*/
