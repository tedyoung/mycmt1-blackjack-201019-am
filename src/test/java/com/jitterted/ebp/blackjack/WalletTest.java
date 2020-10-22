package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WalletTest {

  @Test
  public void newWalletIsEmpty() throws Exception {
    Wallet wallet = new Wallet();

    assertThat(wallet.isEmpty())
        .isTrue();
  }

  @Test
  public void addMoneyToNewWalletIsNotEmpty() throws Exception {
    Wallet wallet = new Wallet();

    wallet.addMoney(10);

    assertThat(wallet.isEmpty())
        .isFalse();
  }

  @Test
  public void newWalletHasZeroBalance() throws Exception {
    Wallet wallet = new Wallet();

    assertThat(wallet.balance())
        .isZero();
  }

  @Test
  public void addMoneyOf10BalanceIs10() throws Exception {
    Wallet wallet = new Wallet();

    wallet.addMoney(10);

    assertThat(wallet.balance())
        .isEqualTo(10);
  }

  @Test
  public void addMoneyOf9And8BalanceIs17() throws Exception {
    Wallet wallet = new Wallet();

    wallet.addMoney(9);
    wallet.addMoney(8);

    assertThat(wallet.balance())
        .isEqualTo(9 + 8);
  }

  @Test
  public void addMoneyOf0ThrowsIllegalArgumentException() throws Exception {
    Wallet wallet = new Wallet();

    assertThatThrownBy(() -> {
      wallet.addMoney(0);
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void walletWith25Bet13BalanceIs12() throws Exception {
    // Given
    Wallet wallet = new Wallet();
    wallet.addMoney(25);

    // When
    wallet.bet(13);

    // Then
    assertThat(wallet.balance())
        .isEqualTo(25 - 13);
  }

  @Test
  public void walletWith44Bet11AndBet12BalanceIs21() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(44);

    wallet.bet(11);
    wallet.bet(12);

    assertThat(wallet.balance())
        .isEqualTo(44 - 11 - 12);
  }

  @Test
  public void walletWith87Bet87IsEmpty() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(87);

    wallet.bet(87);

    assertThat(wallet.isEmpty())
        .isTrue();
  }

  @Test
  public void betMoreThanBalanceThrowsException() throws Exception {
    Wallet wallet = new Wallet();
    wallet.addMoney(40);

    assertThatThrownBy(() -> {
      wallet.bet(41);
    }).isInstanceOf(IllegalArgumentException.class);
  }

}
