package org.example;

import java.util.Map;

public class Card implements Comparable<Card> {

    public final CardValue value;
    public final CardSuit suite;
    public final int id;

    public Card(CardValue value, CardSuit suite) {
        this.suite = suite;
        this.value = value;
        id = getIdFor(value, suite);
    }

    public static int getIdFor(CardValue value, CardSuit suit) {
        return value.ordinal() * CardValue.values().length + suit.ordinal();
    }

    public int getId() {
        return id;
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
