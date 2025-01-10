package com.pregunta.kirby.controller;

import com.pregunta.kirby.dtos.user.LoginUserDTO;
import com.pregunta.kirby.exception.EmptyFieldException;
import com.pregunta.kirby.exception.ExistingUserException;
import com.pregunta.kirby.exception.NonExistingUserException;
import com.pregunta.kirby.model.User;
import com.pregunta.kirby.service.EmailService;
import com.pregunta.kirby.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class SessionController {

    final private UserService userService;
    final private EmailService emailService;
    final private HttpSession session;

    public SessionController(UserService userService, EmailService emailService, HttpSession session) {
        this.userService = userService;
        this.emailService = emailService;
        this.session = session;
    }

    @PostMapping("/sendMail")
    @ResponseBody
    public String sendMail(@RequestParam("email") String email) {
        try {
            Random random = new Random();
            session.setAttribute("randomNumber", random.nextInt(1000000));
            userService.validateThatTheEmailIsNotUsed(email);
            emailService.sendEmail(email, (Integer) session.getAttribute("randomNumber"));
        } catch (MessagingException e) {
            return "¡Ocurrió un error al enviar el mail!";
        } catch (ExistingUserException e) {
            return e.getMessage();
        }
        return null;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginUserDTO loginDTO) {
        try {
            userService.validateFieldsLogin(loginDTO);
            User user = userService.login(loginDTO);
            session.setAttribute("user", user);
        } catch (NonExistingUserException | EmptyFieldException e) {
            return e.getMessage();
        }
        return null;
    }

    @GetMapping("/session")
    @ResponseBody
    public User session() {
        return userService.findUserById(1);
    }
}
