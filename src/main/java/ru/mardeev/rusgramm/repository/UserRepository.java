package ru.mardeev.rusgramm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mardeev.rusgramm.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
