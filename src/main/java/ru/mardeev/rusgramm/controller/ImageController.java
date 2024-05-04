package ru.mardeev.rusgramm.controller;

import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/viewMyImages")
    public RedirectView viewMyImages(Model model, @RequestParam(defaultValue = "0") int page) {
        String username = (String) model.getAttribute("username");
        int pageSize = 10;
        model.addAttribute("images", imageService.getImages(username, page, pageSize));
        return new RedirectView("/viewMyImages?username=" + username + "&page=" + page);
    }


    @GetMapping("/viewAllImages")
    public RedirectView viewAllImages(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        model.addAttribute("images", imageService.getImages(null, page, pageSize));
        model.addAttribute("page", page);
        return new RedirectView("/viewAllImages" + "?page=" + page);
    }

    @PostMapping("/deleteImage")
    public RedirectView deleteImage(@RequestParam("id") Long id) {
        imageService.deleteImage(id);
        return new RedirectView("/viewMyImages");
    }

}
