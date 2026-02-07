package auca.ac.rw.question1LibraryApi.controller;

import auca.ac.rw.question1LibraryApi.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with 3 sample books
    public BookController() {
        books.add(new Book(nextId++, "Clean Code", "Robert Martin", "978-0132350884", 2008));
        books.add(new Book(nextId++, "Effective Java", "Joshua Bloch", "978-0134685991", 2018));
        books.add(new Book(nextId++, "Spring in Action", "Craig Walls", "978-1617294945", 2020));
    }

    // 1. GET /api/books - Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(books);
    }

    // 2. GET /api/books/{id} - Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. GET /api/books/search?title={title} - Search books by title
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> result = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
        return ResponseEntity.ok(result);
    }

    // 4. POST /api/books - Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book newBook) {
        newBook.setId(nextId++);
        books.add(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    // 5. DELETE /api/books/{id} - Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        return removed ? 
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : 
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}