package dev.scibaric.meterreadings;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Meter readings API", version = "1.0", description = "Meter readings"))
public class MeterReadingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeterReadingsApplication.class, args);
    }

}
