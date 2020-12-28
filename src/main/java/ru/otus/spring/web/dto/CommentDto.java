package ru.otus.spring.web.dto;

import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

public class CommentDto {
    private String id;
    private String text;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(Long.toString(comment.getId()), comment.getText());
    }

    public CommentDto(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
