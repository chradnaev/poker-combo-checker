package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.ComboType.*;
import static org.example.CardValue.*;

class PokerHandComboTest {
    private final ComboCalculator comboCalculator = new ComboCalculator();
    private final Comparator<PokerHand> comparator = comboCalculator.getPokerHandComparator();

    @Test
    public void compareHighCards() {
        PokerHand hand1 = new PokerHand("KS 2H 5C JD TD");
        PokerHand hand2 = new PokerHand("2C 3C AC 4C 5C");
        Comparator<PokerHand> comparator = comboCalculator.getPokerHandComparator();

        assertEquals(-1, comparator.compare(hand1, hand2));
        assertEquals(1, comparator.compare(hand2, hand1));
        assertEquals(0, comparator.compare(hand1, hand1));
        assertEquals(0, comparator.compare(hand2, hand2));
    }

    @Test
    public void sortingHands() {
        var hands =Arrays.asList(
                new PokerHand("KS 2H 5C JD TD"), // high card
                new PokerHand("KS KH KC JD JC"), // full house
                new PokerHand("JS JC KS TD 6H"), // pair
                new PokerHand("TS TC TD TH KS"), // 4oak + king
                new PokerHand("TS TC TD TH 7S"), // 4oak + 7
                new PokerHand("4S 5S 6S 7S 8S"), // straight flush
                new PokerHand("TC JH QS KS AH"), // straight
                new PokerHand("3C AC KC 5C 8C"), // flush
                new PokerHand("4S 4C 4H TD 5S"), // 2 pairs
                new PokerHand("4S 4C TH TD 5S")  // 3oak
        );
        hands.sort(comboCalculator.getPokerHandComparator());

        var combos = hands.stream()
                .map(comboCalculator::calculateCombo)
                .map(Combo::getComboType)
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
        var king = new PokerHand("JS JC KS TD 6H"); // pair + king
        var ten = new PokerHand("JS JC 5S TD 6H"); // pair + ten

        assertEquals(1, comparator.compare(king,ten));
    }

    @Test
    public void sortingSameCombo() {
        var threeJack = new PokerHand("JS JC JD TD 6H");
        var threeKing = new PokerHand("KS KC KD TS 6C");

        assertEquals(0, comparator.compare(threeJack, threeKing));
    }

    @Test
    public void highCard() {
        PokerHand hand = new PokerHand("KS 2H 5C JD TD");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.HIGH_CARD, combo.getComboType());
        assertEquals(KING, combo.getKicker().get());
    }

    @Test
    public void pair() {
        PokerHand hand = new PokerHand("KS KH 5C JD TD");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.PAIR, combo.getComboType());
        assertEquals(JACK, combo.getKicker().get());
    }

    @Test
    public void twoPairs() {
        PokerHand hand = new PokerHand("KS KH 5C JD JC");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.TWO_PAIRS, combo.getComboType());
        assertEquals(FIVE, combo.getKicker().get());
    }

    @Test
    public void threeOfAKind() {
        PokerHand hand = new PokerHand("5S KD 5D JD 5C");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.THREE_OF_A_KIND, combo.getComboType());
        assertEquals(KING, combo.getKicker().get());
    }

    @Test
    public void straight() {
        PokerHand hand = new PokerHand("5S 6D 7D 8D 9D");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.STRAIGHT, combo.getComboType());
        assertFalse(combo.getKicker().isPresent());
    }

    @Test
    public void flush() {
        PokerHand hand = new PokerHand("5D KD TD JD 9D");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FLUSH, combo.getComboType());
        assertFalse(combo.getKicker().isPresent());
    }

    @Test
    public void fullHouse() {
        PokerHand hand = new PokerHand("JD JC QD QS QC");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FULL_HOUSE, combo.getComboType());
        assertFalse(combo.getKicker().isPresent());
    }

    @Test
    public void fourOfAKind() {
        PokerHand hand = new PokerHand("KS KH 5C KC KD");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.FOUR_OF_A_KIND, combo.getComboType());
        assertEquals(FIVE, combo.getKicker().get());
    }

    @Test
    public void straightFlush() {
        PokerHand hand = new PokerHand("5D 6D 7D 8D 9D");

        var combo = comboCalculator.calculateCombo(hand);

        assertEquals(ComboType.STRAIGHT_FLUSH, combo.getComboType());
        assertFalse(combo.getKicker().isPresent());
    }
}