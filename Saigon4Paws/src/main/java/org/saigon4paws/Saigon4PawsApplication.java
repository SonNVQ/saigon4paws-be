package org.saigon4paws;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.saigon4paws.DTO.Bank;
import org.saigon4paws.Services.Impl.BankServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,  ManagementWebSecurityAutoConfiguration.class})
public class Saigon4PawsApplication {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Saigon4PawsApplication.class, args);
    }

}
