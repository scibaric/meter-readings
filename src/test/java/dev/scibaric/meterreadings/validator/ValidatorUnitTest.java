package dev.scibaric.meterreadings.validator;

import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.repository.MeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ValidatorUnitTest {

    @MockBean
    private Validator mockValidator;

    @MockBean
    private MeterRepository meterRepository;

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator(meterRepository);
    }

    @Test
    void validateMeterId_whenMeterWithIdExist_ItsOK() {
        // given
        Long id = 1L;

        // when
        doNothing().when(mockValidator).validateMeterId(id);
        mockValidator.validateMeterId(id);

        // then
        verify(mockValidator).validateMeterId(id);
    }

    @Test
    void validateMeterId_whenMeterIdIsNull_thenThrowException() {
        assertThatThrownBy(() -> validator.validateMeterId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Meter id must not be null");
    }

    @Test
    void validateMeterId_whenMeterIdIsZero_thenThrowException() {
        assertThatThrownBy(() -> validator.validateMeterId(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Meter id must be greater than 0");
    }

    @Test
    void validateMeterId_whenMeterWithIdDoesNotExist_thenThrowException() {
        Long id = 5L;
        String message = String.format("Meter with id %d does not exist", id);
        assertThatThrownBy(() -> validator.validateMeterId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @Test
    void validateYear_whenYearIsCurrent_ItsOK() {
        // given
        Integer year = Year.now().getValue();

        // when
        doNothing().when(mockValidator).validateYear(year);
        mockValidator.validateYear(year);

        // then
        verify(mockValidator).validateYear(year);
    }

    @Test
    void validateYear_yearIsInThePast_ItsOK() {
        // given
        Integer year = 2020;

        // when
        doNothing().when(mockValidator).validateYear(year);
        mockValidator.validateYear(year);

        // then
        verify(mockValidator).validateYear(year);
    }

    @Test
    void validateYear_ifYearIsNull_thenThrowException() {
        assertThatThrownBy(() -> validator.validateYear(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Year must not be null");
    }

    @Test
    void validateYear_ifYearIsLessThan0_thenThrowException() {
        assertThatThrownBy(() -> validator.validateYear(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Year must be greater than or equal 0");
    }

    @Test
    void validateYear_ifYearIsInTheFuture_thenThrowException() {
        int yearInFuture = Year.now().getValue() + 1;
        assertThatThrownBy(() -> validator.validateYear(yearInFuture))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Year must not be in the future");
    }

    @Test
    void validateMonth_ifMonthIs7_ItsOK() {
        // given
        Integer month = 7;

        // when
        doNothing().when(mockValidator).validateMonth(month);
        mockValidator.validateMonth(month);

        // then
        verify(mockValidator).validateMonth(month);
    }

    @Test
    void validateMonth_ifMonthIsNull_thenThrowException() {
        assertThatThrownBy(() -> validator.validateMonth(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Month must not be null");
    }

    @Test
    void validateMonth_ifMonthIsLessThan1_thenThrowException() {
        assertThatThrownBy(() -> validator.validateMonth(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Month must be between 1 and 12");
    }

    @Test
    void validateMonth_ifMonthIsGreaterThan12_thenThrowException() {
        assertThatThrownBy(() -> validator.validateMonth(13))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Month must be between 1 and 12");
    }

    @Test
    void validateEnergyConsumed_ifEnergyConsumedIs20_ItsOK() {
        // given
        Integer energyConsumed = 20;

        // when
        doNothing().when(mockValidator).validateEnergyConsumed(energyConsumed);
        mockValidator.validateEnergyConsumed(energyConsumed);

        // then
        verify(mockValidator).validateEnergyConsumed(energyConsumed);
    }

    @Test
    void validateEnergyConsumed_ifEnergyConsumedIsNull_thenThrowException() {
        assertThatThrownBy(() -> validator.validateEnergyConsumed(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Energy consumed must not be null");
    }

    @Test
    void validateEnergyConsumed_ifEnergyConsumedIsLessThan0_thenThrowException() {
        assertThatThrownBy(() -> validator.validateEnergyConsumed(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Energy consumed must greater or equals 0");
    }

    @Test
    void validateMeterReadingDTO_whenMeterReadingDTOFieldsArePopulated_ItsOK() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(1L);
        meterReadingDTO.setYear(2020);
        meterReadingDTO.setMonth(4);
        meterReadingDTO.setEnergyConsumed(13);

        // when
        doNothing().when(mockValidator).validateMeterReadingDTO(meterReadingDTO);
        mockValidator.validateMeterReadingDTO(meterReadingDTO);

        // then
        verify(mockValidator).validateMeterReadingDTO(meterReadingDTO);
    }

    @Test
    void validateMeterReadingDTO_whenMeterReadingDTOIsNull_thenThrowException() {
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Meter reading must not be null");
    }

    @Test
    void validateMeterReadingDTO_whenMeterIdIsNull_thenThrowException() {
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(null);
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Meter id must not be null");
    }

    @Test
    void validateMeterReadingDTO_whenMeterIdIsZero_thenThrowException() {
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(0L);
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Meter id must be greater than 0");
    }

    @Test
    void validateMeterReadingDTO_whenMeterWithIdDoesNotExist_thenThrowException() {
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setMeterId(5L);
        String message = String.format("Meter with id %d does not exist", meterReadingDTO.getMeterId());
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @Test
    void validateMeterReadingDTO_ifYearIsNull_thenThrowException() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        Long id = 1L;
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(null);

        // when
        when(meterRepository.existsById(id)).thenReturn(true);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Year must not be null");

        verify(meterRepository).existsById(id);
    }

    @Test
    void validateMeterReadingDTO_ifYearIsLessThan0_thenThrowException() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        Long id = 1L;
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(-1);

        // when
        when(meterRepository.existsById(id)).thenReturn(true);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Year must be greater than or equal 0");

        verify(meterRepository).existsById(id);
    }

    @Test
    void validateMeterReadingDTO_ifYearIsInTheFuture_thenThrowException() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        Long id = 1L;
        meterReadingDTO.setMeterId(id);

        int yearInFuture = Year.now().getValue() + 1;
        meterReadingDTO.setYear(yearInFuture);

        // when
        when(meterRepository.existsById(id)).thenReturn(true);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Year must not be in the future");

        verify(meterRepository).existsById(id);
    }

    @Test
    void validateMeterReadingDTO_ifMonthIsNull_thenThrowException() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        Long id = 1L;
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(2020);
        meterReadingDTO.setMonth(null);

        // when
        when(meterRepository.existsById(id)).thenReturn(true);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Month must not be null");

        verify(meterRepository).existsById(id);
    }

    @Test
    void validateMeterReadingDTO_ifMonthIsLessThan1_thenThrowException() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        Long id = 1L;
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(2020);
        meterReadingDTO.setMonth(0);

        // when
        when(meterRepository.existsById(id)).thenReturn(true);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Month must be between 1 and 12");

        verify(meterRepository).existsById(id);
    }

    @Test
    void validateMeterReadingDTO_ifMonthIsGreaterThan12_thenThrowException() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        Long id = 1L;
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(2020);
        meterReadingDTO.setMonth(13);

        // when
        when(meterRepository.existsById(id)).thenReturn(true);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Month must be between 1 and 12");

        verify(meterRepository).existsById(id);
    }

    @Test
    void validateMeterReadingDTO_ifEnergyConsumedIsNull_thenThrowException() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        Long id = 1L;
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(2020);
        meterReadingDTO.setMonth(6);
        meterReadingDTO.setEnergyConsumed(null);

        // when
        when(meterRepository.existsById(id)).thenReturn(true);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Energy consumed must not be null");

        verify(meterRepository).existsById(id);
    }

    @Test
    void validateMeterReadingDTO_ifEnergyConsumedIsLessThan0_thenThrowException() {
        // given
        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        Long id = 1L;
        meterReadingDTO.setMeterId(id);
        meterReadingDTO.setYear(2020);
        meterReadingDTO.setMonth(6);
        meterReadingDTO.setEnergyConsumed(-1);

        // when
        when(meterRepository.existsById(id)).thenReturn(true);

        // then
        assertThatThrownBy(() -> validator.validateMeterReadingDTO(meterReadingDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Energy consumed must greater or equals 0");

        verify(meterRepository).existsById(id);
    }
}