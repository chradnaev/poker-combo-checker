package org.example;

import java.util.List;
import java.util.stream.IntStream;

public record Combo(ComboType comboType, List<CardValue> comboCards, List<CardValue> kickers) implements Comparable<Combo> {

    @Override
    public int compareTo(Combo o) {
        int comp = comboType.compareTo(o.comboType);
        if (comp == 0) {
            comp = compareCards(comboCards, o.comboCards);
        }
        if (comp == 0) {
            comp = compareCards(kickers, o.kickers);
        }
        return comp;
    }

    private int compareCards(List<CardValue> cardsLeft, List<CardValue> cardsRight) {
        return IntStream.range(0, Math.min(cardsLeft.size(), cardsRight.size()))
                .map(i -> cardsLeft.get(i).compareTo(cardsRight.get(i)))
                .filter(i -> i != 0)
                .findFirst()
                .orElse(0);
    }
}
