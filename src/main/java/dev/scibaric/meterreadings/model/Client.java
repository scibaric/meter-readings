package dev.scibaric.meterreadings.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private Address address;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY)
    private Meter meter;

    public Client() {
    }

    public Client(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;

        return Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
