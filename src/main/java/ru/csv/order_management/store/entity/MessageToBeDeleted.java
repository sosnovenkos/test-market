package ru.csv.order_management.store.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "to_be_deleted_message")
public class MessageToBeDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "message_id")
    private Integer messageId;
}
