package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HandBustedTest {

  private static final Suit DUMMY_SUIT = Suit.HEARTS;

  @Test
  public void handWithCardsValuedAt21IsNotBusted() throws Exception {
    Hand hand = createHand("Q", "2", "9");

    assertThat(hand.isBusted())
        .isFalse();
  }

  @Test
  public void handWithCardsValuedAt22IsBusted() throws Exception {
    Hand hand = createHand("Q", "3", "9");

    assertThat(hand.isBusted())
        .isTrue();

  }

  private Hand createHand(String... ranks) {
    List<Card> cards = new ArrayList<>();
    for (String rank : ranks) {
      cards.add(new Card(DUMMY_SUIT, rank));
    }
    return new Hand(cards);
  }

}
