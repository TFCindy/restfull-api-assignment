# Question 1: Library Book Management API

A RESTful API for managing library books.

## How to Run
1. Java 17 and Maven required
2. Run: `mvn spring-boot:run`
3. API: `http://localhost:8080`

## Endpoints

### 1. GET /api/books
- Get all books
- Response: `200 OK`

### 2. GET /api/books/{id}
- Get book by ID
- Response: `200 OK` or `404 NOT FOUND`

### 3. GET /api/books/search?title={title}
- Search books by title
- Example: `/api/books/search?title=clean`
- Response: `200 OK`

### 4. POST /api/books
- Add new book
- Request body: Book JSON
- Response: `201 CREATED`

### 5. DELETE /api/books/{id}
- Delete book by ID
- Response: `204 NO CONTENT` or `404 NOT FOUND`

## Sample Book Object
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert Martin",
  "isbn": "978-0132350884",
  "publicationYear": 2008
}
```
## Testing Evidence
#### Screenshots available in /screenshots folder.
#### Postman collection: /postman-collection/Library Book API.json