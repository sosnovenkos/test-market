package ru.exampl.bot2.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.exampl.bot2.store.entity.DbEntityItems;

import java.util.List;


@Repository
public class ItemRepositoryImpl {
    @Autowired
    private ItemJpaRepository itemJpaRepository;

    public List<DbEntityItems> findAllItems() {
        return itemJpaRepository.findAll();
    }

    public DbEntityItems findItem(Long id){
        return itemJpaRepository.findById(id).get();
    }
}
