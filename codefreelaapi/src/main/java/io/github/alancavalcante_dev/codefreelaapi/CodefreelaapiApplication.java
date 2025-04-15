package io.github.alancavalcante_dev.codefreelaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CodefreelaapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodefreelaapiApplication.class, args);
	}

}
