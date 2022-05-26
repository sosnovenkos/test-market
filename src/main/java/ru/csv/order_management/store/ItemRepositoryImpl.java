package ru.csv.order_management.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.Items;

import java.util.List;


@Repository
public class ItemRepositoryImpl {
    @Autowired
    private ItemJpaRepository itemJpaRepository;

    public List<Items> findAllItems() {
        return itemJpaRepository.findAll();
    }

    public List<Items> findHeadGroup() {
        return itemJpaRepository.findByParentId(null);
    }

    public List<Items> findChildGroup(Long parentId) {
        return itemJpaRepository.findByParentId(parentId);
    }

    public Items findItem(Long id){
        return itemJpaRepository.findById(id).get();
    }

    public List<Items> findItems(List<Long> ids) {
        return itemJpaRepository.findAllById(ids);
    }
}
