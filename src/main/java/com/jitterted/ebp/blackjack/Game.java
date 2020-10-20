package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

  private final Deck deck;

  private final List<Card> dealerHand = new ArrayList<>();
  private final List<Card> playerHand = new ArrayList<>();

  public static void main(String[] args) {
    displayWelcomeScreen();
    playGame();
    resetScreen();
  }

  private static void playGame() {
    Game game = new Game();
    game.initialDeal();
    game.play();
  }

  private static void resetScreen() {
    System.out.println(ansi().reset());
  }

  private static void displayWelcomeScreen() {
    System.out.println(ansi()
                           .bgBright(Ansi.Color.WHITE)
                           .eraseScreen()
                           .cursor(1, 1)
                           .fgGreen().a("Welcome to")
                           .fgRed().a(" Jitterted's")
                           .fgBlack().a(" BlackJack"));
  }

  public Game() {
    deck = new Deck();
  }

  public void initialDeal() {
    dealRoundOfCards();
    dealRoundOfCards();
  }

  private void dealRoundOfCards() {
    // deal first round of cards, players first
    dealOneCardToPlayer();
    dealOneCardToDealer();
  }

  private void dealOneCardToDealer() {
    dealerHand.add(deck.draw());
  }

  private void dealOneCardToPlayer() {
    playerHand.add(deck.draw());
  }

  public void play() {
    // get Player's decision: hit until they stand, then they're done (or they go bust)
    boolean playerBusted = false;
    playerBusted = playerTurn(playerBusted);

    dealerTurn(playerBusted);

    displayFinalGameState();

    displayGameOutcome(playerBusted);
  }

  private void displayGameOutcome(boolean playerBusted) {
    if (playerBusted) {
      System.out.println("You Busted, so you lose.  💸");
    } else if (handValueOf(dealerHand) > 21) {
      System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
    } else if (handValueOf(dealerHand) < handValueOf(playerHand)) {
      System.out.println("You beat the Dealer! 💵");
    } else if (handValueOf(dealerHand) == handValueOf(playerHand)) {
      System.out.println("Push: The house wins, you Lose. 💸");
    } else {
      System.out.println("You lost to the Dealer. 💸");
    }
  }

  private void dealerTurn(boolean playerBusted) {
    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>stand)
    if (!playerBusted) {
      while (handValueOf(dealerHand) <= 16) {
        dealOneCardToDealer();
      }
    }
  }

  private boolean playerTurn(boolean playerBusted) {
    while (!playerBusted) {
      displayGameState();
      String playerChoice = inputFromPlayer().toLowerCase();
      if (playerChoseStand(playerChoice)) {
        break;
      }
      if (playerChoseHit(playerChoice)) {
        dealOneCardToPlayer();
        playerBusted = isPlayerBusted(playerBusted);
      } else {
        System.out.println("You need to [H]it or [S]tand");
      }
    }
    return playerBusted;
  }

  private boolean playerChoseHit(String playerChoice) {
    return playerChoice.startsWith("h");
  }

  private boolean playerChoseStand(String playerChoice) {
    return playerChoice.startsWith("s");
  }

  private boolean isPlayerBusted(boolean playerBusted) {
    if (handValueOf(playerHand) > 21) {
      playerBusted = true;
    }
    return playerBusted;
  }

  public int handValueOf(List<Card> hand) {
    int handValue = rawValueOf(hand);

    // does the hand contain at least 1 Ace?
    boolean hasAce = hasAce(hand);

    // if the total hand value <= 11, then count the Ace as 11 by adding 10
    if (hasAce && handValue < 11) {
      handValue += 10;
    }

    return handValue;
  }

  private boolean hasAce(List<Card> hand) {
    return hand
        .stream()
        .anyMatch(card -> card.rankValue() == 1);
  }

  private int rawValueOf(List<Card> hand) {
    return hand
        .stream()
        .mapToInt(Card::rankValue)
        .sum();
  }

  private String inputFromPlayer() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private void displayGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    System.out.println(dealerHand.get(0).display()); // first card is Face Up

    // second card is the hole card, which is hidden
    displayBackOfCard();

    System.out.println();
    System.out.println("Player has: ");
    displayHand(playerHand);
    System.out.println(" (" + handValueOf(playerHand) + ")");
  }

  private void displayBackOfCard() {
    System.out.print(
        ansi()
            .cursorUp(7)
            .cursorRight(12)
            .a("┌─────────┐").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("└─────────┘"));
  }

  private void displayHand(List<Card> hand) {
    System.out.println(hand.stream()
                           .map(Card::display)
                           .collect(Collectors.joining(
                               ansi().cursorUp(6).cursorRight(1).toString())));
  }

  private void displayFinalGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    displayHand(dealerHand);
    System.out.println(" (" + handValueOf(dealerHand) + ")");

    System.out.println();
    System.out.println("Player has: ");
    displayHand(playerHand);
    System.out.println(" (" + handValueOf(playerHand) + ")");
  }
}
