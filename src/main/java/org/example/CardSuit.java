package org.example;

import java.util.Arrays;

public enum CardSuit {
    SUITE_SPADES('S'),
    SUITE_HEARTS('H'),
    SUITE_DIAMONDS('D'),
    SUITE_CLUBS('C');

    public final char shortName;

    CardSuit(char shortName) {
        this.shortName = shortName;
    }

    public static CardSuit byShortName(char shortName) {
        return Arrays.stream(values())
                .filter(s -> s.shortName == shortName)
                .findFirst()
                .orElseThrow(() -> new CardValidationException("Unknown card suite: "+shortName));
    }
}
