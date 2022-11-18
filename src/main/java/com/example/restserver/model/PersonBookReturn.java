package com.example.restserver.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class PersonBookReturn {
    
    @Id
    @GeneratedValue
    private Long id;


    @NotNull
    Date returnDate;


    @ManyToOne
    private BookCopy returnCopy;


    
    @ManyToOne
    private Person returnPerson;


    public PersonBookReturn(){}

    public PersonBookReturn(BookCopy copy, Person person, Date returnDate){
        this.returnCopy=copy;
        this.returnPerson=person;
        this.returnDate=returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BookCopy getReturnCopy() {
        return returnCopy;
    }

    public void setReturnCopy(BookCopy returnCopy) {
        this.returnCopy = returnCopy;
    }

    public Person getReturnPerson() {
        return returnPerson;
    }

    public void setReturnPerson(Person returnPerson) {
        this.returnPerson = returnPerson;
    }

   

    


}
