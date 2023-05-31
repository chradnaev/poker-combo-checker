package org.example;


import java.util.*;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand> {
    public static final int HAND_CARD_COUNT = 5;
    private final SortedSet<Card> cards;
    private final Combo combo;

    public PokerHand(String raw, ComboCalculator calculator) {
        var parts = raw.split(" ");
        if (parts.length != HAND_CARD_COUNT) {
            throw new CardValidationException("Cards in hand should be five: " + raw);
        }
        cards = Arrays.stream(parts)
                .map(this::parseCard)
                .collect(Collectors.toCollection(TreeSet::new));
        if (cards.size() != HAND_CARD_COUNT) {
            throw new CardValidationException("Duplicate card with same value and suite: " + raw);
        }
        this.combo = calculator.calculateCombo(this);
    }

    private Card parseCard(String cardRaw) {
        if (cardRaw.length() != 2) {
            throw new CardValidationException("Invalid card format: " + cardRaw);
        }
        CardValue value = CardValue.byShortName(cardRaw.charAt(0));
        CardSuit suite = CardSuit.byShortName(cardRaw.charAt(1));
        return new Card(value, suite);
    }

    public Combo getCombo() {
        return combo;
    }

    public Set<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "PokerHand{" +
                "cards=" + cards +
                '}';
    }

    @Override
    public int compareTo(PokerHand o) {
        return combo.compareTo(o.combo);
    }
}
