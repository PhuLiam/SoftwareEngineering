package admin_user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import admin_user.dto.UserRegistrationDto;
import admin_user.model.User;
import admin_user.model.Profile;
import admin_user.repositories.UserRepository;
import admin_user.repositories.ProfileRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;
    
    @Override
    public boolean emailExists(String email) {
        return userRepository.findByProfileEmail(email) != null;
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        Profile profile = new Profile();
        profile.setName(registrationDto.getName());
        profile.setPhone(registrationDto.getPhone());
        profile.setEmail(registrationDto.getEmail());
        profile.setAddress(registrationDto.getAddress());
        profile = profileRepository.save(profile);

        User user = new User();
        user.setType(registrationDto.getType());
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setProfile(profile);
        return userRepository.save(user);
    }

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Optional<User> findByUsername(String username) {
	    return userRepository.findByUsername(username);
	}
}