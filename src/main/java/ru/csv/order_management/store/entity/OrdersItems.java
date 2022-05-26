package ru.csv.order_management.store.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "orders_items")
public class OrdersItems {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "item_id ")
    private Long itemId;

}
