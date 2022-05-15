package mk.ukim.finki.service;

import mk.ukim.finki.model.User;

public interface AuthService {

    User login(String username, String password);
}
