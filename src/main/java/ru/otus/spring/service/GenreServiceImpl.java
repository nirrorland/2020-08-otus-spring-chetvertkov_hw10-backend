package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreRepository;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreRepository = genreDao;
    }

    @Override
    @HystrixCommand(groupKey = "GenreService", fallbackMethod = "getCachedGenreById", commandKey = "getGenreById",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")})
    public Genre getById(int id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    @HystrixCommand(groupKey = "GenreService", fallbackMethod = "getAllCachedGenres", commandKey = "getAllGenres",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")})
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    @HystrixCommand(groupKey = "GenreService", fallbackMethod = "getCachedGenreByName", commandKey = "getGenreByName",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")})
    public Genre getByName(String name) {
        return genreRepository.findByNameIgnoreCase(name).orElse(null);
    }

    public Genre getCachedGenreById(int id) {
        return new Genre(id, "CachedGenre" + 1);
    }

    public Genre getCachedGenreByName(String name) {
        return new Genre((int)(Math.random() * 50 + 1), "CachedGenre" + name);
    }

    public List<Genre> getAllCachedGenres() {
        List<Genre> result = new ArrayList<>();
        result.add(getCachedGenreById(1));
        result.add(getCachedGenreById(2));
        return result;
    }
}
