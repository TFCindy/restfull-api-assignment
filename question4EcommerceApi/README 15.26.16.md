# Question 4: E-Commerce Product API

A RESTful API for e-commerce product catalog management.

## How to Run
1. Java 17 and Maven required
2. Run: `mvn spring-boot:run`
3. API: `http://localhost:8080`

## Endpoints

### 1. GET /api/products
- Get all products with pagination
- Query params: `page` (default: 1), `limit` (default: 10)
- Example: `/api/products?page=2&limit=5`
- Response: `200 OK`

### 2. GET /api/products/{productId}
- Get product details by ID
- Response: `200 OK` or `404 NOT FOUND`

### 3. GET /api/products/category/{category}
- Get products by category
- Example: `/api/products/category/Electronics`
- Response: `200 OK`

### 4. GET /api/products/brand/{brand}
- Get products by brand
- Example: `/api/products/brand/Apple`
- Response: `200 OK`

### 5. GET /api/products/search?keyword={keyword}
- Search products by keyword in name or description
- Example: `/api/products/search?keyword=iphone`
- Response: `200 OK`

### 6. GET /api/products/price-range?min={min}&max={max}
- Get products within price range
- Example: `/api/products/price-range?min=50&max=200`
- Response: `200 OK`

### 7. GET /api/products/in-stock
- Get products with stockQuantity > 0
- Response: `200 OK`

### 8. POST /api/products
- Add new product
- Request body: Product JSON
- Response: `201 CREATED`

### 9. PUT /api/products/{productId}
- Update product details
- Request body: Updated Product JSON
- Response: `200 OK` or `404 NOT FOUND`

### 10. PATCH /api/products/{productId}/stock?quantity={quantity}
- Update stock quantity
- Example: `/api/products/1/stock?quantity=100`
- Response: `200 OK` or `404 NOT FOUND`

### 11. DELETE /api/products/{productId}
- Delete product
- Response: `204 NO CONTENT` or `404 NOT FOUND`

## Sample Product Object
```json
{
  "productId": 1,
  "name": "iPhone 15",
  "description": "Latest Apple smartphone",
  "price": 999.99,
  "category": "Electronics",
  "stockQuantity": 50,
  "brand": "Apple"
}
```
## Sample Products Created
Created 12 products across categories:

Electronics (3): iPhone 15, Samsung TV, MacBook Pro

Clothing (3): Nike Air Max, Levi's Jeans, Adidas T-Shirt

Books (2): Clean Code, The Alchemist

Home & Kitchen (2): Coffee Maker, Blender

Beauty (2): Perfume, Face Cream

## Testing Evidence
Screenshots available in /screenshots folder.
Postman collection: /postman-collection/E-Commerce Product API.json