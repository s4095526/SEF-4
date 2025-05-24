package src.main.java;
import java.util.HashMap;
import java.util.Date;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints; // A variable that holds the demerit points with the offense day
    private boolean isSuspended;

    // Constructor
    public Person(String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
        this.demeritPoints = new HashMap<>();
        this.isSuspended = false;
    }

    // Default constructor
    public Person() {
        this.demeritPoints = new HashMap<>();
        this.isSuspended = false;
    }

    public boolean addPerson() {
        //TODO: This method adds information about a person to a TXT file.
        //Condition 1: PersonID should be exactly 10 characters long;
        //the first two characters should be numbers between 2 and 9, there should be at least two special characters between characters 3 and 8,
        //and the last two characters should be upper case letters (A - Z). Example: "56s_d%&fAB"
        //Condition 2: The address of the Person should follow the following format: Street Number|Street|City|State|Country.
        //The State should be only Victoria. Example: 32|Highland Street|Melbourne|Victoria|Australia.
        //Condition 3: The format of the birth date of the person should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        //Instruction: If the Person's information meets the above conditions and any other conditions you may want to consider,
        //the information should be inserted into a TXT file, and the addPerson function should return true.
        //Otherwise, the information should not be inserted into the TXT file, and the addPerson function should return false.
        return true;
    }
    
    public boolean updatePersonalDetails() {
        //TODO: This method allows updating a given person's ID, firstName, lastName, address and birthday in a TXT file.
        //Changing personal details will not affect their demerit points or the suspension status.
        // All relevant conditions discussed for the addPerson function also need to be considered and checked in the updatePerson function.
        //Condition 1: If a person is under 18, their address cannot be changed.
        //Condition 2: If a person's birthday is going to be changed, then no other personal detail (i.e, person's ID, firstName, lastName, address) can be changed.
        //Condition 3: If the first character/digit of a person's ID is an even number, then their ID cannot be changed.
        //Instruction: If the Person's updated information meets the above conditions and any other conditions you may want to consider,
        //the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true.
        //Otherwise, the Person's information should not be updated in the TXT file, and the updatePersonalDetails function should return false.
        return true;
    }

    public String addDemeritPoints() {
        //TODO: This method adds demerit points for a given person in a TXT file.
        //Condition 1: The format of the date of the offense should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        //Condition 2: The demerit points must be a whole number between 1-6
        //Condition 3: If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
        //If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.
        //Instruction: If the above conditions and any other conditions you may want to consider are met, the demerit points for a person should be inserted into the TXT file,
        //and the addDemeritPoints function should return "Success". Otherwise, the addDemeritPoints function should return "Failed".
        return "Success";
    }

    // Getters and Setters
    public String getPersonID() { return personID; }
    public void setPersonID(String personID) { this.personID = personID; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    
    public boolean isSuspended() { return isSuspended; }
    public void setSuspended(boolean suspended) { isSuspended = suspended; }
    
    public HashMap<Date, Integer> getDemeritPoints() { return demeritPoints; }
}