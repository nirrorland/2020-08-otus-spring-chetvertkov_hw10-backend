package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.bouncycastle.crypto.prng.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorRepository = authorDao;
    }

    @Override
    @HystrixCommand(groupKey = "AuthorService", fallbackMethod = "getCachedAuthorById", commandKey = "getAuthorById",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    public Author getById(int id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    @HystrixCommand(groupKey = "AuthorService", fallbackMethod = "getCachedAuthorByName", commandKey = "getAuthorByName",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    public Author getByName(String name) {
        return authorRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    @HystrixCommand(groupKey = "AuthorService", fallbackMethod = "getAllCachedAuthors", commandKey = "getAllAuthors",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getCachedAuthorById(int id) {
        return new Author(id, "CachedAuthod" + id);
    }

    public Author getCachedAuthorByName(String name) {
        return new Author( (int)(Math.random() * 50 + 1), "CachedAuthod" + name);
    }

    public List<Author> getAllCachedAuthors(){
        List<Author> result = new ArrayList<>();
        result.add(getCachedAuthorById(1));
        result.add(getCachedAuthorById(2));
        return result;
    }
}
