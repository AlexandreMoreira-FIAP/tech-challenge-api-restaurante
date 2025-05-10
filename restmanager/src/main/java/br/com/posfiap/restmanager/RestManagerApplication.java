package br.com.posfiap.restmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.posfiap.restmanager")
public class RestManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestManagerApplication.class, args);
	}

}
