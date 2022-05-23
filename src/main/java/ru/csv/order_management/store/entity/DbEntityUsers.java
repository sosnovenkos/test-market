package ru.csv.order_management.store.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "users")
public class DbEntityUsers {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_status")
    private String userStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
}
