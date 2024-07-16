



package admin_user.controller;

import admin_user.dto.ProductDto;
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
@RequestMapping("/ShoppingCart")
public class ShoppingCartController {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserDetailsService userDetailsService;

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

		return "ShoppingCart/ShoppingCart";

	}
	
//	@GetMapping("/AddProduct")
//	public String AddProduct(Model model, Principal principal) {
//		if (principal != null) {
//			UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//			model.addAttribute("user", userDetails);
//		} else {
//
//			model.addAttribute("message", "You need to log in to access the compose page.");
//			return "redirect:/login";
//		}
//		List<ProductCatalog> productCatalogs = productRepository.findAll();
//		model.addAttribute("productCatalogs", productCatalogs);
//
//	return "/ShoppingCart/AddProduct";
//}
	

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
//	        return "ShoppingCart/productdetails";
//	    
//	}

}