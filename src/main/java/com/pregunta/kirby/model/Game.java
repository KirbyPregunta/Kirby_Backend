package com.pregunta.kirby.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Game extends BaseEntity {

    private Integer score;
    @ManyToOne
    private User user;
}
