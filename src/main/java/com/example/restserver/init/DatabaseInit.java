package com.example.restserver.init;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.example.restserver.model.Author;
import com.example.restserver.model.AuthorByBook;
import com.example.restserver.model.Book;
import com.example.restserver.model.BookCopy;
import com.example.restserver.model.Person;
import com.example.restserver.repository.AuthorByBookRepository;
import com.example.restserver.repository.AuthorRepository;
import com.example.restserver.repository.BookCopyRepository;
import com.example.restserver.repository.BookRepository;
import com.example.restserver.repository.PersonRepository;

@Component
@Profile("devel")
public class DatabaseInit implements ApplicationRunner{

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

    @Override
    public void run(ApplicationArguments args) throws Exception {


        List<Person> persons= Arrays.asList(
            new Person("Person1"),
            new Person("Person2"),
            new Person("Person3"),
            new Person("Person4"),
            new Person("Person5"),
            new Person("Person6"),
            new Person("Person7"),
            new Person("Person8"),
            new Person("Person9"),
            new Person("Person10"),
            new Person("Person11"),
            new Person("Person12"),
            new Person("Person13"),
            new Person("Person14"),
            new Person("Person15"),
            new Person("Person16"),
            new Person("Person17"),
            new Person("Person18"),
            new Person("Person19"),
            new Person("Person20")
        );


        for (Person person: persons){
            personRepository.save(person);
        }


        List<Author> authors= Arrays.asList(
            new Author("Author1"),
            new Author("Author2"),
            new Author("Author3"),
            new Author("Author4"),
            new Author("Author5"),
            new Author("Author6"),
            new Author("Author7"),
            new Author("Author8"),
            new Author("Author9"),
            new Author("Author10"),
            new Author("Author11"),
            new Author("Author12")
        );


        for (Author auth: authors){
            authorRepository.save(auth);
        }



        List<Book> books= Arrays.asList(
        new Book("Book100", "isbn1", "https://kbimages1-a.akamaihd.net/54a4b628-b12d-4d4c-88be-44ad83af4ce0/353/569/90/False/harry-potter-and-the-sorcerer-s-stone-the-first-book-in-the-phenomenal-internationally-bestselling-harry-potter-series-by-j-k-rowling.jpg"),
        new Book("Book101", "isbn2", "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F6%2F2018%2F03%2F91xl8vzglyl-2000.jpg"),
        new Book("Book102", "isbn3", "https://m.media-amazon.com/images/I/51VnBfFdVBL.jpg"),
        new Book("Book103", "isbn4", "https://cdn.lifehack.org/wp-content/uploads/2015/03/Hobbit_book.jpg"),
        new Book("Book104", "isbn5", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSs6dMPIs8h70fdlkaVDbGJXJ_vyjr5rvESyQ&usqp=CAU"),
        new Book("Book105", "isbn6", "https://www.oberlo.com/media/1603897583-image16-1.jpg?w=1824&fit=max"),
        new Book("Book106", "isbn7", "https://bestlifeonline.com/wp-content/uploads/sites/3/2020/10/Harry-Potter-and-the-Chamber-of-Secrets-book-cover.jpg"),
        new Book("Book107", "isbn8", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxGIYXhy5zOFjIxAiUrVvzbl92P9iWO1sF1Q&usqp=CAU"),
        new Book("Book109", "isbn9", "https://www.ka-writing.com/wp-content/uploads/2014/09/life_of_pi.jpg"),
        new Book("Book110", "isbn10", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbQ83Wnf_zrvaLmu5aJdN-sC5sH93fZsbW2A&usqp=CAU"),
        new Book("Book111", "isbn11", "https://dennybradburybooks.files.wordpress.com/2011/05/the-witches.jpg"),
        new Book("Book112", "isbn12", "https://www.creatopy.com/blog/wp-content/uploads/2020/08/The-Godfather-Book-Cover-399x600.jpg"),
        new Book("Book113", "isbn13", "https://hips.hearstapps.com/vader-prod.s3.amazonaws.com/1607103555-51wmv-2LokL.jpg?crop=1xw:0.993xh;center,top&resize=480:*"),
        new Book("Book114", "isbn14", "https://cdn.lifehack.org/wp-content/uploads/2015/03/purple.jpg"),
        new Book("Book115", "isbn15", "https://www.oberlo.com/media/1603897574-image30-1.jpg?w=1824&fit=max"),
        new Book("Book116", "isbn16", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1554382721i/44784133.jpg")
        );

        for (Book b: books){
            bookRepository.save(b);
            AuthorByBook authorByBook= new AuthorByBook(b,authors.get(0));
            AuthorByBook authorByBook2= new AuthorByBook(b,authors.get(1));
            AuthorByBook authorByBook3= new AuthorByBook(b,authors.get(3));
            authorByBookRepository.save(authorByBook);
            authorByBookRepository.save(authorByBook2);
            authorByBookRepository.save(authorByBook3);
            for (int i=0; i<6; i++){
                BookCopy newCopy= new BookCopy(b,LocalDateTime.now());
                bookCopyRepository.save(newCopy);
                b.getCopies().add(newCopy);
            }
        }
    }
    
}
