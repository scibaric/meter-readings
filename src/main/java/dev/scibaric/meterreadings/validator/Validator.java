package dev.scibaric.meterreadings.validator;

import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.repository.MeterReadingRepository;
import dev.scibaric.meterreadings.repository.MeterRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Year;

/**
 * Class for validating user inputs. Methods for validating raise {@link IllegalArgumentException} if parameters do not
 * meet the predefined conditions.
 */
@Component
public class Validator {

    private final MeterRepository meterRepository;

    private final MeterReadingRepository meterReadingRepository;

    public Validator(MeterRepository meterRepository, MeterReadingRepository meterReadingRepository) {
        this.meterRepository = meterRepository;
        this.meterReadingRepository = meterReadingRepository;
    }

    /**
     * Validates meter id, if conditions are not met {@link IllegalArgumentException} is raised.
     * Meter id must not be null, must be greater than zero and should exist in the database.
     *
     * @param id Meter id
     * @throws IllegalArgumentException
     */
    public void validateMeterId(Long id) {
        Assert.notNull(id, "Meter id must not be null");
        Assert.isTrue(id > 0, "Meter id must be greater than 0");
        Assert.isTrue(meterRepository.existsById(id), String.format("Meter with id %d does not exist", id));
    }

    /**
     * Validates year, if conditions are not met {@link IllegalArgumentException} is raised.
     * Year must not be null, must be greater than zero or equal zero and must not be in the future.
     *
     * @param year Year
     * @throws IllegalArgumentException
     */
    public void validateYear(Integer year) {
        Assert.notNull(year, "Year must not be null");
        Assert.isTrue(year >= 0, "Year must be greater than or equal 0");
        Assert.isTrue(year <= Year.now().getValue(), "Year must not be in the future");
    }

    /**
     * Validates month, if conditions are not met {@link IllegalArgumentException} is raised.
     * Month must not be null and must be between one and twelve.
     *
     * @param month Month
     * @throws IllegalArgumentException
     */
    public void validateMonth(Integer month) {
        Assert.notNull(month, "Month must not be null");
        Assert.isTrue(month >= 1 && month <= 12, "Month must be between 1 and 12");
    }

    /**
     * Validates consumed energy, if conditions are not met {@link IllegalArgumentException} is raised.
     * Consumed energy must not be null and must be greater than or equal zero.
     *
     * @param energyConsumed Energy consumed
     * @throws IllegalArgumentException
     */
    public void validateEnergyConsumed(Integer energyConsumed) {
        Assert.notNull(energyConsumed, "Energy consumed must not be null");
        Assert.isTrue(energyConsumed >= 0, "Energy consumed must greater or equals 0");
    }

    /**
     * Validates meter reading, if conditions are not met {@link IllegalArgumentException} is raised.
     * Meter reading must not be null and must meet conditions defined in {@link #validateMeterId(Long)},
     * {@link #validateYear(Integer)}, {@link #validateMonth(Integer)}, {@link #validateEnergyConsumed(Integer)}.
     *
     * @param meterReadingDTO Meter reading
     * @throws IllegalArgumentException
     */
    public void validateMeterReadingDTO(MeterReadingDTO meterReadingDTO) {
        Assert.notNull(meterReadingDTO, "Meter reading must not be null");
        validateMeterId(meterReadingDTO.getMeterId());
        validateYear(meterReadingDTO.getYear());
        validateMonth(meterReadingDTO.getMonth());
        validateEnergyConsumed(meterReadingDTO.getEnergyConsumed());
    }

    /**
     * Validates meter reading id, if conditions are not met {@link IllegalArgumentException} is raised.
     * Meter reading id must not be null, must greater than zero and should exist in database.
     *
     * @param meterReadingId Meter reading id
     * @throws IllegalArgumentException
     */
    public void validateMeterReadingId(Long meterReadingId) {
        Assert.notNull(meterReadingId, "Meter reading id must not be null");
        Assert.isTrue(meterReadingId > 0, "Meter reading id must be greater than 0");
        Assert.isTrue(meterReadingRepository.existsById(meterReadingId),
                String.format("Meter reading with id %d does not exist", meterReadingId));
    }
}
