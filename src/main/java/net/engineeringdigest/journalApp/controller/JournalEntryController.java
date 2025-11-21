package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journals")
public class JournalEntryController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournals() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            User user = userService.getUserByUserName(userName);

            List<JournalEntity> journals = user.getJournalEntries();

            return ResponseEntity.ok(journals);

        }   catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }   catch (Exception e) {
            // General fallback in case of unexpected error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while fetching journals.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {
            JournalEntity journal = journalService.getJournalById(id,userName).orElse(null);
            return ResponseEntity.status(HttpStatus.OK).body(journal);  // 200 OK
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> createJournal(@RequestBody JournalEntity journal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {

            JournalEntity createdJournal = journalService.createJournal(journal, userName);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdJournal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> updateJournal(@RequestParam String id, @RequestBody JournalEntity journal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

      // we are removing findNyuser name
        try {
            JournalEntity updatedJournal = journalService.updateJournal(id, journal,userName);
            return ResponseEntity.ok(updatedJournal); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJournal(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            journalService.deleteJournal(id,userName);
            return ResponseEntity.ok("Journal with ID " + id + " deleted successfully."); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
