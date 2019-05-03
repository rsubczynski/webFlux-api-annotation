package com.flux.demoflux;

import com.flux.demoflux.model.Product;
import com.flux.demoflux.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class DemoFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoFluxApplication.class, args);
	}


	@Bean
	CommandLineRunner init (ProductRepository productRepository){
		return args -> {
			Flux<Product> productFlux = Flux.just(
					new Product(null,"Big Latte",2.99),
					new Product(null,"Big Decaf",2.49),
					new Product(null,"Green Tea",1.99)
			).flatMap(productRepository::save);

			productFlux.thenMany(productRepository.findAll())
					.subscribe(System.out::println);
		};

	}
}
