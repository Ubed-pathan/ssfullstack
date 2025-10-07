# ssbackend

Spring Boot 3 (Java 21) backend with CRUD APIs for Country, State, District, and Language. Docker Compose sets up Postgres and pgAdmin.

## Quick start

1. Copy env file and start DB

```bash
cp .env.example .env
docker compose up -d
```

2. Run backend

```bash
./mvnw spring-boot:run
```

API base: `http://localhost:8080/api`

- Countries: `/countries`
- States: `/states`
- Districts: `/districts`
- Languages: `/languages`

Each resource supports:
- POST create
- GET /{id}
- PUT /{id}
- DELETE /{id}
- GET list with `page`, `size`, and optional filters:
  - Countries: `q`
  - States: `countryId`, `q`
  - Districts: `stateId`, `q`
  - Languages: `q`

CORS allows `http://localhost:5173` (Vite dev server).
