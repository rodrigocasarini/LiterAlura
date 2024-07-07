package br.com.alura.literalura.repository;

import br.com.alura.literalura.dto.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByNameAndBirthAndDeath(String name, Integer birth, Integer death);

    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birth AND a.death")
    List<Author> authorLiveYear(Integer year);

}
