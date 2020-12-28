package ru.otus.spring.web.dto;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

public class GenreDto {
    private String id;
    private String genreName;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(Long.toString(genre.getId()), genre.getName());
    }

    public GenreDto(String id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
