package com.ali.ecommerce.util;

import com.ali.ecommerce.model.Product;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

//the below class can be made a generic one to include all the entity classes of this project
public class ProductFieldNames {

    public static List<String> getProductClassFieldNames() {

//        reflection:
        Class<?> productClass = Product.class;

//        return List.of(productClass.getDeclaredFields());

        List<String> fieldNames = Stream.of(productClass.getDeclaredFields())
//                the above line is equivalent to "List.of(productClass.getDeclaredFields()).stream()"
                .map(Field::getName)
//                the above line is equivalent to "map(field -> field.getName())"
                .toList();

//        we can choose to throw an exception if the list of field names is empty

        return fieldNames;
    }
}
