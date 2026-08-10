# Entity relationship diagram

```mermaid
erDiagram
    USERS ||--o{ GATE_PASSES : "hosts"
    USERS ||--o{ GATE_PASSES : "creates"
    USERS ||--o{ GATE_PASSES : "approves"
    VISITORS ||--o{ GATE_PASSES : "holds"

    USERS {
        bigint id PK
        varchar full_name
        varchar email UK
        varchar password "bcrypt hash"
        varchar role "ADMIN | HOST | SECURITY"
        varchar department
        varchar phone
        boolean active
    }

    VISITORS {
        bigint id PK
        varchar full_name
        varchar phone
        varchar email
        varchar company
        varchar id_proof_type
        varchar id_proof_number
        varchar address
        datetime created_at
    }

    GATE_PASSES {
        bigint id PK
        varchar pass_code UK "VP-XXXXXXXX"
        bigint visitor_id FK
        bigint host_id FK
        bigint created_by FK
        bigint approved_by FK
        varchar purpose
        datetime expected_entry
        datetime expected_exit
        varchar status "PENDING | APPROVED | REJECTED | CHECKED_IN | CHECKED_OUT | CANCELLED"
        varchar vehicle_number
        int number_of_visitors
        varchar remarks
        datetime check_in_time
        datetime check_out_time
        datetime created_at
        datetime updated_at
    }
```

## Notes

- `pass_code` is a unique, human-readable code (`VP-` plus 8 characters from an alphabet that
  excludes look-alike characters such as `O`, `0`, `I` and `1`) used by security staff at the gate.
- A visitor is deduplicated by `phone` when a pass is raised with inline visitor details.
- `check_in_time` / `check_out_time` are set only by the gate check-in and check-out operations,
  which makes them the source of truth for the "inside campus now" and "check-ins today" statistics.
