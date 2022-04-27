package ru.exampl.bot2.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {
    public String date;
    public String sum;
    public List<Item> items;



}
