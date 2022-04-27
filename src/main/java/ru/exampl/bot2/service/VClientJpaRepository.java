package ru.exampl.bot2.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.entity.DbEntityAction;

import java.util.List;
import java.util.UUID;


public interface VClientJpaRepository extends JpaRepository<DbEntityAction, UUID> {
    List<DbEntityAction> findByUserId(Integer userId);

}
