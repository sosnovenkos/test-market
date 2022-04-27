package ru.exampl.bot2.domain;

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
