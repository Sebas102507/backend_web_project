package com.example.restserver.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class BookCopy {

    @Id
    @GeneratedValue
    private Long id;



    @NotNull
    private LocalDateTime creationDate;


    
    @ManyToOne
    private Book book;


    @JsonIgnore
    @OneToOne(mappedBy = "copy")
    private PersonBookLend personBookLend;


    @JsonIgnore
    @OneToMany(mappedBy = "returnCopy")
    List<PersonBookReturn> personBookReturns= new ArrayList<>();



    public BookCopy(){}

    public BookCopy(Book book,LocalDateTime creationDate){
        this.book=book;
        this.creationDate=creationDate;
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


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public PersonBookLend getPersonBookLend() {
        return personBookLend;
    }

    public void setPersonBookLend(PersonBookLend personBookLend) {
        this.personBookLend = personBookLend;
    }

    public List<PersonBookReturn> getPersonBookReturns() {
        return personBookReturns;
    }

    public void setPersonBookReturns(List<PersonBookReturn> personBookReturns) {
        this.personBookReturns = personBookReturns;
    }


    

}
