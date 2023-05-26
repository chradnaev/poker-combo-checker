package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {
    private final ComboCalculator comboCalculator = new ComboCalculator();

    @Test
    public void compareHighCards() {
        PokerHand hand1 = new PokerHand(comboCalculator, "KS 2H 5C JD TD");
        PokerHand hand2 = new PokerHand(comboCalculator,"2C 3C AC 4C 5C");

        assertEquals(-1, hand1.compareTo(hand2));
        assertEquals(1, hand2.compareTo(hand1));
        assertEquals(0, hand1.compareTo(hand1));
        assertEquals(0, hand2.compareTo(hand2));
    }

    @Test
    public void highCard() {
        PokerHand hand = new PokerHand(comboCalculator, "KS 2H 5C JD TD");
        assertEquals(ComboType.HIGH_CARD, hand.getCombo().comboType);
        assertEquals(Card.KING, hand.getCombo().kicker.get().value);
    }

    @Test
    public void twoPairs() {
        PokerHand hand = new PokerHand(comboCalculator, "KS KH 5C JD JD");
        assertEquals(ComboType.TWO_PAIRS, hand.getCombo().comboType);
        assertEquals(Card.FIVE, hand.getCombo().kicker.get().value);
    }
}