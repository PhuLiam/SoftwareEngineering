package admin_user.service;

import java.util.Optional;

import admin_user.dto.UserRegistrationDto;
import admin_user.model.User;

public interface UserService {
	
	boolean emailExists(String email);

	boolean usernameExists(String username);

	User registerUser(UserRegistrationDto registrationDto);
	
    User getUserByUsername(String username);
    Optional<User> findByUsername(String username); // Add this line


}