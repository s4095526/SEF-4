package src.main;
import java.util.HashMap;
import java.util.Date;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthDate;
    private HashMap<Date, Integer> demeritPoints;
    private boolean isSuspended;


    public boolean addPerson() {
        //
        return true;
    }
    
    public static void updatePersonalDetails() {
    
    }

    public String addDemeritPoints() {

        return "Success";
    }
}