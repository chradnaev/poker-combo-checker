package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PokerHandValidationTest {

    @Test
    public void duplicateCard() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand("5D 5D 7S JD JS")
        );
    }

    @Test
    public void notFiveCards() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand("5D 6S JS 9C 7S KS")
        );
    }

    @Test
    public void invalidCardFormat() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand("5DD 6S JS 9C 7S")
        );
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand("5DD 6S JS 9C 7S")
        );
    }

    @Test
    public void unknownCardSuiteOrValue() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand("1D 6S JS 9C 7S")
        );
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand("5D 6S JS 9C 7Z")
        );
    }
}
