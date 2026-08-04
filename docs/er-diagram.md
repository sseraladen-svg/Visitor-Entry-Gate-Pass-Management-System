# ER Diagram

## Entity Relationship Diagram

```
┌──────────────┐         ┌──────────────┐         ┌──────────────┐
│   Visitor    │         │   GatePass   │         │  EntryLog    │
├──────────────┤         ├──────────────┤         ├──────────────┤
│ PK id        │         │ PK id        │         │ PK id        │
│    name      │         │    passNumber│         │    entryTime  │
│    email     │         │    issuedAt  │         │    exitTime   │
│    phone     │         │    validFrom │         │    gateName   │
│ idProofType  │         │  validUntil  │         │ securityGuard│
│ idProofNumber│         │    status    │         │ FK visitor_id │
│    purpose   │         │ FK visitor_id│         │ FK gate_pass_id│
│   createdAt  │         │   host_id    │         │              │
└──────┬───────┘         └──────┬───────┘         └──────┬───────┘
       │                        │                        │
       │                        │                        │
       │ 1                    N │ 1                    N │
       │                        │                        │
       ▼                        ▼                        │
┌──────────────┐                                        │
│    Host      │                                        │
├──────────────┤                                        │
│ PK id        │                                        │
│    name      │                                        │
│    email     │                                        │
│ department   │                                        │
│ designation  │                                        │
└──────────────┘                                        │
                                                         │
                                                         │
┌───────────────────────────────────────────────────────┘
│
│ 1
│
└───────────────────────────────────────────────────────┐
                                                           │
```

## Table Relationships

### Visitor
- **One-to-Many** with GatePass (one visitor can have multiple gate passes)
- **One-to-Many** with EntryLog (one visitor can have multiple entry logs)

### Host
- **One-to-Many** with GatePass (one host can approve multiple gate passes)

### GatePass
- **Many-to-One** with Visitor (each gate pass belongs to one visitor)
- **Many-to-One** with Host (each gate pass is assigned to one host)
- **One-to-Many** with EntryLog (one gate pass can have multiple entry logs)

### EntryLog
- **Many-to-One** with Visitor (each entry log belongs to one visitor)
- **Many-to-One** with GatePass (each entry log belongs to one gate pass)

## Table Definitions

### visitors
- `id` (PK): Auto-increment primary key
- `name`: Visitor's full name
- `email`: Visitor's email address
- `phone`: Contact phone number
- `idProofType`: Type of ID proof (Aadhaar, PAN, Driving License, etc.)
- `idProofNumber`: ID proof number
- `purpose`: Purpose of visit
- `createdAt`: Timestamp of visitor registration

### hosts
- `id` (PK): Auto-increment primary key
- `name`: Host's full name
- `email`: Host's email address
- `department`: Department name
- `designation`: Job designation

### gate_passes
- `id` (PK): Auto-increment primary key
- `passNumber`: Unique gate pass number
- `issuedAt`: Timestamp when pass was issued
- `validFrom`: Valid from timestamp
- `validUntil`: Valid until timestamp
- `status`: Pass status (PENDING, APPROVED, REJECTED, EXPIRED)
- `visitor_id` (FK): Reference to visitors table
- `host_id` (FK): Reference to hosts table

### entry_logs
- `id` (PK): Auto-increment primary key
- `entryTime`: Timestamp of entry
- `exitTime`: Timestamp of exit
- `gateName`: Name of the gate used
- `securityGuardName`: Name of security guard on duty
- `visitor_id` (FK): Reference to visitors table
- `gate_pass_id` (FK): Reference to gate_passes table
