package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.Address;

import java.util.List;


public interface AddressJpaRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}
