# Vision Entry Gate Pass Management System

A Java Spring Boot application for managing visitor entry gate passes with database persistence and real-time verification.

## Technology Stack

- **Backend:** Java 17, Spring Boot 3.5.0, Spring Data JPA, Spring Security
- **Database:** MySQL 8.0
- **Frontend:** HTML5, CSS3, JavaScript
- **Build Tool:** Maven

## Prerequisites

1. **Java 17 or higher** - Download from [Eclipse Adoptium](https://adoptium.net/)
2. **Maven** - Download from [Apache Maven](https://maven.apache.org/download.cgi)
3. **MySQL 8.0** - Download from [MySQL](https://dev.mysql.com/downloads/mysql/)
4. **IDE** - IntelliJ IDEA (recommended) or VS Code

## Database Setup

1. **Install MySQL** and start the MySQL service
2. **Create database:**
   ```sql
   CREATE DATABASE visitor_gate_pass_system;
   ```
3. **Update database credentials** in `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/visitor_gate_pass_system
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   ```

## Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/sseraladen-svg/Visitor-Entry-Gate-Pass-Management-System.git
   cd Visitor-Entry-Gate-Pass-Management-System
   ```

2. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

3. **Build the project:**
   ```bash
   mvn clean install
   ```

## Running the Application

### Option 1: Using Maven
```bash
cd backend
mvn spring-boot:run
```

### Option 2: Using IDE
1. Open the project in IntelliJ IDEA
2. Navigate to `VisionGateApplication.java`
3. Right-click and select "Run 'VisionGateApplication.main()'"

### Option 3: Using JAR file
```bash
cd backend
mvn clean package
java -jar target/vision-entry-gate-pass-1.0.0.jar
```

## Accessing the Application

Once the application starts, open your browser and navigate to:

- **Homepage:** http://localhost:8080/index.html
- **Login:** http://localhost:8080/login.html
- **Create Pass:** http://localhost:8080/create-pass.html
- **Verify Pass:** http://localhost:8080/verify-pass.html

## Default Credentials

- **Username:** admin
- **Password:** admin123

## User Flow

1. **Login** with admin credentials
2. **Create Gate Pass** by entering visitor details
3. **System generates** unique pass ID and saves to database
4. **Verify Gate Pass** using the generated pass ID
5. **System validates** pass against database and shows result

## Project Structure

```
backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/visiongate/
│       │       ├── entity/          # JPA Entities
│       │       ├── repository/      # JPA Repositories
│       │       ├── config/          # Security Configuration
│       │       ├── UserController.java
│       │       ├── GatePassController.java
│       │       └── VisionGateApplication.java
│       └── resources/
│           ├── static/            # HTML/CSS/JS files
│           ├── application.properties
│           └── data.sql          # Initial data
docs/
└── diagrams/                     # System diagrams
```

## Features

- ✅ Database-backed authentication
- ✅ Persistent gate pass storage
- ✅ Real-time pass verification
- ✅ Modern responsive UI
- ✅ Role-based access control
- ✅ Comprehensive audit trail

## Troubleshooting

**Port 8080 already in use:**
- Change port in `application.properties`: `server.port=8081`

**Database connection failed:**
- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure database `visitor_gate_pass_system` exists

**Maven not found:**
- Add Maven to system PATH
- Use IDE's built-in Maven support

## Documentation

- [Problem Statement](Problem_Statement.md)
- [System Diagrams](docs/diagrams/DIAGRAMS.md)
- [Presentation](docs/Vision_Gate_Presentation.pptx)

## License

This project is created for educational purposes.