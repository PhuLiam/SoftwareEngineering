package admin_user.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import admin_user.dto.UserRegistrationDto;
import admin_user.model.Profile;
import admin_user.model.User;
import admin_user.repositories.UserRepository;
import admin_user.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class ClassUsersController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@GetMapping({ "", "/" })
	public String showUsersPage(Model model, Principal principal) {
		if (principal != null) {
	        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
	        model.addAttribute("user", userDetails);
	    } else {
	        model.addAttribute("message", "No authenticated user");
	       return "redirect:/login";
	    }

		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "users/index";
	}

	
	@GetMapping("/create")
	public String showCreatePage(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
			} else {
		        
		        model.addAttribute("message", "You need to log in to access the compose page.");
		        
		        return "redirect:/login";
		    }
		
		
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		model.addAttribute("userRegistrationDto", userRegistrationDto);
		return "users/createuser";
	}
	

	@PostMapping("/createuser")
	public String createUser(@ModelAttribute("userRegistrationDto") @Valid UserRegistrationDto userRegistrationDto,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "users/createuser";
		}

		userService.registerUser(userRegistrationDto);

		redirectAttributes.addFlashAttribute("message", "User created successfully!");

		return "redirect:/users";
	}
	
	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam Long id,  Principal principal) {
		
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
			} else {
		        
		        model.addAttribute("message", "You need to log in to access the compose page.");
		        
		        return "redirect:/login";
		    }
			
		try {
			

			User user = userRepository.findById(id).get();
			Profile profile = user.getProfile();
			model.addAttribute("user", user);

			UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
			
			userRegistrationDto.setId(user.getId());
			userRegistrationDto.setUsername(user.getUsername());
			userRegistrationDto.setPassword(passwordEncoder.encode(user.getPassword())); 
			userRegistrationDto.setType(user.getType());
			userRegistrationDto.setName(profile.getName());
			userRegistrationDto.setPhone(profile.getPhone());
			userRegistrationDto.setEmail(profile.getEmail());
			userRegistrationDto.setAddress(profile.getAddress());

			model.addAttribute("userId", user.getId());
			model.addAttribute("userRegistrationDto", userRegistrationDto);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/users";
		}

		return "users/edituser";
	}

	@PostMapping("/edit")
	public String updateUser(Model model, @RequestParam Long id,
			@Valid @ModelAttribute UserRegistrationDto userRegistrationDto, BindingResult result) {
		
		try {
			User user = userRepository.findById(id).get();
			Profile profile = user.getProfile();
			model.addAttribute("user", user);

			if (result.hasErrors()) {
				model.addAttribute("userRegistrationDto", userRegistrationDto);
				return "users/edituser";
			}
			

			user.setUsername(userRegistrationDto.getUsername());
			user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
			user.setType(userRegistrationDto.getType());
			profile.setName(userRegistrationDto.getName());
			profile.setPhone(userRegistrationDto.getPhone());
			profile.setEmail(userRegistrationDto.getEmail());
			profile.setAddress(userRegistrationDto.getAddress());

			userRepository.save(user);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
		return "redirect:/users";
	}

	@GetMapping("/delete")
	public String deleteUser(Model model, @RequestParam Long id) {
	    try {
	        // Check if a user with the given ID exists
	        Optional<User> userOptional = userRepository.findById(id);
	        if (userOptional.isPresent()) {
	            // If the user exists, delete the user
	            userRepository.deleteById(id);
	            model.addAttribute("message", "User deleted successfully");
	        } else {
	            // If the user does not exist, add an error message to the model
	            model.addAttribute("errorMessage", "User not found with id: " + id);
	        }
	    } catch (Exception e) {
	        // Log the exception (consider using a proper logging framework)
	        System.err.println("Error deleting user: " + e.getMessage());
	        // Add a generic error message to the model
	        model.addAttribute("errorMessage", "Error deleting user");
	    }
	    // Redirect back to the users page (or wherever you list the users)
	    return "redirect:/users";
	}

}
