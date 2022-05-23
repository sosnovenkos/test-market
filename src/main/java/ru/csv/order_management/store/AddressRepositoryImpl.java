package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.DbEntityAddress;
import ru.csv.order_management.store.entity.DbEntityOrder;
import ru.csv.order_management.store.entity.DbEntityUser;

import java.util.List;

@Repository
@AllArgsConstructor
public class AddressRepositoryImpl {
        private final AddressJpaRepository addressJpaRepository;



    public List<DbEntityAddress> findByUserId(Long userid) {
        return addressJpaRepository.findByUserId(userid);
    }


    public DbEntityAddress save(DbEntityAddress address) {
            return addressJpaRepository.save(address);
    }
}