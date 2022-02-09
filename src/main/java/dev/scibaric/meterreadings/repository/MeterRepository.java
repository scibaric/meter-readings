package dev.scibaric.meterreadings.repository;

import dev.scibaric.meterreadings.model.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface repository designed to fetch data from METER table in database.
 */
public interface MeterRepository extends JpaRepository<Meter, Long> {
}
