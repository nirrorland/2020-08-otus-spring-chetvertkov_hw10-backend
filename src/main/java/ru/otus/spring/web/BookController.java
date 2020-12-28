package ru.otus.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.BookStorage;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.web.dto.BookDto;
import ru.otus.spring.web.dto.CommentDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;
    private final BookStorage bookStorage;

    @Autowired
    public BookController(BookService bookService, CommentService commentService, BookStorage bookStorage) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.bookStorage = bookStorage;
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookStorage.getAllBooks().stream().map(BookDto::toDto)
                .collect(Collectors.toList()));
    }


    @PostMapping("/book")
    public ResponseEntity<Book> addBook(@RequestBody BookDto bookDto) {
        try {
            bookStorage.insertBook(bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre());
            Book addedBook = bookStorage.getBookByName(bookDto.getName());
            return ResponseEntity.ok(addedBook);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);
        }

    }


    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getById(id);
        return ResponseEntity.ok(BookDto.toDto(book));
    }


    @PutMapping("/book")
    public ResponseEntity<Book> updateBook(@RequestBody BookDto bookDto) {
        try {
            Book beforeUpdatedBook = bookService.getById(Long.parseLong(bookDto.getId()));
            bookStorage.updateBook(beforeUpdatedBook.getName(), bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre());
            Book updatedBook = bookStorage.getBookByName(bookDto.getName());
            return ResponseEntity.ok(updatedBook);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);
        }

    }


    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/book/{id}/comments")
    public ResponseEntity<List<CommentDto>> getBookCommentsForBook(@PathVariable("id") Long id) {
        Book book = bookService.getById(id);
        List<Comment> comments = bookStorage.getCommentsForBook(book.getName());
        return ResponseEntity.ok(comments.stream().map(CommentDto::toDto)
                .collect(Collectors.toList()));
    }
}
