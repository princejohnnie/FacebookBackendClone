package com.example.facebookbackendclone.repository;

import com.example.facebookbackendclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    void deleteByEmail(String email);

    Boolean existsByEmail(String email);

    @Modifying
    @Query("update User u set u.firstName = :firstName, u.lastName = :lastName, u.email = :email, u.dob = :dob, u.country = :country, u.gender = :gender, u.password = :password")
    User update(String firstName, String lastName, String email, String dob, String country, String gender, String password);
}
