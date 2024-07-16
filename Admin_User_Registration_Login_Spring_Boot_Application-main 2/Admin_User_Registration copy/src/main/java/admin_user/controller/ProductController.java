package admin_user.controller;

import admin_user.dto.ProductDto;
import admin_user.service.ProductService;

//import admin_user.dto.ProductDto;
import admin_user.dto.UserRegistrationDto;
import admin_user.model.ProductCatalog;
import admin_user.model.User;
import admin_user.repositories.ProductRepository;
import admin_user.repositories.UserRepository;
import jakarta.validation.Valid;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private ProductService productService;

	@GetMapping({ "", "/" })
	public String showProductsList(Model model, Principal principal) {
		if (principal != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
			model.addAttribute("user", userDetails);
		} else {

			model.addAttribute("message", "You need to log in to access the compose page.");
			return "redirect:/login";
		}
		
		List<ProductCatalog> productCatalogs = productRepository.findAll();
		model.addAttribute("productCatalogs", productCatalogs);

		return "Products/Products";

	}

	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("id") Long productId) {

			try {
				ProductCatalog productCatalog = productRepository.findById(productId).get();
				
				Path imagePath = Paths.get("public/images/" + productCatalog.getImageFileName());
			try {
				Files.delete(imagePath); // Use deleteIfExists to avoid throwing an exception if the file doesn't exist
			} catch (IOException ex) {
				System.out.println("Exception: " + ex.getMessage());
			}
			productRepository.delete(productCatalog);
			
			
		}catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/products"; // Redirect back to the products page
	}
	
//	@GetMapping("/search")
//    public String searchProducts(@RequestParam("q") String query, Model model, Principal principal) {
//		
//		if (principal != null) {
//			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//			model.addAttribute("user", userDetails);
//		} else {
//
//			model.addAttribute("message", "You need to log in to access the compose page.");
//			return "redirect:/login";
//		}
//        // Perform the search using the query
//        List<ProductCatalog> searchResults = productService.searchProducts(query);
//        
//        // Add the search results to the model
//        model.addAttribute("searchResults", searchResults);
//        
//        // Return the view that displays the search results
//        return "searchResults";
//    }

//	@GetMapping("/search")
//    public String search(@RequestParam String query, Model model) {
//        List<ProductCatalog> searchResults = productService.searchProducts(query);
//        model.addAttribute("searchResults", searchResults);
//        return "searchResults"; // Name of the template that shows the search results
//    }
	// Additional methods like create, edit, etc. can be added here

//
//
//	@GetMapping("/edit")
//	public String showUpdateProduct(Model model, @RequestParam("id") Long productId, Principal principal) {
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
//		try {
//
//			ProductCatalog productCatalog = productRepository.findById(productId).get();
//			model.addAttribute("productCatalog", productCatalog);
//
//			ProductDto productDto = new ProductDto();
//			
//			productDto.setName(productCatalog.getName());
//			productDto.setDescription(productCatalog.getDescription());
//			productDto.setPrice(productCatalog.getPrice());
//			productDto.setStockQuantity(productCatalog.getStockQuantity());
//			productDto.setCategory(productCatalog.getCategory());
//
//			model.addAttribute("productDto", productDto);
//		} catch (Exception ex) {
//			System.out.println("Exception: " + ex.getMessage());
//			return "redirect:/products";
//		}
//		return "products/EditProduct";
//	}
//
//	@PostMapping("/edit")
//	private String updateProduct(Model model, @RequestParam Long productId,
//			@Valid @ModelAttribute ProductDto productDto, BindingResult result, Principal principal) {
//
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
//		try {
//            ProductCatalog productCatalog = productRepository.findById(productId)
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
//
//            User user = userRepository.findByUsername(principal.getName())
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + principal.getName()));
//
//            productCatalog.setSeller(user);
//            
//			// Update the rest of the product details from the ProductDto
//			if (!productDto.getImageFile().isEmpty()) {
//				String uploadDir = "/public/images/";
//				Path oldPathImagePath = Paths.get(uploadDir + productCatalog.getImageFileName());
//				try {
//					Files.delete(oldPathImagePath);
//				} catch (Exception ex) {
//					System.out.println("Exception: " + ex.getMessage());
//				}
//
//				MultipartFile image = productDto.getImageFile();
//				Date createDate = new Date();
//				String storageFileName = createDate.getTime() + "_" + image.getOriginalFilename();
//
//				try (InputStream inputStream = image.getInputStream()) {
//					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
//							StandardCopyOption.REPLACE_EXISTING);
//				} catch (Exception ex) {
//					System.out.println("Exception: " + ex.getMessage());
//				}
//				productCatalog.setImageFileName(storageFileName);
//			}
//			productCatalog.setName(productDto.getName());
//			productCatalog.setDescription(productDto.getDescription());
//			productCatalog.setPrice(productDto.getPrice());
//			productCatalog.setStockQuantity(productDto.getStockQuantity());
//			productCatalog.setCategory(productDto.getCategory());
//
//			// Save the updated ProductCatalog entity
//			productRepository.save(productCatalog);
//
//		} catch (Exception ex) {
//			System.out.println("Exception: " + ex.getMessage());
//			ex.printStackTrace();
//			return "redirect:/products";
//		}
//
//		return "redirect:/Products";
//	}
//	
//

//	@GetMapping("/create")
//	public String ShowCreateProduct(Model model, Principal principal) {
//		if (principal != null) {
//			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//			model.addAttribute("user", userDetails);
//		} else {
//
//			model.addAttribute("message", "You need to log in to access the compose page.");
//			return "redirect:/login";
//		}
//
//		ProductDto productDto = new ProductDto();
//		model.addAttribute("productDto", productDto); // Make sure this line is present
//		return "products/CreateProduct";
//	}
//
//	@PostMapping("/create")
//	public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result, Model model,
//	        Principal principal) {
//		if (principal != null) {
//			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//			model.addAttribute("user", userDetails);
//		} else {
//
//			model.addAttribute("message", "You need to log in to access the compose page.");
//			return "redirect:/login";
//		}
//
//	    if (result.hasErrors()) {
//	        return "Products/CreateProduct";
//	    }
//
//	 // Fetch the currently authenticated user based on the username
//	    User user = userRepository.findByUsername(principal.getName())
//	            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));
//
//	    String uploadDir = "public/images/";
//	    String storeFileName = null;
//
//	    try {
//	        MultipartFile image = productDto.getImageFile();
//	        if (image.isEmpty()) {
//	            result.addError(new FieldError("productDto", "imageFile", "The image file is empty."));
//	            return "Products/CreateProduct";
//	        }
//
//	        Date createdAt = new Date();
//	        storeFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
//	        Path uploadPath = Paths.get(uploadDir);
//
//	        if (!Files.exists(uploadPath)) {
//	            Files.createDirectories(uploadPath);
//	        }
//
//	        try (InputStream inputStream = image.getInputStream()) {
//	            Files.copy(inputStream, Paths.get(uploadDir + storeFileName), StandardCopyOption.REPLACE_EXISTING);
//	        }
//	    } catch (IOException ex) {
//	        ex.printStackTrace();
//	        model.addAttribute("errorMessage", "Error uploading image file.");
//	        return "errorPage"; // Redirect to an appropriate error page
//	    }
//
//	    // Create a new ProductCatalog entity and populate it with data from the ProductDto
//	    ProductCatalog productCatalog = new ProductCatalog();
//	    productCatalog.setName(productDto.getName());
//	    productCatalog.setDescription(productDto.getDescription());
//	    productCatalog.setPrice(productDto.getPrice());
//	    productCatalog.setStockQuantity(productDto.getStockQuantity());
//	    productCatalog.setCategory(productDto.getCategory());
//	    productCatalog.setCreateDate(new Date());
//	    productCatalog.setImageFileName(storeFileName);
//
//	    // Set the currently authenticated user as the seller of the product
//	    productCatalog.setSeller(user);
//
//	    // Save the product entity
//	    productRepository.save(productCatalog);
//
//	    return "redirect:/products";
//	}
//	
}