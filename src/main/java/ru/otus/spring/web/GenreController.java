package ru.otus.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.web.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genre")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAll().stream().map(GenreDto::toDto)
                .collect(Collectors.toList()));
    }
}
