package com.Library.ManagementSys.controllers;

import com.Library.ManagementSys.dto.AuthorDto;
import com.Library.ManagementSys.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/author")
public class AuthorController
{
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid AuthorDto authorDto)
    {
        return authorService.createAuthor(authorDto);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAll()
    {
        return authorService.getAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> findAuthorById(@PathVariable Long id)
    {
        System.out.println("IIDIDIDIIDIDIDI");
        return authorService.getAuthorById(id);
//        return new ResponseEntity<>(authorService.getAuthorById(id).getBody(),HttpStatus.OK);
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<List<AuthorDto>> findAuthorByName(@PathVariable String name)
    {
        return authorService.getAuthorByName(name);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteAuthorByName(@PathVariable Long id)
    {
        if( authorService.deleteAuthor(id)) return ResponseEntity.ok(true);

        return ResponseEntity.notFound().build();
    }




}
