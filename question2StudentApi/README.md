# Question 2: Student Registration API

A RESTful API for student registration and information management.

## How to Run
1. Java 17 and Maven required
2. Run: `mvn spring-boot:run`
3. API: `http://localhost:8080`

## Endpoints

### 1. GET /api/students
- Get all students
- Response: `200 OK`

### 2. GET /api/students/{studentId}
- Get student by ID
- Response: `200 OK` or `404 NOT FOUND`

### 3. GET /api/students/major/{major}
- Get students by major
- Example: `/api/students/major/Computer Science`
- Response: `200 OK`

### 4. GET /api/students/filter?gpa={minGpa}
- Filter students with GPA >= minGpa
- Example: `/api/students/filter?gpa=3.5`
- Response: `200 OK`

### 5. POST /api/students
- Register new student
- Request body: Student JSON
- Response: `201 CREATED`

### 6. PUT /api/students/{studentId}
- Update student information
- Request body: Updated Student JSON
- Response: `200 OK` or `404 NOT FOUND`

## Testing Scenarios

### Scenario 1: Filter by Computer Science Major
- Endpoint: `GET /api/students/major/Computer Science`
- Expected: 3 Computer Science students

### Scenario 2: Filter by GPA >= 3.5
- Endpoint: `GET /api/students/filter?gpa=3.5`
- Expected: Students with GPA 3.5 or higher

## Testing Evidence
Screenshots available in `/screenshots` folder.
Postman collection: `/postman-collection/student-api-postman-collection.json`