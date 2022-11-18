package com.example.restserver.model;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AuthorByBook {

    @Id
    @GeneratedValue
    private Long id;


    @JsonIgnore
    @ManyToOne
    private Book book;


    @JsonIgnore
    @ManyToOne
    private Author author;


    public AuthorByBook(){}

    public AuthorByBook(Book book, Author author){
        this.book=book;
        this.author=author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }


    

    
}
