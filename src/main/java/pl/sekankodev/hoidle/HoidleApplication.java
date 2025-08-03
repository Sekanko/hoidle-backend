package pl.sekankodev.hoidle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pl.sekankodev")
@EnableCaching
public class HoidleApplication {
    public static void main(String[] args) {
        SpringApplication.run(HoidleApplication.class, args);
    }
}
