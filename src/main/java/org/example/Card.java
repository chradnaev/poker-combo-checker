package org.example;

import com.google.common.collect.ComparisonChain;

import java.util.Map;
import java.util.Set;

public class Card implements Comparable<Card> {
    public static final char SUITE_SPADES = 'S';
    public static final char SUITE_HEARTS = 'H';
    public static final char SUITE_DIAMONDS = 'D';
    public static final char SUITE_CLUBS = 'C';
    public static final char TWO = '2';
    public static final char THREE = '3';
    public static final char FOUR = '4';
    public static final char FIVE = '5';
    public static final char SIX = '6';
    public static final char SEVEN = '7';
    public static final char EIGHT = '8';
    public static final char NINE = '9';
    public static final char TEN = 'T';
    public static final char JACK = 'J';
    public static final char QUEEN = 'Q';
    public static final char KING = 'K';
    public static final char ACE = 'A';
    public static final Set<Character> SUITES = Set.of(SUITE_SPADES, SUITE_HEARTS, SUITE_DIAMONDS, SUITE_CLUBS);
    public static final Map<Character, Integer> VALUES = Map.ofEntries(
            Map.entry(TWO, 0),
            Map.entry(THREE, 1),
            Map.entry(FOUR, 2),
            Map.entry(FIVE, 3),
            Map.entry(SIX, 4),
            Map.entry(SEVEN, 5),
            Map.entry(EIGHT, 6),
            Map.entry(NINE, 7),
            Map.entry(TEN, 8),
            Map.entry(JACK, 9),
            Map.entry(QUEEN, 10),
            Map.entry(KING, 11),
            Map.entry(ACE, 12));

    public final char value;
    public final char suite;

    public Card(char value, char suite) {
        if (!VALUES.containsKey(value)) {
            throw new IllegalArgumentException("Value not found: " + value);
        }
        if (!SUITES.contains(suite)) {
            throw new IllegalArgumentException("Suite not found: " + suite);
        }
        this.suite = suite;
        this.value = value;
    }

    public int valueIndex() {
        return VALUES.get(value);
    }

    @Override
    public int compareTo(Card o) {
        return ComparisonChain.start()
                .compare(valueIndex(), o.valueIndex())
                .compare(suite, o.suite)
                .result();
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suite=" + suite +
                '}';
    }
}
