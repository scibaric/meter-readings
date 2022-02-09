package dev.scibaric.meterreadings.repository;

import dev.scibaric.meterreadings.model.Meter;
import dev.scibaric.meterreadings.model.MeterReading;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MeterRepositoryUnitTest {

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenMeterReadingsExist_thenReturnResult() {
        Integer total = meterReadingRepository.aggregateConsumptionByMeterIdAndYear(1L, 2020);

        assertThat(total)
                .isNotNull()
                .isEqualTo(195);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenMeterReadingsDoNotExist_thenReturnNull() {
        Integer total = meterReadingRepository.aggregateConsumptionByMeterIdAndYear(1L, 2021);

        assertThat(total).isNull();
    }

    @Test
    void findMeterReadingsByMeterIdAndYear_whenMeterReadingsExist_thenReturnResult() {
        List<MeterReading> meterReadings = meterReadingRepository.findMeterReadingsByMeterIdAndYear(1L, 2020);

        assertThat(meterReadings)
                .isNotNull()
                .isNotEmpty()
                .hasSize(12);
    }

    @Test
    void findMeterReadingsByMeterIdAndYear_whenMeterReadingsDoesNotExist_thenReturnEmptySet() {
        List<MeterReading> meterReadings = meterReadingRepository.findMeterReadingsByMeterIdAndYear(1L, 2021);

        assertThat(meterReadings)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void findMeterReadingsByMeterIdAndYearAndMonth_whenMeterReadingsExist_thenReturnResult() {
        MeterReading meterReadings = meterReadingRepository
                .findMeterReadingByMeterIdAndYearAndMonth(1L, 2020, 3);

        assertThat(meterReadings).isNotNull();
        assertThat(meterReadings.getMeter().getId()).isEqualTo(1);
        assertThat(meterReadings.getMonth()).isEqualTo(3);
        assertThat(meterReadings.getEnergyConsumed()).isEqualTo(9);
    }

    @Test
    void findMeterReadingsByMeterIdAndYearAndMonth_whenMeterReadingsDoesNotExist_thenReturnEmptySet() {
        MeterReading meterReadings = meterReadingRepository
                .findMeterReadingByMeterIdAndYearAndMonth(1L, 2021, 5);

        assertThat(meterReadings).isNull();
    }

    @Test
    void save_saveSuccessfully() {
        MeterReading meterReadings = meterReadingRepository.save(new MeterReading(new Meter(1L), 2021, 1, 17));

        MeterReading result = meterReadingRepository.findById(meterReadings.getId()).get();

        assertThat(result).isNotNull();
        assertThat(result.getYear()).isEqualTo(2021);
        assertThat(result.getMonth()).isEqualTo(1);
        assertThat(result.getEnergyConsumed()).isEqualTo(17);
    }

    @Test
    void update_updateSuccessfully() {
        MeterReading meterReading = meterReadingRepository.findMeterReadingByMeterIdAndYearAndMonth(1L, 2020, 1);

        MeterReading updatedMeterReading = meterReadingRepository.save(new MeterReading(new Meter(1L), 2020, 1, 27));

        assertThat(updatedMeterReading)
                .isNotNull()
                .isNotEqualTo(meterReading);
        assertThat(updatedMeterReading.getMeter().getId()).isEqualTo(meterReading.getMeter().getId());
        assertThat(updatedMeterReading.getYear()).isEqualTo(meterReading.getYear());
        assertThat(updatedMeterReading.getMonth()).isEqualTo(meterReading.getMonth());
        assertThat(updatedMeterReading.getEnergyConsumed()).isNotEqualTo(meterReading.getEnergyConsumed());
    }

    @Test
    void delete_deleteSuccessfully() {
        Long id = 1l;
        meterReadingRepository.deleteById(id);

        Optional<MeterReading> result = meterReadingRepository.findById(id);

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "100, false"
    })
    void existsById_ifMeterReadingExistsById_theReturnTrue(Long meterReadingId, Boolean exists) {
        assertThat(meterReadingRepository.existsById(meterReadingId)).isEqualTo(exists);
    }
}