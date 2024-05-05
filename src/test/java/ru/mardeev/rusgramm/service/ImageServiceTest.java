package ru.mardeev.rusgramm.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import ru.mardeev.rusgramm.entity.Image;
import ru.mardeev.rusgramm.repository.ImageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    @Test
    public void testUploadValidImage() {

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());
        when(imageRepository.save(any())).thenReturn(null);
        String result = imageService.upload("testUser", image);
        assertEquals("Image uploaded", result);
    }

    @Test
    public void testUploadEmptyImage() {
        MockMultipartFile image = new MockMultipartFile("image", "".getBytes());
        String result = imageService.upload("testUser", image);
        assertNull(result);
    }

    @Test
    public void testDeleteImage() {
        imageService.deleteImage(1L);
        verify(imageRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetImages() {
        List<Image> images = new ArrayList<>();
        images.add(new Image());
        images.add(new Image());
        images.add(new Image());
        Page<Image> page = new PageImpl<>(images);
        when(imageRepository.findAllByOwnerName(eq("testUser"), any(Pageable.class))).thenReturn(page);
        Page<Image> result = imageService.getImages("testUser", 0, 10);
        assertEquals(3, result.getTotalElements());
    }

    @Test
    public void testUploadImageFromFile() throws IOException {
        Path imagePath = new ClassPathResource("imageTest.jpeg").getFile().toPath();
        byte[] imageBytes = Files.readAllBytes(imagePath);
        MockMultipartFile imageFile = new MockMultipartFile("image", "imageTest.jpeg", "image/jpeg", imageBytes);
        when(imageRepository.save(any(Image.class))).thenReturn(new Image());
        String result = imageService.upload("testUser", imageFile);
        assertEquals("Image uploaded", result);
    }
}
