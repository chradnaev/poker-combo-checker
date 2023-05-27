package org.example;

import com.google.common.collect.ComparisonChain;

import java.util.Optional;

@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType" })
public class Combo implements Comparable<Combo> {
    public static final int MAX_VALUE = 20;
    public final ComboType comboType;
    public final Optional<Character> kicker;
    public final int score;

    public Combo(ComboType comboType, Optional<Character> kicker) {
        this.comboType = comboType;
        this.kicker = kicker;
        this.score = comboType.ordinal() * MAX_VALUE + kicker.map(Card::valueIndexFor).orElse(0);
    }


    @Override
    public int compareTo(Combo o) {
        return ComparisonChain.start()
                .compare(score, o.score)
                .result();
    }

    @Override
    public String toString() {
        return "Combo{" +
                "comboType=" + comboType +
                ", kicker=" +kicker.map(c -> c.toString()).orElse("no") +
                ", score=" + score +
                '}';
    }
}
