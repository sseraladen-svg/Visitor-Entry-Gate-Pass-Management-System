# Architecture

```mermaid
flowchart LR
    subgraph Browser
        UI[React SPA<br/>Vite + React Router]
    end

    subgraph Backend[Spring Boot 3 API]
        SEC[JWT filter + role checks]
        CTRL[Controllers<br/>auth / visitors / gate-passes / dashboard]
        SRV[Services<br/>UserService, VisitorService, GatePassService]
        REPO[Spring Data JPA repositories]
    end

    DB[(H2 file DB / MySQL)]

    UI -- "REST + Bearer token" --> SEC --> CTRL --> SRV --> REPO --> DB
```

## Layers

| Layer | Responsibility |
| --- | --- |
| `controller` | HTTP endpoints, request validation, DTO mapping |
| `service` | Business rules: pass lifecycle transitions, expiry checks, pass-code generation |
| `repository` | Spring Data JPA persistence |
| `security` | `JwtService` (issue/parse tokens), `JwtAuthenticationFilter`, `AppUserDetailsService` |
| `config` | Security filter chain, CORS, demo data seeding |

## Request flow — check-in at the gate

```mermaid
sequenceDiagram
    participant S as Security staff (UI)
    participant A as API
    participant D as Database

    S->>A: GET /api/gate-passes/code/VP-XXXX
    A->>D: findByPassCode
    A-->>S: pass details + status
    S->>A: POST /api/gate-passes/code/VP-XXXX/check-in
    A->>A: require status APPROVED and not expired
    A->>D: status = CHECKED_IN, checkInTime = now
    A-->>S: updated pass
```

## Roles

| Role | Can do |
| --- | --- |
| `ADMIN` | Everything: manage users and visitors, approve, check in/out |
| `HOST` | Raise passes, approve/reject/cancel passes, view visitors |
| `SECURITY` | Raise passes, verify passes, check visitors in and out |
