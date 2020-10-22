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

}
