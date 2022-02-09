package dev.scibaric.meterreadings.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "METER")
public class Meter {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID", unique = true, nullable = false)
    private Client client;

    @OneToMany(mappedBy = "meter", fetch = FetchType.LAZY)
    private Set<MeterReading> meterReading;

    public Meter() {
    }

    public Meter(Long id) {
        this.id = id;
    }

    public Meter(Long id, Client client) {
        this.id = id;
        this.client = client;
    }
}
