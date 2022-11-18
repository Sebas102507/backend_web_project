package com.example.restserver.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class Book {

    @Id
    @GeneratedValue
    public Long id;

    @NotNull
     String title;
    @NotNull
     String isbn;
  
    @NotNull
    @Column(columnDefinition="TEXT", length = 4096)
     String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
     List<BookCopy> copies= new ArrayList<>();

     @JsonIgnore
     @OneToMany(mappedBy = "book")
      List<AuthorByBook> authorByBooks= new ArrayList<>();




    public Book() {}

    public Book(String title, String isbn, String imageUrl) {
        this.title = title;
        this.isbn = isbn;
        this.imageUrl=imageUrl;
    }



    public String getTitle() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book [isbn=" + isbn + ", title=" + title +" id="+id+ "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }


    
}