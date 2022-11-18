package com.example.restserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restserver.model.PersonBookLend;

@Repository
public interface PersonBookLendRepository extends JpaRepository<PersonBookLend,Long>{

}