# Question 3: Restaurant Menu API

A RESTful API for restaurant menu management.

## How to Run
1. Java 17 and Maven required
2. Run: `mvn spring-boot:run`
3. API: `http://localhost:8080`

## Endpoints

### 1. GET /api/menu
- Get all menu items
- Response: `200 OK`

### 2. GET /api/menu/{id}
- Get specific menu item
- Response: `200 OK` or `404 NOT FOUND`

### 3. GET /api/menu/category/{category}
- Get items by category
- Categories: "Appetizer", "Main Course", "Dessert", "Beverage"
- Example: `/api/menu/category/Appetizer`
- Response: `200 OK`

### 4. GET /api/menu/available
- Get available items
- Query parameter: `?available=true` (default) or `?available=false`
- Example: `/api/menu/available?available=false`
- Response: `200 OK`

### 5. GET /api/menu/search?name={name}
- Search menu items by name
- Example: `/api/menu/search?name=chicken`
- Response: `200 OK`

### 6. POST /api/menu
- Add new menu item
- Request body: MenuItem JSON
- Response: `201 CREATED`

### 7. PUT /api/menu/{id}/availability
- Toggle item availability
- Response: `200 OK` or `404 NOT FOUND`

### 8. DELETE /api/menu/{id}
- Remove menu item
- Response: `204 NO CONTENT` or `404 NOT FOUND`

## Sample MenuItem Object
```json
{
  "id": 1,
  "name": "Garlic Bread",
  "description": "Fresh baked bread with garlic butter",
  "price": 4.99,
  "category": "Appetizer",
  "available": true
}
```
## Challenge: Menu Items
Created 10 menu items across all categories:

Appetizers (2): Garlic Bread, Bruschetta

Main Courses (3): Grilled Chicken, Vegetable Pasta, Beef Steak

Desserts (2): Chocolate Cake, Cheesecake

Beverages (3): Orange Juice, Coffee, Iced Tea

## Testing Evidence
Screenshots available in /screenshots folder.
Postman collection: /postman-collection/Restaurant Menu API.json