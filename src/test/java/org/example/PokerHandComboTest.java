package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.ComboType.*;

class PokerHandComboTest {
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
    public void sortingHands() {
        var hands =Arrays.asList(
                new PokerHand(comboCalculator, "KS 2H 5C JD TD"), // high card
                new PokerHand(comboCalculator, "KS KH KC JD JC"), // full house
                new PokerHand(comboCalculator, "JS JC KS TD 6H"), // pair
                new PokerHand(comboCalculator, "TS TC TD TH KS"), // 4oak + king
                new PokerHand(comboCalculator, "TS TC TD TH 7S"), // 4oak + 7
                new PokerHand(comboCalculator, "4S 5S 6S 7S 8S"), // straight flush
                new PokerHand(comboCalculator, "TC JH QS KS AH"), // straight
                new PokerHand(comboCalculator, "3C AC KC 5C 8C"), // flush
                new PokerHand(comboCalculator, "4S 4C 4H TD 5S"), // 2 pairs
                new PokerHand(comboCalculator, "4S 4C TH TD 5S")  // 3oak
        );
        hands.sort(Comparator.naturalOrder());

        var combos = hands.stream()
                .map(h -> h.getCombo().comboType)
                .toList();
        var expected = Arrays.asList(
                HIGH_CARD,
                PAIR,
                TWO_PAIRS,
                THREE_OF_A_KIND,
                STRAIGHT,
                FLUSH,
                FULL_HOUSE,
                FOUR_OF_A_KIND,
                FOUR_OF_A_KIND,
                STRAIGHT_FLUSH
        );
        assertEquals(expected, combos);
    }

    @Test
    public void sortingWithKicker() {
        var king = new PokerHand(comboCalculator, "JS JC KS TD 6H"); // pair + king
        var ten = new PokerHand(comboCalculator, "JS JC 5S TD 6H"); // pair + ten

        assertEquals(1, king.compareTo(ten));
    }

    @Test
    public void sortingSameCombo() {
        var threeJack = new PokerHand(comboCalculator, "JS JC JD TD 6H");
        var threeKing = new PokerHand(comboCalculator, "KS KC KD TS 6C");

        assertEquals(0, threeJack.compareTo(threeKing));
    }

    @Test
    public void highCard() {
        PokerHand hand = new PokerHand(comboCalculator, "KS 2H 5C JD TD");

        assertEquals(ComboType.HIGH_CARD, hand.getCombo().comboType);
        assertEquals(Card.KING, hand.getCombo().kicker.get());
    }

    @Test
    public void pair() {
        PokerHand hand = new PokerHand(comboCalculator, "KS KH 5C JD TD");

        assertEquals(ComboType.PAIR, hand.getCombo().comboType);
        assertEquals(Card.JACK, hand.getCombo().kicker.get());
    }

    @Test
    public void twoPairs() {
        PokerHand hand = new PokerHand(comboCalculator, "KS KH 5C JD JC");

        assertEquals(ComboType.TWO_PAIRS, hand.getCombo().comboType);
        assertEquals(Card.FIVE, hand.getCombo().kicker.get());
    }

    @Test
    public void threeOfAKind() {
        PokerHand hand = new PokerHand(comboCalculator, "5S KD 5D JD 5C");

        assertEquals(ComboType.THREE_OF_A_KIND, hand.getCombo().comboType);
        assertEquals(Card.KING, hand.getCombo().kicker.get());
    }

    @Test
    public void straight() {
        PokerHand hand = new PokerHand(comboCalculator, "5S 6D 7D 8D 9D");

        assertEquals(ComboType.STRAIGHT, hand.getCombo().comboType);
        assertFalse(hand.getCombo().kicker.isPresent());
    }

    @Test
    public void flush() {
        PokerHand hand = new PokerHand(comboCalculator, "5D KD TD JD 9D");

        assertEquals(ComboType.FLUSH, hand.getCombo().comboType);
        assertFalse(hand.getCombo().kicker.isPresent());
    }

    @Test
    public void fullHouse() {
        PokerHand hand = new PokerHand(comboCalculator, "JD JC QD QS QC");

        assertEquals(ComboType.FULL_HOUSE, hand.getCombo().comboType);
        assertFalse(hand.getCombo().kicker.isPresent());
    }

    @Test
    public void fourOfAKind() {
        PokerHand hand = new PokerHand(comboCalculator, "KS KH 5C KC KD");

        assertEquals(ComboType.FOUR_OF_A_KIND, hand.getCombo().comboType);
        assertEquals(Card.FIVE, hand.getCombo().kicker.get());
    }

    @Test
    public void straightFlush() {
        PokerHand hand = new PokerHand(comboCalculator, "5D 6D 7D 8D 9D");

        assertEquals(ComboType.STRAIGHT_FLUSH, hand.getCombo().comboType);
        assertFalse(hand.getCombo().kicker.isPresent());
    }
}