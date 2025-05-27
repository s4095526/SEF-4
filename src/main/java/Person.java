import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.*;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints; // A variable that holds the demerit points with the offense day
    private boolean isSuspended;
    
    // Constants for file names and date format
    private static final String PERSON_FILE = "persons.txt";
    private static final String DEMERIT_FILE = "demerit_points.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    // Constructor
    public Person(String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
        this.demeritPoints = new HashMap<>();
        this.isSuspended = false;
        DATE_FORMAT.setLenient(false); // Strict date parsing
    }

    // Default constructor
    public Person() {
        this.demeritPoints = new HashMap<>();
        this.isSuspended = false;
        DATE_FORMAT.setLenient(false);
    }

    public boolean addPerson(String fileName) {
        //TODO: This method adds information about a person to a TXT file.
        //TODO: file name beign written too and if the file exists or not
        
        //the information should be inserted into a TXT file, and the addPerson function should return true.
        //Otherwise, the information should not be inserted into the TXT file, and the addPerson function should return false.
        // Validate personID: exactly 10 characters
        if (personID.length() != 10) {
            return false;
        }
        // First two characters should be digits between 2 and 9
        if (!Character.isDigit(personID.charAt(0)) || !Character.isDigit(personID.charAt(1))) {
            return false;
        }
        int firstDigit = Character.getNumericValue(personID.charAt(0));
        int secondDigit = Character.getNumericValue(personID.charAt(1));
        if (firstDigit < 2 || firstDigit > 9 || secondDigit < 2 || secondDigit > 9) {
            return false;
        }
        // At least two special characters between characters 3 and 8 (index 2 to 7)
        int specialCount = 0;
        for (int i = 2; i < 8; i++) {
            char c = personID.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                specialCount++;
            }
        }
        if (specialCount < 2) {
            return false;
        }
        // Last two characters should be uppercase letters (A-Z)
        if (!Character.isUpperCase(personID.charAt(8)) || !Character.isUpperCase(personID.charAt(9))) {
            return false;
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
        
        String filetxt = personID + "|" + firstName + "|" + lastName + "|" + address + "|" + birthdate + "|" + isSuspended + "\n";
        
        try {
            try (FileWriter fileWriter = new FileWriter(fileName, true)) {
                fileWriter.write(filetxt);
            }
        } catch (Exception e) {
            System.out.println("Could not create or write to file: " + e.getMessage());
            return false;
        }

        return true;
    }
    
    public boolean updatePersonalDetails(String newID, String newFirstName, String newLastName, String newAddress, String newBirthdate) {
        //This method allows updating a given person's ID, firstName, lastName, address and birthday in a TXT file.
        //Changing personal details will not affect their demerit points or the suspension status.
        // All relevant conditions discussed for the addPerson function also need to be considered and checked in the updatePerson function.
        //Condition 1: If a person is under 18, their address cannot be changed.
        //Condition 2: If a person's birthday is going to be changed, then no other personal detail (i.e, person's ID, firstName, lastName, address) can be changed.
        //Condition 3: If the first character/digit of a person's ID is an even number, then their ID cannot be changed.
        //Instruction: If the Person's updated information meets the above conditions and any other conditions you may want to consider,
        //the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true.
        //Otherwise, the Person's information should not be updated in the TXT file, and the updatePersonalDetails function should return false.
        if (!personExists(this.personID)){
            return false;
        }

        //Condition 2: Birthdate changed, no other fields can change
        if (!this.birthdate.equals(newBirthdate)){
            if (!newID.equals(this.personID) || !newFirstName.equals(this.firstName) || !newLastName.equals(this.lastName) || !newAddress.equals(this.address)){
                this.birthdate = newBirthdate;
                return false;
            } 
        }

        //Condition 3: If first digit of ID is even, ID cannot be changed
        if (!this.personID.equals(newID)){
            char firstChar = this.personID.charAt(0);
            if (Character.isDigit(firstChar) && Integer.parseInt(String.valueOf(firstChar)) % 2 == 0){
                return false;
            }
        }

        //Condition 1: If under 18, address cannot be changed
        int age = calculateAge(this.birthdate);
        if (age < 18 && !this.address.equals(newAddress)){
            return false;
        }

        //Validate all new fields meet the same conditions as addPerson
        //Validate newID
        if (newID.length() != 10){
            return false;
        }
        if (!newID.matches("[2-9][0-9].*[^a-zA-Z0-9].*[^A-Z0-9].*[A-Z]{2}")){
            return false;
        }
        
        // Validate new address format
        if (!newAddress.matches("\\d+\\|[^|]+\\|[^|]+\\|Victoria\\|[^|]+")) {
            return false;
        }

        //Validate new birthdate format
        if (!newBirthdate.matches("\\d{2}-\\d{2}-\\d{4}")){
            return false;
        }

        try {
            int day = Integer.parseInt(newBirthdate.substring(0, 2));
            int month = Integer.parseInt(newBirthdate.substring(3, 5));
            int year = Integer.parseInt(newBirthdate.substring(6));

            if (year < 1900 || year > 2025) return false;
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;

            //Additional validations
            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.set(year, month - 1, day);
            cal.getTime(); //check if date is invalid
        } catch (Exception e){
            return false;
        }

        //All validations passed
        String originalID = this.personID;
        this.personID = newID;
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.address = newAddress;
        this.birthdate = newBirthdate;

        // Update the file
        return updatePersonInFile(originalID);
    }

    private boolean updatePersonInFile (String originalID) {
        try {
            // read all persons
            List<String> lines = new ArrayList<>();
            boolean personFound = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(PERSON_FILE))) {
                String line;
                while ((line = reader.readLine()) != null){
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6 && parts[0].equals(originalID)) {
                        //Update record
                        line = String.join("|", this.personID, this.firstName, this.lastName, this.address, this.birthdate, String.valueOf(this.isSuspended));
                        personFound = true;
                    }
                    lines.add(line);
                }
            }
            if (!personFound) {
                return false;
            }

            // Wrtie all persons back to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(PERSON_FILE))){
                for (String line : lines){
                    writer.println(line);
                }
            }

            return true;
        } catch (IOException e) {
            System.out.println("Error updating person details: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds demerit points for a person to the TXT file
     * @param offenseDate date of offense in DD-MM-YYYY format
     * @param points demerit points (must be between 1-6)
     * @return "Success" if added successfully, "Failed" otherwise
     */
    public String addDemeritPoints(String offenseDate, int points) {
        //TODO: This method adds demerit points for a given person in a TXT file.
        //Condition 1: The format of the date of the offense should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        //Condition 2: The demerit points must be a whole number between 1-6
        //Condition 3: If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
        //If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.
        //Instruction: If the above conditions and any other conditions you may want to consider are met, the demerit points for a person should be inserted into the TXT file,
        //and the addDemeritPoints function should return "Success". Otherwise, the addDemeritPoints function should return "Failed".
        
        // Condition 1: Validate offense date format (DD-MM-YYYY)
        if (!isValidDateFormat(offenseDate)) {
            System.out.println("Invalid offense date format. Expected DD-MM-YYYY");
            return "Failed";
        }
        
        // Condition 2: Validate demerit points range (1-6)
        if (points < 1 || points > 6) {
            System.out.println("Demerit points must be between 1-6");
            return "Failed";
        }
        
        // Check if person exists in the system
        if (!personExists(this.personID)) {
            System.out.println("Person does not exist in the system");
            return "Failed";
        }
        
        try {
            // Parse the offense date
            Date offense = DATE_FORMAT.parse(offenseDate);
            
            // Add demerit points to the person's record
            this.demeritPoints.put(offense, points);
            
            // Condition 3: Check suspension based on age and total points
            checkAndUpdateSuspensionStatus(offense);
            
            // Write demerit points to file
            if (writeDemeritPointsToFile(offenseDate, points)) {
                // Also update person file with new suspension status
                updatePersonSuspensionInFile();
                return "Success";
            } else {
                System.out.println("Failed to write demerit points to file");
                return "Failed";
            }
            
        } catch (ParseException e) {
            System.out.println("Error parsing offense date: " + e.getMessage());
            return "Failed";
        }
    }
    
    /**
     * Validates if the date string follows DD-MM-YYYY format
     * @param dateString the date string to validate
     * @return true if valid format, false otherwise
     */
    private boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }
        
        try {
            DATE_FORMAT.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Checks if person exists in the persons.txt file
     * @param personID the person ID to check
     * @return true if person exists, false otherwise
     */
    private boolean personExists(String personID) {
        if (personID == null) {
            return false;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(PERSON_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 0 && parts[0].equals(personID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File doesn't exist or can't be read - person doesn't exist
            return false;
        }
        return false;
    }
    
    /**
     * Calculates age based on birthdate
     * @param birthdate the birthdate string in DD-MM-YYYY format
     * @return age in years
     */
    private int calculateAge(String birthdate) {
        try {
            Date birth = DATE_FORMAT.parse(birthdate);
            Date now = new Date();
            long ageInMillis = now.getTime() - birth.getTime();
            return (int) (ageInMillis / (365.25 * 24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            System.out.println("Error parsing birthdate: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Calculates total demerit points within the last 2 years from the given date
     * @param fromDate the reference date
     * @return total points within 2 years
     */
    private int calculateTotalPointsInTwoYears(Date fromDate) {
        int totalPoints = 0;
        
        // Calculate date 2 years ago from the offense date
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(Calendar.YEAR, -2);
        Date twoYearsAgo = cal.getTime();
        
        // Sum up all demerit points within the 2-year window
        for (Map.Entry<Date, Integer> entry : demeritPoints.entrySet()) {
            Date offenseDate = entry.getKey();
            // Check if offense date is within the 2-year window
            if (offenseDate.after(twoYearsAgo) && !offenseDate.after(fromDate)) {
                totalPoints += entry.getValue();
            }
        }
        
        return totalPoints;
    }
    
    /**
     * Checks and updates suspension status based on age and total demerit points
     * @param currentOffenseDate the current offense date
     */
    private void checkAndUpdateSuspensionStatus(Date currentOffenseDate) {
        int age = calculateAge(this.birthdate);
        int totalPoints = calculateTotalPointsInTwoYears(currentOffenseDate);
        
        // Condition 3: Check suspension thresholds
        if (age < 21) {
            // Under 21: suspended if total points > 6
            if (totalPoints > 6) {
                this.isSuspended = true;
                System.out.println("Person under 21 suspended with " + totalPoints + " points");
            }
        } else {
            // 21 or over: suspended if total points > 12
            if (totalPoints > 12) {
                this.isSuspended = true;
                System.out.println("Person 21+ suspended with " + totalPoints + " points");
            }
        }
    }
    
    /**
     * Writes demerit points to the demerit_points.txt file
     * @param offenseDate the offense date
     * @param points the demerit points
     * @return true if successful, false otherwise
     */
    private boolean writeDemeritPointsToFile(String offenseDate, int points) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DEMERIT_FILE, true))) {
            // Format: personID|offenseDate|points|isSuspended
            writer.println(personID + "|" + offenseDate + "|" + points + "|" + isSuspended);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to demerit points file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Placeholder method to write person to file (will be implemented by addPerson team member)
     * @return true if successful, false otherwise
     */
    private boolean writePersonToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PERSON_FILE, true))) {
            // Format: personID|firstName|lastName|address|birthdate|isSuspended
            writer.println(personID + "|" + firstName + "|" + lastName + "|" + 
                          address + "|" + birthdate + "|" + isSuspended);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to person file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Updates the person's suspension status in the persons.txt file
     * @return true if successful, false otherwise
     */
    private boolean updatePersonSuspensionInFile() {
        try {
            // Read all lines from the file
            java.util.List<String> lines = new java.util.ArrayList<>();
            boolean personFound = false;
            
            try (BufferedReader reader = new BufferedReader(new FileReader(PERSON_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6 && parts[0].equals(this.personID)) {
                        // Update the suspension status (last field)
                        parts[5] = String.valueOf(this.isSuspended);
                        line = String.join("|", parts);
                        personFound = true;
                    }
                    lines.add(line);
                }
            }
            
            if (!personFound) {
                return false;
            }
            
            // Write all lines back to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(PERSON_FILE))) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
            
            return true;
        } catch (IOException e) {
            System.out.println("Error updating person suspension status: " + e.getMessage());
            return false;
        }
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