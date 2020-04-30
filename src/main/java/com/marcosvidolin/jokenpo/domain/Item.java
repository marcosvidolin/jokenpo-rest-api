package com.marcosvidolin.jokenpo.domain;

import com.marcosvidolin.jokenpo.domain.exception.BusinessException;

import java.util.Arrays;

public enum Item {

    SPOCK("Spock"),
    SCISSORS("Scissors"),
    PAPER("Paper"),
    ROCK("Rock"),
    LIZARD("Lizard");

    private final String name;

    Item(String name) {
        this.name = name;
    }

    public static Item getItemByNameIgnoreCase(String name) throws BusinessException {
        Item item = Arrays.stream(Item.values())
                .filter(e -> e.name().equalsIgnoreCase(name)).findAny()
                .orElse(null);

        if (item == null) {
            throw new BusinessException("Invalid item. Try one of these: " + Item.values());
        }
        return item;
    }
}
