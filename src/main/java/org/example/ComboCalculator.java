package org.example;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ComboCalculator {

    public Combo calculateCombo(PokerHand hand) {
        var group = groupByValue(hand);
        if (group.containsValue(2)) {
            if (group.containsValue(3)) {
                return new Combo(ComboType.FULL_HOUSE, getTopOfCount(group, i -> i == 2 || i == 3), Collections.emptyList());
            }
            var countPairs = group.values().stream()
                    .filter(n -> n == 2)
                    .count();
            if (countPairs == 2) {
                return new Combo(ComboType.TWO_PAIRS, getTopOfCount(group, i -> i == 2), getKicker(group));
            }
            return new Combo(ComboType.PAIR, getTopOfCount(group, i -> i == 2), getKicker(group));
        }
        if (group.containsValue(3)) {
            return new Combo(ComboType.THREE_OF_A_KIND, getTopOfCount(group, i -> i == 3), getKicker(group));
        }
        boolean sameSuit = isSameSuit(hand);
        if (isStraight(hand)) {
            return sameSuit ? new Combo(ComboType.STRAIGHT_FLUSH, getTopCard(hand), Collections.emptyList())
                    : new Combo(ComboType.STRAIGHT, getTopCard(hand), Collections.emptyList());
        }
        if (sameSuit) {
            return new Combo(ComboType.FLUSH, getTopCard(hand), Collections.emptyList());
        }
        if (group.containsValue(4)) {
            return new Combo(ComboType.FOUR_OF_A_KIND, getTopOfCount(group, i -> i == 4), getKicker(group));
        }
        return new Combo(ComboType.HIGH_CARD, getTopCard(hand), getKicker(group));
    }

    private boolean isSameSuit(PokerHand hand) {
        Card first = hand.getCards().iterator().next();
        return hand.getCards().stream()
                .skip(1)
                .noneMatch(c -> c.suite() != first.suite());
    }

    private boolean isStraight(PokerHand hand) {
        var it = hand.getCards().iterator();
        return hand.getCards().stream()
                .skip(1)
                .noneMatch(c -> it.next().value().ordinal() != c.value().ordinal() + 1);
    }

    private Map<CardValue, Integer> groupByValue(PokerHand hand) {
        HashMap<CardValue, Integer> valueCounts = new HashMap<>(hand.getCards().size());
        for (Card card : hand.getCards()) {
            valueCounts.merge(card.value(), 1, Integer::sum);
        }
        return valueCounts;
    }

    private List<CardValue> getKicker(Map<CardValue, Integer> groupedByValue) {
        Comparator<Map.Entry<CardValue, Integer>> comparator = Comparator.comparingInt(e -> e.getKey().ordinal());
        return groupedByValue.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .sorted(comparator.reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private CardValue getTopCard(PokerHand hand) {
        return hand.getCards().stream()
                .findFirst()
                .map(Card::value)
                .orElseThrow(() -> new IllegalStateException("Empty card hand"));
    }

    private CardValue getTopOfCount(Map<CardValue, Integer> groupedByValue, Predicate<Integer> countPredicate) {
        Comparator<Map.Entry<CardValue, Integer>> comparator = Comparator.comparingInt(e -> e.getKey().ordinal());
        return groupedByValue.entrySet().stream()
                .filter(e -> countPredicate.test(e.getValue()))
                .min(comparator)
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException("Empty kicker list in hand"));
    }
}
