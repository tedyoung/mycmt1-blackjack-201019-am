package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameBettingTest {

  @Test
  public void newGamePlayerBalanceIs0() throws Exception {
    Game game = new Game();

    assertThat(game.playerBalance())
        .isZero();
  }

  @Test
  public void playerDeposits100BalanceIs100() throws Exception {
    Game game = new Game();

    game.playerDeposits(100);

    assertThat(game.playerBalance())
        .isEqualTo(100);
  }

  @Test
  public void playerWith100Best75BalanceIs25() throws Exception {
    Game game = new Game();
    game.playerDeposits(100);

    game.playerBets(75);

    assertThat(game.playerBalance())
        .isEqualTo(25);
  }

  @Test
  public void playerWith100Bets75WhenWinBalanceIs175() throws Exception {
    Game game = new Game();
    game.playerDeposits(100);
    game.playerBets(75);

    game.playerWins();

    assertThat(game.playerBalance())
        .isEqualTo(175);
  }

}