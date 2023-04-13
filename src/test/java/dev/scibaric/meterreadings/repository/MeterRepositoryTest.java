package dev.scibaric.meterreadings.repository;

import dev.scibaric.meterreadings.model.Client;
import dev.scibaric.meterreadings.model.Meter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MeterRepositoryTest {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeAll
    public void setUp() {
        clientRepository.save(new Client(1L, "Luka Modrić"));
        meterRepository.save(new Meter(1L, new Client(1L)));
    }

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "13, false"
    })
    void existsById_ifMeterReadingExistsById_theReturnTrue(Long meterId, Boolean exists) {
        assertThat(meterRepository.existsById(meterId)).isEqualTo(exists);
    }

}