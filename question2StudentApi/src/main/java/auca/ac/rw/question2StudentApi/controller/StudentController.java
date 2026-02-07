package auca.ac.rw.question2StudentApi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.rw.question2StudentApi.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with 5 sample students as required
    public StudentController() {
        students.add(new Student(nextId++, "John", "Doe", "john@example.com", "Computer Science", 3.8));
        students.add(new Student(nextId++, "Jane", "Smith", "jane@example.com", "Mathematics", 3.5));
        students.add(new Student(nextId++, "Bob", "Johnson", "bob@example.com", "Computer Science", 3.2));
        students.add(new Student(nextId++, "Alice", "Williams", "alice@example.com", "Physics", 3.9));
        students.add(new Student(nextId++, "Charlie", "Brown", "charlie@example.com", "Computer Science", 3.7));
    }

    // 1. GET /api/students - Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    // 2. GET /api/students/{studentId} - Get student by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();
        
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. GET /api/students/major/{major} - Get all students by major
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        List<Student> result = students.stream()
                .filter(student -> student.getMajor().equalsIgnoreCase(major))
                .toList();
        return ResponseEntity.ok(result);
    }

    // 4. GET /api/students/filter?gpa={minGpa} - Filter students with GPA >= minGpa
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudentsByGPA(@RequestParam Double gpa) {
        List<Student> result = students.stream()
                .filter(student -> student.getGpa() >= gpa)
                .toList();
        return ResponseEntity.ok(result);
    }

    // 5. POST /api/students - Register a new student
    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student newStudent) {
        newStudent.setStudentId(nextId++);
        students.add(newStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    // 6. PUT /api/students/{studentId} - Update student information
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, 
                                                 @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                updatedStudent.setStudentId(studentId);
                students.set(i, updatedStudent);
                return ResponseEntity.ok(updatedStudent);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
