<a id="readme-top"></a>

<!-- PROJECT HEADER -->
<br />
<div align="center">
  <h3 align="center">challenge-alura-backend</h3>

  <p align="center">
    A REST API for managing room reservations built with Spring Boot
    <br />
    <a href="https://github.com/rogerbertan/challenge-alura-backend/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    &middot;
    <a href="https://github.com/rogerbertan/challenge-alura-backend/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

This is a Room Reservation Management System developed as part of the Alura Backend Challenge. The API provides a complete solution for managing room bookings, including:

- **User Management**: Create, update, delete, and list users who can make reservations
- **Room Management**: Manage available rooms with their capacities
- **Reservation System**: Book rooms with validation for time conflicts and capacity limits

The system includes smart validation to prevent double-bookings and ensures that reservation end times are always after start times.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

- [![Spring Boot][SpringBoot]][SpringBoot-url]
- [![Java][Java]][Java-url]
- [![PostgreSQL][PostgreSQL]][PostgreSQL-url]
- [![Docker][Docker]][Docker-url]
- [![Maven][Maven]][Maven-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running, follow these steps.

### Prerequisites

- Java 21+
- Maven 3.x
- Docker & Docker Compose (optional, for containerized deployment)
- PostgreSQL (if not using Docker)

### Installation

#### Option 1: Using Docker (Recommended)

1. Clone the repo
   ```sh
   git clone https://github.com/rogerbertan/challenge-alura-backend.git
   ```
2. Start the application with Docker Compose
   ```sh
   docker-compose up
   ```
   This will start both the PostgreSQL database and the application on port 8080.

#### Option 2: Manual Setup

1. Clone the repo
   ```sh
   git clone https://github.com/rogerbertan/challenge-alura-backend.git
   ```
2. Configure PostgreSQL database (create database named `reservas`)
3. Update `src/main/resources/application-prod.properties` with your database credentials
4. Build and run the application
   ```sh
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
   ```

#### Option 3: Development Mode (H2 Database)

1. Clone the repo
   ```sh
   git clone https://github.com/rogerbertan/challenge-alura-backend.git
   ```
2. Run with dev profile (uses in-memory H2 database)
   ```sh
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->

## Usage

The API provides three main resources:

### Users (`/api/v1/usuarios`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/usuarios` | List all users |
| GET | `/api/v1/usuarios/{id}` | Get user by ID |
| POST | `/api/v1/usuarios` | Create new user |
| PUT | `/api/v1/usuarios` | Update user |
| DELETE | `/api/v1/usuarios/{id}` | Delete user |

### Rooms (`/api/v1/salas`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/salas` | List all rooms |
| GET | `/api/v1/salas/{id}` | Get room by ID |
| POST | `/api/v1/salas` | Create new room |
| PUT | `/api/v1/salas` | Update room |
| DELETE | `/api/v1/salas/{id}` | Delete room |

### Reservations (`/api/v1/reservas`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/reservas` | List reservations (paginated) |
| GET | `/api/v1/reservas/{id}` | Get reservation by ID |
| POST | `/api/v1/reservas` | Create reservation |
| PUT | `/api/v1/reservas` | Update reservation |
| DELETE | `/api/v1/reservas/cancelar/{id}` | Cancel reservation |

### Example Request

```sh
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome": "John Doe", "email": "john@example.com"}'
```

_For more examples, please refer to the [Postman Collection](postman_collection.json)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->

[contributors-shield]: https://img.shields.io/github/contributors/rogerbertan/challenge-alura-backend.svg?style=for-the-badge
[contributors-url]: https://github.com/rogerbertan/challenge-alura-backend/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/rogerbertan/challenge-alura-backend.svg?style=for-the-badge
[forks-url]: https://github.com/rogerbertan/challenge-alura-backend/network/members
[stars-shield]: https://img.shields.io/github/stars/rogerbertan/challenge-alura-backend.svg?style=for-the-badge
[stars-url]: https://github.com/rogerbertan/challenge-alura-backend/stargazers
[issues-shield]: https://img.shields.io/github/issues/rogerbertan/challenge-alura-backend.svg?style=for-the-badge
[issues-url]: https://github.com/rogerbertan/challenge-alura-backend/issues
[SpringBoot]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot
[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://openjdk.org/
[PostgreSQL]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/
[Docker]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[Maven]: https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white
[Maven-url]: https://maven.apache.org/