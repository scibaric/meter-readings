package dev.scibaric.meterreadings.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "ADDRESS", uniqueConstraints = {
        @UniqueConstraint(name = "ADRESS_UC",
            columnNames = {"STREET", "HOUSE_NO", "CITY"}
        )
})
public class Address {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STREET", nullable = false)
    private String street;

    @Column(name = "HOUSE_NO", nullable = false)
    private String houseNo;

    @Column(name = "CITY", nullable = false)
    private String city;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID", unique = true, nullable = false)
    private Client client;

    public Address() {
    }

    public Address(Long id, String street, String houseNo, String city) {
        this.id = id;
        this.street = street;
        this.houseNo = houseNo;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;

        return Objects.equals(street, address.street) && Objects.equals(houseNo, address.houseNo) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNo, city);
    }
}
