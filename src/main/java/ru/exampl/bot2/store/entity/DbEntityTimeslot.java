package ru.exampl.bot2.store.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "timeslot")
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class DbEntityTimeslot implements Serializable {
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

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Time time;
}
