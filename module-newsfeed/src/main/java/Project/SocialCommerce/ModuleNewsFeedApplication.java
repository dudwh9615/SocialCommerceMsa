package Project.SocialCommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication/*(exclude = {SecurityAutoConfiguration.class})*/
@EnableDiscoveryClient
@EnableFeignClients
public class ModuleNewsFeedApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleNewsFeedApplication.class, args);
	}

}
