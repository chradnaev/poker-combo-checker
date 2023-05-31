package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.ComboType.*;
import static org.example.CardValue.*;

public class PokerHandComboTest {
    private final ComboCalculator comboCalculator = new ComboCalculator();

    @Test
    void compareHighCards() {
        var hand1 = new PokerHand("KS 2H 5C JD TD", comboCalculator);
        var hand2 = new PokerHand("2C 3C AC 4C 5C", comboCalculator);

        assertEquals(-1, hand1.compareTo(hand2));
        assertEquals(1, hand2.compareTo(hand1));
        assertEquals(0, hand1.compareTo(hand1));
        assertEquals(0, hand2.compareTo(hand2));
    }

    @Test
    void sortingHands() {
        var hands =Arrays.asList(
                new PokerHand("KS 2H 5C JD TD", comboCalculator), // high card
                new PokerHand("KS KH KC JD JC", comboCalculator), // full house
                new PokerHand("JS JC KS TD 6H", comboCalculator), // pair
                new PokerHand("TS TC TD TH KS", comboCalculator), // 4oak + king
                new PokerHand("TS TC TD TH 7S", comboCalculator), // 4oak + 7
                new PokerHand("4S 5S 6S 7S 8S", comboCalculator), // straight flush
                new PokerHand("TC JH QS KS AH", comboCalculator), // straight
                new PokerHand("3C AC KC 5C 8C", comboCalculator), // flush
                new PokerHand("4S 4C 4H TD 5S", comboCalculator), // 2 pairs
                new PokerHand("4S 4C TH TD 5S", comboCalculator)  // 3oak
        );

        Collections.sort(hands);

        var actual = hands.stream()
                .map(comboCalculator::calculateCombo)
                .map(Combo::comboType)
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
        assertEquals(expected, actual);
    }

    @Test
    void sortingWithKicker() {
        var king = new PokerHand("JS JC KS TD 6H", comboCalculator); // pair + king
        var ten = new PokerHand("JS JC 5S TD 6H", comboCalculator); // pair + ten

        var comparison = king.compareTo(ten);

        assertEquals(1, comparison);
    }

    @Test
    void sortingSameCombo() {
        var threeJack = new PokerHand("JS JC JD TD 6H", comboCalculator);
        var threeKing = new PokerHand("KS KC KD TS 6C", comboCalculator);

        var comparison = threeJack.compareTo(threeKing);

        assertEquals(0, comparison);
    }

    @Test
    void highCard() {
        var hand = new PokerHand("KS 2H 5C JD TD", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.HIGH_CARD, combo.comboType());
        assertEquals(KING, combo.kicker().get());
    }

    @Test
    void pair() {
        var hand = new PokerHand("KS KH 5C JD TD", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.PAIR, combo.comboType());
        assertEquals(JACK, combo.kicker().get());
    }

    @Test
    void twoPairs() {
        var hand = new PokerHand("KS KH 5C JD JC", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.TWO_PAIRS, combo.comboType());
        assertEquals(FIVE, combo.kicker().get());
    }

    @Test
    void threeOfAKind() {
        var hand = new PokerHand("5S KD 5D JD 5C", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.THREE_OF_A_KIND, combo.comboType());
        assertEquals(KING, combo.kicker().get());
    }

    @Test
    void straight() {
        var hand = new PokerHand("5S 6D 7D 8D 9D", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.STRAIGHT, combo.comboType());
        assertFalse(combo.kicker().isPresent());
    }

    @Test
    void flush() {
        var hand = new PokerHand("5D KD TD JD 9D", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FLUSH, combo.comboType());
        assertFalse(combo.kicker().isPresent());
    }

    @Test
    void fullHouse() {
        var hand = new PokerHand("JD JC QD QS QC", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FULL_HOUSE, combo.comboType());
        assertFalse(combo.kicker().isPresent());
    }

    @Test
    void fourOfAKind() {
        var hand = new PokerHand("KS KH 5C KC KD", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FOUR_OF_A_KIND, combo.comboType());
        assertEquals(FIVE, combo.kicker().get());
    }

    @Test
    void straightFlush() {
        var hand = new PokerHand("5D 6D 7D 8D 9D", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.STRAIGHT_FLUSH, combo.comboType());
        assertFalse(combo.kicker().isPresent());
    }
}