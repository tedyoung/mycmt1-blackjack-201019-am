package com.jitterted.ebp.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Hand {
  private final List<Card> cards = new ArrayList<>();

  public Hand(List<Card> cards) {
    this.cards.addAll(cards);
  }

  public Hand() {
  }

  void addCard(Card card) {
    cards.add(card);
  }

  private int value() {
    int handValue = cards
        .stream()
        .mapToInt(Card::rankValue)
        .sum();

    // does the hand contain at least 1 Ace?
    boolean hasAce = cards
        .stream()
        .anyMatch(card -> card.rankValue() == 1);

    // if the total hand value <= 11, then count the Ace as 11 by adding 10
    if (hasAce && handValue <= 11) {
      handValue += 10;
    }

    return handValue;
  }

  boolean isBusted() {
    return value() > 21;
  }

  void displayHand() {
    System.out.println(cards.stream()
                            .map(Card::display)
                            .collect(Collectors.joining(
                                     ansi().cursorUp(6).cursorRight(1).toString())));
  }

  boolean beats(Hand hand) {
    return hand.value() < value();
  }

  boolean pushes(Hand hand) {
    return hand.value() == value();
  }

  String displayForFirstCard() {
    return cards.get(0).display();
  }

  boolean shouldDealerHit() {
    return value() <= 16;
  }

  public String displayValue() {
    return String.valueOf(value());
  }

  public boolean valueEquals(int value) {
    return value() == value;
  }
}
