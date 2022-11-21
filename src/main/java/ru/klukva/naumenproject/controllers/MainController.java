package ru.klukva.naumenproject.controllers;


import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.services.UserService;
import ru.klukva.naumenproject.services.UserSynchronizingService;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public String getStart() {
        return "start_page";
    }

    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal BankUser user, Model model) {
        user = UserSynchronizingService.synchronize(user);
        model.addAttribute("user", user);
        return "home_page";
    }
}
