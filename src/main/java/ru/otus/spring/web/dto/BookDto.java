package ru.otus.spring.web.dto;

import ru.otus.spring.domain.Book;

public class BookDto {
    private String id;
    private String name;
    private String author;
    private String genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }



    public BookDto(String id, String name, String author, String genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public static BookDto toDto(Book book) {
        return new BookDto(Long.toString(book.getId()), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
    }




}
