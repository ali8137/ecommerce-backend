package com.ali.ecommerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateNewCategoryRequestDTO {

    @NotNull(message = "category names cannot be null")
//    @NotBlank
////  - @NotBlank can't be used on a List, it is only applied on a String. @NotEmpty is
////    used for a List instead
    @NotEmpty(message = "category names cannot be empty")
    private List<String> categoryNames;
    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be blank")
    private String description;
//    description is the description of the last category (the most child category)
    private Long parentId;

}
