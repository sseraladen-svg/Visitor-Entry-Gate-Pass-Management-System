# Visitor Entry Gate Pass Management System

A full-stack application for issuing, approving and verifying visitor gate passes at a campus or
office gate. Visitors are registered once, a gate pass is raised against a host, the host or an
admin approves it, and security staff check the visitor in and out at the gate using the pass code.

- **Backend** — Java 17, Spring Boot 3, Spring Security (JWT), Spring Data JPA, H2 (default) / MySQL
- **Frontend** — React 18, React Router, Axios, Vite

## Features

| Area | Capability |
| --- | --- |
| Authentication | JWT login, role-based access for `ADMIN`, `HOST`, `SECURITY` |
| Visitors | Register, search (name/phone), update, delete (admin only) |
| Gate passes | Raise a pass for a new or existing visitor, approve / reject / cancel |
| Gate operations | Verify a pass by code, check in, check out, expiry and status guards |
| Dashboard | Visitor and pass counts, visitors currently inside, check-ins today |

### Pass lifecycle

```
PENDING ──approve──▶ APPROVED ──check-in──▶ CHECKED_IN ──check-out──▶ CHECKED_OUT
   │                    │
   ├──reject──▶ REJECTED └──cancel──▶ CANCELLED
```

## Running the backend

```bash
mvn spring-boot:run           # http://localhost:8080, H2 file database in ./data
mvn test                      # integration tests (in-memory H2)
```

MySQL instead of H2 (uses the variables from `.env.example`):

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

On first start the application seeds three users (password `admin123`, override with
`SEED_DEFAULT_PASSWORD`):

| Email | Role |
| --- | --- |
| `admin@college.edu` | ADMIN |
| `security@college.edu` | SECURITY |
| `anita.rao@college.edu` | HOST |

## Running the frontend

```bash
cd frontend
npm install
npm run dev                   # http://localhost:5173, proxies /api to localhost:8080
```

Set `VITE_API_URL` (see `frontend/.env.example`) to call an API on a different host.

## Configuration

| Variable | Default | Description |
| --- | --- | --- |
| `SERVER_PORT` | `8080` | Backend HTTP port |
| `JWT_SECRET` | dev value | HMAC signing key — must be set in production |
| `JWT_EXPIRATION` | `86400000` | Token lifetime in ms |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:3000,http://localhost:5173` | Allowed browser origins |
| `SEED_ENABLED` | `true` | Seed demo users when the user table is empty |
| `DB_HOST` / `DB_PORT` / `DB_NAME` / `DB_USERNAME` / `DB_PASSWORD` | see `.env.example` | MySQL settings (`mysql` profile) |

## API

| Method | Endpoint | Roles |
| --- | --- | --- |
| `POST` | `/api/auth/login` | public |
| `POST` | `/api/auth/register` | ADMIN |
| `GET` | `/api/auth/me` | any |
| `GET` | `/api/users` | ADMIN |
| `GET` | `/api/users/hosts` | any |
| `GET/POST/PUT` | `/api/visitors`, `/api/visitors/{id}` | any |
| `DELETE` | `/api/visitors/{id}` | ADMIN |
| `GET` | `/api/gate-passes?status=&hostId=` | any |
| `POST` | `/api/gate-passes` | any |
| `POST` | `/api/gate-passes/{id}/approve` \| `/reject` \| `/cancel` | ADMIN, HOST |
| `GET` | `/api/gate-passes/code/{passCode}` | any |
| `POST` | `/api/gate-passes/code/{passCode}/check-in` \| `/check-out` | ADMIN, SECURITY |
| `GET` | `/api/dashboard/stats` | any |

Example — create a pass for a walk-in visitor:

```bash
TOKEN=$(curl -s -X POST localhost:8080/api/auth/login -H 'Content-Type: application/json' \
  -d '{"email":"admin@college.edu","password":"admin123"}' | jq -r .token)

curl -X POST localhost:8080/api/gate-passes -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' -d '{
    "visitor": {"fullName":"Ravi Kumar","phone":"9876543210","idProofType":"Aadhaar"},
    "hostId": 3, "purpose": "Project discussion",
    "expectedEntry": "2026-01-01T10:00:00", "expectedExit": "2026-01-01T13:00:00"}'
```

## Documentation

- [Architecture](docs/architecture-diagram.md)
- [ER diagram](docs/er-diagram.md)
