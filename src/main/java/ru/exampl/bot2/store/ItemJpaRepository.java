package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.store.entity.DbEntityItems;

import java.util.List;
import java.util.UUID;

public interface ItemJpaRepository extends JpaRepository<DbEntityItems, UUID> {
}
