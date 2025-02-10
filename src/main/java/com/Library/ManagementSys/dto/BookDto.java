package com.Library.ManagementSys.dto;

import com.Library.ManagementSys.entity.AuthorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;
    @NotBlank(message = "Book Title should not be blank")
    @Length(min = 3,max = 100,message = "book title should be of min length 10 & max 100")
    private String title;

//    @JsonIgnore
    private AuthorDto author;

    @Min(value = 0,message = "year < 0")
    @Max(value = 2025,message = "year > 2025")
    private Integer publishedYear;

    @NotBlank(message = "Genre should not be blank")
    private String genre;

    @PastOrPresent(message = "published year is in the future !!")
    private LocalDateTime createDate;

    @PastOrPresent(message = "published year is in the future !!")
    private LocalDateTime updatedDate;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public @Min(value = 0, message = "year < 0") @Max(value = 2025, message = "year > 2025") Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(@Min(value = 0, message = "year < 0") @Max(value = 2025, message = "year > 2025") Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public @NotBlank(message = "Genre should not be blank") String getGenre() {
        return genre;
    }

    public void setGenre(@NotBlank(message = "Genre should not be blank") String genre) {
        this.genre = genre;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
