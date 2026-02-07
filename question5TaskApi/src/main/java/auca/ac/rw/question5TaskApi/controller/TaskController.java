package auca.ac.rw.question5TaskApi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.rw.question5TaskApi.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with sample tasks
    public TaskController() {
        tasks.add(new Task(nextId++, "Complete Spring Boot Assignment", 
                "Finish all 5 questions for REST API assignment", false, "HIGH", "2026-02-10"));
        tasks.add(new Task(nextId++, "Buy Groceries", 
                "Milk, eggs, bread, fruits", false, "MEDIUM", "2026-02-06"));
        tasks.add(new Task(nextId++, "Call Mom", 
                "Weekly check-in call", true, "LOW", "2026-02-05"));
        tasks.add(new Task(nextId++, "Prepare for Exam", 
                "Study chapters 1-5 for upcoming test", false, "HIGH", "2026-02-15"));
        tasks.add(new Task(nextId++, "Clean Room", 
                "Organize desk and do laundry", true, "LOW", "2026-02-04"));
        tasks.add(new Task(nextId++, "Workout", 
                "Gym session - cardio and weights", false, "MEDIUM", "2026-02-07"));
    }

    // 1. GET /api/tasks - Get all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    // 2. GET /api/tasks/{taskId} - Get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();
        
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. GET /api/tasks/status?completed={true/false} - Get tasks by completion status
    @GetMapping("/status")
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam Boolean completed) {
        List<Task> result = tasks.stream()
                .filter(task -> task.isCompleted() == completed)
                .toList();
        return ResponseEntity.ok(result);
    }

    // 4. GET /api/tasks/priority/{priority} - Get tasks by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        List<Task> result = tasks.stream()
                .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                .toList();
        return ResponseEntity.ok(result);
    }

    // 5. POST /api/tasks - Create new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
        // Validate priority
        if (!isValidPriority(newTask.getPriority())) {
            return ResponseEntity.badRequest().build();
        }
        
        // Validate date format (simple check)
        if (!isValidDateFormat(newTask.getDueDate())) {
            return ResponseEntity.badRequest().build();
        }
        
        newTask.setTaskId(nextId++);
        tasks.add(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    // 6. PUT /api/tasks/{taskId} - Update task
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, 
                                           @RequestBody Task updatedTask) {
        // Validate priority
        if (!isValidPriority(updatedTask.getPriority())) {
            return ResponseEntity.badRequest().build();
        }
        
        // Validate date format
        if (!isValidDateFormat(updatedTask.getDueDate())) {
            return ResponseEntity.badRequest().build();
        }
        
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId().equals(taskId)) {
                updatedTask.setTaskId(taskId);
                tasks.set(i, updatedTask);
                return ResponseEntity.ok(updatedTask);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 7. PATCH /api/tasks/{taskId}/complete - Mark task as completed
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long taskId) {
        Optional<Task> foundTask = tasks.stream()
                .filter(task -> task.getTaskId().equals(taskId))
                .findFirst();
        
        if (foundTask.isPresent()) {
            Task task = foundTask.get();
            task.setCompleted(true);
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 8. DELETE /api/tasks/{taskId} - Delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean removed = tasks.removeIf(task -> task.getTaskId().equals(taskId));
        return removed ? 
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : 
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Helper method to validate priority
    private boolean isValidPriority(String priority) {
        return priority != null && 
               (priority.equalsIgnoreCase("LOW") || 
                priority.equalsIgnoreCase("MEDIUM") || 
                priority.equalsIgnoreCase("HIGH"));
    }

    // Helper method to validate date format (simple validation)
    private boolean isValidDateFormat(String date) {
        if (date == null || date.length() != 10) 
            return false;
        // Simple format check: YYYY-MM-DD
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}
