package mk.ukim.finki.model.dto;

import lombok.Data;

import mk.ukim.finki.model.enumerations.Role;
import mk.ukim.finki.model.User;


@Data
public class UserDetailsDto {
    private String username;
    private Role role;

    public static UserDetailsDto of(User user){
        UserDetailsDto details = new UserDetailsDto();
        details.username = user.getUsername();
        details.role = user.getRole();
        return details;
    }
}
