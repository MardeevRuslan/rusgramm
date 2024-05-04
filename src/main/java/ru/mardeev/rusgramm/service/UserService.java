package ru.mardeev.rusgramm.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.mardeev.rusgramm.entity.User;
import ru.mardeev.rusgramm.repository.UserRepository;

import java.util.Optional;

@Service
@Data
public class UserService {
    private final UserRepository loginRepository;

    public boolean isValidLogin(String username, String password) {
        Optional<User> userOptional = loginRepository.findById(username);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        return user.getPassword().equals(password);
    }

    public boolean createUser(String username, String password) {
        if (loginRepository.findById(username).isEmpty()) {
            loginRepository.save(new User(username, password));
            return true;
        }
        return false;
    }
}
