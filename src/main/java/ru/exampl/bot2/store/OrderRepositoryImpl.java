package ru.exampl.bot2.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.exampl.bot2.domain.Order;
import ru.exampl.bot2.store.entity.DbEntityItems;
import ru.exampl.bot2.store.entity.DbEntityOrder;

import java.util.*;

@Repository
public class OrderRepositoryImpl {
    @Autowired
    private OrderJpaRepository orderJpaRepository;
    public Order o1 = Order.builder().items(List.of()).date(new GregorianCalendar().getTime().toString()).sum("2569").build();
    public Order o2 = Order.builder().items(List.of()).date(new GregorianCalendar().getTime().toString()).sum("1236").build();
    public Order o3 = Order.builder().items(List.of()).date("22.08.2019").sum("1566").build();

    public List<Order> findByUserId(String userid) {
        return List.of(o1, o2, o3);
    }

//    public DbEntityOrder findOrderInCartStatus() {
////            return orderJpaRepository.findByStatus("CART");
//        }

    public DbEntityOrder findOrder(Long id) {
        return orderJpaRepository.findById(id).get();
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