package ru.csv.order_management.store.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "timeslot")
public class DbEntityTimeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private long id;

    @Column (name = "timeslots")
    private String timeslot;

    @Column(name = "parent_id")
    private Long parentId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
