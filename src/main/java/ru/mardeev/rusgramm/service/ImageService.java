package ru.mardeev.rusgramm.service;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mardeev.rusgramm.entity.Image;
import ru.mardeev.rusgramm.entity.User;
import ru.mardeev.rusgramm.repository.ImageRepository;
import java.util.List;


@Service
@Data
public class ImageService {
    private final ImageRepository imageRepository;

    @SneakyThrows
    public String upload(String username, MultipartFile image)  {
        if (image.isEmpty() || image.getSize() > 5 * 1024 * 1024) {
            return null;}
        User user = new User(username, null);
        imageRepository.save(new Image(user, image.getOriginalFilename(), image.getBytes()));
        return "Image uploaded";
    }


    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    public List<Image> getImages(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return imageRepository.findAllByOwnerName(username, pageable);
    }


}
