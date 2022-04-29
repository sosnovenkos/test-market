package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.store.entity.DbEntityAction;

import java.util.List;
import java.util.UUID;

public interface ActionJpaRepository extends JpaRepository<DbEntityAction, UUID> {
    List<DbEntityAction> findByUserId(Integer userId);

    DbEntityAction findByUserName(String s);

}
