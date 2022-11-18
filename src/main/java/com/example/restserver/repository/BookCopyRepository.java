package com.example.restserver.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.restserver.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy,Long>{
    
    @Query("select b from BookCopy b where b.book.id=?1")
    List<BookCopy> findAllCopiesByBook(Long id);


    @Query("select b from BookCopy b where b.book.id=?1 and not exists(select 1 from PersonBookLend l where l.copy.id=b.id)")
    List<BookCopy> findAllAvailableCopiesByBook(Long id);


//     SELECT *
// FROM  BOOK_COPY
// WHERE BOOK_COPY.BOOK_ID=16 AND NOT EXISTS(SELECT 1 FROM PERSON_BOOK_LEND WHERE PERSON_BOOK_LEND.COPY_ID=BOOK_COPY.ID)





}