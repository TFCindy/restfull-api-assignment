package auca.ac.rw.bonusUserApi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.rw.bonusUserApi.model.ApiResponse;
import auca.ac.rw.bonusUserApi.model.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private List<UserProfile> userProfiles = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with sample user profiles
    public UserProfileController() {
        userProfiles.add(new UserProfile(nextId++, "john_doe", "john@example.com", 
                "John Doe", 25, "USA", "Software developer from California", true));
        userProfiles.add(new UserProfile(nextId++, "jane_smith", "jane@example.com", 
                "Jane Smith", 30, "Canada", "Data scientist from Toronto", true));
        userProfiles.add(new UserProfile(nextId++, "bob_johnson", "bob@example.com", 
                "Bob Johnson", 22, "UK", "Student at London University", false));
        userProfiles.add(new UserProfile(nextId++, "alice_williams", "alice@example.com", 
                "Alice Williams", 28, "Australia", "Graphic designer from Sydney", true));
        userProfiles.add(new UserProfile(nextId++, "charlie_brown", "charlie@example.com", 
                "Charlie Brown", 35, "USA", "Project manager from New York", false));
        userProfiles.add(new UserProfile(nextId++, "emma_wilson", "emma@example.com", 
                "Emma Wilson", 26, "Germany", "Marketing specialist from Berlin", true));
    }

    // 1. GET /api/users - Get all user profiles
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUsers() {
        ApiResponse<List<UserProfile>> response = ApiResponse.success(
                "All user profiles retrieved successfully", 
                userProfiles
        );
        return ResponseEntity.ok(response);
    }

    // 2. GET /api/users/{userId} - Get user profile by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserById(@PathVariable Long userId) {
        Optional<UserProfile> user = userProfiles.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
        
        if (user.isPresent()) {
            ApiResponse<UserProfile> response = ApiResponse.success(
                    "User profile retrieved successfully", 
                    user.get()
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<UserProfile> response = ApiResponse.error(
                    "User profile with ID " + userId + " not found"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 3. GET /api/users/search/username?username={username} - Search by username
    @GetMapping("/search/username")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByUsername(
            @RequestParam String username) {
        List<UserProfile> result = userProfiles.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase()))
                .toList();
        
        String message = result.isEmpty() ? 
                "No users found with username containing: " + username :
                "Users found with username containing: " + username;
        
        ApiResponse<List<UserProfile>> response = ApiResponse.success(message, result);
        return ResponseEntity.ok(response);
    }

    // 4. GET /api/users/search/country?country={country} - Search by country
    @GetMapping("/search/country")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByCountry(
            @RequestParam String country) {
        List<UserProfile> result = userProfiles.stream()
                .filter(user -> user.getCountry().equalsIgnoreCase(country))
                .toList();
        
        String message = result.isEmpty() ? 
                "No users found from country: " + country :
                "Users found from country: " + country;
        
        ApiResponse<List<UserProfile>> response = ApiResponse.success(message, result);
        return ResponseEntity.ok(response);
    }

    // 5. GET /api/users/search/age-range?min={min}&max={max} - Search by age range
    @GetMapping("/search/age-range")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByAgeRange(
            @RequestParam int min,
            @RequestParam int max) {
        
        List<UserProfile> result = userProfiles.stream()
                .filter(user -> user.getAge() >= min && user.getAge() <= max)
                .toList();
        
        String message = result.isEmpty() ? 
                "No users found in age range " + min + "-" + max :
                "Users found in age range " + min + "-" + max;
        
        ApiResponse<List<UserProfile>> response = ApiResponse.success(message, result);
        return ResponseEntity.ok(response);
    }

    // 6. GET /api/users/active - Get active users
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getActiveUsers() {
        List<UserProfile> result = userProfiles.stream()
                .filter(UserProfile::isActive)
                .toList();
        
        ApiResponse<List<UserProfile>> response = ApiResponse.success(
                "Active users retrieved successfully", 
                result
        );
        return ResponseEntity.ok(response);
    }

    // 7. GET /api/users/inactive - Get inactive users
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getInactiveUsers() {
        List<UserProfile> result = userProfiles.stream()
                .filter(user -> !user.isActive())
                .toList();
        
        ApiResponse<List<UserProfile>> response = ApiResponse.success(
                "Inactive users retrieved successfully", 
                result
        );
        return ResponseEntity.ok(response);
    }

    // 8. POST /api/users - Create new user profile
    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> createUser(@RequestBody UserProfile newUser) {
        // Check if username already exists
        boolean usernameExists = userProfiles.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(newUser.getUsername()));
        
        if (usernameExists) {
            ApiResponse<UserProfile> response = ApiResponse.error(
                    "Username '" + newUser.getUsername() + "' already exists"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        // Check if email already exists
        boolean emailExists = userProfiles.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(newUser.getEmail()));
        
        if (emailExists) {
            ApiResponse<UserProfile> response = ApiResponse.error(
                    "Email '" + newUser.getEmail() + "' already exists"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        newUser.setUserId(nextId++);
        newUser.setActive(true); // New users are active by default
        userProfiles.add(newUser);
        
        ApiResponse<UserProfile> response = ApiResponse.success(
                "User profile created successfully", 
                newUser
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 9. PUT /api/users/{userId} - Update user profile
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUser(
            @PathVariable Long userId, 
            @RequestBody UserProfile updatedUser) {
        
        for (int i = 0; i < userProfiles.size(); i++) {
            if (userProfiles.get(i).getUserId().equals(userId)) {
                // Check if new username conflicts with other users
                boolean usernameConflict = userProfiles.stream()
                        .filter(user -> !user.getUserId().equals(userId))
                        .anyMatch(user -> user.getUsername().equalsIgnoreCase(updatedUser.getUsername()));
                
                if (usernameConflict) {
                    ApiResponse<UserProfile> response = ApiResponse.error(
                            "Username '" + updatedUser.getUsername() + "' already exists"
                    );
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                
                // Check if new email conflicts with other users
                boolean emailConflict = userProfiles.stream()
                        .filter(user -> !user.getUserId().equals(userId))
                        .anyMatch(user -> user.getEmail().equalsIgnoreCase(updatedUser.getEmail()));
                
                if (emailConflict) {
                    ApiResponse<UserProfile> response = ApiResponse.error(
                            "Email '" + updatedUser.getEmail() + "' already exists"
                    );
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                
                updatedUser.setUserId(userId);
                userProfiles.set(i, updatedUser);
                
                ApiResponse<UserProfile> response = ApiResponse.success(
                        "User profile updated successfully", 
                        updatedUser
                );
                return ResponseEntity.ok(response);
            }
        }
        
        ApiResponse<UserProfile> response = ApiResponse.error(
                "User profile with ID " + userId + " not found"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 10. PATCH /api/users/{userId}/activate - Activate user profile
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<UserProfile>> activateUser(@PathVariable Long userId) {
        Optional<UserProfile> foundUser = userProfiles.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
        
        if (foundUser.isPresent()) {
            UserProfile user = foundUser.get();
            user.setActive(true);
            
            ApiResponse<UserProfile> response = ApiResponse.success(
                    "User profile activated successfully", 
                    user
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<UserProfile> response = ApiResponse.error(
                    "User profile with ID " + userId + " not found"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 11. PATCH /api/users/{userId}/deactivate - Deactivate user profile
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<ApiResponse<UserProfile>> deactivateUser(@PathVariable Long userId) {
        Optional<UserProfile> foundUser = userProfiles.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
        
        if (foundUser.isPresent()) {
            UserProfile user = foundUser.get();
            user.setActive(false);
            
            ApiResponse<UserProfile> response = ApiResponse.success(
                    "User profile deactivated successfully", 
                    user
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<UserProfile> response = ApiResponse.error(
                    "User profile with ID " + userId + " not found"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 12. DELETE /api/users/{userId} - Delete user profile
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        boolean removed = userProfiles.removeIf(user -> user.getUserId().equals(userId));
        
        if (removed) {
            ApiResponse<Void> response = ApiResponse.success(
                    "User profile deleted successfully"
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } else {
            ApiResponse<Void> response = ApiResponse.error(
                    "User profile with ID " + userId + " not found"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
