package dev.scibaric.meterreadings.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.model.Address;
import dev.scibaric.meterreadings.model.Client;
import dev.scibaric.meterreadings.model.Meter;
import dev.scibaric.meterreadings.model.MeterReading;
import dev.scibaric.meterreadings.repository.AddressRepository;
import dev.scibaric.meterreadings.repository.ClientRepository;
import dev.scibaric.meterreadings.repository.MeterReadingRepository;
import dev.scibaric.meterreadings.repository.MeterRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MeterControllerIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void setUp() {
        clientRepository.saveAll(
                List.of(
                        new Client(1L, "Luka Modric"),
                        new Client(2L, "Mateo Kovacic"),
                        new Client(3L, "Josko Gvardiol")
                )
        );


        meterRepository.saveAll(
                List.of(
                        new Meter(1L, new Client(1L)),
                        new Meter(2L, new Client(2L)),
                        new Meter(3L, new Client(3L))
                )
        );

        addressRepository.saveAll(
                List.of(
                        new Address(1L, "Ilica", "10", "Zagreb", new Client(1L)),
                        new Address(2L, "Maksimirska", "8", "Zagreb", new Client(2L)),
                        new Address(3L, "Jurisiceva", "20", "Zagreb", new Client(3L))
                )
        );

        meterReadingRepository.saveAll(
                List.of(
                        new MeterReading(1L, new Meter(1L), 2020, 1, 11),
                        new MeterReading(2L, new Meter(1L), 2020, 2, 14),
                        new MeterReading(3L, new Meter(1L), 2020, 3, 9),
                        new MeterReading(4L, new Meter(1L), 2020, 4, 23),
                        new MeterReading(5L, new Meter(1L), 2020, 5, 16),
                        new MeterReading(6L, new Meter(1L), 2020, 6, 18),
                        new MeterReading(7L, new Meter(1L), 2020, 7, 12),
                        new MeterReading(8L, new Meter(1L), 2020, 8, 17),
                        new MeterReading(9L, new Meter(1L), 2020, 9, 8),
                        new MeterReading(10L, new Meter(1L), 2020, 10, 25),
                        new MeterReading(11L, new Meter(1L), 2020, 11, 20),
                        new MeterReading(12L, new Meter(1L), 2020, 12, 22),
                        new MeterReading(13L, new Meter(2L), 2020, 1, 7),
                        new MeterReading(14L, new Meter(2L), 2020, 2, 15),
                        new MeterReading(15L, new Meter(2L), 2020, 3, 12),
                        new MeterReading(16L, new Meter(2L), 2020, 4, 19),
                        new MeterReading(17L, new Meter(2L), 2020, 5, 13),
                        new MeterReading(18L, new Meter(2L), 2020, 6, 11),
                        new MeterReading(19L, new Meter(2L), 2020, 7, 16),
                        new MeterReading(20L, new Meter(2L), 2020, 8, 24),
                        new MeterReading(21L, new Meter(2L), 2020, 9, 29),
                        new MeterReading(22L, new Meter(2L), 2020, 10, 8),
                        new MeterReading(23L, new Meter(2L), 2020, 11, 14),
                        new MeterReading(24L, new Meter(2L), 2020, 12, 15)
                )
        );

    }

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
        int month = 12;
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
    @MethodSource("provideMeterReadingDTOsAndMessagesForSaving")
    void saveMeterReading_shouldReturnExceptionMessage(MeterReadingDTO meterReadingDTO, String message) throws Exception {
        mockMvc.perform(post("/api/meter/reading")
                        .content(asJsonString(meterReadingDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    void updateMeterReading_whenMeterReadingDTOIsCompleted_thenReturnResult() throws Exception {
        Long id = 1L;
        Integer year = 2020;
        Integer month = 1;
        Integer energyConsumed = 20;
        mockMvc.perform(put("/api/meter/reading")
                        .content(asJsonString(new MeterReadingDTO(year, month, energyConsumed, id)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.year").value(year))
                .andExpect(jsonPath("$.month").value(month))
                .andExpect(jsonPath("$.energyConsumed").value(energyConsumed))
                .andExpect(jsonPath("$.meterId").value(id));
    }

    @ParameterizedTest
    @MethodSource("provideMeterReadingDTOsAndMessagesForUpdating")
    void updateMeterReading_shouldReturnExceptionMessage(MeterReadingDTO meterReadingDTO, String message) throws Exception {
        mockMvc.perform(put("/api/meter/reading")
                        .content(asJsonString(meterReadingDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(message));
    }

    private static Stream<Arguments> provideMeterReadingDTOsAndMessagesForSaving() {
        Long meterId = 1L;
        Integer year = 2020;
        Integer month = 2;
        Integer energyConsumed = 15;
        String m = Month.of(2).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        return Stream.concat(provideMeterReadingDTOsAndMessages(),
                Stream.of(Arguments.of(new MeterReadingDTO(year, month, energyConsumed, meterId),
                String.format("Meter reading for meter id %d, year %d and month %s already exists", meterId, year, m))));
    }

    private static Stream<Arguments> provideMeterReadingDTOsAndMessagesForUpdating() {
        Long meterId = 1L;
        Integer year = 2021;
        int month = 2;
        Integer energyConsumed = 15;
        String m = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        return Stream.concat(provideMeterReadingDTOsAndMessages(),
                Stream.of(Arguments.of(new MeterReadingDTO(year, month, energyConsumed, meterId),
                        String.format("Meter reading for meter id %d, year %d and month %s does not exist", meterId, year, m))));
    }

    private static Stream<Arguments> provideMeterReadingDTOsAndMessages() {
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
                Arguments.of(new MeterReadingDTO(2021, 1, null, 1L), "Energy consumed must not be null"),
                Arguments.of(new MeterReadingDTO(2021, 1, -1, 1L), "Energy consumed must greater or equals 0")
        );
    }

    @Test
    void deleteMeterReadingById_deletedSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/meter/reading/1"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, Meter reading id must be greater than 0",
            "100, Meter reading with id 100 does not exist"
    }, nullValues = {"null"})
    void deleteMeterReadingById_shouldThrowException(Long meterReadingId, String message) throws Exception {
        mockMvc.perform(delete("/api/meter/reading/{id}", meterReadingId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(message));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}