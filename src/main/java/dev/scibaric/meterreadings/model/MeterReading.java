package dev.scibaric.meterreadings.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "METER_READING")
public class MeterReading {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "METER_ID", nullable = false)
    private Meter meter;

    @Column(name = "YEAR", nullable = false)
    private Integer year;

    @Column(name = "MONTH", nullable = false)
    private Integer month;

    @Column(name = "ENERGY_CONSUMED", nullable = false)
    private Integer energyConsumed;

    public MeterReading() {
    }

    public MeterReading(Meter meter, Integer year, Integer month, Integer energyConsumed) {
        this.meter = meter;
        this.year = year;
        this.month = month;
        this.energyConsumed = energyConsumed;
    }

    public MeterReading(Long id, Meter meter, Integer year, Integer month, Integer energyConsumed) {
        this.id = id;
        this.meter = meter;
        this.year = year;
        this.month = month;
        this.energyConsumed = energyConsumed;
    }
}
