package dev.scibaric.meterreadings.repository;

import dev.scibaric.meterreadings.model.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface repository designed to fetch and store data to METER_READING table in database.
 */
@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    /**
     * Performing select query with parameters <b>id</b> and <b>year</b> on METER_READING table and returns SUM of
     * ENERGY_CONSUMED column.
     * @param id Meter id
     * @param year Year
     * @return Integer
     */
    @Query("select sum(mr.energyConsumed) from MeterReading mr " +
            "join mr.meter m where m.id = :id and mr.year = :year")
    Integer aggregateConsumptionByMeterIdAndYear(@Param("id") Long id, @Param("year") Integer year);

    /**
     * Performing select query with parameters <b>id</b> and <b>year</b> on METER_READING table and returning fetched
     * rows by meter id and year. Rows are stored in {@link List}.
     * @param id Meter id
     * @param year Year
     * @return {@link List}
     */
    List<MeterReading> findMeterReadingsByMeterIdAndYear(Long id, Integer year);

    /**
     * Performing select query with parameters <b>id</b>, <b>year</b> and <b>month</b> on METER_READING table and
     * returning fetched row by meter id, year and month. One row represents one object of {@link MeterReading}.
     * @param id Meter id
     * @param year Year
     * @param month Month
     * @return {@link MeterReading}
     */
    MeterReading findMeterReadingByMeterIdAndYearAndMonth(Long id, Integer year, Integer month);
}
