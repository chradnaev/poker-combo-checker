package org.example;

import com.google.common.base.Preconditions;

import java.util.*;
import java.util.function.Predicate;

public class ComboCalculator {

    public Combo calculateCombo(PokerHand hand) {
        boolean sameSuit = isSameSuit(hand);
        if (isStraight(hand)) {
            return sameSuit ? new Combo(ComboType.STRAIGHT_FLUSH, null)
                    : new Combo(ComboType.STRAIGHT, null);
        }
        List<Card> pairs = getPairs(hand);
        if (pairs.size() == 2) {
            return new Combo(ComboType.TWO_PAIRS, getKicker(hand,
                    c -> c.value != pairs.get(0).value && c.value != pairs.get(1).value));
        } else if (pairs.size() == 1) {
            if (getThreeOfAKind(hand).isPresent()) {
                return new Combo(ComboType.FULL_HOUSE, null);
            } else {
                return new Combo(ComboType.PAIR, getKicker(hand, c -> c.value != pairs.get(0).value));
            }
        } else {
            Preconditions.checkState(pairs.size() == 0);
            return new Combo(ComboType.HIGH_CARD, hand.getAt(PokerHand.HAND_CARD_COUNT - 1));
        }
    }

    private boolean isSameSuit(PokerHand hand) {
        return hand.getCards().stream()
                .skip(1)
                .filter(c -> c.suite == hand.getAt(0).suite)
                .count() == hand.getCards().size();
    }

    private boolean isStraight(PokerHand hand) {
        for (int index = 1; index < PokerHand.HAND_CARD_COUNT; ++index) {
            if (hand.getAt(index).value - hand.getAt(index - 1).value != 1) {
                return false;
            }
        }
        return true;
    }

    private List<Card> getPairs(PokerHand hand) {
        List<Card> pairs = new ArrayList<>(2);
        for (int index = 1; index < PokerHand.HAND_CARD_COUNT; ++index) {
            if (hand.getAt(index).value == hand.getAt(index - 1).value) {
                pairs.add(hand.getAt(index - 1));
            }
        }
        return pairs;
    }

    private Optional<Card> getThreeOfAKind(PokerHand hand) {
        int start = 0;
        for (int index = 1; index < PokerHand.HAND_CARD_COUNT; ++index) {
            if (hand.getAt(index).value != hand.getAt(index - 1).value) {
                start = index;
            } else if (index - start + 1 == 3) {
                return Optional.of(hand.getAt(index));
            }
        }
        return Optional.empty();
    }

    private Card getKicker(PokerHand hand, Predicate<Card> predicate) {
        return hand.getCards().stream()
                .filter(predicate)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("All cards are part of combo"));
    }
}
