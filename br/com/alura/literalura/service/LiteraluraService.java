package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.Author;
import br.com.alura.literalura.dto.Book;
import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiteraluraService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void saveBook(Book book){
        Book existingBook = bookRepository.getByTitle(book.getTitle());

        if (existingBook == null){
            Author author = book.getAuthor();

            Author existingAuthor = authorRepository.findByNameAndBirthAndDeath(
                    author.getName(),author.getBirth(), author.getDeath());

            if (existingAuthor != null){
                book.setAuthor(existingAuthor);
                existingAuthor.addBooks(book);
            }else {
                Author saveAuthor = authorRepository.save(author);
                book.setAuthor(saveAuthor);
                saveAuthor.addBooks(book);
            }
            bookRepository.save(book);
            System.out.println("new book registered");
        }else {
            System.out.println("book already registered");
        }
    }
        public List<Book> findAll(){
        return bookRepository.findAll();
        }
        public List<Author> findAllAuthors(){
        return authorRepository.findAll();
        }
        public List<Author> authorLiveYear(Integer year){
        return authorRepository.authorLiveYear(year);
        }
        public Integer countBookLanguages(String languages){
        return (int) bookRepository.countBookLanguages(languages);
        }
        public List<Book>findBookByLanguages(String languages){
        return bookRepository.findBookByLanguages(languages);
        }
}
