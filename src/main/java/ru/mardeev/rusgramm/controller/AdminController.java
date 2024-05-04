package ru.mardeev.rusgramm.controller;

import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mardeev.rusgramm.service.UserService;

@Controller
@Data
public class AdminController {
    private final UserService userService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public String upload(Model model) {
        return "upload";
    }

    @GetMapping("/viewAllImagesAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewAllImagesAdmin(Model model) {
        return "viewAllImagesAdmin";
    }

}
