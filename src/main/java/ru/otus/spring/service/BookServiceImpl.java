package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookRepository;

    @Autowired
    public BookServiceImpl(BookDao BookDao) {
        this.bookRepository = BookDao;
    }

    @Override
    @HystrixCommand(groupKey = "GenreService", fallbackMethod = "getCachedBookById", commandKey = "getBookById",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")}
    )
    public Book getById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    @HystrixCommand(groupKey = "GenreService", fallbackMethod = "getCachedBookByName", commandKey = "getBookByName",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")})
    public Book getByName(String name) {
        return bookRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    @HystrixCommand(groupKey = "BookService", fallbackMethod = "getAllCachedBooks", commandKey = "getAllBooks",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")})
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void insert(Book book) {
        bookRepository.saveAndFlush(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        bookRepository.saveAndFlush(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
        bookRepository.flush();
    }

    public Book getCachedBookById(long id) {

        return new Book(id,"Cached book" + id, null, null);
    }

    public Book getCachedBookByName(String name) {
        return new Book(1,"Cached book" + name, null, null);
    }

    public List<Book> getAllCachedBooks() {
        return new ArrayList<Book>();
    }

}
