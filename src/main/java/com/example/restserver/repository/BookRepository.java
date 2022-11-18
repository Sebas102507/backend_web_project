package com.example.restserver.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.restserver.model.Author;
import com.example.restserver.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    Book findByid(Long id);

    // @Query("select b from BookCopy b where b.book.id=?1")
    @Query("select auth from AuthorByBook authB left join Author auth on authB.author.id=auth.id where authB.book.id=?1")
    List<Author> findAllAuthorsByBook(Long id);

}
