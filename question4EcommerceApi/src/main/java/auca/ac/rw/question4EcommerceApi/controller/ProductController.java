package auca.ac.rw.question4EcommerceApi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.rw.question4EcommerceApi.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<Product> products = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with at least 10 products
    public ProductController() {
        // Electronics
        products.add(new Product(nextId++, "iPhone 15", "Latest Apple smartphone", 999.99, "Electronics", 50, "Apple"));
        products.add(new Product(nextId++, "Samsung TV", "55 inch 4K Smart TV", 699.99, "Electronics", 30, "Samsung"));
        products.add(new Product(nextId++, "MacBook Pro", "Apple laptop with M3 chip", 1299.99, "Electronics", 25, "Apple"));
        
        // Clothing
        products.add(new Product(nextId++, "Nike Air Max", "Running shoes", 129.99, "Clothing", 100, "Nike"));
        products.add(new Product(nextId++, "Levi's Jeans", "Blue denim jeans", 79.99, "Clothing", 75, "Levi's"));
        products.add(new Product(nextId++, "Adidas T-Shirt", "Cotton sports t-shirt", 29.99, "Clothing", 0, "Adidas")); // Out of stock
        
        // Books
        products.add(new Product(nextId++, "Clean Code", "Software development book", 39.99, "Books", 40, "Pearson"));
        products.add(new Product(nextId++, "The Alchemist", "Novel by Paulo Coelho", 14.99, "Books", 60, "HarperCollins"));
        
        // Home & Kitchen
        products.add(new Product(nextId++, "Coffee Maker", "Automatic coffee machine", 89.99, "Home & Kitchen", 20, "Keurig"));
        products.add(new Product(nextId++, "Blender", "High-speed kitchen blender", 49.99, "Home & Kitchen", 35, "Ninja"));
        
        // Beauty
        products.add(new Product(nextId++, "Perfume", "Women's fragrance", 59.99, "Beauty", 15, "Chanel"));
        products.add(new Product(nextId++, "Face Cream", "Moisturizing cream", 29.99, "Beauty", 0, "Nivea")); // Out of stock
        
        // Total: 12 products (exceeds 10 requirement)
    }

    // 1. GET /api/products - Get all products (with pagination)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        
        int startIndex = (page - 1) * limit;
        int endIndex = Math.min(startIndex + limit, products.size());
        
        if (startIndex >= products.size()) {
            return ResponseEntity.ok(new ArrayList<>()); // Empty page
        }
        
        List<Product> pagedProducts = products.subList(startIndex, endIndex);
        return ResponseEntity.ok(pagedProducts);
    }

    // 2. GET /api/products/{productId} - Get product details
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();
        
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. GET /api/products/category/{category} - Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> result = products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();
        return ResponseEntity.ok(result);
    }

    // 4. GET /api/products/brand/{brand} - Get products by brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> result = products.stream()
                .filter(product -> product.getBrand().equalsIgnoreCase(brand))
                .toList();
        return ResponseEntity.ok(result);
    }

    // 5. GET /api/products/search?keyword={keyword} - Search products
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> result = products.stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                  product.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
        return ResponseEntity.ok(result);
    }

    // 6. GET /api/products/price-range?min={min}&max={max} - Price range filter
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        
        List<Product> result = products.stream()
                .filter(product -> product.getPrice() >= min && product.getPrice() <= max)
                .toList();
        return ResponseEntity.ok(result);
    }

    // 7. GET /api/products/in-stock - Get products with stock > 0
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> result = products.stream()
                .filter(product -> product.getStockQuantity() > 0)
                .toList();
        return ResponseEntity.ok(result);
    }

    // 8. POST /api/products - Add new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        newProduct.setProductId(nextId++);
        products.add(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    // 9. PUT /api/products/{productId} - Update product details
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, 
                                                 @RequestBody Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                updatedProduct.setProductId(productId);
                products.set(i, updatedProduct);
                return ResponseEntity.ok(updatedProduct);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 10. PATCH /api/products/{productId}/stock - Update stock quantity
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStockQuantity(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        
        Optional<Product> foundProduct = products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
        
        if (foundProduct.isPresent()) {
            Product product = foundProduct.get();
            product.setStockQuantity(quantity);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 11. DELETE /api/products/{productId} - Delete product
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean removed = products.removeIf(product -> product.getProductId().equals(productId));
        return removed ? 
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : 
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
