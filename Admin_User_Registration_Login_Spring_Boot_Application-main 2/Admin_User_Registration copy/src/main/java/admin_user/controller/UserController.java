package admin_user.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import admin_user.dto.ProductDto;
import admin_user.dto.UserRegistrationDto;
import admin_user.model.ProductCatalog;
import admin_user.model.User;
import admin_user.repositories.ProductRepository;
import admin_user.repositories.UserRepository;
import admin_user.service.ProductService;
import admin_user.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	private Object ProductCatalog;

	@GetMapping("/registration")
	public String getRegistrationPage(Model model) {
		model.addAttribute("user", new UserRegistrationDto());
		return "register";
	}

	@PostMapping("/registration")

	public String registerUser(@ModelAttribute("user") UserRegistrationDto registrationDto,
			RedirectAttributes redirectAttributes, Model model) {

		if (userService.emailExists(registrationDto.getEmail())) {
			model.addAttribute("user", registrationDto);
			model.addAttribute("errorMessage", "An account with this email already exists!");
			return "register";
		}
//
//		if (userService.usernameExists(registrationDto.getUsername())) {
//			model.addAttribute("user", registrationDto);
//			model.addAttribute("errorMessage", "This username is already taken. Please choose another one.");
//			return "register";
//		}

		Optional<User> userOptional = userService.findByUsername(registrationDto.getUsername());
		if (userOptional.isPresent()) {
			model.addAttribute("user", registrationDto);
			model.addAttribute("errorMessage", "This username is already taken. Please choose another one.");
			return "register";
		}
		// Check if passwords match
		if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
			model.addAttribute("user", registrationDto);
			model.addAttribute("errorMessage", "Passwords must match!");
			return "register";
		}

		userService.registerUser(registrationDto);
		redirectAttributes.addFlashAttribute("message", "Registered Successfully!");
		return "redirect:/thankyoupage";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/user")
	public String userPage(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}
		return "user";
	}

	@GetMapping("/admin")
	public String adminPage(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}
		return "admin";
	}

	@GetMapping("/seller")
	public String sellerPage(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}
		return "seller";
	}

	@GetMapping("/thankyoupage")
	public String thankyoupage() {
		return "thankyoupage";
	}

	@GetMapping("/successPage")
	public String successPage(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}
		return "successPage";
	}

	@GetMapping("/mailbox")
	public String mailboxPage(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}
		return "mailbox";
	}

	@GetMapping("/compose")
	public String composePage(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}
		return "compose";
	}

//	
//
	@GetMapping("/CreateProduct")
	public String showCreateProductForm(Model model, Principal principal) {

		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}

		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto", productDto); // Make sure this line is present
		return "/CreateProduct";
	}

	@PostMapping("/CreateProduct")
	public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result, Model model,
			Principal principal) {

		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}

		if (productDto.getImageFile() == null || productDto.getImageFile().isEmpty()) {
			result.addError(new FieldError("productDto", "imageFile", "The image file is required"));
		}

		if (result.hasErrors()) {
			return "/CreateProduct";
		}

		User seller = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Seller not found"));

//save image file
		MultipartFile image = productDto.getImageFile();
		Date createDate = new Date();

		String storeFileName = createDate.getTime() + "_" + image.getOriginalFilename();

		try {
			String uploadDir = "public/images/";
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storeFileName), StandardCopyOption.REPLACE_EXISTING);
			}

		} catch (Exception ex) {
			System.out.println("Exceotion: " + ex);
		}
		// Create and save the product

		ProductCatalog productCatalog = new ProductCatalog();
		productCatalog.setName(productDto.getName());
		productCatalog.setDescription(productDto.getDescription());
		productCatalog.setPrice(productDto.getPrice());
		productCatalog.setStockQuantity(productDto.getStockQuantity());
		productCatalog.setCategory(productDto.getCategory());
		productCatalog.setCreateDate(createDate);
		productCatalog.setImageFileName(storeFileName);
		productCatalog.setSeller(seller); // Set the seller

		productRepository.save(productCatalog);

		return "redirect:/products";
	}

	@GetMapping("/edit")
	public String showUpdateProduct(Model model, @RequestParam("id") Long productId, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");

			return "redirect:/login";
		}

		try {

			ProductCatalog productCatalog = productRepository.findById(productId).get();

			model.addAttribute("productCatalog", productCatalog);

			ProductDto productDto = new ProductDto();
			productDto.setProductId(productCatalog.getProductId());
			productDto.setName(productCatalog.getName());
			productDto.setDescription(productCatalog.getDescription());
			productDto.setPrice(productCatalog.getPrice());
			productDto.setStockQuantity(productCatalog.getStockQuantity());
			productDto.setCategory(productCatalog.getCategory());

			model.addAttribute("productDto", productDto);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/products";
		}
		return "/EditProduct";
	}

	@PostMapping("/edit")
	private String updateProduct(Model model, @RequestParam("id") Long productId,
			@Valid @ModelAttribute ProductDto productDto, BindingResult result, Principal principal) {

		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");

			return "redirect:/login";
		}

		try {
			ProductCatalog productCatalog = productRepository.findById(productId).get();

			if (result.hasErrors()) {
				return ("/EditProduct");
			}

			// Update the rest of the product details from the ProductDto
			if (!productDto.getImageFile().isEmpty()) {
				// delete old emage
				String uploadDir = "public/images/";
				Path oldImagePath = Paths.get(uploadDir + productCatalog.getImageFileName());

				try {
					Files.delete(oldImagePath);

				} catch (Exception ex) {
					System.out.println("Exception: " + ex.getMessage());
				}

				// save new image file
				MultipartFile image = productDto.getImageFile();

				Date createDate = new Date();
				String storageFileName = createDate.getTime() + "_" + image.getOriginalFilename();

				try (InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				}

				productCatalog.setImageFileName(storageFileName);
			}

			productCatalog.setName(productDto.getName());
			productCatalog.setDescription(productDto.getDescription());
			productCatalog.setPrice(productDto.getPrice());
			productCatalog.setStockQuantity(productDto.getStockQuantity());
			productCatalog.setCategory(productDto.getCategory());

			// Save the updated ProductCatalog entity
			productRepository.save(productCatalog);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
			return "redirect:/products";
		}

		return "redirect:/products";
	}

//	@GetMapping("/productdetails")
//	public String showProductDetails(@PathVariable Long productId, Model model, Principal principal) {
//		if (principal != null) {
//			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//			model.addAttribute("user", userDetails);
//		} else {
//
//			model.addAttribute("message", "You need to log in to access the compose page.");
//
//			return "redirect:/login";
//		}
//		
//		
//		try {
//		ProductCatalog productCatalog = productRepository.findById(productId).get();
//		model.addAttribute("productCatalog", productCatalog);
//
//		ProductDto productDto = new ProductDto();
//		productDto.setProductId(productCatalog.getProductId());
//		productDto.setName(productCatalog.getName());
//		productDto.setDescription(productCatalog.getDescription());
//		productDto.setPrice(productCatalog.getPrice());
//		productDto.setStockQuantity(productCatalog.getStockQuantity());
//		productDto.setCategory(productCatalog.getCategory());
//
//		model.addAttribute("productDto", productDto);
//		model.addAttribute("productCatalog", productCatalog);
//		} catch (Exception ex) {
//			System.out.println("Exception: " + ex.getMessage());
//			return "redirect:/ShoppingCart";
//		}
//	        // Handle the case where the product doesn't exist (e.g., redirect to an error page or the product list)
//	        return "/productdetails";
//	    
//	}

	@GetMapping("/productdetails")
	public String productdetails(Model model, @RequestParam("id") Long productId, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");

			return "redirect:/login";
		}

		try {

			ProductCatalog productCatalog = productRepository.findById(productId).get();

			model.addAttribute("productCatalog", productCatalog);

			ProductDto productDto = new ProductDto();
			productDto.setProductId(productCatalog.getProductId());
			productDto.setName(productCatalog.getName());
			productDto.setDescription(productCatalog.getDescription());
			productDto.setPrice(productCatalog.getPrice());
			productDto.setStockQuantity(productCatalog.getStockQuantity());
			productDto.setCategory(productCatalog.getCategory());

			model.addAttribute("productDto", productDto);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/ShoppingCart";
		}
		return "/productdetails";
	}
	@GetMapping("/AddProduct")
	public String AddProduct(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}
		List<ProductCatalog> productCatalogs = productRepository.findAll();
		model.addAttribute("productCatalogs", productCatalogs);

	return "AddProduct";
}

}