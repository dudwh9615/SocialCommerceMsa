package Project.SocialCommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication/*(exclude = {SecurityAutoConfiguration.class})*/
@EntityScan(basePackages = "Project.SocialCommerce.model")
public class SocialCommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialCommerceApplication.class, args);
	}

}
