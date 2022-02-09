package dev.scibaric.meterreadings.service;

import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.exception.ResourceNotFoundException;

/**
 * Service interface called by {@link dev.scibaric.meterreadings.controller.MeterController} to perform business
 * logic. Service interacts with validator and repository.
 */
public interface MeterService {
    /**
     * Method accepts <b>id</b> and <b>year</b> parameters, aggregates consumed electricity by meter id and year.
     * Result is stored in {@link MeterReadingDTO} and returned.
     * Method can throw {@link IllegalArgumentException} if parameters do not satisfy requirements. Another exception
     * that can be thrown is {@link dev.scibaric.meterreadings.exception.ResourceNotFoundException} if results are
     * not found.
     * @param id Meter id
     * @param year Year
     * @throws IllegalArgumentException If parameters do not satisfy requirements.
     * @throws ResourceNotFoundException If results are not found
     * @return {@link MeterReadingDTO}
     */
    MeterReadingDTO aggregateConsumptionByMeterIdAndYear(Long id, Integer year);

    /**
     * Method accepts <b>id</b> and <b>year</b> parameters, returns consumed electricity per month by meter id and year.
     * Result is stored in {@link MeterReadingDTO} and returned.
     * Method can throw {@link IllegalArgumentException} if parameters do not satisfy requirements. Another exception
     * that can be thrown is {@link dev.scibaric.meterreadings.exception.ResourceNotFoundException} if results are
     * not found.
     * @param id Meter id
     * @param year Year
     * @throws IllegalArgumentException If parameters do not satisfy requirements.
     * @throws ResourceNotFoundException If results are not found
     * @return {@link MeterReadingDTO}
     */
    MeterReadingDTO findByMeterIdAndYear(Long id, Integer year);

    /**
     * Method accepts <b>id</b>, <b>year</b> and <b>month</b>parameters, returns consumed electricity per month by
     * meter id, year and month. Result is stored in {@link MeterReadingDTO} and returned.
     * Method can throw {@link IllegalArgumentException} if parameters do not satisfy requirements. Another exception
     * that can be thrown is {@link dev.scibaric.meterreadings.exception.ResourceNotFoundException} if results are
     * not found.
     * @param id Meter id
     * @param year Year
     * @param month Month
     * @throws IllegalArgumentException If parameters do not satisfy requirements.
     * @throws ResourceNotFoundException If results are not found
     * @return {@link MeterReadingDTO}
     */
    MeterReadingDTO findByMeterIdAndYearAndMonth(Long id, Integer year, Integer month);

    /**
     * Method accepts parameter <b>meterReadingDTO</b>. Parameter is then validated and if there is no
     * meter reading for meter with id, year and month object is mapped to
     * {@link dev.scibaric.meterreadings.model.MeterReading}. {@link dev.scibaric.meterreadings.model.MeterReading} is
     * propagated to the database if everything is alright. {@link MeterReadingDTO} is returned.
     * Method can throw {@link IllegalArgumentException} if parameter is not valid.
     *
     * @param meterReadingDTO Meter reading DTO
     * @throws IllegalArgumentException If parameters do not satisfy requirements and if meter reading for meter id
     * year and month exist.
     * @return {@link MeterReadingDTO}
     */
    MeterReadingDTO saveMeterReading(MeterReadingDTO meterReadingDTO);
}
