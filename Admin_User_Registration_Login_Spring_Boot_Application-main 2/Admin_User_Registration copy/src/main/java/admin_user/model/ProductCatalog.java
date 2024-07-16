package admin_user.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product_catalog")
public class ProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
   
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    
//    private long sellerId;

    
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private BigDecimal price;
    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 1, message = "Stock quantity must be at least 1")
    private int stockQuantity;
    private String category;
    @Column(name = "create_date")
    private Date createDate;
    private String imageFileName;

   
       
    // No-argument constructor
    public ProductCatalog() {
    }

    // Your existing constructors, getters, and setters

    
	public long getProductId() {
		return productId;
	}

	

	

	public ProductCatalog(long productId, User seller, String name, String description, BigDecimal price,
			@NotNull(message = "Stock quantity cannot be null") @Min(value = 1, message = "Stock quantity must be at least 1") int stockQuantity,
			String category, Date createDate, String imageFileName) {
		super();
		this.productId = productId;
		this.seller = seller;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.category = category;
		this.createDate = createDate;
		this.imageFileName = imageFileName;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	

//	public ProductCatalog(long productId, long sellerId, String name, String description, BigDecimal price,
//			@NotNull(message = "Stock quantity cannot be null") @Min(value = 1, message = "Stock quantity must be at least 1") int stockQuantity,
//			String category, Date createDate, String imageFileName) {
//		super();
//		this.productId = productId;
//		this.sellerId = sellerId;
//		this.name = name;
//		this.description = description;
//		this.price = price;
//		this.stockQuantity = stockQuantity;
//		this.category = category;
//		this.createDate = createDate;
//		this.imageFileName = imageFileName;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

//	public long getSellerId() {
//		return sellerId;
//	}
//
//	public void setSellerId(long sellerId) {
//		this.sellerId = sellerId;
//	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

//	public long getSellerId() {
//		return sellerId;
//	}
//
//	public void setSellerId(long sellerId) {
//		this.sellerId = sellerId;
//	}

	

	

	
	

	

    // Getters and setters
    
}
