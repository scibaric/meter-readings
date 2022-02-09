package dev.scibaric.meterreadings.service;

import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.exception.ResourceNotFoundException;
import dev.scibaric.meterreadings.model.Address;
import dev.scibaric.meterreadings.model.Client;
import dev.scibaric.meterreadings.model.Meter;
import dev.scibaric.meterreadings.model.MeterReading;
import dev.scibaric.meterreadings.repository.MeterReadingRepository;
import dev.scibaric.meterreadings.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MeterServiceUnitTest {

    @MockBean
    private MeterReadingRepository meterReadingRepository;

    @MockBean
    private Validator validator;

    private MeterService service;

    @BeforeEach
    void setup(){
        service = new MeterServiceImpl(meterReadingRepository, validator);
    }

    @Test
    void validateMeterId_whenMeterIdIsNull_thenThrowException() {
        // given
        Long id = null;
        String message = "Meter id must not be null";

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterId(id);

        // then
        assertThatThrownBy(() -> validator.validateMeterId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterId(id);
    }

    @Test
    void validateMeterId_whenMeterIdIsZero_thenThrowException() {
        // given
        Long id = 0L;
        String message = "Meter id must not be greater than 0";

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterId(id);

        // then
        assertThatThrownBy(() -> validator.validateMeterId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterId(id);
    }

    @Test
    void validateMeterId_whenMeterIdDoesNotExist_thenThrowException() {
        // given
        Long id = 7L;
        String message = String.format("Meter with id %d does not exist", id);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterId(id);

        // then
        assertThatThrownBy(() -> validator.validateMeterId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterId(id);
    }

    @Test
    void validateYear_whenYearIsNull_thenThrowException() {
        // given
        Integer year = null;
        String message = "Year must not be null";

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateYear(year);

        // then
        assertThatThrownBy(() -> validator.validateYear(year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateYear(year);
    }

    @Test
    void validateYear_whenYearLessThanEqualZero_thenThrowException() {
        // given
        Integer year = 0;
        String message = "Year must be greater than or equal 0";

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateYear(year);

        // then
        assertThatThrownBy(() -> validator.validateYear(year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateYear(year);
    }

    @Test
    void validateYear_whenYearIsInTheFuture_thenThrowException() {
        // given
        Integer year = Year.now().getValue() + 1;
        String message = "Year must not be in the future";

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateYear(year);

        // then
        assertThatThrownBy(() -> validator.validateYear(year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateYear(year);
    }

    @Test
    void validateMonth_whenMonthIsNull_thenThrowException() {
        // given
        Integer month = null;
        String message = "Month must not be null";

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMonth(month);

        // then
        assertThatThrownBy(() -> validator.validateMonth(month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMonth(month);
    }

    @Test
    void validateMonth_whenMonthIsLessThan1_thenThrowException() {
        // given
        Integer month = 0;
        String message = "Month must be between 1 and 12";

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMonth(month);

        // then
        assertThatThrownBy(() -> validator.validateMonth(month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMonth(month);
    }

    @Test
    void validateMonth_whenMonthIsGreaterThan12_thenThrowException() {
        // given
        Integer month = 13;
        String message = "Month must be between 1 and 12";

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMonth(month);

        // then
        assertThatThrownBy(() -> validator.validateMonth(month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMonth(month);
    }

    @Test
    void validateMeterReadingDTO_whenMeterIdIsNull_thenThrowException() {
        // given
        Long id = null;
        String message = "Meter id must not be null";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenMeterIdIsZero_thenThrowException() {
        // given
        Long id = 0L;
        String message = "Meter id must not be greater than 0";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenMeterIdDoesNotExist_thenThrowException() {
        // given
        Long id = 7L;
        String message = String.format("Meter with id %d does not exist", id);
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenYearIsNull_thenThrowException() {
        // given
        Integer year = null;
        String message = "Year must not be null";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenYearLessThanEqualZero_thenThrowException() {
        // given
        Integer year = 0;
        String message = "Year must be greater than or equal 0";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenYearIsInTheFuture_thenThrowException() {
        // given
        Integer year = Year.now().getValue() + 1;
        String message = "Year must not be in the future";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenMonthIsNull_thenThrowException() {
        // given
        Integer month = null;
        String message = "Month must not be null";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMonth(month);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenMonthIsLessThan1_thenThrowException() {
        // given
        Integer month = 0;
        String message = "Month must be between 1 and 12";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMonth(month);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenMonthIsGreaterThan12_thenThrowException() {
        // given
        Integer month = 13;
        String message = "Month must be between 1 and 12";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMonth(month);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenEnergyConsumedIsNull_thenThrowException() {
        // given
        Integer energyConsumed = null;
        String message = "Energy consumed must not be null";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setEnergyConsumed(energyConsumed);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenEnergyConsumedIsLessThan0_thenThrowException() {
        // given
        Integer energyConsumed = -1;
        String message = "Energy consumed must greater or equals 0";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setEnergyConsumed(energyConsumed);

        // when
        doThrow(new IllegalArgumentException(message))
                .when(validator)
                .validateMeterReadingDTO(meterReadingDTO);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(validator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenYearIsCurrent_ThenReturnResult() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer total = 30;
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setTotal(total);

        // when
        when(meterReadingRepository.aggregateConsumptionByMeterIdAndYear(id, year)).thenReturn(total);

        meterReadingDTO = service.aggregateConsumptionByMeterIdAndYear(id, year);

        // then
        assertThat(meterReadingDTO.getYear())
                .isEqualTo(year);
        assertThat(meterReadingDTO.getTotal())
                .isEqualTo(total);

        verify(meterReadingRepository).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenYear2021_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = 2021;
        Integer total = 30;
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setTotal(total);

        // when
        when(meterReadingRepository.aggregateConsumptionByMeterIdAndYear(id, year)).thenReturn(total);
        MeterReadingDTO result = service.aggregateConsumptionByMeterIdAndYear(id, year);

        // then
        assertThat(result.getYear())
                .isEqualTo(year);
        assertThat(result.getTotal())
                .isEqualTo(total);

        verify(meterReadingRepository).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenReadingForYearDoesNotExist_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 1994;
        String message = String.format("Meter readings for meter id %d and year %d does not exist", id, year);

        // when
        when(meterReadingRepository.aggregateConsumptionByMeterIdAndYear(id, year)).thenReturn(null);

        // then
        assertThatThrownBy(() -> service.aggregateConsumptionByMeterIdAndYear(id, year))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(message);

        verify(meterReadingRepository).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenMeterReadingsExist_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = 2020;
        List<MeterReading> meterReadings = new ArrayList<>();

        Meter meter = new Meter(id,
                new Client(id, "Luka Modric",
                        new Address(id, "Ilica", "10", "Zagreb")
                )
        );

        meterReadings.add(new MeterReading(1L, meter, year, 1, 17));
        meterReadings.add(new MeterReading(2L, meter, year, 2, 14));

        Map<String, Integer> monthlyEnergyConsumption = new HashMap<>();
        monthlyEnergyConsumption.put(Month.of(1).getDisplayName(TextStyle.FULL, Locale.ENGLISH), 17);
        monthlyEnergyConsumption.put(Month.of(2).getDisplayName(TextStyle.FULL, Locale.ENGLISH), 14);

        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonthlyEnergyConsumption(monthlyEnergyConsumption);

        // when
        when(meterReadingRepository.findMeterReadingsByMeterIdAndYear(id, year)).thenReturn(meterReadings);
        MeterReadingDTO result = service.findByMeterIdAndYear(id, year);

        // then
        assertThat(result)
                .isNotNull()
                .isEqualTo(meterReadingDTO);

        assertThat(result.getMonthlyEnergyConsumption())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(monthlyEnergyConsumption)
                .containsValue(17);

        verify(meterReadingRepository).findMeterReadingsByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenMeterReadingsDoesNotExist_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 2023;

        // when
        when(meterReadingRepository.findMeterReadingsByMeterIdAndYear(id, year)).thenReturn(new ArrayList<>());

        // then
        assertThatThrownBy(() -> service.findByMeterIdAndYear(id, year))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(String.format("Meter readings for meter id %d and year %d does not exist", id, year));

        verify(meterReadingRepository).findMeterReadingsByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenMeterReadingsExist_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = 2020;
        Integer month = 2;

        Meter meter = new Meter(id,
                new Client(id, "Luka Modric",
                        new Address(id, "Ilica", "10", "Zagreb")
                )
        );

        MeterReading meterReading = new MeterReading(2L, meter, year, 2, 14);

        Map<String, Integer> monthlyEnergyConsumption = new HashMap<>();
        monthlyEnergyConsumption.put(Month.of(2).getDisplayName(TextStyle.FULL, Locale.ENGLISH), 14);

        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonthlyEnergyConsumption(monthlyEnergyConsumption);

        // when
        when(meterReadingRepository.findMeterReadingByMeterIdAndYearAndMonth(id, year, month)).thenReturn(meterReading);
        MeterReadingDTO result = service.findByMeterIdAndYearAndMonth(id, year, month);

        // then
        assertThat(result)
                .isNotNull()
                .isEqualTo(meterReadingDTO);

        assertThat(result.getMonthlyEnergyConsumption())
                .isNotNull()
                .isNotEmpty()
                .containsKey("February")
                .containsValue(14);

        verify(meterReadingRepository).findMeterReadingByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenMeterReadingsDoesNotExist_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 2023;
        Integer month = 11;
        String m = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        // when
        when(meterReadingRepository.findMeterReadingsByMeterIdAndYear(id, year)).thenReturn(new ArrayList<>());

        // then
        assertThatThrownBy(() -> service.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(String.format("Meter reading for meter id %d, year %d and month %s does not exist", id, year, m));

        verify(meterReadingRepository).findMeterReadingByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void saveMeterReading_whenMeterReadingIsCompleted_performSaveAndReturnResult() {
        // given
        Long id = 1L;
        Integer year = 2021;
        Integer month = 4;
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonth(month);
        meterReadingDTO.setEnergyConsumed(15);
        String m = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        // when
        when(meterReadingRepository.findMeterReadingByMeterIdAndYearAndMonth(meterReadingDTO.getMeterId(),
                meterReadingDTO.getYear(), meterReadingDTO.getMonth())).thenReturn(null);

        MeterReading meterReading = new MeterReading(new Meter(meterReadingDTO.getMeterId()),
                meterReadingDTO.getYear(), meterReadingDTO.getMonth(), meterReadingDTO.getEnergyConsumed());

        when(meterReadingRepository.save(meterReading)).thenReturn(meterReading);

        MeterReadingDTO result = service.saveMeterReading(meterReadingDTO);

        // then
        assertThat(result)
                .isNotNull()
                .isEqualTo(meterReadingDTO);

        verify(meterReadingRepository).findMeterReadingByMeterIdAndYearAndMonth(id, year, month);
        verify(meterReadingRepository).save(meterReading);
    }

    @Test
    void saveMeterReading_whenMeterReadingForSpecificMonthExist_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 2020;
        Integer month = 4;
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonth(month);
        meterReadingDTO.setEnergyConsumed(15);
        String m = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        MeterReading meterReading = new MeterReading(new Meter(meterReadingDTO.getMeterId()),
                meterReadingDTO.getYear(), meterReadingDTO.getMonth(), meterReadingDTO.getEnergyConsumed());

        // when
        when(meterReadingRepository.findMeterReadingByMeterIdAndYearAndMonth(meterReadingDTO.getMeterId(),
                meterReadingDTO.getYear(), meterReadingDTO.getMonth())).thenReturn(meterReading);

        // then
        assertThatThrownBy(() -> service.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("Meter reading for meter id %d, year %d and month %s already exists", id, year, m));

        verify(meterReadingRepository).findMeterReadingByMeterIdAndYearAndMonth(id, year, month);
        verify(meterReadingRepository, never()).save(meterReading);
    }

}