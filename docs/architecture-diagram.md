# Architecture Diagram

## System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Frontend (React)                         │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌─────────────┐ │
│  │  Login   │  │Visitor   │  │  Pass    │  │  Entry Log  │ │
│  │  Page    │  │  Form    │  │  View    │  │    List     │ │
│  └──────────┘  └──────────┘  └──────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ HTTP/REST API
                            ▼
┌─────────────────────────────────────────────────────────────┐
│              Backend (Spring Boot)                           │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              Controller Layer                         │  │
│  │  VisitorController │ GatePassController │ EntryLog  │  │
│  └──────────────────────────────────────────────────────┘  │
│                            │                                 │
│                            ▼                                 │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              Service Layer                            │  │
│  │  VisitorService │ GatePassService │ EntryLogService  │  │
│  └──────────────────────────────────────────────────────┘  │
│                            │                                 │
│                            ▼                                 │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              Repository Layer                         │  │
│  │  VisitorRepository │ GatePassRepository │ EntryLog   │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│              Database (MySQL/PostgreSQL)                    │
│  visitors │ hosts │ gate_passes │ entry_logs               │
└─────────────────────────────────────────────────────────────┘
```

## Component Description

### Frontend
- **Login Page**: Authentication and authorization
- **Visitor Form**: Registration form for visitors
- **Pass View**: Display and manage gate passes
- **Entry Log List**: View entry/exit logs

### Backend
- **Controller Layer**: REST API endpoints
- **Service Layer**: Business logic implementation
- **Repository Layer**: Data access layer using JPA

### Database
- Relational database for storing visitor, host, gate pass, and entry log data
