package com.Library.ManagementSys.services;

import com.Library.ManagementSys.dto.AuthorDto;
import com.Library.ManagementSys.dto.BookDto;
import com.Library.ManagementSys.entity.AuthorEntity;
import com.Library.ManagementSys.entity.BookEntity;
import com.Library.ManagementSys.exceptions.ResourceNotFound;
import com.Library.ManagementSys.repositories.AuthorRepo;
import com.Library.ManagementSys.repositories.BookRepo;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BookService
{
    private final BookRepo bookRepo;
    private final ModelMapper modelMapper;
    private final AuthorRepo authorRepo;

    public Integer getNumber(Integer i) {
        return new Random().nextInt(i);
    }

    public BookService(BookRepo bookRepo, ModelMapper modelMapper, AuthorRepo authorRepo) {
        this.bookRepo = bookRepo;
        this.modelMapper = modelMapper;
        this.authorRepo = authorRepo;
    }

    public ResponseEntity<BookDto> createBook(BookDto bookDto)
    {
        bookDto.setPublishedYear(bookDto.getPublishedYear()-getNumber(2000));
        bookDto.setTitle(bookDto.getTitle()+getNumber(100));
        BookEntity bookEntity=modelMapper.map(bookDto,BookEntity.class);
        return ResponseEntity.ok(modelMapper
                .map(bookRepo.save(bookEntity), BookDto.class));
    }

    public ResponseEntity<List<BookDto>> getAll()
    {
        return ResponseEntity.ok(bookRepo
                .findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList()));
    }

    public ResponseEntity<BookDto> updateBookAuthor(Long bookId, Long authorId)
    {
        BookEntity bookEntity= bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFound("No book found with id ->"+bookId));
        AuthorEntity authorEntity=authorRepo.findById(authorId).orElseThrow(()->new ResourceNotFound("No Author found with id ->"+authorId));

        bookEntity.setAuthor(authorEntity);


        return new ResponseEntity<>(modelMapper.map(bookRepo.save(bookEntity),BookDto.class), HttpStatus.OK);

    }

    public ResponseEntity<BookDto> getBookById(Long bookId)
    {
        return new ResponseEntity<>(modelMapper
                .map(bookRepo.findById(bookId),BookDto.class),HttpStatus.OK);
    }

    public ResponseEntity<List<BookDto>> getBookAfterDate(Integer year)
    {
        List<BookEntity> bookEntity = bookRepo.findByPublishedYearAfter(year);

        bookEntity.stream()
                .map(book -> modelMapper.map(book,BookDto.class))
                .toList();

        return new ResponseEntity<>(bookEntity.stream()
                .map(book -> modelMapper.map(book,BookDto.class))
                .toList(),HttpStatus.OK);
    }

    public Boolean deleteById(Long id)
    {
        if(!bookRepo.existsById(id))
        {
            throw new ResourceNotFound("No book found with id ->" + id);
        }

        bookRepo.deleteById(id);

        return true;
    }

    public ResponseEntity<List<BookDto>> findByAuthor(Long id)
    {
        List<BookEntity> bookEntities=bookRepo.findAll();

        List<BookDto> bookDtos = bookEntities.stream()
                .filter(book -> book.getAuthor().getId().equals(id))
                .map(x->modelMapper.map(x,BookDto.class))
                .toList();

        return new ResponseEntity<>(bookDtos,HttpStatus.OK);


    }

    public ResponseEntity<List<BookDto>> getBookByTitle(String title)
    {
        List<BookEntity> bookEntities = bookRepo.findByTitleContaining(title);


        return ResponseEntity.ok(bookEntities.stream()
                .map(book -> modelMapper.map(book,BookDto.class))
                .toList());
    }


}
