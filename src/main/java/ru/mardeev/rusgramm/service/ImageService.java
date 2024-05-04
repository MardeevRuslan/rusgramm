package ru.mardeev.rusgramm.service;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mardeev.rusgramm.entity.Image;
import ru.mardeev.rusgramm.entity.User;
import ru.mardeev.rusgramm.repository.ImageRepository;
import ru.mardeev.rusgramm.repository.UserRepository;

import java.io.IOException;

@Service
@Data
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @SneakyThrows
    public String upload(String username, MultipartFile image)  {
        if (image.isEmpty() || image.getSize() > 5 * 1024 * 1024) {
            return null;}
        User user = new User(username, null);
        imageRepository.save(new Image(user, image.getOriginalFilename(), image.getBytes()));
        return "Image uploaded";
    }
}
