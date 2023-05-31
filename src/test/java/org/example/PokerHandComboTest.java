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

        assertTrue(hand1.compareTo(hand2) > 0);
        assertTrue(hand2.compareTo(hand1) < 0);
        assertEquals(0, hand1.compareTo(hand1));
        assertEquals(0, hand2.compareTo(hand2));
    }

    @Test
    void sortingHands() {
        var hands = Arrays.asList(
                new PokerHand("KS 2H 5C JD TD", comboCalculator), // high card
                new PokerHand("KS KH KC JD JC", comboCalculator), // full house
                new PokerHand("JS JC KS TD 6H", comboCalculator), // pair
                new PokerHand("TS TC TD TH KS", comboCalculator), // 4oak + king
                new PokerHand("TS TC TD TH 7S", comboCalculator), // 4oak + 7
                new PokerHand("4S 5S 6S 7S 8S", comboCalculator), // straight flush
                new PokerHand("TC JH QS KS AH", comboCalculator), // straight
                new PokerHand("3C AC KC 5C 8C", comboCalculator), // flush
                new PokerHand("4S 4C 4H TD 5S", comboCalculator), // 3oak
                new PokerHand("4S 4C TH TD 5S", comboCalculator)  // 2 pairs
        );

        Collections.sort(hands);

        var actual = hands.stream()
                .map(PokerHand::getCombo)
                .map(Combo::comboType)
                .toList();
        var expected = Arrays.asList(
                STRAIGHT_FLUSH,
                FOUR_OF_A_KIND,
                FOUR_OF_A_KIND,
                FULL_HOUSE,
                FLUSH,
                STRAIGHT,
                THREE_OF_A_KIND,
                TWO_PAIRS,
                PAIR,
                HIGH_CARD
        );
        assertEquals(expected, actual);
    }

    @Test
    void sortingWithKicker() {
        var six = new PokerHand("JS JC KS TD 6H", comboCalculator); // pair + king + ten + 6
        var three = new PokerHand("JS JC KD TD 3H", comboCalculator); // pair + king + ten + 3

        var comparison = three.compareTo(six);

        assertTrue(comparison > 0);
    }

    @Test
    void sortingSameComboWithDifferentCards() {
        var threeJack = new PokerHand("JS JC JD QD 6H", comboCalculator); // three of Jack + Queen
        var threeKing = new PokerHand("KS KC KD TS 6C", comboCalculator); // three of King + Ten

        var comparison = threeJack.compareTo(threeKing);

        assertTrue(comparison > 0);
    }

    @Test
    void highCard() {
        var hand = new PokerHand("KS 2H 5C JD TD", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.HIGH_CARD, combo.comboType());
        assertEquals(Arrays.asList(KING, JACK, TEN, FIVE, TWO), combo.kicker());
    }

    @Test
    void pair() {
        var hand = new PokerHand("KS KH 5C JD TD", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.PAIR, combo.comboType());
        assertEquals(Arrays.asList(JACK, TEN, FIVE), combo.kicker());
    }

    @Test
    void twoPairs() {
        var hand = new PokerHand("KS KH 5C JD JC", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.TWO_PAIRS, combo.comboType());
        assertEquals(Arrays.asList(FIVE), combo.kicker());
    }

    @Test
    void threeOfAKind() {
        var hand = new PokerHand("5S KD 5D JD 5C", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.THREE_OF_A_KIND, combo.comboType());
        assertEquals(Arrays.asList(KING, JACK), combo.kicker());
    }

    @Test
    void straight() {
        var hand = new PokerHand("5S 6D 7D 8D 9D", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.STRAIGHT, combo.comboType());
        assertTrue(combo.kicker().isEmpty());
    }

    @Test
    void flush() {
        var hand = new PokerHand("5D KD TD JD 9D", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FLUSH, combo.comboType());
        assertTrue(combo.kicker().isEmpty());
    }

    @Test
    void fullHouse() {
        var hand = new PokerHand("JD JC QD QS QC", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FULL_HOUSE, combo.comboType());
        assertTrue(combo.kicker().isEmpty());
    }

    @Test
    void fourOfAKind() {
        var hand = new PokerHand("KS KH 5C KC KD", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FOUR_OF_A_KIND, combo.comboType());
        assertEquals(Arrays.asList(FIVE), combo.kicker());
    }

    @Test
    void straightFlush() {
        var hand = new PokerHand("5D 6D 7D 8D 9D", comboCalculator);

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.STRAIGHT_FLUSH, combo.comboType());
        assertTrue(combo.kicker().isEmpty());
    }
}