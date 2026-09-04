# simple-docker-webapp

A minimal Spring Boot (Java 21) web app for practicing Docker, Docker Compose, Maven, SonarQube, and Trivy.

## Endpoints
- `GET /` — terminal-style landing page with a boot animation and live stats
- `GET /api/info` — JSON: message, timestamp, Java/OS info, uptime
- `GET /api/greet?name=YourName` — JSON greeting
- `GET /actuator/health` — health check (used by Docker/Compose healthcheck)

## 1. Build & run locally with Maven
```bash
mvn clean package
java -jar target/simple-docker-webapp.jar
# visit http://localhost:8080
```

## 2. Build & run with Docker
```bash
docker build -t simple-docker-webapp:latest .
docker run -p 8080:8080 simple-docker-webapp:latest
```

## 3. Run with Docker Compose
```bash
docker compose up --build
# or, to run detached:
docker compose up -d --build

docker compose ps
docker compose logs -f
docker compose down
```

## Notes
- The Dockerfile uses a multi-stage build (Maven build stage → slim JRE Alpine runtime) to keep the final image small and reduce the attack surface Trivy will report on.
- The container runs as a non-root user by default — a common security practice you'll see flagged in scans if missing.
