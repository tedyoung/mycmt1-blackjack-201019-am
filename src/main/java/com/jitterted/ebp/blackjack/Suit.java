package com.jitterted.ebp.blackjack;

// VALUE OBJECT
public enum Suit {
  HEARTS("♥", true),
  CLUBS("♣", false),
  DIAMONDS("♦", true),
  SPADES("♠", false);

  private final String symbol;
  private final boolean isRed;

  Suit(String symbol, boolean isRed) {
    this.symbol = symbol;
    this.isRed = isRed;
  }

  public String displaySymbol() {
    return symbol;
  }

  boolean isRed() {
    return isRed;
  }
}
