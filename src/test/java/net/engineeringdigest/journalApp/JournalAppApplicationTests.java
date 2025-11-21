package net.engineeringdigest.journalApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JournalAppApplicationTests {

    @Test
    void additionTest() {
        int result = 2 + 3;
        assertEquals(5, result, "2 + 3 should be 5");
    }

}
