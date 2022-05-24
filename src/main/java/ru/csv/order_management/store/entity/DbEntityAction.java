package ru.csv.order_management.store.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;


@Data
@Entity
@Table(name = "action")
public class DbEntityAction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "data")
    private String data;

    @Column(name = "waiting_for_action")
    private boolean waitingForAction;
}
