package dev.scibaric.meterreadings.repository;

import dev.scibaric.meterreadings.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
