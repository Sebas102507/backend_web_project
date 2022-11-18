package com.example.restserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.restserver.model.Author;


@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>{

}
