package admin_user.dto;

import java.util.Date;
import java.util.List;

public class ShoppingCartDto {

    private Long cartId;
    private Long userId; // Assuming you want to keep track of the user ID associated with the cart
    private Date createDate;
    private List<CartItemDto> cartItems; // Assuming you have a CartItemDto

    // Constructors, Getters, and Setters

    public ShoppingCartDto() {
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }
}
