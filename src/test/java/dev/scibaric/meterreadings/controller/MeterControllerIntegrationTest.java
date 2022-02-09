package dev.scibaric.meterreadings.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MeterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void aggregateConsumptionByMeterIdAndYear_whenReadingExists_thenReturnResult() throws Exception {
        mockMvc.perform(get("/api/meter/1/consumption/aggregation/year/2020"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(2020))
                .andExpect(jsonPath("$.total").value(195));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2021, /api/meter/{id}/consumption/aggregation/year/{year}",
            "1, 2021, /api/meter/{id}/year/{year}",
    })
    void aggregateConsumptionByMeterIdAndYear_whenReadingForYearDoesNotExist_thenReturnExceptionMessage(Long id, Integer year, String url) throws Exception {
        mockMvc.perform(get(url, id, year))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(String.format("Meter readings for meter id %d and year %d does not exist", id, year)));
    }

    @ParameterizedTest
    @CsvSource({
            "0, /api/meter/{id}/consumption/aggregation/year/2020",
            "0, /api/meter/{id}/year/2020",
            "0, /api/meter/{id}/year/2020/month/3"
    })
    void ifMeterIdLessThanOne_thenReturnExceptionMessage(Long id, String url) throws Exception {
        mockMvc.perform(get(url, id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Meter id must be greater than 0"));
    }

    @ParameterizedTest
    @CsvSource({
            "10, /api/meter/{id}/consumption/aggregation/year/2020",
            "11, /api/meter/{id}/year/2020",
            "12, /api/meter/{id}/year/2020/month/3"
    })
    void ifMeterIdDoesNotExist_thenReturnExceptionMessage(Long id, String url) throws Exception {
        mockMvc.perform(get(url, id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(String.format("Meter with id %d does not exist", id)));
    }

    @ParameterizedTest
    @CsvSource({
            "-1, /api/meter/1/consumption/aggregation/year/{year}",
            "-1, /api/meter/1/year/{year}",
            "-1, /api/meter/1/year/{year}/month/3"
    })
    void ifYearIsLessThan0_thenReturnExceptionMessage(Integer year, String url) throws Exception {
        mockMvc.perform(get(url, year))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Year must be greater than or equal 0"));
    }

    @ParameterizedTest
    @MethodSource("provideYearsInFutureAndURLs")
    void ifYearIsInFuture_thenReturnExceptionMessage(Integer year, String url) throws Exception {
        mockMvc.perform(get(url, year))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Year must not be in the future"));
    }

    private static Stream<Arguments> provideYearsInFutureAndURLs() {
        Integer yearInTheFuture = Year.now().getValue() + 1;
        return Stream.of(
                Arguments.of(yearInTheFuture, "/api/meter/1/consumption/aggregation/year/{year}"),
                Arguments.of(yearInTheFuture, "/api/meter/1/year/{year}"),
                Arguments.of(yearInTheFuture, "/api/meter/1/year/{year}/month/3")
        );
    }

    @Test
    void findByMeterIdAndYear_whenReadingExists_thenReturnResult() throws Exception {
        mockMvc.perform(get("/api/meter/1/year/2020"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(2020))
                .andExpect(jsonPath("$.monthlyEnergyConsumption.length()").value(12))
                .andExpect(jsonPath("$.monthlyEnergyConsumption.January").value(11));
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenReadingExists_thenReturnResult() throws Exception {
        mockMvc.perform(get("/api/meter/1/year/2020/month/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(2020))
                .andExpect(jsonPath("$.monthlyEnergyConsumption.length()").value(1))
                .andExpect(jsonPath("$.monthlyEnergyConsumption.January").value(11));
    }

    @Test
    void findByMeterIdAndYearAndMonth_whenReadingDoesNotExist_thenReturnExceptionMessage() throws Exception {
        Long id = 1L;
        Integer year = 2021;
        Integer month = 12;
        String m = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        mockMvc.perform(get("/api/meter/{id}/year/{year}/month/{month}", id, year, month))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(
                        String.format("Meter reading for meter id %d, year %d and month %s does not exist", id, year, m)));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 13})
    void ifMonthIsLessThan1OrGreaterThan12_thenReturnExceptionMessage(Integer month) throws Exception {
        mockMvc.perform(get("/api/meter/1/year/2020/month/{month}", month))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Month must be between 1 and 12"));
    }

    @Test
    void saveMeterReading_whenMeterReadingDTOIsCompleted_thenReturnResult() throws Exception {
        Long id = 1L;
        Integer year = 2021;
        Integer month = 1;
        Integer energyConsumed = 15;
        mockMvc.perform(post("/api/meter/reading")
                .content(asJsonString(new MeterReadingDTO(year, month, energyConsumed, id)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.year").value(year))
                .andExpect(jsonPath("$.month").value(month))
                .andExpect(jsonPath("$.energyConsumed").value(energyConsumed))
                .andExpect(jsonPath("$.meterId").value(id));
    }

    @ParameterizedTest
    @MethodSource("provideMeterReadingDTOsAndMessages")
    void saveMeterReading_shouldReturnExceptionMessage(MeterReadingDTO meterReadingDTO, String message) throws Exception {
        mockMvc.perform(post("/api/meter/reading")
                        .content(asJsonString(meterReadingDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(message));
    }

    private static Stream<Arguments> provideMeterReadingDTOsAndMessages() {
        String month = Month.of(2).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return Stream.of(
                Arguments.of(new MeterReadingDTO(2021, 1, 15, null), "Meter id must not be null"),
                Arguments.of(new MeterReadingDTO(2021, 1, 15, 0L), "Meter id must be greater than 0"),
                Arguments.of(new MeterReadingDTO(2021, 1, 15, 10L), String.format("Meter with id %d does not exist", 10L)),
                Arguments.of(new MeterReadingDTO(null, 1, 15, 1L), "Year must not be null"),
                Arguments.of(new MeterReadingDTO(-1, 1, 15, 1L), "Year must be greater than or equal 0"),
                Arguments.of(new MeterReadingDTO(Year.now().getValue() + 1, 1, 15, 1L), "Year must not be in the future"),
                Arguments.of(new MeterReadingDTO(2021, null, 15, 1L), "Month must not be null"),
                Arguments.of(new MeterReadingDTO(2021, 0, 15, 1L), "Month must be between 1 and 12"),
                Arguments.of(new MeterReadingDTO(2021, 13, 15, 1L), "Month must be between 1 and 12"),
                Arguments.of(new MeterReadingDTO(2020, 2, 15, 1L),
                        String.format("Meter reading for meter id %d, year %d and month %s already exists", 1L, 2020, month))
        );
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}