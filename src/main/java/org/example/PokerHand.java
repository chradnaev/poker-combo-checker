package org.example;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PokerHand implements Comparable<PokerHand> {
    public static final int HAND_CARD_COUNT = 5;
    private final List<Card> cards;
    private final Combo combo;

    public PokerHand(ComboCalculator comboCalculator, String raw) {
        String[] parts = raw.split(" ");
        if (parts.length != HAND_CARD_COUNT) {
            throw new IllegalArgumentException("Cards in hand should be five");
        }
        cards = new ArrayList<>(parts.length);
        for (String cardRaw : parts) {
            if (cardRaw.length() != 2) {
                throw new IllegalArgumentException("Invalid card format");
            }
            char value = cardRaw.charAt(0);
            char suite = cardRaw.charAt(1);
            cards.add(new Card(value, suite));
        }
        cards.sort(Comparator.naturalOrder());
        combo = comboCalculator.calculateCombo(this);
    }

    @Override
    public int compareTo(PokerHand o) {
        return combo.compareTo(o.combo);
    }

    public Card getAt(int index) {
        return cards.get(index);
    }

    public List<Card> getCards() {
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
