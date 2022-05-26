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
@Table(name = "user")
@TypeDefs({ @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "telegram_user_id")
    private Long userId;

    @Column(name = "addresses")
    @Type(type = "jsonb")
    private List<Long> addresses;

    @Column(name = "orders")
    @Type(type = "jsonb")
    private List<Long> orders;
}
