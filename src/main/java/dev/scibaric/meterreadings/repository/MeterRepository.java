package dev.scibaric.meterreadings.repository;

import dev.scibaric.meterreadings.model.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface repository designed to fetch data from METER table in database.
 */
@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {
}
