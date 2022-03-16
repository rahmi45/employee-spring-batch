package fr.atos.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class EmployeeSpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeSpringBatchApplication.class, args);
    }

}
