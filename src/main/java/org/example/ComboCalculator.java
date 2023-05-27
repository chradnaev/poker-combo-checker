package org.example;

import java.util.*;

public class ComboCalculator {

    public Combo calculateCombo(PokerHand hand) {
        boolean sameSuit = isSameSuit(hand);
        if (isStraight(hand)) {
            return sameSuit ? new Combo(ComboType.STRAIGHT_FLUSH, Optional.empty())
                    : new Combo(ComboType.STRAIGHT, Optional.empty());
        }
        if (sameSuit) {
            return new Combo(ComboType.FLUSH, Optional.empty());
        }
        Map<Character, Integer> group = groupByValue(hand);
        if (group.containsValue(4)) {
            return new Combo(ComboType.FOUR_OF_A_KIND, getKicker(group));
        }
        if (group.containsValue(2)) {
            if (group.containsValue(3)) {
                return new Combo(ComboType.FULL_HOUSE, Optional.empty());
            }
            var countPairs = group.values().stream()
                    .filter(n -> n == 2)
                    .count();
            if (countPairs == 2) {
                return new Combo(ComboType.TWO_PAIRS, getKicker(group));
            }
            return new Combo(ComboType.PAIR, getKicker(group));
        }
        if (group.containsValue(3)) {
            return new Combo(ComboType.THREE_OF_A_KIND, getKicker(group));
        }
        return new Combo(ComboType.HIGH_CARD, getKicker(group));
    }

    private boolean isSameSuit(PokerHand hand) {
        Card first = hand.getCards().iterator().next();
        return hand.getCards().stream()
                .skip(1)
                .noneMatch(c -> c.suite != first.suite);
    }

    private boolean isStraight(PokerHand hand) {
        var it = hand.getCards().iterator();
        return hand.getCards().stream()
                .skip(1)
                .noneMatch(c -> it.next().valueIndex() != c.valueIndex() - 1);
    }

    private Map<Character, Integer> groupByValue(PokerHand hand) {
        HashMap<Character, Integer> valueCounts = new HashMap<>(5);
        for (Card card : hand.getCards()) {
            valueCounts.merge(card.value, 1, (integer, integer2) -> integer + 1);
        }
        return valueCounts;
    }

    private Optional<Character> getKicker(Map<Character, Integer> groupedByValue) {
        Comparator<Map.Entry<Character, Integer>> comparator = Comparator.comparingInt(e -> Card.valueIndexFor(e.getKey()));
        return groupedByValue.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .max(comparator)
                .map(Map.Entry::getKey);
    }
}
