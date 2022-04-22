package ru.exampl.bot2;

import lombok.Builder;
import lombok.Data;
import ru.exampl.bot2.command.Item;

import java.util.List;

@Data
@Builder
public class Order {
    public String date;
    public String sum;
    public List<Item> items;



}
