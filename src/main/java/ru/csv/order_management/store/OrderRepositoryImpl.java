package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.domain.Order;
import ru.csv.order_management.store.entity.DbEntityOrder;

import java.util.*;

@Repository
@AllArgsConstructor
public class OrderRepositoryImpl {
        private final OrderJpaRepository orderJpaRepository;



    public List<DbEntityOrder> findByUserId(Long userid) {
        return orderJpaRepository.findByUserId(userid);
    }


    public DbEntityOrder saveOrder(DbEntityOrder order) {
        try {
            return orderJpaRepository.save(order);
        } catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }

    public DbEntityOrder findOrderInCartStatus(Long userId) {
        return orderJpaRepository.findByStatusAndUserId("cart", userId);
    }
}