# Visitor Entry Gate Pass Management System

A comprehensive web-based system for managing visitor entries, gate passes, and entry logs at educational institutions or corporate facilities.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

- **Visitor Management**: Register and manage visitor information with ID proof verification
- **Gate Pass System**: Generate, approve, and manage gate passes for visitors
- **Entry/Exit Logging**: Track visitor entry and exit times with security guard verification
- **Host Management**: Manage hosts (employees/faculty) who can approve visitor passes
- **Real-time Dashboard**: View active visitors and pass status
- **Security**: Role-based access control and JWT authentication
- **Audit Trail**: Complete history of all visitor movements

## 🛠 Technology Stack

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **MySQL/PostgreSQL** (Database)
- **Maven** (Build Tool)

### Frontend
- **React 18+**
- **React Router** (Navigation)
- **Axios** (HTTP Client)
- **CSS/SCSS** (Styling)

## 📁 Project Structure

```
visitor-gate-pass-system/
│
├── src/main/java/com/college/visitorgatepass/
│   ├── controller/     → VisitorController, GatePassController, EntryLogController
│   ├── service/        → VisitorService, GatePassService, EntryLogService
│   ├── repository/     → VisitorRepository, GatePassRepository, EntryLogRepository
│   └── model/          → Visitor, Host, GatePass, EntryLog
│
├── docs/                → architecture diagram, ER diagram
│
├── frontend/ (React)
│   └── src/pages/       → Login, VisitorForm, PassView, EntryLogList
│
├── .env.example
├── .gitignore
├── README.md
└── pom.xml
```

## 📦 Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- Node.js 16+ and npm/yarn
- MySQL 8.0+ or PostgreSQL 12+
- Git

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/sseraladen-svg/Visitor-Entry-Gate-Pass-Management-System.git
cd Visitor-Entry-Gate-Pass-Management-System
```

### 2. Backend Setup

```bash
# Navigate to the project root
cd visitor-gate-pass-system

# Create environment file
cp .env.example .env

# Update .env with your database credentials
```

### 3. Database Setup

```sql
-- Create database
CREATE DATABASE visitor_gate_pass_system;

-- Use the database
USE visitor_gate_pass_system;
```

### 4. Frontend Setup

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Create environment file
cp .env.example .env.local
```

## ⚙️ Configuration

### Backend Configuration (.env)

```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=visitor_gate_pass_system
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET=your_jwt_secret_key_here
JWT_EXPIRATION=86400000
SERVER_PORT=8080
```

### Frontend Configuration (.env.local)

```env
REACT_APP_API_URL=http://localhost:8080/api
```

## 🏃 Running the Application

### Start Backend

```bash
# From project root
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Start Frontend

```bash
# From frontend directory
cd frontend
npm start
```

The frontend will start on `http://localhost:3000`

## 📚 API Documentation

### Visitor Endpoints

- `GET /api/visitors` - Get all visitors
- `POST /api/visitors` - Create new visitor
- `GET /api/visitors/{id}` - Get visitor by ID

### Gate Pass Endpoints

- `GET /api/gate-passes` - Get all gate passes
- `POST /api/gate-passes` - Create new gate pass
- `GET /api/gate-passes/{id}` - Get gate pass by ID
- `PUT /api/gate-passes/{id}/approve` - Approve gate pass

### Entry Log Endpoints

- `GET /api/entry-logs` - Get all entry logs
- `POST /api/entry-logs` - Create entry log
- `GET /api/entry-logs/{id}` - Get entry log by ID
- `GET /api/entry-logs/visitor/{visitorId}` - Get logs by visitor

## 🗄 Database Schema

### Tables

- **visitors**: Stores visitor information
- **hosts**: Stores host (employee/faculty) information
- **gate_passes**: Stores gate pass details and status
- **entry_logs**: Tracks entry and exit times

For detailed ER diagram and relationships, see [docs/er-diagram.md](docs/er-diagram.md)

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Your Name** - Initial work

## 🙏 Acknowledgments

- Spring Boot Team
- React Community
- All contributors

---

For more information, please refer to the [docs](docs/) directory for architecture diagrams and detailed documentation.
