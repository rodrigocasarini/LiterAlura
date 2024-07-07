package br.com.alura.literalura.dto;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="authors")
public class Author{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private Integer birth;
    private Integer death;


    @OneToMany(mappedBy= "author" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<Book>();

    public Author(){}

    public Author(String title, AuthorDTO authorDTO){
        this.name = authorDTO.name();
        this.birth = authorDTO.birth();
        this.death = authorDTO.death();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth() {
        return birth;
    }

    public void setBirth(Integer birth) {
        this.birth = birth;
    }

    public Integer getDeath() {
        return death;
    }

    public void setDeath(Integer death) {
        this.death = death;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBooks(Book book) {
        book.setAuthor(this);
        this.books.add(book);
    }
public void setBooks(Book book){
        this.books.add(book);
}
    @Override
    public String toString() {
       return  "\n----------- Autor -----------" +
                "\nNome: " + name +
                "\nAno de Nascimento: " + birth +
                "\nAno de Falecimento: " + death +
                "\n------------------------------\n";
    }
}