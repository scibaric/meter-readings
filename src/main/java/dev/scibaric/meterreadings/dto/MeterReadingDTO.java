package dev.scibaric.meterreadings.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

/**
 * DTO for saving to and fetching results from the database.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeterReadingDTO {
    private Long id;
    private Integer year;
    private Integer month;
    private Integer total;
    private Integer energyConsumed;
    private Map<String, Integer> monthlyEnergyConsumption;
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
