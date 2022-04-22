package ru.exampl.bot2;

import org.springframework.stereotype.Repository;
import ru.exampl.bot2.command.Item;

import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository{
    public Item tvSony = Item.builder().name("TV Sony").price(120).build();
    public Item pc = Item.builder().name("PC").price(120).build();
    public Item laptopLenovo = Item.builder().name("Laptop Lenovo").price(120).build();
    public Item laptopHp = Item.builder().name("Laptop HP").price(120).build();
    public Item tabletHuawei = Item.builder().name("Tablet Huawei").price(120).build();
    public Item tvPanasonic = Item.builder().name("TV Panasonic").price(120).build();
    public Item smartWatch = Item.builder().name("Smart watch").price(120).build();
    public Item phoneApple = Item.builder().name("Phone Apple").price(120).build();
    public OrderRepositoryImpl orderRepository;

    @Override
    public List<Item> findByOrderNumber(String userId, int orderNumber) {

        return List.of(phoneApple, smartWatch);
    }
}
