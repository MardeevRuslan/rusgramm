package ru.mardeev.rusgramm.controller;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.mardeev.rusgramm.entity.Image;
import ru.mardeev.rusgramm.service.ImageService;

import java.util.List;

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
    public String viewMyImages(Model model, @RequestParam(defaultValue = "0") int page) {
        String username = (String) model.getAttribute("username");
        int pageSize = 10;
        Page<Image> userImages = imageService.getImages(username, page, pageSize);
        model.addAttribute("images", userImages);
        model.addAttribute("username", username);
        model.addAttribute("page", page);
        model.addAttribute("hasNextPage", userImages.getSize() == pageSize);
        return "viewMyImages";
    }


    @GetMapping("/viewAllImages")
    public String viewAllImages(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        Page<Image> images = imageService.getImages(null, page, pageSize);
        model.addAttribute("images", images);
        model.addAttribute("page", page);
        model.addAttribute("hasNextPage", images.getSize() == pageSize);
        return "viewAllImages";
    }

    @PostMapping("/deleteImage")
    public RedirectView deleteImage(@RequestParam("id") Long id) {
        imageService.deleteImage(id);
        return new RedirectView("/viewMyImages");
    }

    @PostMapping("/deleteImageAdmin")
    public RedirectView deleteImageAdmin(@RequestParam("id") Long id) {
        imageService.deleteImage(id);
        return new RedirectView("/viewAllImagesAdmin");
    }

}
