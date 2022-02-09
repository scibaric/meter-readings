package dev.scibaric.meterreadings.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * DTO for saving to and fetching results from the database.
 */
@Data
@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeterReadingDTO {
    @Schema(description = "Meter reading id")
    private Long id;

    @Schema(description = "Meter reading year", example = "2020", minimum = "0")
    private Integer year;
    @Schema(description = "Meter reading month", example = "2", minimum = "1", maximum = "12")
    private Integer month;
    @Schema(description = "Meter reading aggregate energy consumed", example = "195")
    private Integer total;
    @Schema(description = "Meter reading energy consumed", example = "23", minimum = "0")
    private Integer energyConsumed;
    @Schema(description = "Meter reading energy consumed per month", example = "January: 23", minimum = "0")
    private Map<String, Integer> monthlyEnergyConsumption;
    @Schema(description = "Meter id", example = "10")
    private Long meterId;

    public MeterReadingDTO() {
    }

    public MeterReadingDTO(Integer year, Integer month, Integer energyConsumed, Long meterId) {
        this.year = year;
        this.month = month;
        this.energyConsumed = energyConsumed;
        this.meterId = meterId;
    }

    public MeterReadingDTO(Integer year, Map<String, Integer> monthlyEnergyConsumption) {
        this.year = year;
        this.monthlyEnergyConsumption = monthlyEnergyConsumption;
    }
}
