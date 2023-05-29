package org.example;

import com.google.common.collect.ComparisonChain;

import java.util.Optional;

public record Combo(ComboType comboType, Optional<CardValue> kicker) implements Comparable<Combo> {

    @Override
    public int compareTo(Combo o) {
        return ComparisonChain.start()
                .compare(comboType.ordinal(), o.comboType.ordinal())
                .compare(kicker.map(CardValue::ordinal).orElse(0), o.kicker.map(CardValue::ordinal).orElse(0))
                .result();
    }

    @Override
    public String toString() {
        return "Combo{" +
                "comboType=" + comboType +
                ", kicker=" +kicker.map(String::valueOf).orElse("no") +
                '}';
    }
}
