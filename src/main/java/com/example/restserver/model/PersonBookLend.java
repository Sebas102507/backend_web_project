package com.example.restserver.model;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class PersonBookLend {

    @Id
    @GeneratedValue
    private Long id;

    
    @OneToOne
    private BookCopy copy;


    
    @ManyToOne
    private Person person;



    @NotNull
    Date lendDate;

    @NotNull
    Date expirationDate;

    

    public PersonBookLend(){}

    public PersonBookLend(BookCopy copy, Person person, Date lendDate,Date expirationDate){
        this.copy=copy;
        this.person=person;
        this.lendDate=lendDate;
        this.expirationDate=expirationDate;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public BookCopy getCopy() {
        return copy;
    }


    public void setCopy(BookCopy copy) {
        this.copy = copy;
    }


    public Person getPerson() {
        return person;
    }


    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }



    
    
}
