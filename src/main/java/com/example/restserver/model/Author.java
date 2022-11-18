package com.example.restserver.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Author {
 
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
     String name;



     @JsonIgnore
     @OneToMany(mappedBy = "author")
      List<AuthorByBook> authorByBooks= new ArrayList<>();




     public Author() {}

     public Author(String name) {
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

     

}
