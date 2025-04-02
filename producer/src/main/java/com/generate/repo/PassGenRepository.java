package com.generate.repo;

import com.generate.model.PassGenResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PassGenRepository extends JpaRepository<PassGenResponse, Long> {

    @Query(nativeQuery = true,
     value = "SELECT * FROM passgenresp pgr where pgr.firstname = :firstName " +
             "and pgr.lastname = :lastName and pgr.birth_date = :birthdate")
    Optional<PassGenResponse> getByCriteria(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("birthdate") Date birthdate);
}
