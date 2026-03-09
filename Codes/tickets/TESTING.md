# Tickets demo - Testing guide

This guide documents **how to test** the `Codes/tickets/` demo in two layers:

1. **Automated tests** (repeatable, do not require Docker)
2. **End-to-end (E2E) manual checks** (requires Docker + running services)

---

## Automated tests (no Docker required)

### Users service

```bash
cd Codes/tickets/users
mvnw.cmd test
```

Notes:

- Tests run with profile `test`, using an in-memory H2 DB via `src/test/resources/application-test.properties`.

### Sales service

```bash
cd Codes/tickets/sales
mvnw.cmd test
```

Notes:

- Includes controller-level tests that exercise the real Spring MVC layer + JPA against H2.

### Gateway + Nameserver

```bash
cd Codes/tickets/gateway
mvnw.cmd test

cd Codes/tickets/nameserver
mvnw.cmd test
```

---

## Manual E2E testing (Docker + running services)

### 1) Start Postgres + pgAdmin

```bash
cd Codes/tickets/docker
docker compose -f docker-compose-dev.yml up -d
```

### 2) Start backend services (one terminal each)

Start in this order:

```bash
cd Codes/tickets/nameserver && mvnw.cmd spring-boot:run
cd Codes/tickets/users      && mvnw.cmd spring-boot:run
cd Codes/tickets/sales      && mvnw.cmd spring-boot:run
cd Codes/tickets/gateway    && mvnw.cmd spring-boot:run
```

Quick checks:

- Eureka UI: `http://localhost:8761`
- Gateway root: `http://localhost:8080`
- Users via gateway: `http://localhost:8080/api/users/status`

### 3) Start frontend

```bash
cd Codes/tickets/frontend
npm install
npm run dev
```

Notes:

- This frontend requires **Node.js 18+**. If you are on an older Node (e.g. Node 14), `npm install`/`npm ci` will fail.
- Minimal smoke checks (once Node is compatible):
  - `npm run lint`
  - `npm run build`

### 4) Exercise endpoints with the REST collection

Open `Codes/tickets/tickets.rest` and run requests in order:

- Create/list Users through the gateway (`/api/users/...`)
- Create/list Events through the gateway (`/events/...`)
- Create Sales through the gateway (`/sales/...`)

---

## What “passing” looks like

- All `mvnw.cmd test` commands succeed.
- Eureka shows `USERS-SERVICE`, `SALES-SERVICE`, and `GATEWAY` registered.
- The `.rest` collection requests return `200`/`204` as expected.

