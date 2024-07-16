package admin_user.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProductDto {
	private Long productId;

	
	@NotEmpty(message = "The name is required")
    private String name;

    @NotNull(message = "The stockQuantity is required")
    @Min(value = 1, message = "Stock quantity must be at least 1")
    private int stockQuantity;

    @NotEmpty(message = "The description is required")
    private String description;

    @NotEmpty(message = "The category is required")
    private String category;

    @NotNull(message = "The price is required")
    @Min(value = 0, message = "The price cannot be negative")
    private BigDecimal price;
    @NotNull(message = "The image file is required")

    private MultipartFile imageFile;

    // Getters and Setters

    

    public String getName() {
        return name;
    }

    public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setName(String name) {
        this.name = name;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

	

	
}
