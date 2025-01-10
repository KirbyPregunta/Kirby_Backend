package com.pregunta.kirby.repository;

import com.pregunta.kirby.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Game findTopByUserIdOrderByIdDesc(Integer userId);

    List<Game> findAllByUserIdOrderById(Integer userId);
}
