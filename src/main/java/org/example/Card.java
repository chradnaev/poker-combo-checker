package org.example;

import java.util.Map;

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
    public static final Map<Character, Integer> SUITES = Map.ofEntries(
            Map.entry(SUITE_SPADES, 0),
            Map.entry(SUITE_HEARTS, 1),
            Map.entry(SUITE_DIAMONDS, 2),
            Map.entry(SUITE_CLUBS, 3)
    );
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
            Map.entry(ACE, 12)
    );

    public final char value;
    public final char suite;
    public final int id;

    public Card(char value, char suite) {
        if (!VALUES.containsKey(value)) {
            throw new IllegalArgumentException("Value not found: " + value);
        }
        if (!SUITES.containsKey(suite)) {
            throw new IllegalArgumentException("Suite not found: " + suite);
        }
        this.suite = suite;
        this.value = value;
        id = getIdFor(value, suite);
    }

    public static int getIdFor(char value, char suite) {
        return VALUES.get(value) * VALUES.size() + SUITES.get(suite);
    }

    public static int valueIndexFor(Character value) {
        return VALUES.get(value);
    }

    public int getId() {
        return id;
    }

    public int valueIndex() {
        return valueIndexFor(value);
    }

    public int getSuiteIndex() {
        return SUITES.get(suite);
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(id, o.id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suite=" + suite +
                '}';
    }
}
