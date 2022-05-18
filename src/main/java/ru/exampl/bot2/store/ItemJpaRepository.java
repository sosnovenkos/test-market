package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.store.entity.DbEntityItems;

import java.util.List;


public interface ItemJpaRepository extends JpaRepository<DbEntityItems, Long> {
}
