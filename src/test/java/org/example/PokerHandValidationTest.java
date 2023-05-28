package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PokerHandValidationTest {
    private final ComboCalculator comboCalculator = new ComboCalculator();

    @Test
    public void duplicateCard() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand(comboCalculator, "5D 5D 7S JD JS")
        );
    }

    @Test
    public void notFiveCards() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand(comboCalculator, "5D 6S JS 9C 7S KS")
        );
    }

    @Test
    public void invalidCardFormat() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand(comboCalculator, "5DD 6S JS 9C 7S")
        );
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PokerHand(comboCalculator, "5DD 6S JS 9C 7S")
        );
    }

    @Test
    public void unknownCardSuiteOrValue() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Card('R', 'S')
        );
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Card('7', '2')
        );
    }
}
