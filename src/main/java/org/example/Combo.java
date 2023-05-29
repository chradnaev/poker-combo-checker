package org.example;

import java.util.Optional;

@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType" })
public class Combo implements Comparable<Combo> {
    public static final int MAX_VALUE = 20;
    private final ComboType comboType;
    private final Optional<CardValue> kicker;
    public final int score;

    public Combo(ComboType comboType, Optional<CardValue> kicker) {
        this.comboType = comboType;
        this.kicker = kicker;
        this.score = comboType.ordinal() * MAX_VALUE + kicker.map(CardValue::ordinal).orElse(0);
    }

    @Override
    public int compareTo(Combo o) {
        return Integer.compare(score, o.score);
    }

    public ComboType getComboType() {
        return comboType;
    }

    public Optional<CardValue> getKicker() {
        return kicker;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Combo{" +
                "comboType=" + comboType +
                ", kicker=" +kicker.map(String::valueOf).orElse("no") +
                ", score=" + score +
                '}';
    }
}
