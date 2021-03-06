package ru.csv.order_management.store.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "orders")
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "items")
    @Type(type = "jsonb")
    private List<Long> items;

    @Column(name = "status")
    private String status;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
