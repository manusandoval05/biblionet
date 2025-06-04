package com.biblios.biblionet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BiblionetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblionetApplication.class, args);
	}
	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }
	@GetMapping("/tung")
	public String tung(@RequestParam(value = "name", defaultValue = "World") String name) {
      return "Estás en territorio de Tung Tung Tung Sahur";
    }	

}
