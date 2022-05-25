package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.Address;

import java.util.List;

@Repository
@AllArgsConstructor
public class AddressRepositoryImpl {
    private final AddressJpaRepository addressJpaRepository;

    public List<Address> findByUserId(Long userId) {
        return addressJpaRepository.findByUserId(userId);
    }

    public Address findById(Long addressId) {
        return addressJpaRepository.findById(addressId).get();
    }

    public Address save(Address address) {
        return addressJpaRepository.save(address);
    }
}