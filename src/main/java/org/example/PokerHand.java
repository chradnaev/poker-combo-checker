package org.example;


import java.util.*;

public class PokerHand implements Comparable<PokerHand> {
    public static final int HAND_CARD_COUNT = 5;
    private final SortedSet<Card> cards;
    private final Combo combo;

    public PokerHand(ComboCalculator comboCalculator, String raw) {
        String[] parts = raw.split(" ");
        if (parts.length != HAND_CARD_COUNT) {
            throw new IllegalArgumentException("Cards in hand should be five");
        }
        cards = new TreeSet<>();
        for (String cardRaw : parts) {
            if (cardRaw.length() != 2) {
                throw new IllegalArgumentException("Invalid card format");
            }
            char value = cardRaw.charAt(0);
            char suite = cardRaw.charAt(1);
            if (!cards.add(new Card(value, suite))) {
                throw new IllegalArgumentException("Duplicate card in with same ID");
            }
        }
        combo = comboCalculator.calculateCombo(this);
    }

    @Override
    public int compareTo(PokerHand o) {
        return combo.compareTo(o.combo);
    }

    public Set<Card> getCards() {
        return cards;
    }

    public Combo getCombo() {
        return combo;
    }

    @Override
    public String toString() {
        return "PokerHand{" +
                "cards=" + cards +
                ", combo=" + combo +
                '}';
    }
}
