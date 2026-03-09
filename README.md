# CSI607 - Sistemas Web II

## Lecture Notes and Codes

### *Prof. Fernando Bernardes de Oliveira, Ph.D.*

#### [Department of Computer and Systems (DECSI)](https://decsi.ufop.br/)

---

Here are available lecture notes and codes on CSI607 - Sistemas Web II course at [Universidade Federal de Ouro Preto (UFOP)](http://www.ufop.br). Semester 2025/2.

Proposed content for this semester:

1. Java Spring Boot
1. Spring Web
1. Spring JPA
1. Microservices
1. Messaging Systems
1. Containers and orchestration
1. React.js

---

**Lecture notes and additional resources:**

1. [Setting up development environment](./LectureNotes/setting-environment.md)
1. [Spring Framework](./LectureNotes/spring-framework.md)
1. [Spring Boot](./LectureNotes/spring-boot.md)
1. [Spring JPA](./LectureNotes/spring-jpa.md)
1. [Database model](./Docs/tickets-db-full.png)
1. [Architecture](./LectureNotes/architecture.md)
1. [Spring Microservices](./LectureNotes/spring-microservices.md)
1. [Messaging Systems](./LectureNotes/messaging-system.md)
1. [Containers and orchestration](./LectureNotes/containers.md)
1. [React.js](./LectureNotes/reactjs.md)

---

## Running the Tickets demo (local)

This repository contains multiple code samples. The most “complete app” here is the **Tickets** demo under `Codes/tickets/`:

- **`nameserver`**: Eureka discovery server (port `8761`)
- **`users`**: Users microservice + Postgres (port `3000`)
- **`sales`**: Sales microservice + Postgres (port `4000`)
- **`gateway`**: Spring Cloud Gateway routing + frontend proxy (port `8080`)
- **`frontend`**: React + TypeScript + Vite (port `5173`)

### Prerequisites

- **Java 17+**
  - Note: if `java -version` shows Java 8, you must fix your PATH/JAVA_HOME for interactive runs. The Maven Wrapper may still work, but running services should use Java 17+.
- **Docker Desktop** (for Postgres + pgAdmin via compose)
- **Node.js 18+** (for the React frontend)
  - This frontend uses modern tooling (React/Vite/Tailwind). Older Node versions (e.g. Node 14) will fail during `npm install`/`npm ci`.

### Start database (Postgres + pgAdmin)

From `Codes/tickets/docker/`:

```bash
docker compose -f docker-compose-dev.yml up -d
```

This exposes:

- Postgres on `localhost:9876`
- pgAdmin on `http://localhost:8123` (credentials are in `docker-compose-dev.yml`)

### Start the backend services

Start each service in its own terminal, using the Maven Wrapper so you don’t need Maven installed globally:

```bash
cd Codes/tickets/nameserver && mvnw.cmd spring-boot:run
cd Codes/tickets/users      && mvnw.cmd spring-boot:run
cd Codes/tickets/sales      && mvnw.cmd spring-boot:run
cd Codes/tickets/gateway    && mvnw.cmd spring-boot:run
```

Quick health checks:

- Eureka UI: `http://localhost:8761`
- Gateway: `http://localhost:8080`
- Users status: `http://localhost:8080/api/users/status` (gateway route)

Tip (Windows): you can start the whole backend stack (DB + services) with:

```powershell
powershell -ExecutionPolicy Bypass -File .\Codes\tickets\run-backend.ps1
```

### Start the frontend

From `Codes/tickets/frontend/`:

```bash
npm install
npm run dev
```

Then open:

- Frontend via gateway: `http://localhost:8080/`
- Frontend direct (Vite): `http://localhost:5173/`

### Run automated tests (no Docker required for unit/integration tests)

Both `users` and `sales` have a `test` profile configured to use an in-memory H2 database so tests are repeatable without Postgres:

```bash
cd Codes/tickets/users && mvnw.cmd test
cd Codes/tickets/sales && mvnw.cmd test
```

### Manual API testing (.rest collection)

Use the REST collection at `Codes/tickets/tickets.rest` for copy/pasteable requests (works great with VS Code / IntelliJ REST clients).

---

License: [Creative Commons BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)

Best regards,  
Fernando B Oliveira.

[Contact and info.](mailto:fboliveira@ufop.edu.br)

---

![May the force be with you!](https://media.giphy.com/media/SW52VX6Xtzk1q/giphy.gif)
