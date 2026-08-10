# System Diagrams

## Structure
```
── docs/
    └── diagrams/
        ├── architecture.png   ✓
        ├── er-diagram.png     ✓
        └── class-diagram.png  ✓
```

## 1. Architecture Diagram

Shows how the system components communicate.

```
┌─────────────────────┐
│       User          │
│  Admin / Security   │
└──────────┬──────────┘
           │
           ↓
┌─────────────────────┐
│     Web Browser     │
│ HTML / CSS / JS     │
└──────────┬──────────┘
           │ HTTP
           ↓
┌─────────────────────┐
│   Java Spring Boot  │
│      Backend        │
└──────────┬──────────┘
           │
           ↓
┌─────────────────────┐
│       MySQL         │
│      Database       │
└─────────────────────┘
```

This is enough for Review-I. Don't add cloud, microservices, AI servers, etc. unless they are actually part of your implementation.

## 2. ER Diagram

Shows your database tables and their relationships.

For the Review-I MVP, use:

```
┌──────────────────┐
│      USER        │
├──────────────────┤
│ user_id          │
│ username         │
│ password         │
│ role             │
└────────┬─────────┘
         │
         │ creates
         ↓
┌──────────────────┐
│   GATE_PASS      │
├──────────────────┤
│ pass_id          │
│ visitor_id       │
│ purpose          │
│ status           │
│ created_date     │
└────────┬─────────┘
         │
         │ belongs to
         ↓
┌──────────────────┐
│     VISITOR      │
├──────────────────┤
│ visitor_id       │
│ name             │
│ phone            │
│ purpose          │
└──────────────────┘
```

We can refine the exact relationships once we finalize the database design.

## 3. Class Diagram

Shows the Java classes and how they relate.

For our simple implementation:

```
┌─────────────────────┐
│        User         │
├─────────────────────┤
│ username            │
│ password            │
│ role                │
└─────────────────────┘


┌─────────────────────┐
│      Visitor        │
├─────────────────────┤
│ name                │
│ phone               │
│ purpose             │
└─────────────────────┘


┌─────────────────────┐
│      GatePass       │
├─────────────────────┤
│ passId              │
│ visitorName         │
│ purpose             │
│ status              │
└─────────────────────┘


┌─────────────────────────┐
│    UserController       │
├─────────────────────────┤
│ login()                 │
└─────────────────────────┘


┌─────────────────────────┐
│  GatePassController     │
├─────────────────────────┤
│ createPass()            │
│ verifyPass()            │
└─────────────────────────┘
```

## Important Distinction

| Diagram      | Shows                                          |
|--------------|-------------------------------------------------|
| Architecture | System components and communication            |
| ER Diagram   | Database tables and relationships                |
| Class Diagram| Java classes, attributes, methods, relationships |