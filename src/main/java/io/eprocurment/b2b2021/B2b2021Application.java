package io.eprocurment.b2b2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class B2b2021Application {

    public static void main(String[] args) {
        SpringApplication.run(B2b2021Application.class, args);
    }

}
