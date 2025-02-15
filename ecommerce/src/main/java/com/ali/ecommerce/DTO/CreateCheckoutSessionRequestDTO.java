package com.ali.ecommerce.DTO;

import com.ali.ecommerce.model.CartItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateCheckoutSessionRequestDTO {

    @NotNull(message = "cartItems cannot be null")
    @NotEmpty(message = "cartItems cannot be empty")
    private List<CartItem> cartItems;
    //    or:
//    private Long cartId;
    @NotNull(message = "currency cannot be null")
    @NotBlank(message = "currency cannot be blank")
    private String currency;
    private Long orderId;
    private String successUrl;
    private String cancelUrl;
}
