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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class AuthorService {

    private final AuthorRepo authorRepo;
    private final ModelMapper modelMapper;
    public Integer getNumber() {
        return new Random().nextInt(100);
    }

    public AuthorService(AuthorRepo authorRepo, ModelMapper modelMapper) {
        this.authorRepo = authorRepo;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<AuthorDto> createAuthor(AuthorDto authorDto)
    {
        authorDto.setName(authorDto.getName()+getNumber());
        authorDto.setEmail(authorDto.getEmail()+getNumber());
        AuthorEntity authorEntity = modelMapper.map(authorDto, AuthorEntity.class);
        return ResponseEntity.ok(modelMapper
                .map(authorRepo.save(authorEntity), AuthorDto.class));
    }

    public ResponseEntity<List<AuthorDto>> getAll()
    {
        return ResponseEntity.ok(authorRepo
                .findAll()
                .stream()
                .map(auth -> modelMapper.map(auth, AuthorDto.class))
                .collect(Collectors.toList()));
    }

    public ResponseEntity<AuthorDto> getAuthorById(Long id)
    {
        AuthorDto authorDto=modelMapper.map(authorRepo.findById(id),AuthorDto.class);
        return ResponseEntity.ok(authorDto);
    }

    public ResponseEntity<List<AuthorDto>> getAuthorByName(String name)
    {
        List<AuthorEntity> authorEntities=authorRepo.findByNameContaining(name);


        return ResponseEntity.ok(authorEntities.stream()
                .map(auth -> modelMapper.map(auth,AuthorDto.class))
                .toList());
    }

    public Boolean deleteAuthor(Long id)
    {
        if(!authorRepo.existsById(id)) throw new ResourceNotFound("No author found with id -> "+ id);

        authorRepo.deleteById(id);

        return true;

    }




}
