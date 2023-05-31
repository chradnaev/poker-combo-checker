package org.example;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

public record Card(CardValue value, CardSuit suite) implements Comparable<Card> {

    public Card {
        Preconditions.checkNotNull(value);
        Preconditions.checkNotNull(suite);
    }

    @Override
    public int compareTo(Card o) {
        return -ComparisonChain.start()
                .compare(value, o.value)
                .compare(suite, o.suite)
                .result();
    }
}
