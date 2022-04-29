package ru.exampl.bot2.store.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders_items")
public class DbEntityOrdersItems {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "item_id ")
    private UUID itemId;

}
