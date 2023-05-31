package org.example;

import com.google.common.collect.ComparisonChain;

import java.util.List;
import java.util.stream.IntStream;

public record Combo(ComboType comboType, CardValue comboHighCard, List<CardValue> kicker) implements Comparable<Combo> {

    @Override
    public int compareTo(Combo o) {
        int comp = ComparisonChain.start()
                .compare(comboType, o.comboType)
                .compare(comboHighCard, o.comboHighCard)
                .result();
        if (comp == 0) {
            comp = IntStream.range(0, Math.min(kicker.size(), o.kicker.size()))
                    .map(i -> kicker.get(i).compareTo(o.kicker.get(i)))
                    .filter(i -> i != 0)
                    .findFirst()
                    .orElse(0);
        }
        return -comp;
    }

}
