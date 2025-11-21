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
// here is a cath you have id ,,, first you need to check that userr has id or NOT then proceess in journalRepositiory
        Optional<JournalEntity>journalEntityOptional=journalRepository.findById(id);
        if(journalEntityOptional.isPresent()){
            return journalRepository.findById(id);
        }
        throw new RuntimeException("Journal entry with ID " + id + " not found.");
    }

    public JournalEntity createJournal(JournalEntity journal, String username) {
        User user = userRepository.findByuserName(username);
        /* user have own entry and journal entry ref*/

        if (user == null) {   // we will remove if block when I will learn java 8
            throw new RuntimeException("User with username '" + username + "' not found.");
        }

        JournalEntity savedJournal = journalRepository.save(journal);   // save in journal repository entry with diff id

        user.getJournalEntries().add(savedJournal);  // this is a list that is why added
        // suppose here i can save null user intentionally ... so it will be the conflict .. our property ..
        userRepository.save(user);
        return savedJournal;
    }

    public JournalEntity updateJournal(String id, JournalEntity journal, String userName) {
        userService.getUserByUserName(userName); // throws if not found
        // first we need to check that user Id is perdent in user object than traverse ans this work we can do in controller
        JournalEntity existing = journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal entry with ID " + id + " not found."));
        existing.setTitle(journal.getTitle());
        existing.setContent(journal.getContent());
        return journalRepository.save(existing);
    }

    public void deleteJournal(String id, String userName) {
        User user= userService.getUserByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User with username '" + userName + "' not found.");
        }
        Optional<JournalEntity>journalEntityOptional= journalRepository.findById(id);
        if(journalEntityOptional.isPresent()){
         ArrayList<JournalEntity> updatedEntry =
                 (ArrayList<JournalEntity>) user.getJournalEntries().stream().filter(j-> j.getId()!=id).collect(Collectors.toList());
        user.setJournalEntries( updatedEntry);
            journalRepository.deleteById(id);
        }
        else
            throw new RuntimeException("Journal entry with ID " + id + " not found.");
    }
}
