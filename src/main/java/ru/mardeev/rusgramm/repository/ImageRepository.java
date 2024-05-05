package ru.mardeev.rusgramm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mardeev.rusgramm.entity.Image;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Page<Image> findAllByOwnerName(String username, Pageable pageable);
}
