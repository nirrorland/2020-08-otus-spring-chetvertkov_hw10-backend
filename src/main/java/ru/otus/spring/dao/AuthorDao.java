package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao  extends JpaRepository<Author, Long> {

    Optional<Author> findById(long id);

    List<Author> findAll();

    Optional<Author> findByName(String name);

    Optional<Author> findByNameIgnoreCase(String name);

}
