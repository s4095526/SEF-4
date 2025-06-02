# SEF-4: RoadRegistry Person Management System

## Project Overview

The SEF-4 project is a Java-based person management system for the RoadRegistry platform, developed as part of Assignment 4 for Software Engineering Fundamentals (ISYS3413). This system manages person records, personal details updates, and demerit points tracking with file-based storage.

## Features

The system implements three core functions in the `Person` class:

### 1. addPerson()
- Adds a new person to the system with validation
- Stores person information in `persons.txt`
- Validates person ID format, address format, and birthdate

### 2. updatePersonalDetails()
- Updates existing person information with business rule validation
- Enforces age-based address change restrictions
- Prevents simultaneous ID and other field changes under certain conditions

### 3. addDemeritPoints()
- Records demerit points for traffic offenses
- Automatically calculates suspension status based on age and point totals
- Stores demerit records in `demerit_points.txt`

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Git** (for version control)

## Project Structure

```
SEF-4/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── Person.java          # Main Person class
│   └── test/
│       └── java/
│           └── PersonTest.java      # JUnit test cases
├── .github/
│   └── workflows/
│       └── test.yml                 # GitHub Actions workflow
├── target/                          # Maven build output
├── pom.xml                          # Maven configuration
├── .gitignore                       # Git ignore rules
├── persons.txt                      # Person records (created at runtime)
├── demerit_points.txt              # Demerit point records (created at runtime)
└── README.md                        # This file
```

## How to Run the Project

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <your-repository-url>
   cd SEF-4
   ```

2. **Verify Java and Maven installation**
   ```bash
   java -version    # Should show Java 17+
   mvn -version     # Should show Maven 3.6+
   ```

3. **Compile the project**
   ```bash
   mvn compile
   ```

4. **Run the tests**
   ```bash
   mvn test
   ```

5. **Clean and rebuild (if needed)**
   ```bash
   mvn clean compile
   ```

### Running Individual Components

#### Running Tests Only
```bash
# Run all tests
mvn test

# Run specific test method
mvn test -Dtest=PersonTest#testAddPerson_ValidData_ReturnsSuccess
```

#### Running with Verbose Output
```bash
mvn test -X  # Debug mode with detailed output
```

### GitHub Actions (Continuous Integration)

The project includes automated testing via GitHub Actions:

1. **Automatic triggers:**
   - Push to `main` branch
   - Pull requests to `main` branch

2. **Workflow actions:**
   - Sets up Java 17 environment
   - Runs Maven tests
   - Uploads generated text files as artifacts
   - Displays file contents in logs

## Business Rules & Validation

### Person ID Validation
- Must be exactly 10 characters
- First two characters: digits 2-9
- Characters 3-8: at least 2 special characters
- Last two characters: uppercase letters A-Z
- Example: `56s_d%&fAB`

### Address Format
- Pattern: `Street Number|Street|City|State|Country`
- State must be "Victoria"
- Example: `32|Highland Street|Melbourne|Victoria|Australia`

### Birthdate Format
- Pattern: `DD-MM-YYYY`
- Valid year range: 1900-2025
- Example: `15-11-1990`

### Update Personal Details Rules
1. **Age restriction:** Under 18 cannot change address
2. **Birthdate isolation:** If birthdate changes, no other fields can change
3. **ID restriction:** If first digit is even, ID cannot be changed

### Demerit Points Rules
1. **Date format:** Must be `DD-MM-YYYY`
2. **Points range:** Must be 1-6
3. **Suspension thresholds:**
   - Under 21: suspended if total points > 6 within 2 years
   - 21+: suspended if total points > 12 within 2 years

## Test Cases

The project includes comprehensive test coverage:

### addPerson() Tests (5 test cases)
- Valid data success
- Invalid person ID format
- Invalid address format  
- Invalid birthdate format
- Person ID length validation

### updatePersonalDetails() Tests (5 test cases)
- Under-18 address change restriction
- Birthdate + other field change restriction
- Even first digit ID change restriction
- Valid full name change
- Invalid address format

### addDemeritPoints() Tests (5+ test cases)
- Valid demerit points addition
- Invalid date format
- Invalid points range
- Under-21 suspension logic
- Non-existent person handling

## File Storage

### persons.txt Format
```
personID|firstName|lastName|address|birthdate|isSuspended
56s_d%&fAB|John|Doe|32|Highland Street|Melbourne|Victoria|Australia|15-11-1990|false
```

### demerit_points.txt Format
```
personID|offenseDate|points|isSuspended
56s_d%&fAB|15-03-2024|3|false
```


### Debug Tips

1. **Enable verbose testing:**
   ```bash
   mvn test -Dtest=PersonTest -X
   ```

2. **Check generated files:**
   ```bash
   cat persons.txt
   cat demerit_points.txt
   ```

3. **Clean build artifacts:**
   ```bash
   mvn clean
   rm -f *.txt
   ```

## Development Guidelines

### Code Style
- Follow Java naming conventions
- Add meaningful comments for complex logic
- Use descriptive variable names
- Implement proper error handling

### Testing
- Write tests for all public methods
- Use meaningful test data
- Test both success and failure scenarios
- Follow AAA pattern (Arrange, Act, Assert)

### Git Workflow
- Create feature branches for new development
- Write clear commit messages
- Test locally before pushing
- Review GitHub Actions results

## Assignment Requirements Met

✅ **Maven-based Java project** with proper POM configuration  
✅ **15 JUnit test cases** (5 per function)  
✅ **Implementation of 3 functions** with business logic  
✅ **GitHub repository** with version control  
✅ **GitHub Actions workflow** for automated testing  
✅ **File-based data storage** with TXT files  
✅ **Comprehensive validation** and error handling  

## Contributors

- Student ID: s4101562, s4099526, s4093222
- Course: ISYS3413 Software Engineering Fundamentals
- Assignment: Assignment 4 (Team-based)
- Institution: RMIT University

## License

This project is developed for educational purposes as part of RMIT University coursework.

---

