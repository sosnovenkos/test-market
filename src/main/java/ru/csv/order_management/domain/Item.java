package ru.csv.order_management.domain;

import lombok.Builder;

@Builder
public class Item {
    public String name;
    public int price;

    @Override
    public String toString() {
        return super.toString();
    }
}
