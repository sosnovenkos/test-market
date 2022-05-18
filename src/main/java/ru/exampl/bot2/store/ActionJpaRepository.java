package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.store.entity.DbEntityAction;

import java.util.List;

public interface ActionJpaRepository extends JpaRepository<DbEntityAction, Long> {
    List<DbEntityAction> findByUserId(Integer userId);

    DbEntityAction findByUserName(String s);

}
