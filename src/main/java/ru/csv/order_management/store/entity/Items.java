package ru.csv.order_management.store.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "count")
    private Integer count;

    @Column(name = "weight")
    private Long weight;
}
