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

## 4. Later: SonarQube analysis
Run a local SonarQube server (via Docker), then analyze with the Maven plugin:
```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community

mvn clean verify sonar:sonar \
  -Dsonar.projectKey=simple-docker-webapp \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=<your-generated-token>
```
The JaCoCo plugin already in `pom.xml` will feed code coverage into the SonarQube report.

## 5. Later: Trivy scan
Scan the built image for vulnerabilities:
```bash
trivy image simple-docker-webapp:latest
```
Scan the filesystem/dependencies instead:
```bash
trivy fs .
```

## Notes
- The Dockerfile uses a multi-stage build (Maven build stage → slim JRE Alpine runtime) to keep the final image small and reduce the attack surface Trivy will report on.
- The container runs as a non-root user by default — a common security practice you'll see flagged in scans if missing.
