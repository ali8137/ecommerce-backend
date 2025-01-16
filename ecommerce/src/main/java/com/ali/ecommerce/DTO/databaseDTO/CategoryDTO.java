package com.ali.ecommerce.DTO.databaseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
// - the getters and setters are important for serialization and deserialization. we could
//   have added @Getter and @Setter instead of the above @Data annotation
public class CategoryDTO {
    private Long id;
    private String path;
}
