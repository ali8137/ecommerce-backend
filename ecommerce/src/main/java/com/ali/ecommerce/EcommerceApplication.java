package com.ali.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
// - the above was added because the library -that the Page<> object is part of- will
//   be serialized differently if this annotation is not added after some period of
//   time. check this chat with ChatGPT about that https://chatgpt.com/share/67773480-d76c-8013-9863-fde53df16cd4
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
