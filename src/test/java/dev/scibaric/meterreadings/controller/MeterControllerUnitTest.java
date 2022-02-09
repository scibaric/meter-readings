package dev.scibaric.meterreadings.controller;

import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.exception.ResourceNotFoundException;
import dev.scibaric.meterreadings.service.MeterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MeterControllerUnitTest {

    @MockBean
    private MeterService service;

    private MeterController controller;

    @BeforeEach
    void setUp() {
        controller = new MeterController(service);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenMeterIdExists_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer total = 30;
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setTotal(total);

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenReturn(meterReadingDTO);
        MeterReadingDTO result = controller.aggregateConsumptionByMeterIdAndYear(id, year).getBody();

        // then
        assertThat(result.getYear())
                .isEqualTo(year);
        assertThat(result.getTotal())
                .isEqualTo(total);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenMeterIdIsNull_thenThrowException() {
        // given
        Long id = null;
        Integer year = Year.now().getValue();
        String message = "Meter id must not be null";

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.aggregateConsumptionByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenMeterIdIsZero_thenThrowException() {
        // given
        Long id = 0L;
        Integer year = Year.now().getValue();
        String message = "Meter id must be greater than zero";

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.aggregateConsumptionByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenMeterIdDoesNotExist_thenThrowException() {
        // given
        Long id = 5L;
        Integer year = Year.now().getValue();
        String message = String.format("Meter with id %d does not exist", id);

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.aggregateConsumptionByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenYearIsCurrent_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer total = 30;
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setTotal(total);

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenReturn(meterReadingDTO);
        MeterReadingDTO result = controller.aggregateConsumptionByMeterIdAndYear(id, year).getBody();

        // then
        assertThat(result.getYear())
                .isEqualTo(year);
        assertThat(result.getTotal())
                .isEqualTo(total);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
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
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenReturn(meterReadingDTO);
        MeterReadingDTO result = controller.aggregateConsumptionByMeterIdAndYear(id, year).getBody();

        // then
        assertThat(result.getYear())
                .isEqualTo(year);
        assertThat(result.getTotal())
                .isEqualTo(total);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenYearIsNull_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = null;
        String message = "Year must not be null";

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.aggregateConsumptionByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenYearLessThanEqualZero_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 0;
        String message = "Year must be greater than or equal 0";

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.aggregateConsumptionByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenYearIsInTheFuture_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue() + 1;
        String message = "Year must not be in the future";

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.aggregateConsumptionByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenReadingForYearDoesNotExist_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 1994;
        String message = String.format("Meter readings for meter id %d and year %d does not exist", id, year);

        // when
        when(service.aggregateConsumptionByMeterIdAndYear(id, year)).thenThrow(new ResourceNotFoundException(message));

        // then
        assertThatThrownBy(() -> controller.aggregateConsumptionByMeterIdAndYear(id, year))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(message);

        verify(service).aggregateConsumptionByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenYearIsCurrent_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();

        Map<String, Integer> monthlyEnergyConsumption = new HashMap<>();
        monthlyEnergyConsumption.put("January", 23);
        monthlyEnergyConsumption.put("February", 27);

        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonthlyEnergyConsumption(monthlyEnergyConsumption);

        // when
        when(service.findByMeterIdAndYear(id, year)).thenReturn(meterReadingDTO);
        MeterReadingDTO result = controller.findByMeterIdAndYear(id, year).getBody();

        // then
        assertThat(result.getYear())
                .isEqualTo(year);
        assertThat(result.getMonthlyEnergyConsumption())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsKey("January")
                .containsValue(27);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenYear2021_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = 2021;

        Map<String, Integer> monthlyEnergyConsumption = new HashMap<>();
        monthlyEnergyConsumption.put("April", 15);
        monthlyEnergyConsumption.put("May", 11);

        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonthlyEnergyConsumption(monthlyEnergyConsumption);

        // when
        when(service.findByMeterIdAndYear(id, year)).thenReturn(meterReadingDTO);
        MeterReadingDTO result = controller.findByMeterIdAndYear(id, year).getBody();

        // then
        assertThat(result.getYear())
                .isEqualTo(year);
        assertThat(result.getMonthlyEnergyConsumption())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsKey("May")
                .containsValue(15);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenMeterIdIsNull_thenThrowException() {
        // given
        Long id = null;
        Integer year = Year.now().getValue();
        String message = "Meter id must not be null";

        // when
        when(service.findByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenMeterIdIsZero_thenThrowException() {
        // given
        Long id = 0L;
        Integer year = Year.now().getValue();
        String message = "Meter id must be greater than zero";

        // when
        when(service.findByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenMeterIdDoesNotExist_thenThrowException() {
        // given
        Long id = 5L;
        Integer year = Year.now().getValue();
        String message = String.format("Meter with id %d does not exist", id);

        // when
        when(service.findByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenYearIsNull_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = null;
        String message = "Year must not be null";

        // when
        when(service.findByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenYearLessThanEqualZero_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 0;
        String message = "Year must be greater than or equal 0";

        // when
        when(service.findByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenYearIsInTheFuture_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue() + 1;
        String message = "Year must not be in the future";

        // when
        when(service.findByMeterIdAndYear(id, year)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYear(id, year))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYear_whenReadingForYearDoesNotExist_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 1994;
        String message = String.format("Meter readings for meter id %d and year %d does not exist", id, year);

        // when
        when(service.findByMeterIdAndYear(id, year)).thenThrow(new ResourceNotFoundException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYear(id, year))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYear(id, year);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenYearIsCurrent_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer month = 2;

        Map<String, Integer> monthlyEnergyConsumption = new HashMap<>();
        monthlyEnergyConsumption.put("February", 27);

        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonthlyEnergyConsumption(monthlyEnergyConsumption);

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenReturn(meterReadingDTO);
        MeterReadingDTO result = controller.findByMeterIdAndYearAndMonth(id, year, month).getBody();

        // then
        assertThat(result.getYear())
                .isEqualTo(year);
        assertThat(result.getMonthlyEnergyConsumption())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .containsKey("February")
                .containsValue(27);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenYear2021_thenReturnResult() {
        // given
        Long id = 1L;
        Integer year = 2021;
        Integer month = 5;

        Map<String, Integer> monthlyEnergyConsumption = new HashMap<>();
        monthlyEnergyConsumption.put("May", 11);

        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonthlyEnergyConsumption(monthlyEnergyConsumption);

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenReturn(meterReadingDTO);
        MeterReadingDTO result = controller.findByMeterIdAndYearAndMonth(id, year, month).getBody();

        // then
        assertThat(result.getYear())
                .isEqualTo(year);
        assertThat(result.getMonthlyEnergyConsumption())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .containsKey("May")
                .containsValue(11);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenMeterIdIsNull_thenThrowException() {
        // given
        Long id = null;
        Integer year = Year.now().getValue();
        Integer month = 3;
        String message = "Meter id must not be null";

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenMeterIdIsZero_thenThrowException() {
        // given
        Long id = 0L;
        Integer year = Year.now().getValue();
        Integer month = 3;
        String message = "Meter id must be greater than zero";

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenMeterIdDoesNotExist_thenThrowException() {
        // given
        Long id = 5L;
        Integer year = Year.now().getValue();
        Integer month = 3;
        String message = String.format("Meter with id %d does not exist", id);

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenYearIsNull_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = null;
        Integer month = 3;
        String message = "Year must not be null";

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenYearLessThanEqualZero_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 0;
        Integer month = 3;
        String message = "Year must be greater than or equal 0";

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenYearIsInTheFuture_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue() + 1;
        Integer month = 3;
        String message = "Year must not be in the future";

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenMonthIsNull_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer month = null;
        String message = "Month must not be null";

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 13})
    void findByMeterIdAndYearAndMonth_whenMonthIsNotBetween1And12_thenThrowException(Integer month) {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        String message = "Month must be between 1 and 12";

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenReadingForYearDoesNotExist_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 1994;
        Integer month = 3;
        String m = Month.of(3).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String message = String.format("Meter reading for meter id %d, year %d and month %s does not exist", id, year, m);

        // when
        when(service.findByMeterIdAndYearAndMonth(id, year, month)).thenThrow(new ResourceNotFoundException(message));

        // then
        assertThatThrownBy(() -> controller.findByMeterIdAndYearAndMonth(id, year, month))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(message);

        verify(service).findByMeterIdAndYearAndMonth(id, year, month);
    }
    
    // *****************************

    @Test
    void saveMeterReading_whenMeterReadingIsNull_thenThrowException() {
        // given
        String message = "Meter reading must not be null";
        MeterReadingDTO meterReadingDTO = null;

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenMeterIdIsNull_thenThrowException() {
        // given
        Long id = null;
        String message = "Meter id must not be null";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenMeterIdIsZero_thenThrowException() {
        // given
        Long id = 0L;
        String message = "Meter id must be greater than zero";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenMeterIdDoesNotExist_thenThrowException() {
        // given
        Long id = 5L;
        String message = String.format("Meter with id %d does not exist", id);
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenYearIsNull_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = null;
        String message = "Year must not be null";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenYearLessThanEqualZero_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 0;
        String message = "Year must be greater than or equal 0";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenYearIsInTheFuture_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue() + 1;
        String message = "Year must not be in the future";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenMonthIsNull_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer month = null;
        String message = "Month must not be null";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonth(month);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenMonthIsLessThan1_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer month = 0;
        String message = "Month must be between 1 and 12";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonth(month);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenMonthIsGreaterThan12_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer month = 0;
        String message = "Month must be between 1 and 12";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonth(month);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenEnergyConsumedIsNull_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer month = 4;
        Integer energyConsumed = null;
        String message = "Energy consumed must not be null";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonth(month);
        meterReadingDTO.setEnergyConsumed(energyConsumed);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenEnergyConsumedIsLessThanZero_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = Year.now().getValue();
        Integer month = 4;
        Integer energyConsumed = -1;
        String message = "Energy consumed must greater or equals 0";
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonth(month);
        meterReadingDTO.setEnergyConsumed(energyConsumed);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }

    @Test
    void saveMeterReading_whenMeterReadingForSpecificMonthExist_thenThrowException() {
        // given
        Long id = 1L;
        Integer year = 2020;
        Integer month = 3;
        Integer energyConsumed = 14;
        String m = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String message = String.format("Meter reading for meter id %d, year %d and month %s already exists", id, year, m);
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(year);
        meterReadingDTO.setEnergyConsumed(energyConsumed);

        // when
        when(service.saveMeterReading(meterReadingDTO)).thenThrow(new IllegalArgumentException(message));

        // then
        assertThatThrownBy(() -> controller.saveMeterReading(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);

        verify(service).saveMeterReading(meterReadingDTO);
    }
}