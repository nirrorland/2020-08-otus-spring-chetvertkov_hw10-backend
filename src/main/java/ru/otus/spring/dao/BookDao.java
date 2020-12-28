package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Long> {

    Optional<Book> findById(long id);

    List<Book> findAll();

    Optional<Book> findByName(String name);

    Optional<Book> findByNameIgnoreCase(String name);

    Book save(Book book);

  //  void update(Book book);

    void deleteById(long id);
}
