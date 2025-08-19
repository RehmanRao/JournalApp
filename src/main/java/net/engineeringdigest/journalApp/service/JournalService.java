package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalService {
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public User getUserByUserName(String username) {
        return userRepository.findByuserName(username);
    }

    public Optional<JournalEntity> getJournalById(String id, String userName) {
        User user = userRepository.findByuserName(userName);
        if (user == null) {
            throw new RuntimeException("User with username '" + userName + "' not found.");
        }

        Optional<JournalEntity>journalEntityOptional=journalRepository.findById(id);
        if(journalEntityOptional.isPresent()){
            return journalRepository.findById(id);
        }
        throw new RuntimeException("Journal entry with ID " + id + " not found.");
    }

    public JournalEntity createJournal(JournalEntity journal, String username) {
        User user = userRepository.findByuserName(username);

        if (user == null) {
            throw new RuntimeException("User with username '" + username + "' not found.");
        }

        JournalEntity savedJournal = journalRepository.save(journal);
        user.getJournalEntries().add(savedJournal);
        userRepository.save(user);

        return savedJournal;
    }

    public JournalEntity updateJournal(String id, JournalEntity journal, String userName) {
        User user= userService.getUserByUserName(userName);
            if (user == null) {
                throw new RuntimeException("User with username '" + userName + "' not found.");
            }
        Optional<JournalEntity> existingJournalOpt = journalRepository.findById(id);

        if(existingJournalOpt.isPresent()){
            JournalEntity existingJournal = existingJournalOpt.get();
            existingJournal.setTitle(journal.getTitle());
            existingJournal.setContent(journal.getContent());
           // user.getJournalEntries(existingJournal);
            return journalRepository.save(existingJournal);
        }
        else {
            // Throw or handle not found case
            throw new RuntimeException("Journal entry with ID " + id + " not found.");
        }
    }

    public void deleteJournal(String id, String userName) {
        User user= userService.getUserByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User with username '" + userName + "' not found.");
        }
        Optional<JournalEntity>journalEntityOptional= journalRepository.findById(id);
        if(journalEntityOptional.isPresent()){
         ArrayList<JournalEntity> updatedEntry = (ArrayList<JournalEntity>) user.getJournalEntries().stream().filter(j-> j.getId()!=id).collect(Collectors.toList());
        user.setJournalEntries( updatedEntry);
            journalRepository.deleteById(id);
        }
        else
            throw new RuntimeException("Journal entry with ID " + id + " not found.");
    }
}
