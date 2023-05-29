package org.example;

import java.util.Arrays;
import java.util.Optional;

public enum CardValue {
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    public final char shortName;

    CardValue(char shortName) {
        this.shortName = shortName;
    }

    public static Optional<CardValue> byShortName(char shortName) {
        return Arrays.stream(values())
                .filter(s -> s.shortName == shortName)
                .findFirst();
    }
}
