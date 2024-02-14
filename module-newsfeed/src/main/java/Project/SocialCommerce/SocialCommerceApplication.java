package Project.SocialCommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication/*(exclude = {SecurityAutoConfiguration.class})*/
@EnableDiscoveryClient
@EnableFeignClients
public class SocialCommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialCommerceApplication.class, args);
	}

}
