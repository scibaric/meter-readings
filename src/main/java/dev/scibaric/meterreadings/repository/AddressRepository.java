package dev.scibaric.meterreadings.repository;

import dev.scibaric.meterreadings.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
