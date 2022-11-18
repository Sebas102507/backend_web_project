package com.example.restserver.controller;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.restserver.model.Author;
import com.example.restserver.model.AuthorByBook;
import com.example.restserver.model.Book;
import com.example.restserver.model.BookCopy;
import com.example.restserver.model.Person;
import com.example.restserver.model.PersonBookLend;
import com.example.restserver.model.PersonBookReturn;
import com.example.restserver.repository.AuthorByBookRepository;
import com.example.restserver.repository.AuthorRepository;
import com.example.restserver.repository.BookCopyRepository;
import com.example.restserver.repository.BookRepository;
import com.example.restserver.repository.PersonBookLendRepository;
import com.example.restserver.repository.PersonBookReturnRepository;
import com.example.restserver.repository.PersonRepository;

import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/book")
public class BookController {


    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookCopyRepository bookCopyRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorByBookRepository authorByBookRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonBookLendRepository personBookLendRepository;


    @Autowired
    PersonBookReturnRepository personBookReturnRepository;

    


    //////////////////////////////////REST ENDPOINTS/////////////////////////////////////////////////





    /////////////////////BOOK//////////////////////////////

    static final String GETALLBOOKS="/list";
    static final String GETONEBOOK="/list/{id}";
    static final String ADDBOOK="/list/newBook";
    static final String DELETEBOOK="/list/{id}/deleteBook";
    static final String EDITBOOK="/list/editBook";

    ///////////////////////////////////////////////////







    /////////////////////AUTHOR//////////////////////////////
    static final String GETONEAUTHOR="/list/authors/{id}";
    static final String GETBOOKAUTHORS="/list/{id}/authors";
    static final String GETAUTHORS="/list/authors";
    static final String ADDAUTHORBYBOOK="/list/{id}/authorsbybook";


    ///////////////////////////////////////////////////





    /////////////////////COPIES//////////////////////////////

    static final String GETBOOKCOPIES="/list/copies/{id}";
    static final String GETAVAILABLEBOOKCOPIES="/list/av_copies/{id}";
    static final String GETBOOKCOPY="/list/copy/{id}";
    static final String ADDNEWBOOKCOPY="/list/newCopy/{id}";
    static final String DELETEBOOKCOPY="/list/deleteCopy/{id}";

    ///////////////////////////////////////////////////





    /////////////////////PERSON//////////////////////////////

    static final String GETPERSONS="/list/persons";
    static final String GETONEPERSON="/list/persons/{id}";

    ///////////////////////////////////////////////////






    /////////////////////LENDS//////////////////////////////

    static final String ADDLENDBOOKCOPY="/list/newlendBookCopy";

    static final String GETLENDSBOOKCOPY="/list/lendBookCopy";

    static final String DELETELENDSBOOKCOPY="/list/{id}/deletelendBookCopy";

    ///////////////////////////////////////////////////


    /////////////////////Return//////////////////////////////

    static final String ADDRETURNBOOKCOPY="/list/newReturnBookCopy";

    static final String GETALLRETURNBOOKCOPY="/list/allreturnBookCopy";

    
    ///////////////////////////////////////////////////
    




    /////////////////////////////////////////////////////////////////////////////////////////////




    @Operation(summary = "Retrieves all the books.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETALLBOOKS)
    public List<Book> getAllBooks(){
        List<Book> books= bookRepository.findAll();
        return books;
    }


    @Operation(summary = "Retrieves one book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETONEBOOK)
    public Book findBook(@PathVariable Long id){
        Book book= bookRepository.findById(id).get();
        return book;
    }


    @Operation(summary = "Retrieves book authors.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETBOOKAUTHORS)
    public List<Author> findBookAuthors(@PathVariable Long id){
        List<Author> authors= bookRepository.findAllAuthorsByBook(id);
        return authors;
    }


    @Operation(summary = "Retrieves all authors.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETAUTHORS)
    public List<Author> findAllAuthors(){
        List<Author> authors= authorRepository.findAll();
        return authors;
    }


    @Operation(summary = "Retrieves one book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETONEAUTHOR)
    public Author findOneAuthor(@PathVariable Long id){
        Author author= authorRepository.findById(id).get();
        return author;
    }
    

    @Operation(summary = "Add new book.") 
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @PostMapping(ADDBOOK)
    public Book addBook(@RequestBody Book book){
        log.info("book {}", book.toString());
        bookRepository.save(book);
        log.info("book {}", book.toString());
        return book;
    }


    @Operation(summary = "Delete book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @DeleteMapping(DELETEBOOK)
    public void deleteBook(@PathVariable Long id){
        log.info("Delete id {}",id);
        List<AuthorByBook> authorsByBook= authorByBookRepository.findAllAuthorsByBookByBook(id);

        for (AuthorByBook authorByBook: authorsByBook){
            this.authorByBookRepository.delete(authorByBook);
        }

        Book currentBook= this.bookRepository.findById(id).get();
        log.info(currentBook.toString());
        this.bookCopyRepository.findAllCopiesByBook(id).forEach(copy->{
            this.deleteBookCopy(copy.getId());
        });
        bookRepository.delete(currentBook);
    }


    @Operation(summary = "Edit book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @PutMapping(EDITBOOK)
    public Book editBook(@RequestBody Book book){
        log.info("Edit book {}", book.getId());
        log.info(book.toString());
        bookRepository.save(book);
        return book;
    }



    /////////////////Book Copies//////////////////

    @Operation(summary = "Retrieves one book copy.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETBOOKCOPY)
    public BookCopy getBookCopy(@PathVariable Long id){
        log.info("Getting copy of id {}",id);
        BookCopy copy= bookCopyRepository.findById(id).get();
        return copy;
    }

    @Operation(summary = "Retrieves all the copies of a book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETBOOKCOPIES)
    public List<BookCopy> getAllBookCopies(@PathVariable Long id){
        log.info("Getting copies of  id {}",id);
        List<BookCopy> copies= bookCopyRepository.findAllCopiesByBook(id);
        copies.forEach((copy)->log.info("Copy  name: {}",copy.getBook().getId()));
        return copies;
    }


    @Operation(summary = "Retrieves all the copies of a book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETAVAILABLEBOOKCOPIES)
    public List<BookCopy> getAvailableBookCopies(@PathVariable Long id){
        log.info("Getting copies of  id {}",id);
        List<BookCopy> copies= bookCopyRepository.findAllAvailableCopiesByBook(id);
        copies.forEach((copy)->log.info("Copy  name: {}",copy.getBook().getId()));
        return copies;
    }


   @Operation(summary = "Add new book copy.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @PostMapping(ADDNEWBOOKCOPY)
    public void addBookCopy(@PathVariable Long id){
        Book currentBook= this.findBook(id);
        BookCopy copy= new BookCopy(currentBook,LocalDateTime.now());
        log.info("book {}", copy.toString());
        bookCopyRepository.save(copy);
    }


    @Operation(summary = "Delete book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @DeleteMapping(DELETEBOOKCOPY)
    public void deleteBookCopy(@PathVariable Long id){
        log.info("Delete id {}",id);
        BookCopy currentCopy= this.bookCopyRepository.findById(id).get();
        log.info(currentCopy.toString());
        bookCopyRepository.delete(currentCopy);
    }


    @Operation(summary = "Add new book copy.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @PostMapping(ADDAUTHORBYBOOK)
    public void addAuthorByBook(@RequestBody List<Author>authors,@PathVariable Long id){
        Book book= bookRepository.findById(id).get();
        for (Author authorByBook: authors){
            log.info("ADDING AUTHOR BY BOOK");
            log.info("BOOK ID: "+book.id);
            log.info("AUTHOR: "+authorByBook.getName());
            authorByBookRepository.save(new AuthorByBook(book,authorByBook));
        }
    }


/////////////////Person//////////////////

    @Operation(summary = "Retrieves all the persons.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETPERSONS)
    public List<Person> getAllPersons(){
        List<Person> persons= personRepository.findAll();
        return persons;
    }


    @Operation(summary = "Retrieves one book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETONEPERSON)
    public Person findPerson(@PathVariable Long id){
        Person person= personRepository.findById(id).get();
        return person;
    }

/////////////////Lend Handle//////////////////

    @Operation(summary = "Add new lend book copy request.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @PostMapping(ADDLENDBOOKCOPY)
    public void addNewLendBookCopy(@RequestBody PersonBookLend personBookLend){
        log.info("Lend info Copy Id: "+personBookLend.getCopy().getId());
        //log.info("Lend info Copy Id: "+personBookLend.getCopy().getId());
        log.info("Lend info Person Name: "+personBookLend.getPerson().getName());
        personBookLendRepository.save(personBookLend);
        log.info("NEW BOOK LEND ID: : "+personBookLend.getId());
    }



    @Operation(summary = "Retrieves all the books.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETLENDSBOOKCOPY)
    public List<PersonBookLend> getAllCurrentLends(){
        List<PersonBookLend> curretLends= personBookLendRepository.findAll();
        return curretLends;
    }



    @Operation(summary = "Delete book.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @DeleteMapping(DELETELENDSBOOKCOPY)
    public void deleteLend(@PathVariable Long id){
        log.info("Delete id {}",id);
        PersonBookLend personBookLend= this.personBookLendRepository.findById(id).get();
        log.info(personBookLend.toString());
        this.personBookLendRepository.delete(personBookLend);
    }



    /////////////////Return Handle//////////////////
    @Operation(summary = "Add new lend book copy request.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @PostMapping(ADDRETURNBOOKCOPY)
    public void addNewReturnBookCopy(@RequestBody PersonBookReturn personBookReturn){
        log.info("Lend info Copy Id: "+personBookReturn.getReturnCopy().getId());
        log.info("Lend info Person Name: "+personBookReturn.getReturnPerson().getName());
        personBookReturnRepository.save(personBookReturn);
    }


    @Operation(summary = "Retrieves all the books.")
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    @GetMapping(GETALLRETURNBOOKCOPY)
    public List<PersonBookReturn> getAllReturns(){
        List<PersonBookReturn> allReturns= personBookReturnRepository.findAll();
        return allReturns;
    }



} 
