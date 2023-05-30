package org.example;


import java.util.*;

public class PokerHand {
    public static final int HAND_CARD_COUNT = 5;
    private final SortedSet<Card> cards;

    public PokerHand(String raw) {
        var parts = raw.split(" ");
        if (parts.length != HAND_CARD_COUNT) {
            throw new IllegalArgumentException("Cards in hand should be five");
        }
        cards = Arrays.stream(parts)
                .map(this::parseCard)
                .collect(TreeSet::new,
                        (cards1, card) -> {
                            if (!cards1.add(card)) {
                                throw new IllegalArgumentException("Duplicate card with same suite and value");
                            }
                        },
                        (cards1, cards2) -> {
                        });
    }

    private Card parseCard(String cardRaw) {
        if (cardRaw.length() != 2) {
            throw new IllegalArgumentException("Invalid card format");
        }
        CardValue value = CardValue.byShortName(cardRaw.charAt(0))
                .orElseThrow(() -> new IllegalArgumentException("Unknown card value"));
        CardSuit suite = CardSuit.byShortName(cardRaw.charAt(1))
                .orElseThrow(() -> new IllegalArgumentException("Unknown card suit"));
        return new Card(value, suite);
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
}
