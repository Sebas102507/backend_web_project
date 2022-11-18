package com.example.restserver;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ActiveProfiles("systemtest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)

public class BookControllersSystemTest {
    

   private ChromeDriver browser;
   private WebDriverWait wait;
   private String baseUrl;

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
        new Book("Book100", "isbn1", "https://m.media-amazon.com/images/I/51VnBfFdVBL.jpg"),
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


        //System.setProperty("webdriver.chrome.driver","{Your path Chrome Driver}");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
        // options.addArguments("--headless"); // To hide Chrome window
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-extensions"); // disabling extensions
        // options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("start-maximized"); // open Browser in maximized mode
        // options.addArguments("disable-infobars"); // disabling infobars
        // options.addArguments("--disable-dev-shm-usage"); // overcome limited resource
        // problems
        this.browser = new ChromeDriver(options);

        this.wait = new WebDriverWait(browser, Duration.ofSeconds(5));

        this.baseUrl = "http://localhost:4200";

     }

     @AfterEach
     void end() {
         // driver.close();
         browser.quit();
     }


     @Test
     void addBooks(){
      browser.get(baseUrl + "/addBook");
      WebElement titleInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[1]")));
      WebElement isbnInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[2]")));
      WebElement urlImageInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[3]")));

      WebElement addAuthorButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id=\"addAuthorButton\"]")));
   
      addAuthorButton.click();

      WebElement addBookButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id=\"addBookButton\"]")));
   
      addBookButton.click();


      browser.get(baseUrl + "/bookList");
      List<WebElement> books =  browser.findElements(By.xpath("//div[@class=\"bookFrame\"]"));
      System.out.println("----------The SIZE OF THE BOOKS LIST IS: "+books.size());
      assertEquals(6, books.size());

     }

     @Test
     void seeAllBooks(){
      browser.get(baseUrl + "/bookList");
      List<WebElement> books =  browser.findElements(By.xpath("//div[@class=\"bookFrame\"]"));
      System.out.println("----------The SIZE OF THE BOOKS LIST IS: "+books.size());
      assertEquals(5, books.size());
     }



     @Test
     void deleteBook(){
      browser.get(baseUrl + "/bookList");

      List<WebElement> books =  browser.findElements(By.xpath("//div[@class=\"bookFrame\"]"));
    
      System.out.println("-------------THE CURRENT COPIES LENGTH: "+books.size());

      WebElement editBookButton = browser.findElement(By.xpath("(//div[@class=\"bookFrame\"]/div[@class=\"buttons_frame\"]/button[@id=\"deleteButton\"])[1]"));
      editBookButton.click();

      browser.get(baseUrl + "/bookList");
      
      List<WebElement> books2 =  browser.findElements(By.xpath("//div[@class=\"bookFrame\"]"));

      System.out.println("-------------THE CURRENT COPIES LENGTH: "+books2.size());


      assertEquals(4, books2.size());
     }


     @Test
     void editBook(){
      browser.get(baseUrl + "/editBook/13");
      WebElement titleInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[1]")));
      WebElement isbnInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[2]")));
      WebElement urlImageInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[3]")));

      System.out.println("-------------THE TITLE IS: "+titleInput.getAttribute("value"));
      System.out.println("-------------THE ISBN IS: "+isbnInput.getAttribute("value"));
      System.out.println("-------------THE URL_IMAGE IS: "+urlImageInput.getAttribute("value"));


      titleInput.clear();
      isbnInput.clear();
      urlImageInput.clear();
      titleInput.sendKeys("Book999");
      isbnInput.sendKeys("isbn999");
      urlImageInput.sendKeys("https://pub-static.fotor.com/assets/projects/pages/dddda0b59fb9433eb53e7174981c8b67/blue-minimal-novel-cover-6e355184dc3545c6bec6a9f618f83e0d.jpg");

      WebElement finishButton = browser.findElement(By.id("finishButton"));
      finishButton.click();

      browser.get(baseUrl + "/editBook/13");
      WebElement titleInput2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[1]")));
      WebElement isbnInput2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[2]")));
      WebElement urlImageInput2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input)[3]")));

      System.out.println("-------------THE TITLE 2 IS: "+titleInput2.getAttribute("value"));
      System.out.println("-------------THE ISBN 2 IS: "+isbnInput2.getAttribute("value"));
      System.out.println("-------------THE URL_IMAGE 2 IS: "+urlImageInput2.getAttribute("value"));

      assertEquals("Book999",titleInput2.getAttribute("value"));
      assertEquals("isbn999", isbnInput2.getAttribute("value"));
      assertEquals("https://pub-static.fotor.com/assets/projects/pages/dddda0b59fb9433eb53e7174981c8b67/blue-minimal-novel-cover-6e355184dc3545c6bec6a9f618f83e0d.jpg", urlImageInput2.getAttribute("value"));
     
   }


   @Test
   void addBookCopy(){
    browser.get(baseUrl + "/editBook/13");

    List<WebElement> copies =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//div[@class=\"copyMainFrame\"])")));
    
    System.out.println("-------------THE CURRENT COPIES LENGTH: "+copies.size());

    WebElement addCopyButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"mainDiv\"]/div/button[@id=\"addCopyButton\"]")));
   
    addCopyButton.click();

   
    browser.get(baseUrl + "/editBook/13");

    List<WebElement> copies2 =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//div[@class=\"copyMainFrame\"])")));


    System.out.println("-------------THE SECOND CURRENT COPIES LENGTH: "+copies2.size());

   assertEquals(7, copies2.size());

 }


   @Test
   void deleteBookCopy(){
   browser.get(baseUrl + "/editBook/13");

   List<WebElement> copies =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//div[@class=\"copyMainFrame\"])")));
   
   System.out.println("-------------THE CURRENT COPIES LENGTH: "+copies.size());

   WebElement deleteCopyButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class=\"copyMainFrame\"]/div[@id=\"buttonDiv\"]/button)[1]")));
   
   deleteCopyButton.click();

   
   browser.get(baseUrl + "/editBook/13");

   List<WebElement> copies2 =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//div[@class=\"copyMainFrame\"])")));


   System.out.println("-------------THE SECOND CURRENT COPIES LENGTH: "+copies2.size());

   assertEquals(5, copies2.size());

}



///////////LEND////////////////////////////////

@Test
void addBookLend(){
 browser.get(baseUrl + "/lendBookCopy/13");


 WebElement addLendButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button)[1]")));

 addLendButton.click();


 browser.get(baseUrl + "/returnBook");

 List<WebElement> lends =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class=\"returnMainFrame\"]")));


 System.out.println("-------------THE SECOND CURRENT COPIES LENGTH: "+lends.size());

assertEquals(1, lends.size());

}


@Test
void returnBook(){
 browser.get(baseUrl + "/lendBookCopy/13");


 WebElement addLendButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button)[1]")));

 addLendButton.click();


 browser.get(baseUrl + "/returnBook");

 List<WebElement> lends =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class=\"returnMainFrame\"]")));


 System.out.println("-------------THE SECOND CURRENT COPIES LENGTH: "+lends.size());

assertEquals(1, lends.size());


WebElement returnBookButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class=\"returnMainFrame\"]/div[@id=\"buttonDiv\"]/button)[1]")));

returnBookButton.click();

browser.get(baseUrl + "/returnHistory");

List<WebElement> returns =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class=\"returnMainFrame\"]")));


System.out.println("-------------THE SECOND CURRENT COPIES LENGTH: "+returns.size());

assertEquals(1, returns.size());


}



//div[@class="bookFrame"]



}

