package com.pregunta.kirby.repository;

import com.pregunta.kirby.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findTop3ByOrderByScoreDesc();
}
