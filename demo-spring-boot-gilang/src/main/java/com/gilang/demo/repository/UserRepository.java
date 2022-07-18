package com.gilang.demo.repository;

import com.gilang.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = :username AND u.isDelete = 0 ")
    Optional<User> findByUsername(@Param("username") String username);
}
