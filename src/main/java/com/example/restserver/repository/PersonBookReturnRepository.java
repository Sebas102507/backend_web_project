package com.example.restserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.restserver.model.PersonBookReturn;

@Repository
public interface PersonBookReturnRepository extends JpaRepository<PersonBookReturn,Long>{

}
