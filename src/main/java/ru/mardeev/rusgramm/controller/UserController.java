package ru.mardeev.rusgramm.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.mardeev.rusgramm.service.UserService;

@RestController
@Data
public class UserController {

    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public RedirectView login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {

        if (userService.isAdmin(username, password)) {
            model.addAttribute("username", username);
            return new RedirectView("/admin");
        }
        if (userService.isValidLogin(username, password)) {
            model.addAttribute("username", username);
            return new RedirectView("/dashboard");
        } else {
            return new RedirectView("/login?error=true");
        }
    }


    @GetMapping("/")
    public RedirectView index() {
        return new RedirectView("/login");
    }

    @PostMapping("/register")
    public RedirectView signup(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (userService.createUser(username, password)) {
            return new RedirectView("/dashboard");
        } else {
            model.addAttribute("error", "Username already exists");
            return new RedirectView("/register?error=true");
        }
    }


}
