# Bonus Question: User Profile API

A comprehensive REST API for managing user profiles built with Spring Boot.

---

## How to Run

### Requirements
- Java 17
- Maven

Run the application:

```bash
mvn spring-boot:run
```

API base URL:

```
http://localhost:8080
```

---

## API Endpoints

### 1. Get all users
**GET** `/api/users`  
Response: `200 OK`

---

### 2. Get user by ID
**GET** `/api/users/{userId}`  
Response: `200 OK` or `404 NOT FOUND`

---

### 3. Search by username
**GET** `/api/users/search/username?username={username}`

Example:
```
/api/users/search/username?username=john
```

Response: `200 OK`

---

### 4. Search by country
**GET** `/api/users/search/country?country={country}`

Example:
```
/api/users/search/country?country=USA
```

Response: `200 OK`

---

### 5. Search by age range
**GET** `/api/users/search/age-range?min={min}&max={max}`

Example:
```
/api/users/search/age-range?min=20&max=30
```

Response: `200 OK`

---

### 6. Get active users
**GET** `/api/users/active`  
Response: `200 OK`

---

### 7. Get inactive users
**GET** `/api/users/inactive`  
Response: `200 OK`


### 8. Create user
**POST** `/api/users`  
Request body: `UserProfile JSON`  
Response: `201 CREATED` or `400 BAD REQUEST`


### 9. Update user
**PUT** `/api/users/{userId}`  
Request body: Updated `UserProfile JSON`

Response:
- `200 OK`
- `404 NOT FOUND`
- `400 BAD REQUEST`


### 10. Activate user
**PATCH** `/api/users/{userId}/activate`  
Response: `200 OK` or `404 NOT FOUND`


### 11. Deactivate user
**PATCH** `/api/users/{userId}/deactivate`  
Response: `200 OK` or `404 NOT FOUND`

### 12. Delete user
**DELETE** `/api/users/{userId}`  
Response: `204 NO CONTENT` or `404 NOT FOUND`


## Sample UserProfile Object

```json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "age": 25,
  "country": "USA",
  "bio": "Software developer from California",
  "active": true
}
```


## Sample ApiResponse

```json
{
  "success": true,
  "message": "User profile created successfully",
  "data": {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "age": 25,
    "country": "USA",
    "bio": "Software developer from California",
    "active": true
  }
}
```


## Initial Users

The application loads 6 sample users at startup.

### Active (4)
- john_doe
- jane_smith
- alice_williams
- emma_wilson

### Inactive (2)
- bob_johnson
- charlie_brown

Countries represented:
USA, Canada, UK, Australia, Germany

Age range:
22–35 years


## Testing Evidence

- Screenshots: `/screenshots`
- Postman collection:  
  `/postman-collection/user-api-postman-collection.json`


## Features

- Full CRUD operations
- Advanced search filters
- Activation/deactivation support
- Structured API responses
- Sample data preload
- Postman testing collection

