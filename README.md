# Exercise Backend - Event Management System

A Spring Boot REST API backend for managing events with user authentication and event registration functionality.

## Features

- User registration and login with JWT authentication
- List all available events
- List events that a user is registered for
- Register/unregister for events
- Create new events

## Technology Stack

- Spring Boot 3.2.0
- Spring Security with JWT
- Spring Data JPA
- H2 Database (in-memory)
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

#### Register User
- **POST** `/api/auth/register`
- **Request Body:**
  ```json
  {
    "username": "john_doe",
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response:**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "userId": 1,
    "username": "john_doe"
  }
  ```

#### Login
- **POST** `/api/auth/login`
- **Request Body:**
  ```json
  {
    "username": "john_doe",
    "password": "password123"
  }
  ```
- **Response:**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "userId": 1,
    "username": "john_doe"
  }
  ```

### Events

All event endpoints require authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <token>
```

#### Get All Events
- **GET** `/api/events`
- **Response:**
  ```json
  [
    {
      "id": 1,
      "title": "Spring Boot Workshop",
      "description": "Learn Spring Boot",
      "eventDate": "2024-12-25T10:00:00",
      "location": "Conference Hall A",
      "maxParticipants": 50,
      "currentParticipants": 10,
      "isRegistered": true
    }
  ]
  ```

#### Get My Registered Events
- **GET** `/api/events/my-events`
- **Response:** Same format as Get All Events

#### Create Event
- **POST** `/api/events`
- **Request Body:**
  ```json
  {
    "title": "New Event",
    "description": "Event description",
    "eventDate": "2024-12-25T10:00:00",
    "location": "Location",
    "maxParticipants": 100
  }
  ```

#### Register for Event
- **POST** `/api/events/{eventId}/register`
- **Response:** Event DTO

#### Unregister from Event
- **DELETE** `/api/events/{eventId}/register`

## Database

The application uses H2 file-based persistent database. Data is stored in the `data/` folder. You can access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/exercise-db`
- Username: `sa`
- Password: (empty)

**Note:** The database files (`*.mv.db`, `*.trace.db`) are stored in the `data/` folder and persist between application restarts.

## Security

- Passwords are encrypted using BCrypt
- JWT tokens are used for authentication
- Token expiration: 24 hours (configurable in `application.properties`)

## Example Usage

### 1. Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### 3. Get all events (with token)
```bash
curl -X GET http://localhost:8080/api/events \
  -H "Authorization: Bearer <your-token>"
```

### 4. Get my registered events
```bash
curl -X GET http://localhost:8080/api/events/my-events \
  -H "Authorization: Bearer <your-token>"
```

### 5. Register for an event
```bash
curl -X POST http://localhost:8080/api/events/1/register \
  -H "Authorization: Bearer <your-token>"
```

