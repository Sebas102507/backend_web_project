package com.example.restserver;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
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
import com.example.restserver.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationTest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerIntegrationTest {
    

     @LocalServerPort
     private int port;    


     @Autowired
     TestRestTemplate rest;


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

     private Logger log = LoggerFactory.getLogger(getClass());


    private List<Book> books;



     @BeforeEach
     void init(){
        
        List<Person> persons= Arrays.asList(
            new Person("Person1"),
            new Person("Person2"),
            new Person("Person3"),
            new Person("Person4"),
            new Person("Person5")
        );


        for (Person person: persons){
            personRepository.save(person);
            log.info("Person id: {}", person.getId());
        }


        List<Author> authors= Arrays.asList(
            new Author("Author1"),
            new Author("Author2"),
            new Author("Author3"),
            new Author("Author4"),
            new Author("Author5"),
            new Author("Author6"),
            new Author("Author7")
        );


        for (Author auth: authors){
            authorRepository.save(auth);
            log.info("Author id: {}", auth.getId());
        }


        List<Book> books= Arrays.asList(
        new Book("Book100", "isbn1", "https://kbimages1-a.akamaihd.net/54a4b628-b12d-4d4c-88be-44ad83af4ce0/353/569/90/False/harry-potter-and-the-sorcerer-s-stone-the-first-book-in-the-phenomenal-internationally-bestselling-harry-potter-series-by-j-k-rowling.jpg"),
        new Book("Book101", "isbn2", "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F6%2F2018%2F03%2F91xl8vzglyl-2000.jpg"),
        new Book("Book102", "isbn3", "https://m.media-amazon.com/images/I/51VnBfFdVBL.jpg"),
        new Book("Book103", "isbn4", "https://cdn.lifehack.org/wp-content/uploads/2015/03/Hobbit_book.jpg"),
        new Book("Book104", "isbn5", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSs6dMPIs8h70fdlkaVDbGJXJ_vyjr5rvESyQ&usqp=CAU")
        );

        for (Book b: books){
            bookRepository.save(b);
            log.info("book id: {}", b.getId());
            AuthorByBook authorByBook= new AuthorByBook(b,authors.get(0));
            AuthorByBook authorByBook2= new AuthorByBook(b,authors.get(1));
            AuthorByBook authorByBook3= new AuthorByBook(b,authors.get(3));
            authorByBookRepository.save(authorByBook);
            authorByBookRepository.save(authorByBook2);
            authorByBookRepository.save(authorByBook3);
            for (int i=0; i<6; i++){
                BookCopy newCopy= new BookCopy(b,LocalDateTime.now());
                bookCopyRepository.save(newCopy);
                log.info("book copy id : {}", newCopy.getId());
                b.getCopies().add(newCopy);
            }
        }
     }


     /////////////////BOOKS////////////////


     @Test
     @SuppressWarnings("unchecked")
     void getAllBooks(){
        String url= "http://localhost:"+ port + "/book/list";
        Object[] booksRawData = rest.getForObject(url,Object[].class);
        List<Book> books = (List<Book>) (Object) Arrays.asList(booksRawData);    
        assertEquals(books.size(), 5);
     }

     @Test
     void getOneBook(){
        String url= "http://localhost:"+ port + "/book/list/13";
        Book book = rest.getForObject(url,Book.class);
        log.info("book name: {}", book.getTitle());
        assertEquals(book.getTitle(),"Book100"); 
     }


     @Test
     @SuppressWarnings("unchecked")
     void addOneBook(){
        String url= "http://localhost:"+ port + "/book/list/newBook";
        String url2="http://localhost:"+ port + "/book/list";

        Book newBook= new Book("Book105", "isbn6", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSs6dMPIs8h70fdlkaVDbGJXJ_vyjr5rvESyQ&usqp=CAU");
        rest.postForObject(url, newBook,Void.class);
        Object[] booksRawData = rest.getForObject(url2,Object[].class);
        List<Book> books = (List<Book>) (Object) Arrays.asList(booksRawData);    
        assertEquals(books.size(), 6);
     }



     @Test
     @SuppressWarnings("unchecked")
     void deleteBook(){
        String url= "http://localhost:"+ port + "/book/list/13/deleteBook";
        String url2= "http://localhost:"+ port + "/book/list";
        rest.delete(url);
        Object[] booksRawData = rest.getForObject(url2,Object[].class);
        List<Book> books = (List<Book>) (Object) Arrays.asList(booksRawData);    
        assertEquals(books.size(), 4);
     }



     @Test
     void editBook(){
        String url= "http://localhost:"+ port + "/book/list/editBook";
        String url2= "http://localhost:"+ port + "/book/list/13";
        Book editedBook= rest.getForObject(url2,Book.class);
        editedBook.setName("Book100-Edited");
        rest.put(url,editedBook);
        Book resultBook = rest.getForObject(url2,Book.class);
        assertEquals(resultBook.getTitle(), "Book100-Edited");
     }

    /////////////////////////////////

     /////////////AUTHORS////////////////////


     @Test
     @SuppressWarnings("unchecked")
     void getBookAuthors(){
        String url= "http://localhost:"+ port + "/book/list/13/authors";
        Object[] authorsRawData = rest.getForObject(url,Object[].class);
        List<Author> authors = (List<Author>) (Object) Arrays.asList(authorsRawData);    
        assertEquals(authors.size(), 3);
     }

     
     @Test
     @SuppressWarnings("unchecked")
     void getAllAuthors(){
        String url= "http://localhost:"+ port + "/book/list/authors";
        Object[] authorsRawData = rest.getForObject(url,Object[].class);
        List<Author> authors = (List<Author>) (Object) Arrays.asList(authorsRawData);    
        assertEquals(authors.size(), 7);
     }


     @Test
     @SuppressWarnings("unchecked")
     void addAuthorByBook(){
        String url= "http://localhost:"+ port + "/book/list/authors/11";
        String url2= "http://localhost:"+ port + "/book/list/authors/12";
        String url3= "http://localhost:"+ port + "/book/list/13/authorsbybook";
        String url4= "http://localhost:"+ port + "/book/list/13/authors";
        Author author1 = rest.getForObject(url,Author.class);
        Author author2 = rest.getForObject(url2,Author.class);
        List<Author> newAuthors= new ArrayList<Author>();
        newAuthors.add(author1);
        newAuthors.add(author2);
        rest.postForObject(url3, newAuthors, Void.class);
        Object[] authorsRawData = rest.getForObject(url4,Object[].class);
        List<Author> authors = (List<Author>) (Object) Arrays.asList(authorsRawData);    
        assertEquals(authors.size(), 5);
     }

     


     /////////////////////////////////


    //////////////BOOK COPIES//////////////////



     @Test
     void getBookCopy(){
        String url= "http://localhost:"+ port + "/book/list/copy/17";
        BookCopy copy = rest.getForObject(url,BookCopy.class);
        log.info("book name: {}", copy.getBook().getTitle());
        assertEquals(copy.getBook().getTitle(),"Book100"); 
     }

     @Test
     @SuppressWarnings("unchecked")
     void getAllBookCopies(){
        String url= "http://localhost:"+ port + "/book/list/copies/13";
        Object[] copiesRawData = rest.getForObject(url,Object[].class);
        List<BookCopy> copies = (List<BookCopy>) (Object) Arrays.asList(copiesRawData);
        assertEquals(copies.size(),6); 
     }



     @Test
     @SuppressWarnings("unchecked")
     void addCopy(){
        String url= "http://localhost:"+ port + "/book/list/newCopy/13";
        String url2= "http://localhost:"+ port + "/book/list/copies/13";
        rest.postForObject(url,Void.class,Void.class);
        Object[] copiesRawData = rest.getForObject(url2,Object[].class);
        List<BookCopy> copies = (List<BookCopy>) (Object) Arrays.asList(copiesRawData);
        assertEquals(copies.size(),7); 
     }



     @Test
     @SuppressWarnings("unchecked")
     void deleteCopy(){
        String url= "http://localhost:"+ port + "/book/list/deleteCopy/17";
        String url2= "http://localhost:"+ port + "/book/list/copies/13";
        rest.delete(url);
        Object[] copiesRawData = rest.getForObject(url2,Object[].class);
        List<BookCopy> copies = (List<BookCopy>) (Object) Arrays.asList(copiesRawData);
        assertEquals(copies.size(),5); 
     }


     @Test
     @SuppressWarnings("unchecked")
     void getAllAvailableBookCopies(){

        String url= "http://localhost:"+ port + "/book/list/copy/17";
        String url2= "http://localhost:"+ port + "/book/list/persons/1";
        String url3= "http://localhost:"+ port + "/book/list/newlendBookCopy";
        String url4= "http://localhost:"+ port + "/book/list/lendBookCopy";
 
        BookCopy copy = rest.getForObject(url,BookCopy.class);
        Person person = rest.getForObject(url2,Person.class);
        PersonBookLend newLend = new PersonBookLend(copy, person, new Date(10L),new Date(10L));
        rest.postForObject(url3, newLend, Void.class);
        Object[] lendsRawData = rest.getForObject(url4,Object[].class);
        List<PersonBookLend> lends = (List<PersonBookLend>) (Object) Arrays.asList(lendsRawData);    
        assertEquals(lends.size(), 1);

        String url0= "http://localhost:"+ port + "/book/list/av_copies/13";
        Object[] copiesRawData = rest.getForObject(url0,Object[].class);
        List<BookCopy> copies = (List<BookCopy>) (Object) Arrays.asList(copiesRawData);
        assertEquals(copies.size(),5); 
     }


    /////////////////////////////////


    //////////////PERSON//////////////////

    @Test
    @SuppressWarnings("unchecked")
    void getAllPersons(){
       String url= "http://localhost:"+ port + "/book/list/persons";
       Object[] personsRawData = rest.getForObject(url,Object[].class);
       List<Person> persons = (List<Person>) (Object) Arrays.asList(personsRawData);    
       assertEquals(persons.size(), 5);
    }

    @Test
    void getOnePerson(){
       String url= "http://localhost:"+ port + "/book/list/persons/1";
       Person person = rest.getForObject(url,Person.class);
       assertEquals(person.getName(), "Person1");
    }

    /////////////////////////////////

    //////////////LENDS//////////////////


    @Test
    @SuppressWarnings("unchecked")
    void getAllCurrentLends(){
       String url= "http://localhost:"+ port + "/book/list/lendBookCopy";
       Object[] lendsRawData = rest.getForObject(url,Object[].class);
       List<PersonBookLend> lends = (List<PersonBookLend>) (Object) Arrays.asList(lendsRawData);    
       assertEquals(lends.size(), 0);
    }
    


    @Test
    @SuppressWarnings("unchecked")
    void addNewLendBookCopy(){
       String url= "http://localhost:"+ port + "/book/list/copy/17";
       String url2= "http://localhost:"+ port + "/book/list/persons/1";
       String url3= "http://localhost:"+ port + "/book/list/newlendBookCopy";
       String url4= "http://localhost:"+ port + "/book/list/lendBookCopy";

       BookCopy copy = rest.getForObject(url,BookCopy.class);
       Person person = rest.getForObject(url2,Person.class);
       PersonBookLend newLend = new PersonBookLend(copy, person, new Date(10L),new Date(10L));
       rest.postForObject(url3, newLend, Void.class);
       Object[] lendsRawData = rest.getForObject(url4,Object[].class);
       List<PersonBookLend> lends = (List<PersonBookLend>) (Object) Arrays.asList(lendsRawData);    
       assertEquals(lends.size(), 1);
      
    }


    @Test
    @SuppressWarnings("unchecked")
    void deleteLend(){

        String url= "http://localhost:"+ port + "/book/list/copy/17";
        String url2= "http://localhost:"+ port + "/book/list/persons/1";
        String url3= "http://localhost:"+ port + "/book/list/newlendBookCopy";
        String url4= "http://localhost:"+ port + "/book/list/lendBookCopy";
        String ur5= "http://localhost:"+ port + "/book/list/63/deletelendBookCopy";

        BookCopy copy = rest.getForObject(url,BookCopy.class);
        Person person = rest.getForObject(url2,Person.class);
        PersonBookLend newLend = new PersonBookLend(copy, person, new Date(10L),new Date(10L));
        rest.postForObject(url3, newLend, Void.class);

        Object[] lendsRawData = rest.getForObject(url4,Object[].class);
        List<PersonBookLend> lends = (List<PersonBookLend>) (Object) Arrays.asList(lendsRawData); 
        assertEquals(lends.size(), 1);

       rest.delete(ur5);
        Object[] lendsRawData2 = rest.getForObject(url4,Object[].class);
        List<PersonBookLend> lends2 = (List<PersonBookLend>) (Object) Arrays.asList(lendsRawData2); 
        assertEquals(lends2.size(), 0);
    }


    //////////////RETURNS//////////////////

    @Test
    @SuppressWarnings("unchecked")
    void getAllCurrentReturns(){
       String url= "http://localhost:"+ port + "/book/list/allreturnBookCopy";
       Object[] returnRawData = rest.getForObject(url,Object[].class);
       List<PersonBookReturn> returns = (List<PersonBookReturn>) (Object) Arrays.asList(returnRawData);    
       assertEquals(returns.size(), 0);
    }


    @Test
    @SuppressWarnings("unchecked")
    void addNewReturnBookCopy(){
       String url= "http://localhost:"+ port + "/book/list/copy/17";
       String url2= "http://localhost:"+ port + "/book/list/persons/1";
       String url3= "http://localhost:"+ port + "/book/list/newReturnBookCopy";
       String url4= "http://localhost:"+ port + "/book/list/allreturnBookCopy";

       BookCopy copy = rest.getForObject(url,BookCopy.class);
       Person person = rest.getForObject(url2,Person.class);
       PersonBookReturn newReturn = new PersonBookReturn(copy, person, new Date(10L));
       rest.postForObject(url3, newReturn, Void.class);

       Object[] returnRawData = rest.getForObject(url4,Object[].class);
       List<PersonBookReturn> returns = (List<PersonBookReturn>) (Object) Arrays.asList(returnRawData);    
       assertEquals(returns.size(), 1);
      
    }



}
