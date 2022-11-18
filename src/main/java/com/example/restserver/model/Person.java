package com.example.restserver.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
     String name;


     @JsonIgnore
     @OneToMany(mappedBy = "person")
     List<PersonBookLend> bookLends= new ArrayList<>();


     @JsonIgnore
     @OneToMany(mappedBy = "returnPerson")
     List<PersonBookReturn> bookReturns= new ArrayList<>();



     public Person() {}

     public Person(String name) {
        this.name=name;
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

    public List<PersonBookLend> getBookLends() {
        return bookLends;
    }

    public void setBookLends(List<PersonBookLend> bookLends) {
        this.bookLends = bookLends;
    }

    public List<PersonBookReturn> getBookReturns() {
        return bookReturns;
    }

    public void setBookReturns(List<PersonBookReturn> bookReturns) {
        this.bookReturns = bookReturns;
    }

    
    
}
