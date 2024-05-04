package ru.mardeev.rusgramm.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import ru.mardeev.rusgramm.entity.User;
import ru.mardeev.rusgramm.repository.UserRepository;

import java.util.Optional;

@Service
@Data
public class UserService {
    private final UserRepository loginRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

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
            loginRepository.save(new User(username, password, "USER"));
            return true;
        }
        return false;
    }

    public boolean isAdmin(String username, String password) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
