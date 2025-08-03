package pl.sekankodev.hoidledataupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pl.sekankodev")
public class HoidleDataUpdaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(HoidleDataUpdaterApplication.class, args);
    }
}
