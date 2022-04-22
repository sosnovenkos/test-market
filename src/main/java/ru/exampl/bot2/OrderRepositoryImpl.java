package ru.exampl.bot2;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.exampl.bot2.Order;
import ru.exampl.bot2.OrderRepository;
import ru.exampl.bot2.command.Item;

import java.util.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    public Order o1 = Order.builder().items(List.of()).date(new GregorianCalendar().getTime().toString()).sum("2569").build();
    public Order o2 = Order.builder().items(List.of()).date(new GregorianCalendar().getTime().toString()).sum("1236").build();
    public Order o3 = Order.builder().items(List.of()).date("22.08.2019").sum("1566").build();

    @Override
    public List<Order> findByUserId(String userid) {
//        var tvSony = Item.builder().name("TV Sony").price(120).build();
//        var pc = Item.builder().name("PC").price(120).build();
//        var laptopLenovo = Item.builder().name("Laptop Lenovo").price(120).build();
//        var laptopHp = Item.builder().name("Laptop HP").price(120).build();
//        var tabletHuawei = Item.builder().name("Tablet Huawei").price(120).build();
//        var tvPanasonic = Item.builder().name("TV Panasonic").price(120).build();
//        var smartWatch = Item.builder().name("Smart watch").price(120).build();
//        var phoneApple = Item.builder().name("Phone Apple").price(120).build();
//        var o1 = Order.builder().items(List.of(tvSony, pc, smartWatch)).date(new GregorianCalendar().getTime().toString()).sum("2569").build();
//        var o2 = Order.builder().items(List.of(laptopLenovo, tabletHuawei, phoneApple)).date(new GregorianCalendar().getTime().toString()).sum("1236").build();
//        var o3 = Order.builder().items(List.of(laptopHp, tvPanasonic)).date("22.08.2019").sum("1566").build();
        return List.of(o1, o2, o3);
    }


}
