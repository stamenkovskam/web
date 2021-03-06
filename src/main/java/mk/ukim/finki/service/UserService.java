package mk.ukim.finki.service;

import mk.ukim.finki.model.enumerations.Role;
import mk.ukim.finki.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
