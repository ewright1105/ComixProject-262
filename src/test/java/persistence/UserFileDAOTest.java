package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Collection;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.opencsv.exceptions.CsvException;

import model.Comic;
import model.ComixUser;

public class UserFileDAOTest {

    private UserDAO userDAO;
    private String testFilename = "data/testForUserComics.csv";

    @BeforeEach
    public void setUp() throws IOException, CsvException {
        userDAO = new UserFileDAO(testFilename);
    }

    @Test
    public void createUserTest() throws IOException, CsvException {
        // Setup & Invoke
        String username = "testUser";
        userDAO.createUser(username);
        ComixUser newUser = userDAO.getUserWithName(username);


        // Analyze
        assertEquals("testUser", newUser.getUsername());
    }

    @Test
    public void addToPersonalCollectionTest() throws IOException, CsvException {
        // Setup
        ComixUser newUser = userDAO.createUser("testUser");
        Comic comic = new Comic("Marvel", "Spider-Man", 1, "1", "Jan. 1963");

        // Invoke
        ComixUser updatedUser = userDAO.addToPersonalCollection(newUser, comic);

        // Analyze
        assertNotNull(updatedUser);
        Collection<Comic> personalCollection = updatedUser.getPersonalCollection().getComics();
        assertNotNull(personalCollection);
        assertEquals(1, personalCollection.size());
    }

}
