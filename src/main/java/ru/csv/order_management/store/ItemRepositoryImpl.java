package ru.csv.order_management.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.DbEntityItems;

import java.util.List;


@Repository
public class ItemRepositoryImpl {
    @Autowired
    private ItemJpaRepository itemJpaRepository;

    public List<DbEntityItems> findAllItems() {
        return itemJpaRepository.findAll();
    }

    public List<DbEntityItems> findHeadGroup() {
        return itemJpaRepository.findByParentId(null);
    }

    public List<DbEntityItems> findChildGroup(Long parentId) {
        return itemJpaRepository.findByParentId(parentId);
    }

    public DbEntityItems findItem(Long id){
        return itemJpaRepository.findById(id).get();
    }
}
