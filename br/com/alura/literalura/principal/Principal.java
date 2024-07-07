package br.com.alura.literalura.principal;

import br.com.alura.literalura.dto.Author;
import br.com.alura.literalura.dto.BookDTO;
import br.com.alura.literalura.dto.Book;
import br.com.alura.literalura.dto.ResultDTO;
import br.com.alura.literalura.service.APIAcess;
import br.com.alura.literalura.service.ConvertData;
import br.com.alura.literalura.service.LiteraluraService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner read = new Scanner(System.in);
    private APIAcess apiAcess = new APIAcess();
    private final String ADDRESS = "https://gutendex.com/books/?search=";
    private ConvertData convert = new ConvertData();
    private Book book = new Book();
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private LiteraluraService literaluraService = new LiteraluraService();

    public Principal(LiteraluraService literaluraService) {
        this.literaluraService = literaluraService;
    }

    public void displayMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Search book by title;
                    2 - List registered books;
                    3 - List registered authors;
                    4 - List living authors in the year;
                    5 - List books by language;
                    0 - Exit;
                    """;
            System.out.println(menu);
            option = read.nextInt();
            read.nextLine();

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listLivingAuthorsInTheYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Exit!");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public BookDTO searchBookByTitle() {
        System.out.println("Enter the title of a book:");
        var booktitle = read.nextLine();
        var json = apiAcess.getAddress(ADDRESS + booktitle.replace(" ", "+"));

        ResultDTO resultDTO = convert.getData(json, ResultDTO.class);
        List<BookDTO> bookDTO = new ArrayList<>();
        bookDTO = resultDTO.books();
        try {
            book = new Book(bookDTO.get(0));

            List<Author> authors = bookDTO.stream()
                    .limit(1)
                    .flatMap(b -> b.author().stream()
                            .map(d -> new Author(b.title(), d)))
                    .toList();

            authors.forEach(System.out::println);
            Author author = authors.get(0);

            book.setAuthor(author);
            literaluraService.saveBook(book);

        } catch (Exception e) {
            System.out.println("Book not found!");
        }
        return null;
    }


    private void listRegisteredBooks() {
        books = literaluraService.findAll();
        books.stream().sorted(Comparator.comparing(Book::getNameAuthor))
                .forEach(System.out::println);
    }

    private void listRegisteredAuthors() {
        authors = literaluraService.findAllAuthors();
        authors.stream().sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    private void listLivingAuthorsInTheYear() {
        System.out.println("Enter the year to search? ");
        var year = read.nextInt();
        authors = literaluraService.authorLiveYear(year);
        authors.stream().sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    private void listBooksByLanguage() {
        System.out.println("Enter Languages?\n" +
                "Portuguese = pt\n" +
                "English = en\n" +
                "Spanish = es\n" +
                "French = fr");
        var searchLanguages = read.nextLine();
        Integer type = literaluraService.countBookLanguages(searchLanguages);

        var translating = enumLanguages(searchLanguages);

        if (type == 0) {
            System.out.println("book not found in this language" + translating);
        }
        if (type == 1) {
            System.out.println("Book found" + translating);
        } else {
            System.out.println("Found " + type + "books " + translating);
        }
        listBooksLanguage(searchLanguages);
    }

    private void listBooksLanguage(String languages) {
        books = literaluraService.findBookByLanguages(languages);
        books.forEach(System.out::println);
    }

        private String enumLanguages(String languages){
        var traslate = "";
            switch (languages) {
                case "pt": traslate = "Portuguese";
                    break;
                case "en": traslate = "English";
                    break;
                case "es": traslate = "Spanish";
                    break;
                case "fr": traslate = "French";
                    break;
            }
            return traslate;
        }
}