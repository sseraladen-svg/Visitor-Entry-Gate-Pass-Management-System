# Vision Entry Gate Pass Management System

This is a simple visitor management system I built to handle gate passes digitally. It replaces the old paper-based system with a web application that stores visitor information in a database and allows security personnel to verify passes in real-time.

## What This Project Does

I created this system to solve a common problem - managing visitors entering a facility. Instead of using paper passes that can get lost or forged, this system:

- Lets admins create digital gate passes for visitors
- Stores all visitor information in a database
- Allows security staff to verify passes instantly
- Keeps a record of all visitors for security purposes

## Technology I Used

- **Backend:** Java 17 with Spring Boot (makes web development easier)
- **Database:** H2 for development (can use MySQL for production)
- **Frontend:** Simple HTML, CSS, and JavaScript
- **Build Tool:** Maven (for managing dependencies)

## How to Run This Project

### Prerequisites
You'll need:
- Java 17 or higher installed
- Maven installed (I used version 3.9.16)
- An IDE like IntelliJ IDEA or VS Code

### Getting Started

1. **Navigate to the backend folder:**
   ```bash
   cd backend
   ```

2. **Run the application using Maven:**
   ```bash
   mvn spring-boot:run
   ```
   
   If Maven isn't in your system PATH, use the full path:
   ```bash
   & "C:\apache-maven-3.9.16\bin\mvn.cmd" spring-boot:run
   ```

3. **The application will start on port 8080**

### Alternative: Run from IDE
- Open the project in IntelliJ IDEA
- Find `VisionGateApplication.java` 
- Right-click and select "Run"

## How to Use the Application

Once the application is running, open your browser and go to:

**http://localhost:8080/index.html**

### Login
- Go to http://localhost:8080/login.html
- Username: `admin`
- Password: `admin123`

### Create a Gate Pass
1. Click "Create Pass" in the navigation
2. Enter the visitor's name and purpose of visit
3. Click "Create Gate Pass"
4. The system will generate a unique pass ID (like PASS1, PASS2, etc.)
5. The pass details will be displayed on screen and saved to the database

### Verify a Gate Pass
1. Click "Verify Pass" in the navigation
2. Enter the pass ID you want to check
3. Click "Verify"
4. The system will check the database and show if the pass is valid or not

## How the System Works

I built this using the standard Spring Boot architecture:

```
Frontend (HTML/CSS)
    ↓
Controller (handles HTTP requests)
    ↓
Service Layer (business logic)
    ↓
Repository (database operations)
    ↓
Database (H2/MySQL)
```

**The flow works like this:**
1. User fills out a form in the browser
2. Controller receives the request
3. Service layer handles the business logic (like generating unique pass IDs)
4. Repository saves/retrieves data from the database
5. Response is sent back to show the result

## Database Structure

I designed the database with 5 main tables:

1. **users** - Stores admin user credentials
2. **visitor** - Stores visitor information
3. **employee** - Stores company employee details
4. **gate_pass** - Stores the actual gate passes
5. **entry_log** - Tracks when visitors enter and exit

## Project Structure

```
backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/visiongate/
│       │       ├── entity/          # Database entities (User, Visitor, etc.)
│       │       ├── repository/      # Database access layer
│       │       ├── service/        # Business logic layer
│       │       ├── config/          # Security configuration
│       │       └── controllers/    # HTTP request handlers
│       └── resources/
│           ├── static/            # HTML/CSS/JS files
│           └── application.properties
docs/
└── diagrams/                     # System architecture diagrams
```

## Key Features I Implemented

- **Database Authentication:** Login credentials are checked against the database, not hardcoded
- **Pass Generation:** Automatic unique ID generation for each pass
- **Real-time Verification:** Security staff can instantly verify if a pass is valid
- **Data Persistence:** All passes are stored in the database and persist across application restarts
- **Service Layer:** Proper separation of concerns with business logic in service classes

## Common Issues You Might Face

**Port 8080 is already in use:**
- This happens if another application is using port 8080
- Stop the other application or change the port in `application.properties`

**Maven command not found:**
- Make sure Maven is installed and added to your system PATH
- Or use the full path as shown above

**Database connection issues:**
- The application uses H2 by default, so MySQL setup isn't required for development
- If you want to use MySQL, update the database configuration in `application.properties`

## Documentation

I've included comprehensive documentation:
- [Problem Statement](Problem_Statement.md) - Details about the project requirements
- [System Diagrams](docs/diagrams/) - Architecture, ER diagrams, and class diagrams
- [Presentation](docs/Vision_Gate_Presentation.pptx) - Project overview presentation

## License

This project is created for educational purposes and uses the MIT License.

---

**Note:** This was my first attempt at building a complete Spring Boot application with database integration. I learned a lot about MVC architecture, JPA entities, and REST API development while building this.