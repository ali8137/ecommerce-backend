package com.ali.ecommerce.util;

import com.ali.ecommerce.model.Product;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public class ProductFieldNames { // this class can be made a generic one to include all the entity classes of this project

    public static List<String> getProductClassFieldNames() {

        // get fields of Product entity class by reflection:
        Class<?> productClass = Product.class;
        // return List.of(productClass.getDeclaredFields());
        List<String> fieldNames = Stream.of(productClass.getDeclaredFields()) // this line is equivalent to "List.of(productClass.getDeclaredFields()).stream()"
                .map(Field::getName) // this line is equivalent to "map(field -> field.getName())"
                .toList();

        // we can choose to throw an exception if the list of field names is empty

        return fieldNames;
    }
}
