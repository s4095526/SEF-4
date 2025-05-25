package src.test.java;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Person class - addDemeritPoints function
 * Contains 5 test cases for addDemeritPoints function
 * Assignment 4 - RoadRegistry Person Management System
 */
public class PersonTest {
    
    private Person person;
    private static final String PERSON_FILE = "persons.txt";
    private static final String DEMERIT_FILE = "demerit_points.txt";
    
    @BeforeEach
    public void setUp() {
        // Clean up files before each test
        deleteFileIfExists(PERSON_FILE);
        deleteFileIfExists(DEMERIT_FILE);
        
        // Initialise person object for testing
        person = new Person();
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up files after each test
        deleteFileIfExists(PERSON_FILE);
        deleteFileIfExists(DEMERIT_FILE);
    }
    
    /**
     * Helper method to delete files if they exist
     * @param filename name of file to delete
     */
    private void deleteFileIfExists(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            System.out.println("Could not delete file: " + filename);
        }
    }
    
    // TEST CASES FOR addDemeritPoints() FUNCTION
    
    /**
     * Test Case 1: Valid demerit points data should return Success
     * Test Data: Person("56s_d%&fAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990")
     * OffenseDate: "15-03-2024", Points: 3
     * Expected Result: "Success"
     */
    @Test
    public void testAddDemeritPoints_ValidData_ReturnsSuccess() {
        // Arrange: Create person with valid data and add to system
        person = new Person("56s_d%&fAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        person.addPerson(); // Add person to file first
        
        // Act: Add valid demerit points
        String result = person.addDemeritPoints("15-03-2024", 3);
        
        // Assert: Should return "Success"
        assertEquals("Success", result, "addDemeritPoints should return 'Success' for valid data");
    }
    
    /**
     * Test Case 2: Invalid offense date format should return Failed
     * Test Data: Person("78@#$%^ZAB", "Robert", "Martinez", "82|St Kilda Road|Melbourne|Victoria|Australia", "07-02-1985")
     * OffenseDate: "2024-03-15" (YYYY-MM-DD format instead of DD-MM-YYYY), Points: 4
     * Expected Result: "Failed"
     */
    @Test
    public void testAddDemeritPoints_InvalidDateFormat_ReturnsFailed() {
        // Arrange: Create person and add to system
        person = new Person("78@#$%^ZAB", "Robert", "Martinez", "82|St Kilda Road|Melbourne|Victoria|Australia", "07-02-1985");
        person.addPerson();
        
        // Act: Try to add demerit points with invalid date format (YYYY-MM-DD instead of DD-MM-YYYY)
        String result = person.addDemeritPoints("2024-03-15", 4);
        
        // Assert: Should return "Failed"
        assertEquals("Failed", result, "addDemeritPoints should return 'Failed' for invalid date format");
    }
    
    /**
     * Test Case 3: Invalid demerit points range should return Failed
     * Test Data: Person("89#$%&*CDE", "Jennifer", "Rodriguez", "16|Toorak Road|Melbourne|Victoria|Australia", "24-12-1988")
     * OffenseDate: "20-06-2024", Points: 7 (outside valid range 1-6)
     * Expected Result: "Failed"
     */
    @Test
    public void testAddDemeritPoints_InvalidPointsRange_ReturnsFailed() {
        // Arrange: Create person and add to system
        person = new Person("89#$%&*CDE", "Jennifer", "Rodriguez", "16|Toorak Road|Melbourne|Victoria|Australia", "24-12-1988");
        person.addPerson();
        
        // Act: Try to add demerit points outside valid range (7 > 6)
        String result = person.addDemeritPoints("20-06-2024", 7);
        
        // Assert: Should return "Failed"
        assertEquals("Failed", result, "addDemeritPoints should return 'Failed' for points outside 1-6 range");
    }
    
    /**
     * Test Case 4: Person under 21 with total points >6 should be suspended
     * Test Data: Person("92!@#$%FGH", "Kevin", "Lopez", "38|Camberwell Road|Melbourne|Victoria|Australia", "15-08-2008")
     * Age: ~16 years old (under 21)
     * OffenseDate1: "10-01-2024", Points1: 4
     * OffenseDate2: "15-02-2024", Points2: 3
     * Total Points: 7 (>6 for under 21)
     * Expected Result: Both calls return "Success" and isSuspended = true
     */
    @Test
    public void testAddDemeritPoints_Under21Suspension_SetsCorrectSuspension() {
        // Arrange: Create person under 21 (born 2008, about 16 years old)
        person = new Person("92!@#$%FGH", "Kevin", "Lopez", "38|Camberwell Road|Melbourne|Victoria|Australia", "15-08-2008");
        person.addPerson();
        
        // Act: Add demerit points that should trigger suspension (4 + 3 = 7 points > 6 for under 21)
        String result1 = person.addDemeritPoints("10-01-2024", 4);
        String result2 = person.addDemeritPoints("15-02-2024", 3);
        
        // Assert: Both operations should succeed and person should be suspended
        assertEquals("Success", result1, "First demerit points addition should succeed");
        assertEquals("Success", result2, "Second demerit points addition should succeed");
        assertTrue(person.isSuspended(), "Person under 21 with >6 points should be suspended");
    }
    
    /**
     * Test Case 5: Non-existent person should return Failed
     * Test Data: Person("83*&%$#IJK", "Amanda", "Thomas", "72|High Street|Melbourne|Victoria|Australia", "09-10-1991")
     * Note: Person object created but NOT added to file
     * OffenseDate: "25-07-2024", Points: 2
     * Expected Result: "Failed"
     */
    @Test
    public void testAddDemeritPoints_NonExistentPerson_ReturnsFailed() {
        // Arrange: Create person object but DON'T add to file
        person = new Person("83*&%$#IJK", "Amanda", "Thomas", "72|High Street|Melbourne|Victoria|Australia", "09-10-1991");
        // Note: person.addPerson() is NOT called, so person doesn't exist in file
        
        // Act: Try to add demerit points for person not in file
        String result = person.addDemeritPoints("25-07-2024", 2);
        
        // Assert: Should return "Failed"
        assertEquals("Failed", result, "addDemeritPoints should return 'Failed' for non-existent person");
    }
}