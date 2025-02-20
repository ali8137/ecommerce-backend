package com.ali.ecommerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
/* TODO: add an annotation here to impose validation on many data
    fields at once, for better readability
    */
public class CreateNewCategoryRequestDTO {

    @NotNull(message = "category names cannot be null")
    @NotEmpty(message = "category names cannot be empty")
    private List<String> categoryNames;
    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be blank")
    private String description;
    private Long parentId;

}
