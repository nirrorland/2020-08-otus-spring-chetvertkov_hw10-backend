package ru.otus.spring.web.dto;

import ru.otus.spring.domain.Author;

public class AuthorDto {
    private String id;
    private String authorName;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(Long.toString(author.getId()), author.getName());
    }

    public AuthorDto(String id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
