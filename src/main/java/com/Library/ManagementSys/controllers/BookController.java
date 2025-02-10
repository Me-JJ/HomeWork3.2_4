package com.Library.ManagementSys.controllers;

import com.Library.ManagementSys.dto.AuthorDto;
import com.Library.ManagementSys.dto.BookDto;
import com.Library.ManagementSys.entity.BookEntity;
import com.Library.ManagementSys.services.BookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto)
    {
        return bookService.createBook(bookDto);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll()
    {
        return bookService.getAll();
    }

    @PutMapping(path = "/{bookId}/bookauth/{authId}")
    public ResponseEntity<BookDto> updateBookAuthor(@PathVariable Long bookId,@PathVariable Long authId)
    {
        return bookService.updateBookAuthor(bookId,authId);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable  Long id)
    {
        return bookService.getBookById(id);
    }

    @GetMapping(path = "/year/{date}")
    public ResponseEntity<List<BookDto>> getBookAfterYear(@PathVariable Integer date)
    {
        return bookService.getBookAfterDate(date);
    }

    @GetMapping(path = "/title/{title}")
    public ResponseEntity<List<BookDto>> getBookAfterYear(@PathVariable String title)
    {
        return bookService.getBookByTitle(title);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteBookById(@PathVariable Long id)
    {
        if(bookService.deleteById(id)) return ResponseEntity.ok(true);

        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/findByAuthor/{authId}")
    public ResponseEntity<List<BookDto>> findBookByAuthorId(@PathVariable Long authId)
    {
        return bookService.findByAuthor(authId);
    }

}
