package com.example.restserver.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restserver.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long>{

}
