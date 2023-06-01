package org.example;

import java.util.Arrays;

public enum CardValue {
    ACE('A'),
    KING('K'),
    QUEEN('Q'),
    JACK('J'),
    TEN('T'),
    NINE('9'),
    EIGHT('8'),
    SEVEN('7'),
    SIX('6'),
    FIVE('5'),
    FOUR('4'),
    THREE('3'),
    TWO('2');

    public final char shortName;

    CardValue(char shortName) {
        this.shortName = shortName;
    }

    public static CardValue byShortName(char shortName) {
        return Arrays.stream(values())
                .filter(s -> s.shortName == shortName)
                .findFirst()
                .orElseThrow(() -> new CardValidationException("Unknown card value: "+shortName));
    }
}
