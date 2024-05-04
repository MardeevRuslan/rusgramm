package ru.mardeev.rusgramm.controller;

import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.mardeev.rusgramm.service.ImageService;

@RestController
@Data
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public RedirectView upload(@RequestParam("image") MultipartFile image, Model model) {
        String username = (String) model.getAttribute("username");
        String uploadResult = imageService.upload(username, image);
        if (uploadResult != null) {
            return new RedirectView("/dashboard");
        } else {
            return new RedirectView("/dashboard?error=true");
        }
    }



}
