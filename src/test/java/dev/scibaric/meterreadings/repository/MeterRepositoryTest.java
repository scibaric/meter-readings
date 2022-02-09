package dev.scibaric.meterreadings.repository;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MeterRepositoryTest {

    @Autowired
    private MeterRepository repository;

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "13, false"
    })
    void existsById_ifMeterReadingExistsById_theReturnTrue(Long meterId, Boolean exists) {
        assertThat(repository.existsById(meterId)).isEqualTo(exists);
    }

}