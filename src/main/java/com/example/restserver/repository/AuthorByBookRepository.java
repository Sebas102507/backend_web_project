package com.example.restserver.repository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.restserver.model.AuthorByBook;

@Repository
public interface AuthorByBookRepository extends JpaRepository<AuthorByBook,Long>{

     // @Query("select b from BookCopy b where b.book.id=?1")
     @Query("select authB from AuthorByBook authB where authB.book.id=?1")
     List<AuthorByBook> findAllAuthorsByBookByBook(Long id);
    
}


