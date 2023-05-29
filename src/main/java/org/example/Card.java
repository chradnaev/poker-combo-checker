package org.example;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

import java.util.Objects;

public record Card(CardValue value, CardSuit suite) implements Comparable<Card> {

    public Card {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(suite);
    }

    @Override
    public int compareTo(Card o) {
        return ComparisonChain.start()
                .compare(value, o.value)
                .compare(suite, o.suite)
                .result();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && suite == card.suite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suite);
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suite=" + suite +
                '}';
    }
}
