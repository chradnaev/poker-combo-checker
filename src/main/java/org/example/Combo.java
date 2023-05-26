package org.example;

import com.google.common.collect.ComparisonChain;

import java.util.Optional;

public class Combo implements Comparable<Combo> {
    public static final int MAX_VALUE = 20;
    public final ComboType comboType;
    public final Optional<Card> kicker;
    public final int score;

    public Combo(ComboType comboType, Card kicker) {
        this.comboType = comboType;
        this.kicker = Optional.ofNullable(kicker);
        this.score = comboType.ordinal() * MAX_VALUE + Optional.ofNullable(kicker).map(Card::valueIndex).orElse(0);
    }


    @Override
    public int compareTo(Combo o) {
        return ComparisonChain.start()
                .compare(score, o.score)
                .result();
    }

    public ComboType getComboType() {
        return comboType;
    }

    public Optional<Card> getKicker() {
        return kicker;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Combo{" +
                "comboType=" + comboType +
                ", kicker=" + kicker.map(Card::toString).orElse("no") +
                ", score=" + score +
                '}';
    }
}
