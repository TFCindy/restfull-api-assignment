# Question 5: Task Management API

A RESTful API for task/to-do list management.

## How to Run
1. Java 17 and Maven required
2. Run: `mvn spring-boot:run`
3. API: `http://localhost:8080`

## Endpoints

### 1. GET /api/tasks
- Get all tasks
- Response: `200 OK`

### 2. GET /api/tasks/{taskId}
- Get task by ID
- Response: `200 OK` or `404 NOT FOUND`

### 3. GET /api/tasks/status?completed={true/false}
- Get tasks by completion status
- Example: `/api/tasks/status?completed=true`
- Response: `200 OK`

### 4. GET /api/tasks/priority/{priority}
- Get tasks by priority
- Priority values: "LOW", "MEDIUM", "HIGH"
- Example: `/api/tasks/priority/HIGH`
- Response: `200 OK`

### 5. POST /api/tasks
- Create new task
- Request body: Task JSON
- Priority validation: Only "LOW", "MEDIUM", "HIGH" allowed
- Date format: "YYYY-MM-DD" required
- Response: `201 CREATED` or `400 BAD REQUEST`

### 6. PUT /api/tasks/{taskId}
- Update task
- Request body: Updated Task JSON
- Response: `200 OK`, `404 NOT FOUND`, or `400 BAD REQUEST`

### 7. PATCH /api/tasks/{taskId}/complete
- Mark task as completed
- Response: `200 OK` or `404 NOT FOUND`

### 8. DELETE /api/tasks/{taskId}
- Delete task
- Response: `204 NO CONTENT` or `404 NOT FOUND`

## Sample Task Object
```json
{
  "taskId": 1,
  "title": "Complete Spring Boot Assignment",
  "description": "Finish all 5 questions for REST API assignment",
  "completed": false,
  "priority": "HIGH",
  "dueDate": "2026-02-10"
}
```
## Initial Tasks
Created 6 sample tasks with different priorities and completion status:

High Priority (2): Spring Assignment, Exam Preparation

Medium Priority (2): Buy Groceries, Workout

Low Priority (2): Call Mom, Clean Room

Completed (2): Call Mom, Clean Room

Pending (4): Spring Assignment, Buy Groceries, Exam Preparation, Workout

## Testing Evidence
Screenshots available in /screenshots folder.
Postman collection: /postman-collection/Task Management API.json