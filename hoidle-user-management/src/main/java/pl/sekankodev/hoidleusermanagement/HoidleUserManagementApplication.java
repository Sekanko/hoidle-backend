package pl.sekankodev.hoidleusermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pl.sekankodev")
public class HoidleUserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoidleUserManagementApplication.class, args);
	}

}
