package br.com.alura.literalura.repository;

import br.com.alura.literalura.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book getByTitle(String title);

    List<Book> findAll();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.languages LIKE %:languages%")
    long countBookLanguages(String languages);

    List<Book> findBookByLanguages(String languages);
}
