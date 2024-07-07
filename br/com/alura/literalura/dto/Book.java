package br.com.alura.literalura.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    private String nameAuthor;
    private Integer download;
    private String languages;

    @ManyToOne
    private Author author;

    public Book(){}

    public Book(BookDTO bookDTO){
        this.title = bookDTO.title();
        this.nameAuthor = bookDTO.author().get(0).name();
        this.download = bookDTO.download();
        this.languages = bookDTO.languages().get(0).toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
public Author getAuthor(){
        return author;
        }
        public void setAuthor(Author author){
        this.author = author;
        }

    @Override
    public String toString() {
        return "---------------- Livro ----------------" +
                "\n Título='" + title + "\n" +
                ", Autor='" + nameAuthor + "\n" +
                ", Número de downloads=" + download + "\n"+
                ", Língua ='" + languages + "\n" +
                "---------------------------------------\n";
    }
}

