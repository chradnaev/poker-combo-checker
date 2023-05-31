package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PokerHandValidationTest {
    private final ComboCalculator calculator = new ComboCalculator();

    @Test
    void duplicateCard() {
        Assertions.assertThrows(CardValidationException.class,
                () -> new PokerHand("5D 5D 7S JD JS", calculator)
        );
    }

    @Test
    void notFiveCards() {
        Assertions.assertThrows(CardValidationException.class,
                () -> new PokerHand("5D 6S JS 9C 7S KS", calculator)
        );
    }

    @Test
    void invalidCardFormat() {
        Assertions.assertThrows(CardValidationException.class,
                () -> new PokerHand("5DD 6S JS 9C 7S", calculator)
        );
        Assertions.assertThrows(CardValidationException.class,
                () -> new PokerHand("5DD 6S JS 9C 7S", calculator)
        );
    }

    @Test
    void unknownCardSuiteOrValue() {
        Assertions.assertThrows(CardValidationException.class,
                () -> new PokerHand("1D 6S JS 9C 7S", calculator)
        );
        Assertions.assertThrows(CardValidationException.class,
                () -> new PokerHand("5D 6S JS 9C 7Z", calculator)
        );
    }
}
