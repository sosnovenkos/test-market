package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.DbEntityAddress;

import java.util.List;


public interface AddressJpaRepository extends JpaRepository<DbEntityAddress, Long> {
    List<DbEntityAddress> findByUserId(Long userId);
}
