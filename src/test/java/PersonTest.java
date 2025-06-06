import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
// hi
/**
 * Test class for Person class 
 * - addDemeritPoints function
 * Contains 5 test cases for addDemeritPoints function
 * - updatePersonalDetails fnction
 * Contains 5 test cases for updatePersonalDetails function
 * - addPerson fnction
 * Contains 5 test cases for addPerson function
 * 
 * Assignment 4 - RoadRegistry Person Management System
 */
public class PersonTest {
    
    private Person person;
    private static final String PERSON_FILE = "persons.txt";
    private static final String DEMERIT_FILE = "demerit_points.txt";

    // create persons.txt and demerit_points.txt files before running tests
    static {
        try {
            Files.deleteIfExists(Paths.get(PERSON_FILE));
            Files.deleteIfExists(Paths.get(DEMERIT_FILE));

            Files.createFile(Paths.get(PERSON_FILE));
            Files.createFile(Paths.get(DEMERIT_FILE));
        } catch (IOException e) {
            System.out.println("Could not create test files: " + e.getMessage());
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
        person.addPerson(PERSON_FILE); // Add person to file first
        
        // Act: Add valid demerit points
        String result = person.addDemeritPoints("15-03-2024", 3);
        
        // Assert: Should return "Success"
        assertEquals("Success", result, "addDemeritPoints should return 'Success' for valid data");
        assertTrue(new File(DEMERIT_FILE).exists(), "Demerit points file should be created");
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
        person.addPerson(PERSON_FILE);
        
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
        person.addPerson(PERSON_FILE);
        
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
        person.addPerson(PERSON_FILE);
        
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
    
    // Helper test
    
    /**
     * Test Case 6: Test that demerit points file contains correct data
     */
    @Test
    public void testAddDemeritPoints_FileContainsCorrectData() {
        // Arrange
        person = new Person("56s_d%&fAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        person.addPerson(PERSON_FILE);
        
        // Act
        String result = person.addDemeritPoints("15-03-2024", 3);
        
        // Assert
        assertEquals("Success", result);
        
        // Check file contents
        try (BufferedReader reader = new BufferedReader(new FileReader(DEMERIT_FILE))) {
            String line = reader.readLine();
            assertNotNull(line, "Demerit file should contain at least one line");
            assertTrue(line.contains("56s_d%&fAB"), "Line should contain person ID");
            assertTrue(line.contains("15-03-2024"), "Line should contain offense date");
            assertTrue(line.contains("3"), "Line should contain points");
        } catch (IOException e) {
            fail("Should be able to read demerit points file");
        }
    }
    
    /**
     * Test Case 7: Test suspension threshold for person over 21
     */
    @Test
    public void testAddDemeritPoints_Over21SuspensionThreshold() {
        // Arrange: Create person over 21 (born 1990, about 34 years old)
        person = new Person("67s_d%&fAB", "Sarah", "Smith", "45|Collins Street|Melbourne|Victoria|Australia", "15-11-1990");
        person.addPerson(PERSON_FILE);
        
        // Act: Add points that would suspend under-21 but not over-21 (total = 8 points)
        String result1 = person.addDemeritPoints("10-01-2024", 4);
        String result2 = person.addDemeritPoints("15-01-2024", 4);
        
        // Assert: Should succeed but not be suspended (8 points < 12 for over 21)
        assertEquals("Success", result1);
        assertEquals("Success", result2);
        assertFalse(person.isSuspended(), "Person over 21 with 8 points should not be suspended");
    }
    
    /**
     * Test Case 8: Test empty/null date handling
     */
    @Test
    public void testAddDemeritPoints_EmptyDate_ReturnsFailed() {
        // Arrange
        person = new Person("56s_d%&fAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        person.addPerson(PERSON_FILE);
        
        // Act & Assert
        assertEquals("Failed", person.addDemeritPoints("", 3), "Empty date should return Failed");
        assertEquals("Failed", person.addDemeritPoints(null, 3), "Null date should return Failed");
    }
    
    /**
     * Test Case 9: Test boundary values for points
     */
    @Test
    public void testAddDemeritPoints_BoundaryPoints() {
        // Arrange
        person = new Person("56s_d%&fAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        person.addPerson(PERSON_FILE);
        
        // Act & Assert
        assertEquals("Success", person.addDemeritPoints("15-03-2024", 1), "1 point should be valid");
        assertEquals("Success", person.addDemeritPoints("16-03-2024", 6), "6 points should be valid");
        assertEquals("Failed", person.addDemeritPoints("17-03-2024", 0), "0 points should be invalid");
        assertEquals("Failed", person.addDemeritPoints("18-03-2024", 7), "7 points should be invalid");
    }


    /**
     addPerson() tests
     */

       /**
     * Test Case 1: failed personID conditions should return Failed
     * Test Data: Person("364!eyfIu", "Alice", "Smith", "14|Lonsdale street|Melbourne|Victoria|Australia", "18-10-1992")
     * Expected Result: "Failed"
     */
    @Test
    public void testAddPerson_personIDConditions_ReturnsFailed() {
        // Arrange: Create person with invalid ID (not 10 characters, does not have 2 special charcaters betwen 3-8th character and last charcter is not a capital letter)
        person = new Person("364!eyfIu", "Alice", "Smith", "14|Lonsdale street|Melbourne|Victoria|Australia", "18-10-1992");
        
        // Act: Try to add person despite personID conditions
        boolean result = person.addPerson(PERSON_FILE);
        
        // Assert: Should return false due to ID condition not being met
        assertFalse(result, "addPerson should return false for not meeting conditions of personID");
    }

    /**
     * Test Case 2: failed adress conditions should return Failed
     * Test Data: Person("364!eyfIu", "A1ice", "Smith", "14|Lonsdale street|Tasmania|Victoria|Australia", "18-10-1992")
     * Expected Result: "Failed"
     */
    @Test
    public void testAddPerson_addressConditions_ReturnsFailed() {
        // Arrange: Create person with invalid address (contains number in first character of first name)
        person = new Person("364!ey&yIU", "A1ice", "Smith", "14|Lonsdale street|Melbourne|Tasmania|Australia", "18-10-1992");
        
        // Act: Try to add person despite address conditions
        boolean result = person.addPerson(PERSON_FILE);
        
        // Assert: Should return false due to address condition not being met
        assertFalse(result, "addPerson should return false for not meeting conditions of address");
    }

    /**
     * Test Case 3: failed birthdate conditions should return Failed
     * Test Data: Person("364!ey&yIU", "Alice", "Smith", "14|Lonsdale street|Melbourne|Victoria|Australia", "10-18-2025")
     * Expected Result: "Failed"
     */
    @Test
    public void testAddPerson_birthdateConditions_ReturnsFailed() {
        // Arrange: Create person with invalid birthdate (day and month are swapped)
        person = new Person("364!ey&yIU", "Alice", "Smith", "14|Lonsdale street|Melbourne|Victoria|Australia", "10-18-2025");
        
        // Act: Try to add person despite birthdate conditions
        boolean result = person.addPerson(PERSON_FILE);
        
        // Assert: Should return false due to birthdate condition not being met
        assertFalse(result, "addPerson should return false for not meeting conditions of birthdate");
    }

    /**
     * Test Case 4: cehck that a valid person can be added successfully to the txt file
     * Test Data: Person("364!ey&yIU", "Alice", "Smith", "14|Lonsdale street|Melbourne|Victoria|Australia", "18-10-1992")
     * Expected Result: "Success"
     */
    @Test
    public void testAddPerson_ValidData_ReturnsSuccess() {
        // Arrange: Create person with valid data
        person = new Person("364!ey&yIU", "Alice", "Smith", "14|Lonsdale street|Melbourne|Victoria|Australia", "18-10-1992");
        
        // Act: Try to add person with valid data
        boolean result = person.addPerson(PERSON_FILE);
        
        // Assert: Should return true indicating success
        assertTrue(result, "addPerson should return true for valid person data");
        
        // Verify that the person was added to the file
        assertFalse(person.personExists(PERSON_FILE));
    }

    /**
     * Test Case 5: Check that a person with more than 10 characters in personID fails to be added
     * Test Data: Person("364!y&yIU", "Charlie", "Brown", "22|Collins street|Melbourne|Victoria|Australia", "20-05-1990")
     * Expected Result: "Failed"
     */
    @Test
    public void testAddPerson_TooLongPersonID_ReturnsFailed() {
        // Arrange: Create person with personID longer than 10 characters
        person = new Person("364!y&yIU", "Charlie", "Brown", "22|Collins street|Melbourne|Victoria|Australia", "20-05-1990");
        // Act: Try to add person with too long personID
        boolean result = person.addPerson(PERSON_FILE);
        // Assert: Should return false due to personID length condition not being met
        assertFalse(result, "addPerson should return false for personID longer than 10 characters");
        // Verify that the person was NOT added to the file
        // assertFalse(Files.exists(Paths.get(PERSON_FILE)), "Person file should NOT exist after trying to add invalid person");
        assertFalse(person.personExists(PERSON_FILE));
    }   

 /**
     * updatePersonalDetails Tests
     */
    
    /**
     * Test Case 1: Check that an individual under 18 is unable to change address.
     * Test Data:
     *   - Person: ("29a#x!qzYT", "Leo", "Nguyen", "101|Elm Street|Melbourne|Victoria|Australia", "15-11-2010")
     *   - Update: change address only
     * Expected Result: false (update should fail)
     * @throws InterruptedException 
     */
    @Test
    public void testUpdatePersonalDetails_Under18CannotChangeAddress() throws InterruptedException {
        Person person = new Person("29a#x!qzYT", "Leo", "Nguyen", "101|Elm Street|Melbourne|Victoria|Australia", "15-11-2010"); //Under 18 years
        person.addPerson(PERSON_FILE);
        Thread.sleep(500);

        assertFalse(person.updatePersonalDetails(
            "29a#x!qzYT",
            "29a#x!qzYT",
            "Leo",
            "Nguyen",
            "202|Maple Avenue|Melbourne|Victoria|Australia",    // Attempt to change address
            "15-11-2010"
        ));

        assertEquals("101|Elm Street|Melbourne|Victoria|Australia", person.getAddress()); // Check to make sure that the address is not changed
    }

    /**
     * Test Case 2: Changing both birthdate and another field (last name) should fail.
     * Test Data:
     *   - Person: ("35b$e@rjLK", "Allie", "Smith", "77|River Road|Melbourne|Victoria|Australia", "01-01-2000")
     *   - Update: change last name and birthdate
     * Expected Result: false (update should fail)
     * @throws InterruptedException 
     */
    @Test
    public void testUpdatePersonalDetails_BirthdateAndOtherChangeAttempt() throws InterruptedException {
        Person person = new Person("35b$e@rjLK", "Allie", "Smith", "77|River Road|Melbourne|Victoria|Australia", "01-01-2000");
        person.addPerson(PERSON_FILE);
        Thread.sleep(500);

        boolean result = person.updatePersonalDetails(
            "35b$e@rjLK",
            "35b$e@rjLK",
            "Allie",
            "Johnson",      // Attempt to change last name
            "77|River Road|Melbourne|Victoria|Australia",
            "02-02-2001"   // Attempt to change birthday
        );

        assertFalse(result);
        assertEquals("Smith", person.getLastName());        // Check if the last name has changed (it shouldn't)
        assertEquals("02-02-2001", person.getBirthdate());  // Check to see if the birthday was updated
    }

    /**
     * Test Case 3: Check that if the first digit of the ID is even, the ID cannot be changed.
     * Test Data:
     *   - Person: ("46c@d%uqHE", "Mohammed", "Ali", "88|Oak Lane|Melbourne|Victoria|Australia", "15-11-1990");
     *   - Update: change ID only
     * Expected Result: false (update should fail)
     * @throws InterruptedException 
     */
    @Test
    public void testUpdatePersonalDetails_EvenFirstDigitCannotChangeID() throws InterruptedException {
        Person person = new Person("46c@d%uqHE", "Mohammed", "Ali", "88|Oak Lane|Melbourne|Victoria|Australia", "15-11-1990");;
        person.addPerson(PERSON_FILE);
        Thread.sleep(500);

        assertFalse(person.updatePersonalDetails(
            "46c@d%uqHE",
            "57z#h^klRP",           // Attempt to update person ID
            "Mohammed",
            "Ali",
            "88|Oak Lane|Melbourne|Victoria|Australia",
            "15-11-1990"
        ));

        assertEquals("46c@d%uqHE", person.getPersonID());
    }

    /**
     * Test Case 4: Check that update returns true if full name is changed
     * Test Data:
     *   - Person: ("78s#z^*mCD", "Katherine", "Jobs", "234|Kingsway Rd|Melbourne|Victoria|Australia", "20-10-2004")
     *   - Person2: ("59s_d%&fAB", "John", "Doe", "123|Main St|Melbourne|Victoria|Australia", "15-11-1990")
     *   - Update Person2: ("56s_d%&fAB", "Veronica", "Donovan", "123|Main St|Melbourne|Victoria|Australia", "15-11-1990")
     * Expected Result: true (update should still work)
     * @throws InterruptedException 
     */
    @Test
    public void testUpdatePersonalDetails_ChangeFullNameMultipleEntries() throws InterruptedException {
        Person person = new Person("78s#z^*mCD", "Katherine", "Jobs", "234|Kingsway Rd|Melbourne|Victoria|Australia", "20-10-2004");
        person.addPerson(PERSON_FILE);
        Thread.sleep(500);

        Person person2 = new Person("59s_d%&fAB", "John", "Doe", "123|Main St|Melbourne|Victoria|Australia", "15-11-1990");
        person2.addPerson(PERSON_FILE);
        Thread.sleep(500);

        assertTrue(person2.updatePersonalDetails(
            "59s_d%&fAB",
            "59s_d%&fAB",
            "Veronica",     // Attempt to change the first name of John Doe
            "Donovan",       // Attempt to change the last name of John Doe
            "123|Main St|Melbourne|Victoria|Australia",
            "15-11-1990"
        ));

        assertEquals("Veronica", person2.getFirstName());
        assertEquals("Donovan", person2.getLastName());
    }

    /**
     * Test Case 5: Check with invalid address format (testing addPerson formats).
     * Test Data:
     *   - Person("45b$e@rjLK", "Fatima", "Rahman", "999|Pine Crescent|Melbourne|Victoria|Australia", "15-11-1990")
     *   - Update: invalid address format
     * Expected Result: false (update should fail)
     * @throws InterruptedException 
     */
    @Test
    public void testUpdatePersonalDetails_InvalidAddressFormat() throws InterruptedException {
        Person person = new Person("45b$e@rjLK", "Fatima", "Rahman", "999|Pine Crescent|Melbourne|Victoria|Australia", "15-11-1990");
        person.addPerson(PERSON_FILE);
        Thread.sleep(500);

        assertFalse(person.updatePersonalDetails(
            "45b$e@rjLK",
            "45b$e@rjLK",
            "Fatima",
            "Rahman",
            "Just an address with no format",       // Attempt to input an illegally formatted address
            "15-11-1990"
        ));

        assertEquals("999|Pine Crescent|Melbourne|Victoria|Australia", person.getAddress());
    }
}