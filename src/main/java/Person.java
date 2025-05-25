package src.main.java;
import java.util.*;
import java.io.*;


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

    public boolean addPerson(String fileName) {
        //TODO: This method adds information about a person to a TXT file.
        //TODO: file name beign written too and if the file exists or not
        
        //the information should be inserted into a TXT file, and the addPerson function should return true.
        //Otherwise, the information should not be inserted into the TXT file, and the addPerson function should return false.
        if (personID.length() != 10) {
            return false; // not 10 characters long
        }

        if (!personID.matches("[2-9][0-9].6[A-Z]{2}")) {
            return false; // the placement of charcaters does not match expected 
        }

        for (int i = 2; i < 8; i++) {
            int count = 0;
            char c = personID.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                count ++; 
            }
            if (count < 2) {
                return false; // not enough special characters
            }
        }

        if (!address.matches("\\d+\\|[^|]+\\|[^|]+\\|Victoria\\|[^|]+")) {
            return false; // address format is incorrect
        }

        if (!birthdate.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return false; // birthdate format is incorrect
        }

        if (Integer.parseInt(birthdate.substring(6)) < 1900 || Integer.parseInt(birthdate.substring(6)) > 2025) {
            return false; // birth year is not valid
        }

        if (Integer.parseInt(birthdate.substring(3, 5)) < 1 || Integer.parseInt(birthdate.substring(3, 5)) > 12) {
            return false; // month is not valid
        }

        if (Integer.parseInt(birthdate.substring(0, 2)) < 1 || Integer.parseInt(birthdate.substring(0, 2)) > 31) {
            return false; // day is not valid
        }
       
        // txt file writing now ** NOT FINISHED **
        String fileTxt = "PersonID: " + personID + "\n" +
                            "First Name: " + firstName + "\n" +
                            "Last Name: " + lastName + "\n" +
                            "Address: " + address + "\n" +
                            "Birthdate: " + birthdate + "\n";
        System.out.println("Writing to file: \n" + fileTxt);
        
        try {
            FileWriter fileWriter = new FileWriter(fileName, true); // open in append mode
            fileWriter.write(fileTxt);
        } catch (Exception e) {
            System.out.println("Could not create or write to file: " + e.getMessage());
            return false;
        }

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