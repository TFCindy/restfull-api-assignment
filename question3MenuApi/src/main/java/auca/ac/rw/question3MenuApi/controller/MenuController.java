package auca.ac.rw.question3MenuApi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.rw.question3MenuApi.model.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private List<MenuItem> menuItems = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with at least 8 menu items across categories
    public MenuController() {
        // Appetizers
        menuItems.add(new MenuItem(nextId++, "Garlic Bread", "Fresh baked bread with garlic butter", 4.99, "Appetizer", true));
        menuItems.add(new MenuItem(nextId++, "Bruschetta", "Toasted bread with tomatoes and basil", 5.99, "Appetizer", true));
        
        // Main Courses
        menuItems.add(new MenuItem(nextId++, "Grilled Chicken", "Grilled chicken breast with vegetables", 14.99, "Main Course", true));
        menuItems.add(new MenuItem(nextId++, "Vegetable Pasta", "Pasta with fresh vegetables and tomato sauce", 12.99, "Main Course", true));
        menuItems.add(new MenuItem(nextId++, "Beef Steak", "8oz steak with mashed potatoes", 18.99, "Main Course", false)); // Not available
        
        // Desserts
        menuItems.add(new MenuItem(nextId++, "Chocolate Cake", "Rich chocolate cake with ice cream", 6.99, "Dessert", true));
        menuItems.add(new MenuItem(nextId++, "Cheesecake", "New York style cheesecake", 7.99, "Dessert", true));
        
        // Beverages
        menuItems.add(new MenuItem(nextId++, "Orange Juice", "Freshly squeezed orange juice", 3.99, "Beverage", true));
        menuItems.add(new MenuItem(nextId++, "Coffee", "Fresh brewed coffee", 2.99, "Beverage", true));
        menuItems.add(new MenuItem(nextId++, "Iced Tea", "Cold brewed tea with lemon", 2.99, "Beverage", false)); // Not available
        
        // Total: 10 items (exceeds 8 requirement)
    }

    // 1. GET /api/menu - Get all menu items
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItems);
    }

    // 2. GET /api/menu/{id} - Get specific menu item
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        Optional<MenuItem> item = menuItems.stream()
                .filter(menuItem -> menuItem.getId().equals(id))
                .findFirst();
        
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. GET /api/menu/category/{category} - Get items by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable String category) {
        List<MenuItem> result = menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .toList();
        return ResponseEntity.ok(result);
    }

    // 4. GET /api/menu/available - Get only available items
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems(@RequestParam(required = false, defaultValue = "true") Boolean available) {
        List<MenuItem> result = menuItems.stream()
                .filter(item -> item.isAvailable() == available)
                .toList();
        return ResponseEntity.ok(result);
    }

    // 5. GET /api/menu/search?name={name} - Search menu items by name
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItemsByName(@RequestParam String name) {
        List<MenuItem> result = menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        return ResponseEntity.ok(result);
    }

    // 6. POST /api/menu - Add new menu item
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem newMenuItem) {
        newMenuItem.setId(nextId++);
        menuItems.add(newMenuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMenuItem);
    }

    // 7. PUT /api/menu/{id}/availability - Toggle item availability
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        Optional<MenuItem> foundItem = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
        
        if (foundItem.isPresent()) {
            MenuItem item = foundItem.get();
            item.setAvailable(!item.isAvailable()); // Toggle availability
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 8. DELETE /api/menu/{id} - Remove menu item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));
        return removed ? 
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : 
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
