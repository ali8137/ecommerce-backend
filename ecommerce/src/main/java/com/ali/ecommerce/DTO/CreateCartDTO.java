package com.ali.ecommerce.DTO;

import com.ali.ecommerce.model.CartItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateCartDTO {

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @NotNull(message = "cartItem cannot be null")
    private CartItem cartItem;
}
