package com.pregunta.kirby.controller;

import com.pregunta.kirby.dtos.user.CreateUserDTO;
import com.pregunta.kirby.exception.*;
import com.pregunta.kirby.model.Country;
import com.pregunta.kirby.model.Gender;
import com.pregunta.kirby.model.Game;
import com.pregunta.kirby.model.User;
import com.pregunta.kirby.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
public class UserController {

    final private UserService userService;
    final private HttpSession session;

    @Autowired
    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody CreateUserDTO userDTO) {
        try {
            userService.validateFieldsRegister(userDTO);
            userService.validateThatPasswordsMatch(userDTO.getPassword(), userDTO.getRepeatPassword());
            userService.validateThatTheEmailIsNotUsed(userDTO.getEmail());
            userService.validateThatTheUsernameIsNotUsed(userDTO.getUsername());
            userService.validateThatEmailCodeIsCorrect(userDTO.getEmailCode(), (Integer) session.getAttribute("randomNumber"));
            Gender gender = userService.validateThatGenderExist(userDTO.getGender());
            Country country = userService.validateThatCountryExist(userDTO.getCountry());
            userService.createUser(gender,country,userDTO);
        } catch (EmptyFieldException | DifferentPasswordsException | ExistingUserException |
                 NonExistingGenderException | NonExistingCountryException | EmailCodeIncorrectException e) {
            return e.getMessage();
        }
        return null;
    }

    @GetMapping("/findTopThreeUsersWithTheHighestScore")
    public List<User> findTopThreeUsersWithTheHighestScore() {
        return userService.findTopThreeUsersWithTheHighestScore();
    }

    @GetMapping("/findTheLastUserGame")
    public Game findTheLastUserGame() {
        return userService.findTheLastUserGame(1);
    }

    @GetMapping("/userHistory")
    public List<Game> findUserHistory() {
        return userService.findUserHistory(1);
    }

}
